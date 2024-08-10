package com.mooday.modrest.dayRate;

import com.mooday.modrest.dayRate.dto.CreateDayRateDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mooday.modrest.service.DayRateService;

@RestController
@RequestMapping("/api/day-rate")
public class DayRateController {


    @Autowired
    private DayRateService dayRateService;

    @PostMapping
    public ResponseEntity<DayRate> createRate(@Valid @RequestBody CreateDayRateDto createDayRateDto) {
       DayRate dayRate =  dayRateService.createDayRate(createDayRateDto);
        return ResponseEntity.ok(dayRate);
    }

    //Get one day

    //get all days startDate and endDate

    //delete day

}
