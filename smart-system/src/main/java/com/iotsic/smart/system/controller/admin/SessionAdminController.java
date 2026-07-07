package com.iotsic.smart.system.controller.admin;

import com.iotsic.smart.framework.common.dto.request.PageRequest;
import com.iotsic.smart.framework.common.dto.response.PageResult;
import com.iotsic.smart.framework.common.result.RestResult;
import com.iotsic.smart.framework.security.annotation.RequirePermission;
import com.iotsic.smart.system.entity.LoginStrategy;
import com.iotsic.smart.system.entity.OnlineSession;
import com.iotsic.smart.system.service.LoginStrategyService;
import com.iotsic.smart.system.service.SessionService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 会话管理 Controller
 *
 * @author Ryan
 * @since 2026-07-07
 */
@RestController
@RequestMapping("/api/admin/session")
@RequiredArgsConstructor
public class SessionAdminController {

    private final SessionService sessionService;
    private final LoginStrategyService loginStrategyService;

    /**
     * 查询会话列表
     */
    @GetMapping("/list")
    @RequirePermission
    public RestResult<PageResult<OnlineSession>> list(SessionQuery query) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(query.getPage());
        pageRequest.setPageSize(query.getPageSize());
        
        PageResult<OnlineSession> result = sessionService.list(
            pageRequest, query.getUserId(), query.getDeviceType(), query.getStatus()
        );
        return RestResult.success(result);
    }

    /**
     * 获取会话统计
     */
    @GetMapping("/statistics")
    @RequirePermission
    public RestResult<Map<String, Object>> statistics() {
        return RestResult.success(sessionService.getStatistics());
    }

    /**
     * 踢出指定会话
     */
    @PostMapping("/{id}/kick")
    @RequirePermission
    public RestResult<Void> kick(@PathVariable Long id) {
        sessionService.kickSession(id);
        return RestResult.success();
    }

    /**
     * 踢出用户全部会话
     */
    @PostMapping("/user/{userId}/kick-all")
    @RequirePermission
    public RestResult<Integer> kickUserAll(@PathVariable Long userId) {
        int count = sessionService.kickUserAllSessions(userId);
        return RestResult.success(count);
    }

    /**
     * 踢出设备类型的全部会话
     */
    @PostMapping("/device-type/{deviceType}/kick-all")
    @RequirePermission
    public RestResult<Integer> kickDeviceTypeAll(@PathVariable String deviceType) {
        int count = sessionService.kickDeviceTypeAllSessions(deviceType);
        return RestResult.success(count);
    }

    /**
     * 获取登录策略
     */
    @GetMapping("/strategy")
    @RequirePermission
    public RestResult<LoginStrategy> getStrategy(@RequestParam(required = false) Long tenantId) {
        LoginStrategy strategy = loginStrategyService.getStrategy(tenantId);
        return RestResult.success(strategy);
    }

    /**
     * 更新登录策略
     */
    @PutMapping("/strategy")
    @RequirePermission
    public RestResult<Void> updateStrategy(@RequestBody LoginStrategy strategy) {
        loginStrategyService.updateStrategy(strategy);
        return RestResult.success();
    }

    @Data
    public static class SessionQuery {
        private Integer page = 1;
        private Integer pageSize = 10;
        private Long userId;
        private String deviceType;
        private Integer status;
    }
}
