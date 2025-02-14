package com.fineplay.fineplaybackend.common;

public interface ResponseMesage {
    // HTTP Status 200
    String SUCCESS = "Success."; // public static final 생략 가능

    // HTTP Status 400
    String VALIDATION_FAILED = "Validation failed.";
    String DUPLICATE_EMAIL = "Duplicate email.";
    String DUPLICATE_NICKNAME = "Duplicate nickname.";
    String DUPLICATE_PHONE_NUMBER = "Duplicate tel number.";
    String NOT_EXISTED_USER = "This user does not exist.";
    String NOT_INITIALIZED = "Not initialized.";

    // HTTP Status 401
    String SIGN_IN_FAIL = "Login information mismatch.";
    String DECRYPT_FAIL = "Decrypt decode failed.";
    String AUTHORIZATION_FAIL = "Authorization failed.";

    // HTTP Status 403
    String NO_PERMISSION = "Do not have permission.";

    // HTTP Status 500
    String DATABASE_ERROR = "Database error.";
}

