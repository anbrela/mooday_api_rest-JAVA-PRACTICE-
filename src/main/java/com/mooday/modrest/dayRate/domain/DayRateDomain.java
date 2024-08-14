package com.mooday.modrest.dayRate.domain;


import com.mooday.modrest.dayRate.DayRate;
import com.mooday.modrest.tag.Tag;
import lombok.Data;

import java.util.Set;


@Data
public class DayRateDomain {
    private Long id;
    private String date;
    private Double rate;
    private Long userId;
    private String comment;
    private Set<Tag> tags;

    public DayRateDomain(DayRate dayRate) {
        this.id = dayRate.getId();
        this.date = dayRate.getDate();
        this.rate = dayRate.getRate();
        this.comment = dayRate.getComment();
        this.tags = dayRate.getTags();
    }

}
