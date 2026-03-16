package com.iotsic.ps.common.enums;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    SUCCESS(0, "Success"),
    FAILURE(HttpServletResponse.SC_BAD_REQUEST, "Failure"),
    SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error"),
    NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, "Resource not found"),
    UNAUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"),
    FORBIDDEN(HttpServletResponse.SC_FORBIDDEN, "Forbidden"),
    PARAM_ERROR(HttpServletResponse.SC_BAD_REQUEST, "Parameter error"),
    MISSING_PARAMETER(HttpServletResponse.SC_BAD_REQUEST, "Missing parameter"),
    VALIDATION_ERROR(HttpServletResponse.SC_BAD_REQUEST, "Validation error"),
    TYPE_MISMATCH(HttpServletResponse.SC_BAD_REQUEST, "Type mismatch"),
    METHOD_NOT_ALLOWED(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Method not allowed"),
    INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error"),
    BUSINESS_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Business error"),
    DEMO_DENY(901, "演示模式，禁止写操作"),
    UNKNOWN(9998, "Unknown error"),
    REPEAT_OPERATION(9999, "Repeat operation"),

    SYSTEM_ERROR(1000, "System error"),
    TOKEN_EXPIRED(1005, "Token expired"),
    TOKEN_INVALID(1006, "Token invalid"),

    USER_NOT_FOUND(2000, "User not found"),
    USER_EXIST(2001, "User already exists"),
    PASSWORD_ERROR(2002, "Password error"),
    PHONE_EXIST(2003, "Phone number already exists"),
    EMAIL_EXIST(2004, "Email already exists"),
    USER_DISABLED(2005, "User account disabled"),
    GROUP_NOT_FOUND(2006, "Group not found"),
    GROUP_EXIST(2007, "Group already exists"),
    GROUP_MEMBER_NOT_FOUND(2008, "Group member not found"),
    GROUP_MEMBER_EXIST(2006, "Group member already exists"),

    ENTERPRISE_NOT_FOUND(2100, "Enterprise not found"),
    ENTERPRISE_EXIST(2101, "Enterprise already exists"),

    SCALE_NOT_FOUND(3000, "Scale not found"),
    SCALE_OFFLINE(3001, "Scale is offline"),
    SCALE_NOT_PURCHASED(3002, "Scale not purchased"),
    QUESTION_NOT_FOUND(3003, "Question not found"),
    ANSWER_INVALID(3004, "Answer invalid"),
    RECORD_NOT_FOUND(3005, "Record not found"),
    EXAM_FINISHED(3006, "Exam finished"),
    EXAM_NOT_PAUSED(3007, "Exam not paused"),
    RULE_NOT_FOUND(3008, "Scoring rules not found"),
    DIMENSION_NOT_FOUND(3009, "Dimension not found"),
    OPTION_NOT_FOUND(3010, "Option score not found"),

    ORDER_NOT_FOUND(4000, "Order not found"),
    ORDER_EXPIRED(4001, "Order expired"),
    ORDER_PAID(4002, "Order already paid"),
    ORDER_CANCELLED(4003, "Order cancelled"),
    PAYMENT_FAILED(4004, "Payment failed"),
    REFUND_FAILED(4005, "Refund failed"),
    REFUND_EXCEED(4006, "Refund amount exceeds limit"),
    ORDER_CANNOT_CANCEL(4007, "Order cannot be cancelled"),
    REFUND_NOT_ALLOWED(4008, "Refund not allowed"),
    REFUND_ITEM_EMPTY(4009, "Refund item empty"),
    REFUND_ITEM_INVALID(4010, "Refund item invalid"),
    REFUND_ITEM_EXIST(4011, "Refund item already exists"),
    REFUND_NOT_FOUND(4012, "Refund not found"),
    REFUND_STATUS_ERROR(4013, "Refund status error"),
    CART_ITEM_NOT_FOUND(4008, "Cart item not found"),
    QUOTA_NOT_FOUND(4009, "Quota not found"),
    QUOTA_INSUFFICIENT(4010, "Quota insufficient"),

    THIRD_PARTY_ERROR(5000, "Third party service error"),
    THIRD_PARTY_TIMEOUT(5001, "Third party service timeout"),
    PLATFORM_EXIST(5002, "Platform already exists"),
    CONFIG_NOT_FOUND(5003, "Platform configuration not found"),

    REPORT_NOT_FOUND(6000, "Report not found"),
    REPORT_GENERATING(6001, "Report is generating"),
    REPORT_EXPIRED(6002, "Report expired"),
    CALLBACK_NOT_FOUND(6003, "Callback not found");

    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
