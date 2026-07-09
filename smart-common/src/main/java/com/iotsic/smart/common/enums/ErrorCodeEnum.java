package com.iotsic.smart.common.enums;

import com.iotsic.smart.framework.common.exception.enums.ResultCode;

public interface ErrorCodeEnum {

    ResultCode SYSTEM_ERROR = new ResultCode(1000, "System error");
    ResultCode TOKEN_EXPIRED = new ResultCode(1005, "Token expired");
    ResultCode TOKEN_INVALID = new ResultCode(1006, "Token invalid");
    ResultCode REFRESH_TOKEN_FAILED = new ResultCode(1007, "Refresh token failed");

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

    ResultCode SESSION_NOT_FOUND = new ResultCode(1100, "Session not found");
    ResultCode SESSION_KICK_FAILED = new ResultCode(1101, "Kick session failed");
    ResultCode SESSION_NO_PERMISSION = new ResultCode(1102, "No permission to kick session");
    ResultCode SESSION_OFFLINE = new ResultCode(1103, "Session already offline");
    ResultCode SESSION_EXPIRED = new ResultCode(1104, "Session expired");

    ResultCode STRATEGY_NOT_FOUND = new ResultCode(1200, "Login strategy not found");
    ResultCode STRATEGY_UPDATE_FAILED = new ResultCode(1201, "Update login strategy failed");
    ResultCode STRATEGY_NO_PERMISSION = new ResultCode(1202, "No permission to modify strategy");

    ResultCode LOG_NOT_FOUND = new ResultCode(1300, "Login log not found");
    ResultCode LOG_QUERY_FAILED = new ResultCode(1301, "Query login log failed");

    // ========== OAuth2 客户端 1-002-020-000 =========
    ResultCode OAUTH2_CLIENT_NOT_EXISTS = new ResultCode(1_002_020_000, "OAuth2 客户端不存在");
    ResultCode OAUTH2_CLIENT_EXISTS = new ResultCode(1_002_020_001, "OAuth2 客户端编号已存在");
    ResultCode OAUTH2_CLIENT_DISABLE = new ResultCode(1_002_020_002, "OAuth2 客户端已禁用");
    ResultCode OAUTH2_CLIENT_AUTHORIZED_GRANT_TYPE_NOT_EXISTS = new ResultCode(1_002_020_003, "不支持该授权类型");
    ResultCode OAUTH2_CLIENT_SCOPE_OVER = new ResultCode(1_002_020_004, "授权范围过大");
    ResultCode OAUTH2_CLIENT_REDIRECT_URI_NOT_MATCH = new ResultCode(1_002_020_005, "无效 redirect_uri: {}");
    ResultCode OAUTH2_CLIENT_CLIENT_SECRET_ERROR = new ResultCode(1_002_020_006, "无效 client_secret: {}");

    // ========== OAuth2 授权 1-002-021-000 =========
    ResultCode OAUTH2_GRANT_CLIENT_ID_MISMATCH = new ResultCode(1_002_021_000, "client_id 不匹配");
    ResultCode OAUTH2_GRANT_REDIRECT_URI_MISMATCH = new ResultCode(1_002_021_001, "redirect_uri 不匹配");
    ResultCode OAUTH2_GRANT_STATE_MISMATCH = new ResultCode(1_002_021_002, "state 不匹配");

    // ========== OAuth2 授权 1-002-022-000 =========
    ResultCode OAUTH2_CODE_NOT_EXISTS = new ResultCode(1_002_022_000, "code 不存在");
    ResultCode OAUTH2_CODE_EXPIRE = new ResultCode(1_002_022_001, "code 已过期");

}
