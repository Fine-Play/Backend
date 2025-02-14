package com.fineplay.fineplaybackend.playerSearch.entity;

import com.fineplay.fineplaybackend.auth.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "search_history")
public class SearchHistoryEntity {

    @Id
    @Column(name = "user_id")  // ✅ UserEntity의 PK를 그대로 사용
    private Long userId;

    @OneToOne
    @MapsId  // ✅ userId를 PK이면서 FK로 매핑
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ElementCollection  // ✅ 리스트를 컬럼으로 저장
    @CollectionTable(name = "search_terms", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "search_text")
    private List<String> searchTexts = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now(); // 마지막 검색 시간

    public SearchHistoryEntity(UserEntity user, List<String> searchTexts) {
        this.user = user;
        this.userId = user.getUserId();  // ✅ FK이면서 PK
        this.searchTexts = searchTexts;
        this.updatedAt = LocalDateTime.now();
    }

    public void addSearchText(String searchText) {
        this.searchTexts.add(searchText);
        this.updatedAt = LocalDateTime.now();
    }
}
