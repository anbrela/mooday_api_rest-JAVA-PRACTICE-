package com.mooday.modrest.userConfig;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mooday.modrest.auth.User;
import com.mooday.modrest.tag.Tag;
import jakarta.persistence.*;
import lombok.Data;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "user_config")
@Data
public class UserConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ZonedDateTime checkTime;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
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
