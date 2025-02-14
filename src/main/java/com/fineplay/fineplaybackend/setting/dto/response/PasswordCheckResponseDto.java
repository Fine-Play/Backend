package com.fineplay.fineplaybackend.setting.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor  // 기본 생성자 추가 (빈 객체 생성 가능)
@AllArgsConstructor // 매개변수 있는 생성자 추가
public class PasswordCheckResponseDto {
    private boolean isPasswordCorrect;
    private String errCode;

    // ✅ 비밀번호 확인 성공 응답을 위한 static 메서드
    public static PasswordCheckResponseDto success() {
        return new PasswordCheckResponseDto(true, null);
    }

    // ✅ 비밀번호 확인 실패 응답을 위한 static 메서드
    public static PasswordCheckResponseDto error(String errCode) {
        return new PasswordCheckResponseDto(false, errCode);
    }
}
