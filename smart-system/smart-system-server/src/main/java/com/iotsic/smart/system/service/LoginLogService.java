package com.iotsic.smart.system.service;

import com.iotsic.smart.framework.common.dto.request.PageRequest;
import com.iotsic.smart.framework.common.dto.response.PageResult;
import com.iotsic.smart.system.dto.LoginLogCreateRequest;
import com.iotsic.smart.system.dto.LoginLogRequest;
import com.iotsic.smart.system.dto.LoginLogVO;

/**
 * 登录日志服务接口
 *
 * @author Ryan
 * @since 2026-07-07
 */
public interface LoginLogService {

    /**
     * 记录登录日志
     *
     * @param request 登录日志创建请求
     */
    void logLogin(LoginLogCreateRequest request);

    /**
     * 分页查询登录日志
     *
     * @param pageRequest 分页请求
     * @param request 查询条件
     * @return 分页结果
     */
    PageResult<LoginLogVO> pageList(PageRequest pageRequest, LoginLogRequest request);

    /**
     * 获取登录日志详情
     *
     * @param id 日志ID
     * @return 登录日志详情
     */
    LoginLogVO getDetail(Long id);
}
