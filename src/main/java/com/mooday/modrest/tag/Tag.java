package com.mooday.modrest.tag;

import com.mooday.modrest.userConfig.UserConfig;
import jakarta.persistence.*;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;


@Entity
@Data
@Table(name = "tags", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is mandatory")
    @Column(unique = true, nullable = false)
    private String name;

    @NotEmpty(message = "Language is mandatory")
    private String lang;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int usageCount = 0;

    @ManyToMany(mappedBy = "tags")
    private Set<UserConfig> userConfigs;
}
