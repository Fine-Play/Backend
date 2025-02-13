package com.fineplay.fineplaybackend.playerSearch.service;

import com.fineplay.fineplaybackend.auth.entity.UserEntity;
import com.fineplay.fineplaybackend.auth.repository.UserRepository;
import com.fineplay.fineplaybackend.playerSearch.entity.SearchHistoryEntity;

import com.fineplay.fineplaybackend.playerSearch.repository.SearchHistoryRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final UserRepository userRepository;
    private final SearchHistoryRepository searchHistoryRepository;


    public SearchService(UserRepository userRepository, SearchHistoryRepository searchHistoryRepository) {
        this.userRepository = userRepository;
        this.searchHistoryRepository = searchHistoryRepository;
    }

    // 닉네임 검색
    public List<UserEntity> searchUsers(String keyword) {
        return userRepository.findByNickName(keyword);
    }

    // 검색 기록 저장 (10개까지만 유지)
    public void saveSearchHistory(UserEntity user, List<String> searchTexts) {
        List<SearchHistoryEntity> history = searchHistoryRepository.findByUserOrderByUpdatedAtDesc(user);

        // 검색어 추가
        searchHistoryRepository.save(new SearchHistoryEntity(user, searchTexts));

        // 10개 초과 시 가장 오래된 검색어 삭제
        if (history.size() >= 10) {
            searchHistoryRepository.delete(history.get(history.size() - 1));
        }
    }

//    // 즐겨찾기 추가 (최대 3개까지)
//    public void saveFavoriteSearch(UserEntity user, String searchText, int orderIndex) {
//        List<FavoriteSearchEntity> favorites = favoriteSearchRepository.findByUserOrderByOrderIndex(user);
//
//        if (favorites.size() >= 3) {
//            throw new RuntimeException("즐겨찾기는 최대 3개까지만 가능합니다.");
//        }
//
//        favoriteSearchRepository.save(new FavoriteSearchEntity(user, searchText, orderIndex));
//    }

//    // 즐겨찾기 순서 변경
//    public void updateFavoriteOrder(UserEntity user, List<FavoriteSearchEntity> updatedFavorites) {
//        for (FavoriteSearchEntity favorite : updatedFavorites) {
//            favorite.setOrderIndex(updatedFavorites.indexOf(favorite));
//            favoriteSearchRepository.save(favorite);
//        }
//    }
}
