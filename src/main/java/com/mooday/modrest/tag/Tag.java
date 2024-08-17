package com.mooday.modrest.tag;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tags", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String lang;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int usageCount = 0;

    @Column(name = "user_id")
    private Long userId;

}
