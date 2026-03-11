package com.iotsic.ps.order.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.order.entity.EnterpriseQuota;
import com.iotsic.ps.order.mapper.EnterpriseQuotaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnterpriseQuotaServiceImpl implements EnterpriseQuotaService {

    private final EnterpriseQuotaMapper enterpriseQuotaMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EnterpriseQuota createQuota(Long enterpriseId, Long scaleId, Integer quota, java.math.BigDecimal price) {
        LambdaQueryWrapper<EnterpriseQuota> existWrapper = new LambdaQueryWrapper<>();
        existWrapper.eq(EnterpriseQuota::getEnterpriseId, enterpriseId)
                .eq(EnterpriseQuota::getScaleId, scaleId);
        EnterpriseQuota existQuota = enterpriseQuotaMapper.selectOne(existWrapper);

        if (existQuota != null) {
            existQuota.setTotalQuota(existQuota.getTotalQuota() + quota);
            existQuota.setRemainingQuota(existQuota.getRemainingQuota() + quota);
            existQuota.setUpdateTime(LocalDateTime.now());
            enterpriseQuotaMapper.updateById(existQuota);
            return existQuota;
        }

        EnterpriseQuota enterpriseQuota = new EnterpriseQuota();
        enterpriseQuota.setEnterpriseId(enterpriseId);
        enterpriseQuota.setScaleId(scaleId);
        enterpriseQuota.setTotalQuota(quota);
        enterpriseQuota.setUsedQuota(0);
        enterpriseQuota.setRemainingQuota(quota);
        enterpriseQuota.setPrice(price);
        enterpriseQuota.setStatus(1);
        enterpriseQuota.setCreateTime(LocalDateTime.now());
        enterpriseQuota.setUpdateTime(LocalDateTime.now());

        enterpriseQuotaMapper.insert(enterpriseQuota);
        log.info("创建企业配额: enterpriseId={}, scaleId={}, quota={}", enterpriseId, scaleId, quota);

        return enterpriseQuota;
    }

    @Override
    public EnterpriseQuota getQuotaById(Long id) {
        EnterpriseQuota quota = enterpriseQuotaMapper.selectById(id);
        if (quota == null || quota.getDeleted() == 1) {
            throw BusinessException.of("QUOTA_NOT_FOUND", "配额不存在");
        }
        return quota;
    }

    @Override
    public EnterpriseQuota getEnterpriseScaleQuota(Long enterpriseId, Long scaleId) {
        LambdaQueryWrapper<EnterpriseQuota> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EnterpriseQuota::getEnterpriseId, enterpriseId)
                .eq(EnterpriseQuota::getScaleId, scaleId)
                .eq(EnterpriseQuota::getStatus, 1);
        
        EnterpriseQuota quota = enterpriseQuotaMapper.selectOne(wrapper);
        if (quota == null) {
            throw BusinessException.of("QUOTA_NOT_FOUND", "企业配额不存在");
        }
        
        if (quota.getRemainingQuota() <= 0) {
            throw BusinessException.of("QUOTA_INSUFFICIENT", "企业配额不足");
        }
        
        return quota;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void useQuota(Long enterpriseId, Long scaleId) {
        EnterpriseQuota quota = getEnterpriseScaleQuota(enterpriseId, scaleId);
        
        quota.setUsedQuota(quota.getUsedQuota() + 1);
        quota.setRemainingQuota(quota.getRemainingQuota() - 1);
        
        if (quota.getRemainingQuota() <= 0) {
            quota.setStatus(0);
        }
        
        quota.setUpdateTime(LocalDateTime.now());
        enterpriseQuotaMapper.updateById(quota);
        
        log.info("使用企业配额: enterpriseId={}, scaleId={}, remaining={}", 
                enterpriseId, scaleId, quota.getRemainingQuota());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rechargeQuota(Long id, Integer quantity) {
        EnterpriseQuota quota = getQuotaById(id);
        
        quota.setTotalQuota(quota.getTotalQuota() + quantity);
        quota.setRemainingQuota(quota.getRemainingQuota() + quantity);
        quota.setStatus(1);
        quota.setUpdateTime(LocalDateTime.now());
        
        enterpriseQuotaMapper.updateById(quota);
        
        log.info("充值企业配额: id={}, quantity={}", id, quantity);
    }

    @Override
    public EnterpriseQuota getQuotaByOrderId(Long orderId) {
        return null;
    }

    @Override
    public PageResult<EnterpriseQuota> getEnterpriseQuotas(PageRequest request, Long enterpriseId) {
        Page<EnterpriseQuota> page = new Page<>(request.getPageNum(), request.getPageSize());
        LambdaQueryWrapper<EnterpriseQuota> wrapper = new LambdaQueryWrapper<>();
        
        if (enterpriseId != null) {
            wrapper.eq(EnterpriseQuota::getEnterpriseId, enterpriseId);
        }
        
        wrapper.orderByDesc(EnterpriseQuota::getCreateTime);
        IPage<EnterpriseQuota> result = enterpriseQuotaMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), request.getPageNum(), request.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void expireQuota(Long id) {
        EnterpriseQuota quota = getQuotaById(id);
        
        if (quota.getExpireTime() != null && quota.getExpireTime().isBefore(LocalDateTime.now())) {
            quota.setStatus(0);
            quota.setUpdateTime(LocalDateTime.now());
            enterpriseQuotaMapper.updateById(quota);
            
            log.info("企业配额已过期: id={}", id);
        }
    }
}
