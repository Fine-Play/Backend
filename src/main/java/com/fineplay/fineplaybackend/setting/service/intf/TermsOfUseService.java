package com.fineplay.fineplaybackend.setting.service.intf;

import com.fineplay.fineplaybackend.setting.dto.response.TermsOfUseResponseDto;
import org.springframework.http.ResponseEntity;

public interface TermsOfUseService {
    ResponseEntity<TermsOfUseResponseDto> getTermsOfUse();
}
