package com.example.linkcargo.domain.schedule;

import com.example.linkcargo.domain.port.Port;
import com.example.linkcargo.domain.port.PortRepository;
import com.example.linkcargo.domain.schedule.dto.request.ScheduleCreateRequest;
import com.example.linkcargo.global.response.code.resultCode.ErrorStatus;
import com.example.linkcargo.global.response.exception.handler.PortHandler;
import com.example.linkcargo.global.response.exception.handler.ScheduleHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PortRepository portRepository;

    public Long createSchedule(ScheduleCreateRequest request) {

        // 이미 존재하는 스케줄인지 확인
        if (scheduleRepository.existsByCarrierAndETDAndETAAndTransportType(
                request.carrier(),
                request.ETD(),
                request.ETA(),
                request.transportType())) {
            throw new ScheduleHandler(ErrorStatus.SCHEDULE_ALREADY_EXISTS);
        }

        Port exportPort = portRepository.findById(request.exportPortId())
                .orElseThrow(() -> new PortHandler(ErrorStatus.EXPORT_PORT_NOT_FOUND));
        Port importPort = portRepository.findById(request.importPortId())
                .orElseThrow(() -> new PortHandler(ErrorStatus.IMPORT_PORT_NOT_FOUND));

        Schedule schedule = request.toEntity(exportPort, importPort);

        try {
            Schedule savedSchedule = scheduleRepository.save(schedule);
            return savedSchedule.getId();
        } catch (Exception e) {
            throw new ScheduleHandler(ErrorStatus.SCHEDULE_CREATED_FAIL);
        }
    }
}
