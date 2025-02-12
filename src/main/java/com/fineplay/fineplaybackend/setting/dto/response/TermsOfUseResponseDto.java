package com.fineplay.fineplaybackend.setting.dto.response;

import com.fineplay.fineplaybackend.setting.entity.TermsOfUseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;




@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TermsOfUseResponseDto {
    private String termsContent;
    private String message;
    private String errCode;
}
