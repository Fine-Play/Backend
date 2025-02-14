package com.fineplay.fineplaybackend.setting.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor // 매개변수 있는 생성자 추가
public class AlarmResponseDto {
    private String message;
    private String errCode;


    // ✅ 성공 응답을 위한 static 메서드
    public static AlarmResponseDto success() {
        return new AlarmResponseDto("알림 설정이 성공적으로 변경되었습니다.", null);
    }

    // ✅ 오류 응답을 위한 static 메서드
    public static AlarmResponseDto error(String errCode) {
        return new AlarmResponseDto("알림 설정 변경에 실패했습니다.", errCode);
    }

}