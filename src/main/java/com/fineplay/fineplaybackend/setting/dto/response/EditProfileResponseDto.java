package com.fineplay.fineplaybackend.setting.dto.response;

import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditProfileResponseDto {
    private String message;
    private String errorCode;

    public static EditProfileResponseDto success() {
        return new EditProfileResponseDto("프로필이 성공적으로 수정되었습니다.", null);
    }

    public static EditProfileResponseDto error(String message, String errorCode) {
        return new EditProfileResponseDto(message, errorCode);
    }
}
