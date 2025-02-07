package com.fineplay.fineplaybackend.team.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeamRequestDto {

    @NotBlank(message = "팀 이름은 필수입니다.")
    private String teamName;

    @NotBlank(message = "홈타운1은 필수입니다.")
    private String homeTown1;

    // 홈타운2는 선택 사항
    private String homeTown2;

    @NotBlank(message = "스포츠는 필수입니다.")
    private String sports;

    @NotNull(message = "자동 수락 여부는 필수입니다.")
    private Boolean autoAccept;

    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;
}
