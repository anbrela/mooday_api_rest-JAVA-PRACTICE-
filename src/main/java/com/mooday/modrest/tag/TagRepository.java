package com.mooday.modrest.tag;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByNameContainingIgnoreCaseAndLangOrderByUsageCountDesc(String name, String lang);

    List<Tag>findAllByLangOrderByUsageCountDesc(String lang);
}
