package com.mooday.modrest.dayRate;

import com.mooday.modrest.auth.User;
import com.mooday.modrest.dayRate.domain.DayRateDomain;
import com.mooday.modrest.dayRate.dto.CreateDayRateDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mooday.modrest.service.DayRateService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/day-rate")
public class DayRateController {


    @Autowired
    private DayRateService dayRateService;

    @PostMapping
    public ResponseEntity<DayRate> createRate(@Valid @RequestBody CreateDayRateDto createDayRateDto, HttpServletRequest request) {
       DayRate dayRate =  dayRateService.createDayRate(createDayRateDto, request);
       return ResponseEntity.ok(dayRate);
    }

    //Get one day
    @GetMapping("/{date}")
    public Optional<DayRateDomain> getDayRate(@PathVariable String date, HttpServletRequest request) {
        return dayRateService.getDayRate(date, request);
    }

    //get all days by user startDate and endDate
    @GetMapping
    public List<DayRate> getDayRate(@RequestParam String startDate, @RequestParam String endDate, HttpServletRequest request) {
        return dayRateService.getAllDayRates(endDate, startDate, request);
    }



    //get last 7 days
    @GetMapping("/last")
    public List<DayRateDomain> getLast7Days(HttpServletRequest request) {
        return dayRateService.getLast7Days(request);
    }


}
