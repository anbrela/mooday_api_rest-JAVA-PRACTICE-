package com.mooday.modrest.userConfig;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConfigRepository extends CrudRepository<UserConfig, Long> {
    UserConfig findByUserId(Long userId);
}