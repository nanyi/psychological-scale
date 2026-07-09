package com.iotsic.smart.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.smart.common.enums.LoginLogTypeEnum;
import com.iotsic.smart.common.enums.LoginResultEnum;
import com.iotsic.smart.framework.common.dto.request.PageRequest;
import com.iotsic.smart.framework.common.dto.response.PageResult;
import com.iotsic.smart.framework.common.exception.BusinessException;
import com.iotsic.smart.framework.common.utils.BeanUtils;
import com.iotsic.smart.system.dto.LoginLogCreateRequest;
import com.iotsic.smart.system.dto.LoginLogRequest;
import com.iotsic.smart.system.dto.LoginLogVO;
import com.iotsic.smart.system.entity.LoginLog;
import com.iotsic.smart.system.mapper.LoginLogMapper;
import com.iotsic.smart.system.service.LoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 登录日志服务实现
 *
 * @author Ryan
 * @since 2026-07-07
 */
@Service
@RequiredArgsConstructor
public class LoginLogServiceImpl implements LoginLogService {

    private final LoginLogMapper loginLogMapper;

    @Override
    public void logLogin(LoginLogCreateRequest request) {
        LoginLog loginLog = new LoginLog();
        loginLog.setLogType(request.getLogType());
        loginLog.setUserId(request.getUserId());
        loginLog.setUserType(request.getUserType());
        loginLog.setUsername(request.getUsername());
        loginLog.setResult(request.getResult());
        loginLog.setFailReason(request.getFailReason());
        loginLog.setUserIp(request.getUserIp());
        loginLog.setUserAgent(request.getUserAgent());
        loginLog.setDeviceType(request.getDeviceType());
        loginLog.setDeviceId(request.getDeviceId());
        loginLog.setLoginTime(LocalDateTime.now());
        loginLog.setTenantId(request.getTenantId());
        loginLogMapper.insert(loginLog);
    }

    @Override
    public PageResult<LoginLogVO> pageList(PageRequest pageRequest, LoginLogRequest request) {
        Page<LoginLog> page = new Page<>(pageRequest.getPage(), pageRequest.getPageSize());

        LambdaQueryWrapper<LoginLog> wrapper = new LambdaQueryWrapper<>();
        if (request != null) {
            if (request.getUsername() != null && !request.getUsername().isEmpty()) {
                wrapper.like(LoginLog::getUsername, request.getUsername());
            }
            if (request.getLogType() != null) {
                wrapper.eq(LoginLog::getLogType, request.getLogType());
            }
            if (request.getStartTime() != null) {
                wrapper.ge(LoginLog::getLoginTime, request.getStartTime());
            }
            if (request.getEndTime() != null) {
                wrapper.le(LoginLog::getLoginTime, request.getEndTime());
            }
        }
        wrapper.orderByDesc(LoginLog::getLoginTime);

        IPage<LoginLog> iPage = loginLogMapper.selectPage(page, wrapper);

        return PageResult.of(
                iPage.getRecords().stream().map(this::convertToVO).toList(),
                iPage.getTotal(),
                pageRequest
        );
    }

    @Override
    public LoginLogVO getDetail(Long id) {
        LoginLog loginLog = loginLogMapper.selectById(id);
        if (loginLog == null) {
            throw BusinessException.of(1300, "登录日志不存在");
        }
        return convertToVO(loginLog);
    }

    private LoginLogVO convertToVO(LoginLog loginLog) {
        LoginLogVO vo = BeanUtils.toBean(loginLog, LoginLogVO.class);

        LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.of(loginLog.getLogType());
        vo.setLogTypeDesc(logTypeEnum != null ? logTypeEnum.getDescription() : "未知");

        LoginResultEnum resultEnum = LoginResultEnum.of(loginLog.getResult());
        vo.setResultDesc(resultEnum != null ? resultEnum.getDescription() : "未知");

        return vo;
    }
}
