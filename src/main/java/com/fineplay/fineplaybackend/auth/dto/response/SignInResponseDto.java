package com.fineplay.fineplaybackend.auth.dto.response;

import com.fineplay.fineplaybackend.common.ResponseCode;
import com.fineplay.fineplaybackend.common.ResponseMesage;
import com.fineplay.fineplaybackend.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignInResponseDto extends ResponseDto {

    private String token;
    private int expirationTime;
    private SignInResponseDto(String token)  {
        super(ResponseCode.SUCCESS, ResponseMesage.SUCCESS);
        this.token = token;
        this.expirationTime = 3600; // 1 시간
    }

    // 성공
    public static ResponseEntity<SignInResponseDto> success(String accessToken, String refreshToken) {
        SignInResponseDto result = new SignInResponseDto(accessToken);
        return ResponseEntity.status(HttpStatus.OK).header("X-Refresh-Token", refreshToken).body(result);
    }


    // 유저 정보 없음
    public static ResponseEntity<ResponseDto> signInFail() {
        ResponseDto result = new ResponseDto(ResponseCode.SIGN_IN_FAIL, ResponseMesage.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
