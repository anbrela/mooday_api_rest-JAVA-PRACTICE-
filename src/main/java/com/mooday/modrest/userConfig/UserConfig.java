package com.mooday.modrest.userConfig;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mooday.modrest.auth.User;
import com.mooday.modrest.tag.Tag;
import jakarta.persistence.*;
import lombok.Data;

import jakarta.validation.constraints.NotNull;


import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "user_config")
public class UserConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull(message = "Check time is mandatory")
    private ZonedDateTime checkTime;


    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonManagedReference
    private User user;


    @ManyToMany
    @JoinTable(
            name = "user_config_tags",
            joinColumns = @JoinColumn(name = "user_config_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;


    @PrePersist
    public void prePersist() {
        if (checkTime == null) {
            checkTime = ZonedDateTime.now().withHour(21).withMinute(0).withSecond(0).withNano(0);
        }
    }

}
