package com.fineplay.fineplaybackend.playerSearch.controller;

import com.fineplay.fineplaybackend.auth.entity.UserEntity;
import com.fineplay.fineplaybackend.auth.repository.UserRepository;
import com.fineplay.fineplaybackend.playerSearch.dto.request.SearchHistoryRequestDto;
import com.fineplay.fineplaybackend.playerSearch.dto.response.SearchHistoryResponseDto;
import com.fineplay.fineplaybackend.playerSearch.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;
    private final UserRepository userRepository; // ✅ User 조회를 위한 UserRepository 추가

    public SearchController(SearchService searchService, UserRepository userRepository) {
        this.searchService = searchService;
        this.userRepository = userRepository;
    }

    // ✅ 닉네임으로 유저 검색
    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> searchUsers(@RequestParam String keyword) {
        return ResponseEntity.ok(searchService.searchUsers(keyword));
    }
    // ✅ 검색 기록 저장
    @PostMapping("/history")
    public ResponseEntity<SearchHistoryResponseDto> saveSearchHistory(@RequestBody SearchHistoryRequestDto request) {
        if (request.getUserId() == null) {
            return ResponseEntity.badRequest().body(new SearchHistoryResponseDto("userId가 누락되었습니다."));
        }

        UserEntity user = userRepository.findById(request.getUserId()).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(new SearchHistoryResponseDto("해당 사용자를 찾을 수 없습니다."));
        }

        searchService.saveSearchHistory(user, request.getSearchTexts());

        return ResponseEntity.ok(new SearchHistoryResponseDto("검색 기록이 저장되었습니다.")); // ✅ 응답을 명시적으로 추가
    }


}
