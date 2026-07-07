package com.iotsic.smart.system.controller.admin;

import com.iotsic.smart.framework.common.dto.request.PageRequest;
import com.iotsic.smart.framework.common.dto.response.PageResult;
import com.iotsic.smart.framework.common.result.RestResult;
import com.iotsic.smart.framework.security.annotation.RequirePermission;
import com.iotsic.smart.system.dto.LoginLogRequest;
import com.iotsic.smart.system.dto.LoginLogVO;
import com.iotsic.smart.system.service.LoginLogService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 登录日志 Controller
 *
 * @author Ryan
 * @since 2026-07-07
 */
@RestController
@RequestMapping("/api/login-log")
@RequiredArgsConstructor
public class LoginLogController {

    private final LoginLogService loginLogService;

    /**
     * 分页查询登录日志
     */
    @GetMapping("/page")
    @RequirePermission
    public RestResult<PageResult<LoginLogVO>> page(@ModelAttribute LoginLogPageQuery query) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(query.getPage());
        pageRequest.setPageSize(query.getPageSize());

        LoginLogRequest request = new LoginLogRequest();
        request.setUsername(query.getUsername());
        request.setLogType(query.getLogType());
        request.setStartTime(query.getStartTime());
        request.setEndTime(query.getEndTime());

        PageResult<LoginLogVO> result = loginLogService.pageList(pageRequest, request);
        return RestResult.success(result);
    }

    /**
     * 获取登录日志详情
     */
    @GetMapping("/detail/{id}")
    @RequirePermission
    public RestResult<LoginLogVO> detail(@PathVariable Long id) {
        LoginLogVO vo = loginLogService.getDetail(id);
        return RestResult.success(vo);
    }

    @Data
    public static class LoginLogPageQuery {
        private Integer page = 1;
        private Integer pageSize = 10;
        private String username;
        private Integer logType;
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime startTime;
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime endTime;
    }
}
