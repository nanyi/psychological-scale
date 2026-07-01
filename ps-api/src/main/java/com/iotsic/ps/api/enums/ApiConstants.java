package com.iotsic.ps.api.enums;

import com.iotsic.ps.common.constant.RpcConstants;

/**
 * API 相关的枚举
 *
 * @author Ryan
 * @since 2026-04-28 14:10
 */
public class ApiConstants {

    /**
     * 服务名
     *
     * 注意，需要保证和 spring.application.name 保持一致
     */
    public static final String NAME = "ps-user";

    public static final String PREFIX = RpcConstants.RPC_API_PREFIX +  "/system";
}
