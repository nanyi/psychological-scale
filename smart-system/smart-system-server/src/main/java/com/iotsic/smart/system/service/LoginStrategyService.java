package com.iotsic.smart.system.service;

import com.iotsic.smart.system.entity.LoginStrategy;
import com.iotsic.smart.system.mapper.LoginStrategyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 登录策略服务
 *
 * @author Ryan
 * @since 2026-07-07
 */
@Service
@RequiredArgsConstructor
public class LoginStrategyService {

    private final LoginStrategyMapper loginStrategyMapper;

    /**
     * 获取登录策略
     */
    public LoginStrategy getStrategy(Long tenantId) {
        LoginStrategy strategy = loginStrategyMapper.selectById(tenantId);
        if (strategy == null) {
            strategy = loginStrategyMapper.selectById(0L);
        }
        return strategy;
    }

    /**
     * 更新登录策略
     */
    public void updateStrategy(LoginStrategy strategy) {
        if (strategy.getTenantId() == null) {
            strategy.setTenantId(0L);
        }
        loginStrategyMapper.updateById(strategy);
    }
}
