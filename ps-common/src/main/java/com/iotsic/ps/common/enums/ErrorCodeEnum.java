package com.iotsic.ps.common.enums;

import com.iotsic.smart.framework.common.exception.enums.ResultCode;

public interface ErrorCodeEnum {

    ResultCode SYSTEM_ERROR = new ResultCode(1000, "System error");
    ResultCode TOKEN_EXPIRED = new ResultCode(1005, "Token expired");
    ResultCode TOKEN_INVALID = new ResultCode(1006, "Token invalid");

    ResultCode USER_NOT_FOUND = new ResultCode(2000, "User not found");
    ResultCode USER_EXIST = new ResultCode(2001, "User already exists");
    ResultCode PASSWORD_ERROR = new ResultCode(2002, "Password error");
    ResultCode PHONE_EXIST = new ResultCode(2003, "Phone number already exists");
    ResultCode EMAIL_EXIST = new ResultCode(2004, "Email already exists");
    ResultCode USER_DISABLED = new ResultCode(2005, "User account disabled");
    ResultCode GROUP_NOT_FOUND = new ResultCode(2006, "Group not found");
    ResultCode GROUP_EXIST = new ResultCode(2007, "Group already exists");
    ResultCode GROUP_MEMBER_NOT_FOUND = new ResultCode(2008, "Group member not found");
    ResultCode GROUP_MEMBER_EXIST = new ResultCode(2006, "Group member already exists");

    ResultCode ENTERPRISE_NOT_FOUND = new ResultCode(2100, "Enterprise not found");
    ResultCode ENTERPRISE_EXIST = new ResultCode(2101, "Enterprise already exists");

    ResultCode SCALE_NOT_FOUND = new ResultCode(3000, "Scale not found");
    ResultCode SCALE_OFFLINE = new ResultCode(3001, "Scale is offline");
    ResultCode SCALE_NOT_PURCHASED = new ResultCode(3002, "Scale not purchased");
    ResultCode QUESTION_NOT_FOUND = new ResultCode(3003, "Question not found");
    ResultCode ANSWER_INVALID = new ResultCode(3004, "Answer invalid");
    ResultCode RECORD_NOT_FOUND = new ResultCode(3005, "Record not found");
    ResultCode EXAM_FINISHED = new ResultCode(3006, "Exam finished");
    ResultCode EXAM_NOT_PAUSED = new ResultCode(3007, "Exam not paused");
    ResultCode RULE_NOT_FOUND = new ResultCode(3008, "Scoring rules not found");
    ResultCode DIMENSION_NOT_FOUND = new ResultCode(3009, "Dimension not found");
    ResultCode OPTION_NOT_FOUND = new ResultCode(3010, "Option score not found");

    ResultCode ORDER_NOT_FOUND = new ResultCode(4000, "Order not found");
    ResultCode ORDER_EXPIRED = new ResultCode(4001, "Order expired");
    ResultCode ORDER_PAID = new ResultCode(4002, "Order already paid");
    ResultCode ORDER_CANCELLED = new ResultCode(4003, "Order cancelled");
    ResultCode PAYMENT_FAILED = new ResultCode(4004, "Payment failed");
    ResultCode REFUND_FAILED = new ResultCode(4005, "Refund failed");
    ResultCode REFUND_EXCEED = new ResultCode(4006, "Refund amount exceeds limit");
    ResultCode ORDER_CANNOT_CANCEL = new ResultCode(4007, "Order cannot be cancelled");
    ResultCode REFUND_NOT_ALLOWED = new ResultCode(4008, "Refund not allowed");
    ResultCode REFUND_ITEM_EMPTY = new ResultCode(4009, "Refund item empty");
    ResultCode REFUND_ITEM_INVALID = new ResultCode(4010, "Refund item invalid");
    ResultCode REFUND_ITEM_EXIST = new ResultCode(4011, "Refund item already exists");
    ResultCode REFUND_NOT_FOUND = new ResultCode(4012, "Refund not found");
    ResultCode REFUND_STATUS_ERROR = new ResultCode(4013, "Refund status error");
    ResultCode CART_ITEM_NOT_FOUND = new ResultCode(4008, "Cart item not found");
    ResultCode QUOTA_NOT_FOUND = new ResultCode(4009, "Quota not found");
    ResultCode QUOTA_INSUFFICIENT = new ResultCode(4010, "Quota insufficient");

    ResultCode THIRD_PARTY_ERROR = new ResultCode(5000, "Third party service error");
    ResultCode THIRD_PARTY_TIMEOUT = new ResultCode(5001, "Third party service timeout");
    ResultCode PLATFORM_EXIST = new ResultCode(5002, "Platform already exists");
    ResultCode CONFIG_NOT_FOUND = new ResultCode(5003, "Platform configuration not found");

    ResultCode REPORT_NOT_FOUND = new ResultCode(6000, "Report not found");
    ResultCode REPORT_GENERATING = new ResultCode(6001, "Report is generating");
    ResultCode REPORT_EXPIRED = new ResultCode(6002, "Report expired");
    ResultCode CALLBACK_NOT_FOUND = new ResultCode(6003, "Callback not found");
}
