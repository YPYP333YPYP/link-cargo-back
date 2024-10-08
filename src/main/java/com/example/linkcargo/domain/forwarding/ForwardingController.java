package com.example.linkcargo.domain.forwarding;

import com.example.linkcargo.domain.forwarding.dto.request.ForwardingCreateUpdateRequest;
import com.example.linkcargo.domain.forwarding.dto.response.ForwardingInfoResponse;
import com.example.linkcargo.global.response.ApiResponse;
import com.example.linkcargo.global.response.code.resultCode.SuccessStatus;
import com.example.linkcargo.global.security.CustomUserDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "7. Forwarding", description = "포워딩 업체 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/forwardings")
public class ForwardingController {

    private final ForwardingService forwardingService;

    @Operation(summary = "포워딩 업체 생성", description = "포워딩 업체를 생성합니다. ForwardingCreateUpdateRequest 사용")
    @PostMapping("")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FORWARDING401", description = "이미 존재하는 포워딩 업체 입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FORWARDING402", description = "포워딩 업체 생성에 실패했습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<Long> createForwarding(
        @AuthenticationPrincipal CustomUserDetail userDetail,
        @RequestBody ForwardingCreateUpdateRequest request)
    {
        Long resultId = forwardingService.createForwarding(request);
        return ApiResponse.onSuccess(resultId);
    }

    @Operation(summary = "포워딩 업체 단일 조회 ", description = "포워딩 업체 아이디에 따라 포워딩 업체를 조회 합니다. ForwardingInfoResponse 사용")
    @GetMapping("/{forwardingId}")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FORWARDING403", description = "포워딩 업체가 존재 하지 않습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<ForwardingInfoResponse> findForwarding(
        @AuthenticationPrincipal CustomUserDetail userDetail,
        @Parameter(description = "포워딩 업체 아이디") @PathVariable("forwardingId") Long forwardingId) {
        ForwardingInfoResponse forwardingInfoResponse = forwardingService.findForwarding(forwardingId);
        return ApiResponse.onSuccess(forwardingInfoResponse);
    }

    @Operation(summary = "포워딩 업체 변경 ", description = "포워딩 업체를 변경합니다. ForwardingCreateUpdateRequest 사용 ")
    @PutMapping("/{forwardingId}")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FORWARDING403", description = "포워딩 업체가 존재 하지 않습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FORWARDING404", description = "포워딩 업체 변경에 실패했습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<SuccessStatus> modifyForwarding(
        @AuthenticationPrincipal CustomUserDetail userDetail,
        @Parameter(description = "포워딩 업체 아이디") @PathVariable Long forwardingId,
        @RequestBody ForwardingCreateUpdateRequest request
    ) {
        forwardingService.modifyForwarding(forwardingId, request);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }

    @Operation(summary = "포워딩 업체 삭제", description = "포워딩 업체를 삭제합니다.")
    @DeleteMapping("/{forwardingId}")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FORWARDING403", description = "포워딩 업체가 존재 하지 않습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FORWARDING405", description = "포워딩 업체 삭제에 실패했습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<SuccessStatus> removeForwarding(
        @AuthenticationPrincipal CustomUserDetail userDetail,
        @Parameter(description = "포워딩 업체 아이디") @PathVariable Long forwardingId
    ) {
        forwardingService.removeForwarding(forwardingId);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }

    @Operation(summary = "포워더 포워딩 업체 등록", description = "포워더가 자신이 속한 포워딩 업체를 지정합니다..")
    @PatchMapping("/users/{forwardingId}")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER403", description = "유저가 존재 하지 않습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FORWARDING403", description = "포워딩 업체가 존재 하지 않습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<SuccessStatus> selectForwarding(
        @AuthenticationPrincipal CustomUserDetail userDetail,
        @Parameter(description = "포워딩 업체 아이디") @PathVariable Long forwardingId
    ) {
        forwardingService.selectForwarding(forwardingId, userDetail.getId());
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }
}

