package com.mooday.modrest.userConfig.domain;

import com.mooday.modrest.tag.Tag;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Data
public class UserConfigDomain {
    private Long id;
    private ZonedDateTime checkTime;
    private Long userId;
    private Set<Tag> tags;


    public UserConfigDomain(Long id, ZonedDateTime checkTime, Long userId, Set<Tag> tagIds) {
        this.id = id;
        this.checkTime = checkTime;
        this.userId = userId;
        this.tags = tagIds;
    }
}
