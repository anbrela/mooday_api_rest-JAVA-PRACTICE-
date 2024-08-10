package com.mooday.modrest.userConfig;

import com.mooday.modrest.auth.User;
import com.mooday.modrest.auth.UserRepository;
import com.mooday.modrest.security.JwtUtil;
import com.mooday.modrest.userConfig.dto.CreateUserConfigDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserConfigController {

    @Autowired
    private UserConfigService userConfigService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(path = "/user-config")
    public ResponseEntity<UserConfig> createUserConfig(@RequestBody final @Valid CreateUserConfigDto userConfigDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        User user = jwtUtil.getUserFromToken(token);

        try {
           UserConfig savedUserConfig = userConfigService.createUserConfig(user, userConfigDto);
            return ResponseEntity.ok().body(savedUserConfig);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }
}
