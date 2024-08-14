package com.mooday.modrest.service;

import com.mooday.modrest.dayRate.DayRate;
import com.mooday.modrest.dayRate.DayRateRepository;
import com.mooday.modrest.dayRate.domain.DayRateDomain;
import com.mooday.modrest.dayRate.dto.CreateDayRateDto;
import com.mooday.modrest.dayRate.mapper.DayRateMapper;
import com.mooday.modrest.security.JwtUtil;
import com.mooday.modrest.tag.Tag;
import com.mooday.modrest.tag.TagRepository;
import io.opencensus.tags.Tags;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DayRateService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private DayRateMapper dayRateMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private DayRateRepository dayRateRepository;

    @Autowired
    private TagRepository tagRepository;

    public DayRate createDayRate(CreateDayRateDto createDayRateDto, HttpServletRequest request) {
        Long userId = jwtUtil.getUserFromToken(request.getHeader("Authorization").substring(7)).getId();

        DayRate dayRate = dayRateMapper.createDayRateDtoToDayRate(createDayRateDto);
        String formattedDate = LocalDate.now().format(DATE_FORMATTER);
        Optional<DayRate> existingDayRateOptional = dayRateRepository.findByDateAndUserId(formattedDate, userId);

        Set<Tag> managedTags = dayRate.getTags().stream()
                .map(tag -> tagRepository.findById(tag.getId())
                        .orElseThrow(() -> new RuntimeException("Tag no encontrado")))
                .collect(Collectors.toSet());
        dayRate.setTags(managedTags);

        if (existingDayRateOptional.isPresent()) {
            DayRate existingDayRate = existingDayRateOptional.get();
            existingDayRate.setRate(dayRate.getRate());
            existingDayRate.setComment(dayRate.getComment());
            existingDayRate.setTags(managedTags);

            return dayRateRepository.save(existingDayRate);
        }

        dayRate.setDate(formattedDate);
        dayRate.setUserId(userId);

        return dayRateRepository.save(dayRate);
    }

    public List<DayRate> getAllDayRates(
            String endDate,
            String startDate,
            HttpServletRequest request
    ) {

        Long userId = jwtUtil.getUserFromToken(request.getHeader("Authorization").substring(7)).getId();
        return dayRateRepository.findAllByUserIdAndDateBetweenOrderByDateDesc(userId, startDate, endDate);

    }

    public Optional<DayRateDomain> getDayRate(String date, HttpServletRequest request ) {
        Long userId = jwtUtil.getUserFromToken(request.getHeader("Authorization").substring(7)).getId();

        DayRate dayRate = dayRateRepository.findByDateAndUserId(date, userId).orElseThrow();
        if(dayRate == null) {
            return Optional.ofNullable(null);
        }

        DayRateDomain dayRateDomain = new DayRateDomain(dayRate);


        return Optional.of(dayRateDomain);

    }

    public List<DayRate> getLast7Days(HttpServletRequest request) {
        Long userId = jwtUtil.getUserFromToken(request.getHeader("Authorization").substring(7)).getId();
        return dayRateRepository.findAllByUserIdAndDateBetweenOrderByDateDesc(userId, LocalDate.now().minusDays(7).format(DATE_FORMATTER), LocalDate.now().format(DATE_FORMATTER));
    }

    public void deleteDayRate(Long id) {
        dayRateRepository.deleteById(id);
    }
}
