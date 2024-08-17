package com.mooday.modrest.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import com.mooday.modrest.security.JwtUtil;
import com.mooday.modrest.userConfig.UserConfigRepository;
import com.mooday.modrest.userConfig.UserConfigService;
import com.mooday.modrest.userConfig.dto.CreateUserConfigDto;
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
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserConfigService userConfigService;

    @Autowired
    private UserService userService;

    @PostMapping("/google")
    public ResponseEntity<?> authenticateGoogleUser(HttpServletRequest request) {

        String googleToken = request.getHeader("Authorization").substring(7);
        try {
            GoogleIdToken.Payload payload = googleAuthService.verifyToken(googleToken);
            User user = userService.getOrCreateUser(payload);
            //7 days expiration
            String token = jwtTokenUtil.createToken(user);

            //get user config if not exists create one
            CreateUserConfigDto dto = CreateUserConfigDto.builder()
                    .checkTime("21:00")
                    .build();
            if(user.getUserConfig() == null) {
                userConfigService.createUserConfig(user, dto);
            }

            return ResponseEntity.ok(Map.of(  "user", user, "token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Google ID token");
        }
    }

}
