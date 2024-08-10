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
        try {
        //check if user has a config
            if(user  == null) {
                throw new IllegalArgumentException("User not found");
            }


            //check if user has a config
            UserConfig userConfig = user.getUserConfig();




            if (userConfig != null) {
            throw new IllegalArgumentException("User already has a configuration");
        }


            //if null create a new one
        userConfig = new UserConfig();
        userConfig.setCheckTime(ZonedDateTime.parse(userConfigDto.getCheckTime()));
        userConfig.setUser(user);
        user.setUserConfig(userConfig);
        Set<Tag> tags = new HashSet<>();
        for (Long tagId : userConfigDto.getTagIds()) {
            Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new IllegalArgumentException("Invalid tag ID"));
            tags.add(tag);
        }
        userConfig.setTags(tags);

       return  userConfigRepository.save(userConfig);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
