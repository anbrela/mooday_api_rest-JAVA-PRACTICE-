package com.mooday.modrest.userConfig;

import com.mooday.modrest.auth.User;
import com.mooday.modrest.security.JwtUtil;
import com.mooday.modrest.tag.Tag;
import com.mooday.modrest.tag.TagRepository;
import com.mooday.modrest.userConfig.domain.UserConfigDomain;
import com.mooday.modrest.userConfig.dto.CreateUserConfigDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserConfigService {

    @Autowired
    private UserConfigRepository userConfigRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TagRepository tagRepository;

    public UserConfigDomain createUserConfig(User user, CreateUserConfigDto userConfigDto) {
        try {
        //check if user has a config
            if(user  == null) {
                throw new IllegalArgumentException("User not found");
            }

            //check if user has a config
            UserConfig userConfig = user.getUserConfig();
            if (userConfig != null) {
                //update the existing one
                userConfig.setCheckTime(ZonedDateTime.parse(userConfigDto.getCheckTime()));
                Set<Tag> tags = new HashSet<>();
                for (Long tagId : userConfigDto.getTagIds()) {
                    Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new IllegalArgumentException("Invalid tag ID"));
                    tags.add(tag);
                }
                userConfig.setTags(tags);
                userConfigRepository.save(userConfig);
                return new UserConfigDomain(
                        userConfig.getId(),
                        userConfig.getCheckTime(),
                        user.getId(),
                        userConfig.getTags()
                );
        }

            //if null create a new one
        userConfig = new UserConfig();
            //set default 21:00 pm doesnt has userConfigDto
            userConfig.setCheckTime(
                    ZonedDateTime.now()
                            .withHour(21)
                            .withMinute(0)
                            .withSecond(0)
                            .withNano(0)
            );
        userConfig.setUser(user);
        user.setUserConfig(userConfig);
            if (userConfigDto.getTagIds() != null && !userConfigDto.getTagIds().isEmpty()) {
                Set<Tag> tags = new HashSet<>();
                for (Long tagId : userConfigDto.getTagIds()) {
                    Tag tag = tagRepository.findById(tagId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid tag ID"));
                    tags.add(tag);
                }
                userConfig.setTags(tags);
            } else {
                userConfig.setTags(new HashSet<>()); // Inicializar con un set vacío
            }

        return new UserConfigDomain(
                userConfig.getId(),
                userConfig.getCheckTime(),
                user.getId(),
                userConfig.getTags()
        );
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }


    public UserConfigDomain getUserConfig(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        User user = jwtUtil.getUserFromToken(token);
        UserConfig userConfig = user.getUserConfig();

        if (userConfig == null) {
            throw new RuntimeException("User config not found for user ID: " + user.getId());
        }

        UserConfigDomain userConfigDomain = new UserConfigDomain(
                userConfig.getId(),
                userConfig.getCheckTime(),
                user.getId(),
                userConfig.getTags()
        );

        return userConfigDomain;
    }
}
