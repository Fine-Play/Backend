package com.fineplay.fineplaybackend.mypage.service.impl;

import com.fineplay.fineplaybackend.dto.response.ErrorResponseDto;
import com.fineplay.fineplaybackend.dto.response.ResponseDto;
import com.fineplay.fineplaybackend.mypage.dto.response.MypageProfileResponseDto;
import com.fineplay.fineplaybackend.mypage.dto.request.SelectedStatRequestDto;
import com.fineplay.fineplaybackend.mypage.dto.response.SelectedStatResponseDto;
import com.fineplay.fineplaybackend.mypage.dto.request.PageMoveRequestDto;
import com.fineplay.fineplaybackend.mypage.dto.response.PageMoveResponseDto;
import com.fineplay.fineplaybackend.mypage.entity.UserProfile;
import com.fineplay.fineplaybackend.mypage.entity.UserStat;
import com.fineplay.fineplaybackend.mypage.entity.UserStatVisualization;
import com.fineplay.fineplaybackend.mypage.repository.UserProfileRepository;
import com.fineplay.fineplaybackend.mypage.repository.UserStatRepository;
import com.fineplay.fineplaybackend.mypage.repository.UserStatVisualizationRepository;
import com.fineplay.fineplaybackend.mypage.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class MypageServiceImpl implements MypageService {

    private final UserProfileRepository userProfileRepository;
    private final UserStatRepository userStatRepository;
    private final UserStatVisualizationRepository userStatVisualizationRepository;

    @Autowired
    public MypageServiceImpl(UserProfileRepository userProfileRepository,
                             UserStatRepository userStatRepository,
                             UserStatVisualizationRepository userStatVisualizationRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userStatRepository = userStatRepository;
        this.userStatVisualizationRepository = userStatVisualizationRepository;
    }



    // ✅ 마이페이지 프로필 조회
    @Override
    public MypageProfileResponseDto getMypageProfile(Long userId) {
        Optional<UserProfile> profileOpt = userProfileRepository.findByUserId(userId);
        Optional<UserStat> statOpt = userStatRepository.findByUserId(userId);

        if (profileOpt.isEmpty() || statOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID_USER_ID");
        }

        UserProfile profile = profileOpt.get();
        UserStat stat = statOpt.get();

        return MypageProfileResponseDto.builder()
                .status(200)
                .userName(profile.getNickName())
                .position(stat.getPosition())
                .ovr(stat.getOVR())
                .team1(String.valueOf(profile.getTeam1()))
                .team2(String.valueOf(profile.getTeam2()))
                .team3(String.valueOf(profile.getTeam3()))
                .selectedStat(stat.getSelectedStat())
                // Special Stats
                .CRO(stat.getCRO())
                .HED(stat.getHED())
                .FST(stat.getFST())
                .ACT(stat.getACT())
                .OFF(stat.getOFF())
                .TEC(stat.getTEC())
                .COP(stat.getCOP())
                // Common Stats
                .PAC(stat.getPAC())
                .PAS(stat.getPAS())
                .SPD(stat.getSPD())
                // Position Stats: FW
                .SHO(stat.getSHO())
                .DRV(stat.getDRV())
                // Position Stats: MF
                .DEC(stat.getDEC())
                .DRI(stat.getDRI())
                // Position Stats: DF
                .TAC(stat.getTAC())
                .BLD(stat.getBLD())
                .CROImg("path/to/croImg")
                .HEDImg("path/to/hedImg")
                .FSTImg("path/to/fstImg")
                .ACTImg("path/to/actImg")
                .OFFImg("path/to/offImg")
                .TECImg("path/to/tecImg")
                .COPImg("path/to/copImg")
                .build();
    }

    @Override
    public SelectedStatResponseDto updateSelectedStat(Long tokenUserId, SelectedStatRequestDto requestDto) {
        Long userId = requestDto.getUserId();

        // ❌ 유효하지 않은 userId라면 예외 발생
        Optional<UserStat> userStatOptional = userStatRepository.findByUserId(userId);
        if (userStatOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID_USER_ID");
        }

        // ❌ 토큰을 통해 얻은ID와 다른 userId라면 예외 발생
        if (!tokenUserId.equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID_USER_ID_MISMATCH");
        }

        String selectedStat = requestDto.getSelectedStat().toUpperCase(); // ✅ 대소문자 무시

        // ✅ 선택 가능한 스탯 목록
        Set<String> validStats = Set.of("CRO", "HED", "FST", "ACT", "OFF", "TEC", "COP");

        // ❌ 유효하지 않은 스탯 값이면 예외 발생
        if (!validStats.contains(selectedStat)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID_SELECTED_STAT");
        }

        

        UserStat userStat = userStatOptional.get();
        userStat.setSelectedStat(selectedStat);
        userStatRepository.save(userStat);

        // ✅ 정상적인 응답 반환
        return SelectedStatResponseDto.builder()
                .status(200)
                .stat(selectedStat)
                .msg("stat update : " + selectedStat)
                .build();
    }

    @Override
    public PageMoveResponseDto movePage(String statName, PageMoveRequestDto requestDto) {
        Long userId = requestDto.getUserId();
        Optional<UserStatVisualization> visOpt = userStatVisualizationRepository.findByUserId(userId);

        // ❌ 유효하지 않은 userId이면 예외 발생
        if (visOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID_USER_ID");
        }

        // ✅ 선택 가능한 statName 목록
        Map<String, String> statImageMap = Map.of(
                "PAC", "path/to/PACImg",
                "SPD", "path/to/SPDImg",
                "PAS", "path/to/PASImg",
                "SHO", "path/to/SHOImg",
                "DRV", "path/to/DRVImg",
                "DEC", "path/to/DECImg",
                "DRI", "path/to/DRIImg",
                "TAC", "path/to/TACImg",
                "BLD", "path/to/BLDImg"
        );

        String upperStatName = statName.toUpperCase();

        // ❌ 유효하지 않은 statName이면 예외 발생
        if (!statImageMap.containsKey(upperStatName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID_STAT_NAME");
        }

        // ✅ 정상적인 응답 반환
        return PageMoveResponseDto.builder()
                .status(200)
                .msg("move page : " + upperStatName)
                .img(statImageMap.get(upperStatName))
                .build();
    }
}
