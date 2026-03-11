package com.ps.common.constant;

public class BusinessConstant {

    public static final String SCALE_CACHE_PREFIX = "ps:scale:";
    public static final String ORDER_CACHE_PREFIX = "ps:order:";
    public static final String REPORT_CACHE_PREFIX = "ps:report:";

    public static final Integer DEFAULT_PAGE_SIZE = 10;
    public static final Integer MAX_PAGE_SIZE = 100;

    public static final Integer MAX_QUESTION_COUNT = 500;
    public static final Integer MAX_OPTION_COUNT = 20;

    public static final Integer MIN_PASSWORD_LENGTH = 6;
    public static final Integer MAX_PASSWORD_LENGTH = 20;

    public static final Integer MAX_USERNAME_LENGTH = 50;
    public static final Integer MAX_NICKNAME_LENGTH = 100;

    public static final Integer ORDER_EXPIRE_MINUTES = 30;
    public static final Integer REFUND_DAYS = 7;

    public static final String UPLOAD_PATH = "/data/upload/";
    public static final String EXPORT_PATH = "/data/export/";

    public static final String AVATAR_ALLOWED_TYPES = "jpg,jpeg,png,gif";
    public static final Long AVATAR_MAX_SIZE = 5242880L;

    private BusinessConstant() {
    }
}
