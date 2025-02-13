package com.fineplay.fineplaybackend.playerSearch.dto.request;

import lombok.Getter;
import java.util.List;

@Getter
public class SearchHistoryRequestDto {
    private Long userId;
    private List<String> searchTexts;
}
