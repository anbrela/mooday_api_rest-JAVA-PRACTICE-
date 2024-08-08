package com.mooday.modrest.userConfig;

import com.mooday.modrest.auth.User;
import com.mooday.modrest.security.JwtTokenUtil;
import com.mooday.modrest.userConfig.dto.CreateUserConfigDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-config")
public class UserConfigController {

    @Autowired
    private UserConfigService userConfigService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping
    public ResponseEntity<Void> createUserConfig(@Valid @RequestBody CreateUserConfigDto userConfigDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        User user = jwtTokenUtil.getUserFromToken(token);
        userConfigService.createUserConfig(user, userConfigDto);
        return ResponseEntity.ok().build();
    }
}
