package com.iotsic.ps.common.constant;

public class SystemConstant {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final Long TOKEN_EXPIRE_TIME = 7200000L;
    public static final Long REFRESH_TOKEN_EXPIRE_TIME = 604800000L;

    public static final String TOKEN_SECRET = "psychological-scale-secret-key-2026";

    public static final String REDIS_TOKEN_PREFIX = "ps:token:";
    public static final String REDIS_USER_PREFIX = "ps:user:";

    public static final Integer MAX_LOGIN_DEVICES = 5;

    public static final String DEFAULT_AVATAR = "https://ps-avatar.oss.com/default.png";

    private SystemConstant() {
    }
}
