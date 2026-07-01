package com.iotsic.ps.api.permission;

import com.iotsic.ps.api.enums.ApiConstants;
import com.iotsic.smart.framework.security.api.RemoteSecurityApi;
import org.springframework.cloud.openfeign.FeignClient;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 权限 API
 *
 * @author Ryan
 * @since 2026-04-28 14:08
 */
@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC 服务 - 权限")
public interface PermissionApi extends RemoteSecurityApi {

    String PREFIX = ApiConstants.PREFIX + "/permission";
}
