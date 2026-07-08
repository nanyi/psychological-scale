package com.iotsic.smart.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iotsic.smart.framework.security.service.TokenCacheService;
import com.iotsic.smart.system.entity.LoginStrategy;
import com.iotsic.smart.system.entity.OnlineSession;
import com.iotsic.smart.system.mapper.OnlineSessionMapper;
import com.iotsic.smart.system.service.LoginStrategyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 会话管理器
 *
 * @author Ryan
 * @since 2026-07-07
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SessionManager {

    private final OnlineSessionMapper onlineSessionMapper;
    private final TokenCacheService tokenCacheService;
    private final LoginStrategyService loginStrategyService;

    /**
     * 根据登录策略处理现有会话
     */
    public void handleExistingSessions(Long userId, String deviceType, String deviceId) {
        LoginStrategy strategy = loginStrategyService.getStrategy(null);
        if (strategy == null) {
            return;
        }

        Integer loginPolicy = strategy.getLoginPolicy();
        if (loginPolicy == null) {
            loginPolicy = 3; // 默认同端互斥
        }

        if (loginPolicy == 1) {
            // 单端登录：踢出用户所有会话
            kickAllSessions(userId);
        } else if (loginPolicy == 3) {
            // 同端互斥：踢出同deviceType的会话
            kickDeviceTypeSessions(userId, deviceType);
        }
        // 2-多端登录：不处理
    }

    /**
     * 踢出指定会话
     */
    public void kickSession(Long userId, String deviceType, String deviceId) {
        // 1. 更新数据库状态
        onlineSessionMapper.kickSession(userId, deviceType, deviceId);

        // 2. 删除Redis缓存
        tokenCacheService.removeSession(userId, deviceType, deviceId);

        log.info("踢出会话: userId={}, deviceType={}, deviceId={}", userId, deviceType, deviceId);
    }

    /**
     * 踢出用户所有会话
     */
    public void kickAllSessions(Long userId) {
        // 1. 查询用户所有会话
        LambdaQueryWrapper<OnlineSession> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OnlineSession::getUserId, userId)
               .eq(OnlineSession::getStatus, 1);
        List<OnlineSession> sessions = onlineSessionMapper.selectList(wrapper);

        // 2. 逐个踢出
        for (OnlineSession session : sessions) {
            kickSession(session.getUserId(), session.getDeviceType(), session.getDeviceId());
        }

        log.info("踢出用户全部会话: userId={}, count={}", userId, sessions.size());
    }

    /**
     * 踢出同设备类型的所有会话
     */
    public void kickDeviceTypeSessions(Long userId, String deviceType) {
        // 1. 查询同类型会话
        LambdaQueryWrapper<OnlineSession> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OnlineSession::getUserId, userId)
               .eq(OnlineSession::getDeviceType, deviceType)
               .eq(OnlineSession::getStatus, 1);
        List<OnlineSession> sessions = onlineSessionMapper.selectList(wrapper);

        // 2. 逐个踢出
        for (OnlineSession session : sessions) {
            kickSession(session.getUserId(), session.getDeviceType(), session.getDeviceId());
        }

        log.info("踢出同设备类型会话: userId={}, deviceType={}, count={}", userId, deviceType, sessions.size());
    }

    /**
     * 踢出指定会话（按会话ID）
     */
    public void kickSessionById(Long sessionId) {
        OnlineSession session = onlineSessionMapper.selectById(sessionId);
        if (session != null) {
            kickSession(session.getUserId(), session.getDeviceType(), session.getDeviceId());
        }
    }
}
