package com.mooday.modrest.dayRate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DayRateRepository extends JpaRepository<DayRate, Long> {

    Optional<DayRate> findByDate(String date);
}
