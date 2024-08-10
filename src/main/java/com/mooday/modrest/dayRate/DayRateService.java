package com.mooday.modrest.service;

import com.mooday.modrest.dayRate.DayRate;
import com.mooday.modrest.dayRate.DayRateRepository;
import com.mooday.modrest.dayRate.dto.CreateDayRateDto;
import com.mooday.modrest.dayRate.mapper.DayRateMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DayRateService {


    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Autowired
    private DayRateMapper dayRateMapper;

    @Autowired
    private DayRateRepository dayRateRepository;

    public DayRate createDayRate(CreateDayRateDto createDayRateDto) {
        DayRate dayRate = dayRateMapper.createDayRateDtoToDayRate(createDayRateDto);
        String formattedDate = LocalDate.now().format(DATE_FORMATTER);
        Optional<DayRate> existingDayRateOptional = dayRateRepository.findByDate(formattedDate);

        if (existingDayRateOptional.isPresent()) {
            DayRate existingDayRate = existingDayRateOptional.get();

            existingDayRate.setRate(dayRate.getRate());
            existingDayRate.setComment(dayRate.getComment());

            return dayRateRepository.save(existingDayRate);
        }

        dayRate.setDate(formattedDate);

        return dayRateRepository.save(dayRate);
    }

    public List<DayRate> getAllDayRates() {
        return dayRateRepository.findAll();
    }

    public DayRate getDayRateById(Long id) {
        return dayRateRepository.findById(id).orElse(null);
    }

    public void deleteDayRate(Long id) {
        dayRateRepository.deleteById(id);
    }
}
