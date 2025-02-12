package com.fineplay.fineplaybackend.setting.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor  // 기본 생성자 추가 (빈 객체 생성 가능)
@AllArgsConstructor // 매개변수 있는 생성자 추가
public class EditPasswordResponseDto {
    private String message;
    private String errCode;

    // ✅ 성공 응답을 위한 static 메서드
    public static EditPasswordResponseDto success() {
        return new EditPasswordResponseDto("비밀번호가 성공적으로 변경되었습니다.", null);
    }

    // ✅ 오류 응답을 위한 static 메서드
    public static EditPasswordResponseDto error(String message, String errCode) {
        return new EditPasswordResponseDto(message, errCode);
    }
}
