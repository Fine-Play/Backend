package com.fineplay.fineplaybackend.mypage.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectedStatResponseDto {
    private int status;
    private String msg;
}
