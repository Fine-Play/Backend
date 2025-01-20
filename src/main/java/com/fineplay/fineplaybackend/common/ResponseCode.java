package com.fineplay.fineplaybackend.common;

public interface ResponseCode {

    // HTTP Status 200
    String SUCCESS = "SU"; // public static final 생략 가능

    // HTTP Status 400
    String VALIDATION_FAILED = "VF";
    String DUPLICATE_EMAIL = "DE";
    String DUPLICATE_NICKNAME = "DN";
    String DUPLICATE_PHONE_NUMBER = "DP";
    String NOT_EXISTED_USER = "NU";

    // HTTP Status 401
    String SIGN_IN_FAIL = "SF";
    String AUTHORIZATION_FAIL = "AF";

    // HTTP Status 403
    String NO_PERMISSION = "NP";

    // HTTP Status 500
    String DATABASE_ERROR = "DBE";
}
