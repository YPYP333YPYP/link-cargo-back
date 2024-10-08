package com.example.linkcargo.domain.schedule.dto.response;

import com.example.linkcargo.domain.schedule.Schedule;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record ScheduleListResponse(
    List<ScheduleInfoResponse> schedules,
    int currentPage,
    int totalPages,
    long totalElements
) {
    public static ScheduleListResponse fromEntity(Page<Schedule> schedulePage, List<String> imageUrls) {
        List<ScheduleInfoResponse> scheduleResponses = IntStream.range(0, schedulePage.getContent().size())
            .mapToObj(i -> {
                Schedule schedule = schedulePage.getContent().get(i);
                String imageUrl = imageUrls.get(i % imageUrls.size());
                return ScheduleInfoResponse.fromEntity(schedule, imageUrl);
            })
            .collect(Collectors.toList());

        return ScheduleListResponse.builder()
            .schedules(scheduleResponses)
            .currentPage(schedulePage.getNumber())
            .totalPages(schedulePage.getTotalPages())
            .totalElements(schedulePage.getTotalElements())
            .build();
    }
}
