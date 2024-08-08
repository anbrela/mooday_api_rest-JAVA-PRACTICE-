package com.mooday.modrest.userConfig;

import com.mooday.modrest.auth.User;
import com.mooday.modrest.tag.Tag;
import com.mooday.modrest.tag.TagRepository;
import com.mooday.modrest.userConfig.dto.CreateUserConfigDto;
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
    private TagRepository tagRepository;

    public UserConfig createUserConfig(User user, CreateUserConfigDto userConfigDto) {
        UserConfig userConfig = new UserConfig();

        userConfig.setCheckTime(ZonedDateTime.parse(userConfigDto.getCheckTime()));
        userConfig.setUser(user);
        Set<Tag> tags = new HashSet<>(tagRepository.findAllById(userConfigDto.getTagIds()));
        userConfig.setTags(tags);

        return userConfigRepository.save(userConfig);
    }
}
