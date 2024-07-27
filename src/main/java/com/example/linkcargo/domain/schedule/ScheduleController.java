package com.example.linkcargo.domain.schedule;

import com.example.linkcargo.domain.schedule.dto.request.ScheduleCreateRequest;
import com.example.linkcargo.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
@Tag(name = "3. Schedule", description = "선박 정보 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    @Operation(summary = "선박 정보 생성 ", description = "선박 정보를 생성 합니다. ScheduleCreateRequest 사용")
    @PostMapping("")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SCHEDULE401",description = "이미 존재하는 선박정보 입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SCHEDULE402",description = "선박정보 생성에 실패하였습니다", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<Long> createSchedule(@RequestBody ScheduleCreateRequest request) {
        Long resultId = scheduleService.createSchedule(request);
        return ApiResponse.onSuccess(resultId);
    }
}
