package com.mooday.modrest.tag;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    List<Tag> findByNameContainingIgnoreCaseAndLangOrderByUsageCountDesc(String name, String lang);

    List<Tag>findAllByLangOrderByUsageCountDesc(String lang);

    List<Tag> findAllByUserIdAndLangOrderByUsageCountDesc(Long userId, String lang);

    List<Tag> findByUserIdAndNameContainingIgnoreCaseAndLangOrderByUsageCountDesc(Long userId, String name, String lang);
}
