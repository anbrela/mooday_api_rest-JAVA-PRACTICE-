package com.mooday.modrest.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import com.mooday.modrest.security.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private GoogleAuthService googleAuthService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/google")
    public ResponseEntity<?> authenticateGoogleUser(HttpServletRequest request) {

        String googleToken = request.getHeader("Authorization").substring(7);
        try {
            GoogleIdToken.Payload payload = googleAuthService.verifyToken(googleToken);
            User user = userService.getOrCreateUser(payload);
            //7 days expiration
            String token = jwtTokenUtil.generateTokenFromUser(user, new Date(System.currentTimeMillis() + 604800000));
            //revisar el mapeo por la referencia ciclica
            return ResponseEntity.ok(Map.of(  "user", user, "token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Google ID token");
        }
    }

}
