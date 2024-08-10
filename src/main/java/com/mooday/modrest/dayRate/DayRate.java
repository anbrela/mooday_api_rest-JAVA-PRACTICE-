package com.mooday.modrest.dayRate;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mooday.modrest.tag.Tag;
import jakarta.persistence.*;
import lombok.Data;

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

    @ManyToMany
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
