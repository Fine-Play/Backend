package com.fineplay.fineplaybackend.mypage.service.impl;

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
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public MypageProfileResponseDto getMypageProfile(Long userId) {
        Optional<UserProfile> profileOpt = userProfileRepository.findByUserId(userId);
        Optional<UserStat> statOpt = userStatRepository.findByUserId(userId);
        Optional<UserStatVisualization> visOpt = userStatVisualizationRepository.findByUserId(userId);

        if (profileOpt.isEmpty() || statOpt.isEmpty()) {
            return MypageProfileResponseDto.builder()
                    .status(404)
                    .build();
        }

        UserProfile profile = profileOpt.get();
        UserStat stat = statOpt.get();

        return MypageProfileResponseDto.builder()
                .status(200)
                .userName(profile.getNickName())  // ✅ Optional 사용 안하도록 수정
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
                // 스탯 이미지 경로 (실제 구현에서는 Blob 데이터를 이미지 URL 혹은 Base64 문자열로 변환)
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
    public SelectedStatResponseDto updateSelectedStat(SelectedStatRequestDto requestDto) {
        Long userId = requestDto.getUserId();
        String selectedStat = requestDto.getSelectedStat();

        // ✅ Optional 활용하여 null 체크
        Optional<UserStat> userStatOptional = userStatRepository.findByUserId(userId);
        if (userStatOptional.isEmpty()) {
            return SelectedStatResponseDto.builder()
                    .status(404)
                    .msg("User not found")
                    .build();
        }

        UserStat userStat = userStatOptional.get();
        userStat.setSelectedStat(selectedStat);
        userStatRepository.save(userStat);

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

        if (visOpt.isEmpty()) {
            return PageMoveResponseDto.builder()
                    .status(404)
                    .msg("User not found")
                    .img("path/to/defaultImg")
                    .build();
        }

        String imgPath;
        switch (statName.toLowerCase()) {
            case "pac":
                imgPath = "path/to/PACImg";
                break;
            case "spd":
                imgPath = "path/to/SPDImg";
                break;
            case "pas":
                imgPath = "path/to/PASImg";
                break;
            case "dri":
                imgPath = "path/to/DRIImg";
                break;
            case "dec":
                imgPath = "path/to/DECImg";
                break;
            default:
                imgPath = "path/to/defaultImg";
                break;
        }

        return PageMoveResponseDto.builder()
                .status(200)
                .msg("move page : skill")
                .img(imgPath)
                .build();
    }
}
