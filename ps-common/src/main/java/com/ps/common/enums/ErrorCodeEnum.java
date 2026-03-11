package com.ps.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    SUCCESS("0000", "Success"),
    SYSTEM_ERROR("1000", "System error"),
    PARAM_ERROR("1001", "Parameter error"),
    NOT_FOUND("1002", "Resource not found"),
    UNAUTHORIZED("1003", "Unauthorized"),
    FORBIDDEN("1004", "Forbidden"),
    TOKEN_EXPIRED("1005", "Token expired"),
    TOKEN_INVALID("1006", "Token invalid"),

    USER_NOT_FOUND("2000", "User not found"),
    USER_EXIST("2001", "User already exists"),
    PASSWORD_ERROR("2002", "Password error"),
    PHONE_EXIST("2003", "Phone number already exists"),
    EMAIL_EXIST("2004", "Email already exists"),
    USER_DISABLED("2005", "User account disabled"),

    SCALE_NOT_FOUND("3000", "Scale not found"),
    SCALE_OFFLINE("3001", "Scale is offline"),
    SCALE_NOT_PURCHASED("3002", "Scale not purchased"),
    QUESTION_NOT_FOUND("3003", "Question not found"),
    ANSWER_INVALID("3004", "Answer invalid"),

    ORDER_NOT_FOUND("4000", "Order not found"),
    ORDER_EXPIRED("4001", "Order expired"),
    ORDER_PAID("4002", "Order already paid"),
    ORDER_CANCELLED("4003", "Order cancelled"),
    PAYMENT_FAILED("4004", "Payment failed"),
    REFUND_FAILED("4005", "Refund failed"),
    REFUND_EXCEED("4006", "Refund amount exceeds limit"),

    THIRD_PARTY_ERROR("5000", "Third party service error"),
    THIRD_PARTY_TIMEOUT("5001", "Third party service timeout"),

    REPORT_NOT_FOUND("6000", "Report not found"),
    REPORT_GENERATING("6001", "Report is generating"),
    REPORT_EXPIRED("6002", "Report expired");

    private final String code;
    private final String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
