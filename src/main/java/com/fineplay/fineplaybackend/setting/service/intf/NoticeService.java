package com.fineplay.fineplaybackend.setting.service.intf;

import com.fineplay.fineplaybackend.setting.dto.response.NoticeResponseDto;
import org.springframework.http.ResponseEntity;

public interface NoticeService {
    ResponseEntity<NoticeResponseDto> getNotice();
}
