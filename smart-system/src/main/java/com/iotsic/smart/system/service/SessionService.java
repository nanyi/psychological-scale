package com.iotsic.smart.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.smart.framework.common.dto.request.PageRequest;
import com.iotsic.smart.framework.common.dto.response.PageResult;
import com.iotsic.smart.framework.common.utils.BeanUtils;
import com.iotsic.smart.framework.common.utils.reflect.ReflectUtils;
import com.iotsic.smart.system.entity.OnlineSession;
import com.iotsic.smart.system.manager.SessionManager;
import com.iotsic.smart.system.mapper.OnlineSessionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 会话服务
 *
 * @author Ryan
 * @since 2026-07-07
 */
@Service
@RequiredArgsConstructor
public class SessionService {

    private final OnlineSessionMapper onlineSessionMapper;
    private final SessionManager sessionManager;

    /**
     * 分页查询会话列表
     */
    public PageResult<OnlineSession> list(PageRequest pageRequest, Long userId, String deviceType, Integer status) {
        Page<OnlineSession> page = new Page<>(pageRequest.getPage(), pageRequest.getPageSize());
        
        LambdaQueryWrapper<OnlineSession> wrapper = new LambdaQueryWrapper<>();
        if (userId != null) {
            wrapper.eq(OnlineSession::getUserId, userId);
        }
        if (deviceType != null) {
            wrapper.eq(OnlineSession::getDeviceType, deviceType);
        }
        if (status != null) {
            wrapper.eq(OnlineSession::getStatus, status);
        } else {
            wrapper.in(OnlineSession::getStatus, 1, 2); // 默认查询在线和离线
        }
        wrapper.orderByDesc(OnlineSession::getLastAccessTime);
        
        IPage<OnlineSession> iPage = onlineSessionMapper.selectPage(page, wrapper);
        
        return PageResult.of(iPage.getRecords(), iPage.getTotal(), pageRequest);
    }

    /**
     * 获取会话统计
     */
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        // 在线会话数
        Long onlineCount = onlineSessionMapper.selectCount(
            new LambdaQueryWrapper<OnlineSession>().eq(OnlineSession::getStatus, 1)
        );
        stats.put("onlineCount", onlineCount);
        
        // 离线会话数
        Long offlineCount = onlineSessionMapper.selectCount(
            new LambdaQueryWrapper<OnlineSession>().eq(OnlineSession::getStatus, 2)
        );
        stats.put("offlineCount", offlineCount);
        
        // 按设备类型统计
        Map<String, Long> byDeviceType = new HashMap<>();
        byDeviceType.put("web", onlineSessionMapper.selectCount(
            new LambdaQueryWrapper<OnlineSession>()
                .eq(OnlineSession::getDeviceType, "web")
                .eq(OnlineSession::getStatus, 1)
        ));
        byDeviceType.put("app", onlineSessionMapper.selectCount(
            new LambdaQueryWrapper<OnlineSession>()
                .eq(OnlineSession::getDeviceType, "app")
                .eq(OnlineSession::getStatus, 1)
        ));
        byDeviceType.put("miniprogram", onlineSessionMapper.selectCount(
            new LambdaQueryWrapper<OnlineSession>()
                .eq(OnlineSession::getDeviceType, "miniprogram")
                .eq(OnlineSession::getStatus, 1)
        ));
        byDeviceType.put("pc", onlineSessionMapper.selectCount(
            new LambdaQueryWrapper<OnlineSession>()
                .eq(OnlineSession::getDeviceType, "pc")
                .eq(OnlineSession::getStatus, 1)
        ));
        stats.put("byDeviceType", byDeviceType);
        
        return stats;
    }

    /**
     * 踢出指定会话
     */
    public void kickSession(Long sessionId) {
        sessionManager.kickSessionById(sessionId);
    }

    /**
     * 踢出用户全部会话
     */
    public int kickUserAllSessions(Long userId) {
        LambdaQueryWrapper<OnlineSession> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OnlineSession::getUserId, userId)
               .eq(OnlineSession::getStatus, 1);
        long count = onlineSessionMapper.selectCount(wrapper);
        sessionManager.kickAllSessions(userId);
        return (int) count;
    }

    /**
     * 踢出设备类型的全部会话
     */
    public int kickDeviceTypeAllSessions(String deviceType) {
        LambdaQueryWrapper<OnlineSession> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OnlineSession::getDeviceType, deviceType)
               .eq(OnlineSession::getStatus, 1);
        long count = onlineSessionMapper.selectCount(wrapper);
        sessionManager.kickDeviceTypeSessions(null, deviceType);
        return (int) count;
    }
}
