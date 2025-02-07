package com.fineplay.fineplaybackend.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchPositionRequestDto {

    @NotBlank
    private String position;
}
