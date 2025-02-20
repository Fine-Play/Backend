package com.fineplay.fineplaybackend.playerSearch.controller;

import com.fineplay.fineplaybackend.auth.entity.UserEntity;
import com.fineplay.fineplaybackend.auth.repository.UserRepository;
import com.fineplay.fineplaybackend.playerSearch.dto.request.SearchHistoryRequestDto;
import com.fineplay.fineplaybackend.playerSearch.dto.response.SearchHistoryResponseDto;
import com.fineplay.fineplaybackend.playerSearch.service.SearchService;
import com.fineplay.fineplaybackend.provider.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider; // 🔹 JWT Provider 추가

    public SearchController(SearchService searchService, UserRepository userRepository, JwtProvider jwtProvider) {
        this.searchService = searchService;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    // ✅ 닉네임으로 유저 검색 (토큰 검증 X)
    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> searchUsers(@RequestParam String keyword) {
        return ResponseEntity.ok(searchService.searchUsers(keyword));
    }

    // ✅ 검색 기록 저장 (🔹 JWT 검증 추가)
    @PostMapping("/history")
    public ResponseEntity<SearchHistoryResponseDto> saveSearchHistory(@RequestBody SearchHistoryRequestDto request, HttpServletRequest httpRequest) {
        // 🔹 요청 헤더에서 JWT 토큰 추출
        String token = jwtProvider.getTokenFromRequest(httpRequest);
        if (token == null) {
            return ResponseEntity.status(401).body(new SearchHistoryResponseDto("인증 실패: 토큰이 없습니다."));
        }

        // 🔹 JWT 토큰에서 email 추출
        String email = jwtProvider.validateJwt(token);
        if (email == null) {
            return ResponseEntity.status(403).body(new SearchHistoryResponseDto("인증 실패: 유효하지 않은 토큰입니다."));
        }

        // 🔹 email을 기반으로 userId 조회
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(404).body(new SearchHistoryResponseDto("사용자를 찾을 수 없습니다."));
        }

        // 🔹 검색 기록 저장
        searchService.saveSearchHistory(user, request.getSearchTexts());

        return ResponseEntity.ok(new SearchHistoryResponseDto("검색 기록이 저장되었습니다."));
    }

    // ✅ 검색 기록 조회 (🔹 JWT 검증 추가)
    @GetMapping("/history")
    public ResponseEntity<SearchHistoryResponseDto> getSearchHistory(HttpServletRequest httpRequest) {
        // 🔹 요청 헤더에서 JWT 토큰 추출
        String token = jwtProvider.getTokenFromRequest(httpRequest);
        if (token == null) {
            return ResponseEntity.status(401).body(new SearchHistoryResponseDto("인증 실패: 토큰이 없습니다."));
        }

        // 🔹 JWT 토큰에서 email 추출
        String email = jwtProvider.validateJwt(token);
        if (email == null) {
            return ResponseEntity.status(403).body(new SearchHistoryResponseDto("인증 실패: 유효하지 않은 토큰입니다."));
        }

        // 🔹 email을 기반으로 user 조회
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(404).body(new SearchHistoryResponseDto("사용자를 찾을 수 없습니다."));
        }

        // 🔹 검색 기록 반환
        return ResponseEntity.ok(searchService.getSearchHistory(user));
    }

    // ✅ 검색 기록 삭제 (🔹 JWT 검증 추가)
    @DeleteMapping("/history")
    public ResponseEntity<SearchHistoryResponseDto> deleteSearchHistory(HttpServletRequest httpRequest) {
        // 🔹 요청 헤더에서 JWT 토큰 추출
        String token = jwtProvider.getTokenFromRequest(httpRequest);
        if (token == null) {
            return ResponseEntity.status(401).body(new SearchHistoryResponseDto("인증 실패: 토큰이 없습니다."));
        }

        // 🔹 JWT 토큰에서 email 추출
        String email = jwtProvider.validateJwt(token);
        if (email == null) {
            return ResponseEntity.status(403).body(new SearchHistoryResponseDto("인증 실패: 유효하지 않은 토큰입니다."));
        }

        // 🔹 email을 기반으로 user 조회
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(404).body(new SearchHistoryResponseDto("사용자를 찾을 수 없습니다."));
        }

        // 🔹 검색 기록 삭제
        searchService.deleteSearchHistory(user);

        return ResponseEntity.ok(new SearchHistoryResponseDto("검색 기록이 삭제되었습니다."));
    }
}
