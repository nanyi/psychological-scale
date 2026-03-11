package com.iotsic.ps.scale.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.utils.EncryptUtils;
import com.iotsic.ps.core.entity.Scale;
import com.iotsic.ps.scale.dto.ScaleCreateRequest;
import com.iotsic.ps.scale.dto.ScaleUpdateRequest;
import com.iotsic.ps.scale.mapper.ScaleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScaleServiceImpl implements ScaleService {

    private final ScaleMapper scaleMapper;

    @Override
    public Scale getScaleById(Long id) {
        Scale scale = scaleMapper.selectById(id);
        if (scale == null || scale.getDeleted() == 1) {
            throw BusinessException.of("SCALE_NOT_FOUND", "量表不存在");
        }
        return scale;
    }

    @Override
    public Scale getScaleByCode(String code) {
        LambdaQueryWrapper<Scale> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Scale::getScaleCode, code);
        Scale scale = scaleMapper.selectOne(wrapper);
        if (scale == null || scale.getDeleted() == 1) {
            throw BusinessException.of("SCALE_NOT_FOUND", "量表不存在");
        }
        return scale;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Scale createScale(ScaleCreateRequest request) {
        String scaleCode = EncryptUtils.generateUUID().substring(0, 12);

        Scale scale = new Scale();
        scale.setScaleCode(scaleCode);
        scale.setScaleName(request.getName());
        scale.setScaleDesc(request.getDescription());
        scale.setCategory(request.getCategory());
        scale.setCover(request.getCoverImage());
        scale.setEstimatedTime(request.getDuration());
        
        if (request.getPrice() != null) {
            scale.setPrice(request.getPrice());
        } else {
            scale.setPrice(BigDecimal.ZERO);
        }

        scale.setInstructions(request.getInstruction());
        scale.setAttention(null);
        scale.setStatus(0);
        scale.setIsFree(request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO) == 0 ? 1 : 0);
        scale.setViewCount(0);
        scale.setUseCount(0);
        scale.setCreateTime(LocalDateTime.now());
        scale.setUpdateTime(LocalDateTime.now());

        scaleMapper.insert(scale);
        return scale;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateScale(Long id, ScaleUpdateRequest request) {
        Scale scale = getScaleById(id);

        if (request.getName() != null) {
            scale.setScaleName(request.getName());
        }
        if (request.getDescription() != null) {
            scale.setScaleDesc(request.getDescription());
        }
        if (request.getCategory() != null) {
            scale.setCategory(request.getCategory());
        }
        if (request.getCoverImage() != null) {
            scale.setCover(request.getCoverImage());
        }
        if (request.getDuration() != null) {
            scale.setEstimatedTime(request.getDuration());
        }
        if (request.getPrice() != null) {
            scale.setPrice(request.getPrice());
            scale.setIsFree(request.getPrice().compareTo(BigDecimal.ZERO) == 0 ? 1 : 0);
        }
        if (request.getInstruction() != null) {
            scale.setInstructions(request.getInstruction());
        }
        if (request.getStatus() != null) {
            scale.setStatus(request.getStatus());
        }

        scale.setUpdateTime(LocalDateTime.now());
        scaleMapper.updateById(scale);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishScale(Long id) {
        Scale scale = getScaleById(id);
        scale.setStatus(1);
        scale.setPublishTime(LocalDateTime.now());
        scale.setUpdateTime(LocalDateTime.now());
        scaleMapper.updateById(scale);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void offlineScale(Long id) {
        Scale scale = getScaleById(id);
        scale.setStatus(0);
        scale.setUpdateTime(LocalDateTime.now());
        scaleMapper.updateById(scale);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteScale(Long id) {
        Scale scale = getScaleById(id);
        scale.setDeleted(1);
        scale.setUpdateTime(LocalDateTime.now());
        scaleMapper.updateById(scale);
    }

    @Override
    public PageResult<Scale> getScaleList(PageRequest request, Integer category, Integer status) {
        Page<Scale> page = new Page<>(request.getPageNum(), request.getPageSize());
        LambdaQueryWrapper<Scale> wrapper = new LambdaQueryWrapper<>();
        
        if (category != null) {
            wrapper.eq(Scale::getCategory, category);
        }
        if (status != null) {
            wrapper.eq(Scale::getStatus, status);
        }
        
        wrapper.orderByDesc(Scale::getCreateTime);
        IPage<Scale> result = scaleMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), request.getPageNum(), request.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrementViewCount(Long id) {
        Scale scale = getScaleById(id);
        scale.setViewCount(scale.getViewCount() + 1);
        scaleMapper.updateById(scale);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrementUseCount(Long id) {
        Scale scale = getScaleById(id);
        scale.setUseCount(scale.getUseCount() + 1);
        scaleMapper.updateById(scale);
    }
}
