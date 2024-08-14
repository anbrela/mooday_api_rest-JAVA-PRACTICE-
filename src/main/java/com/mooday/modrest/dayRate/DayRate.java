package com.mooday.modrest.dayRate;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mooday.modrest.auth.User;
import com.mooday.modrest.tag.Tag;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "day_rate")
public class DayRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String date;

    private Double rate;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "day_rate_tags",
            joinColumns = @JoinColumn(name = "day_rate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonBackReference
    private Set<Tag> tags;

    @Column(nullable = true)
    private String comment;
}
