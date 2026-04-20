package com.iotsic.ps.scale.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.api.dto.ScaleResponse;
import com.iotsic.ps.common.enums.ErrorCodeEnum;
import com.iotsic.ps.common.enums.StatusEnum;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.core.entity.Scale;
import com.iotsic.ps.core.entity.ScaleCategory;
import com.iotsic.ps.scale.dto.ScaleCreateRequest;
import com.iotsic.ps.scale.dto.ScaleUpdateRequest;
import com.iotsic.ps.scale.mapper.ScaleCategoryMapper;
import com.iotsic.ps.scale.mapper.ScaleMapper;
import com.iotsic.smart.framework.common.request.PageRequest;
import com.iotsic.smart.framework.common.response.PageResult;
import com.iotsic.smart.framework.encrypt.utils.EncryptUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScaleServiceImpl implements ScaleService {

    private final ScaleMapper scaleMapper;
    private final ScaleCategoryMapper scaleCategoryMapper;

    @Override
    public Scale getScaleById(Long id) {
        Scale scale = scaleMapper.selectById(id);
        if (scale == null || scale.getDeleted()) {
            throw BusinessException.of(ErrorCodeEnum.SCALE_NOT_FOUND.getCode(), "量表不存在");
        }
        return scale;
    }

    @Override
    public Scale getScaleByCode(String code) {
        LambdaQueryWrapper<Scale> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Scale::getScaleCode, code);
        Scale scale = scaleMapper.selectOne(wrapper);
        if (scale == null || scale.getDeleted()) {
            throw BusinessException.of(ErrorCodeEnum.SCALE_NOT_FOUND.getCode(), "量表不存在");
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
        scale.setScaleNameEn(request.getNameEn());
        scale.setCategoryId(request.getCategoryId());
        scale.setDescription(request.getDescription());
        scale.setCover(request.getCoverImage());
        scale.setDuration(request.getDuration());
        scale.setQuestionCount(0);
        scale.setDimensionCount(0);
        scale.setAgeRangeMin(request.getAgeRangeMin());
        scale.setAgeRangeMax(request.getAgeRangeMax());
        scale.setApplicableGender(request.getApplicableGender());
        scale.setTargetAudience(request.getTargetAudience());
        scale.setPlatformId(request.getPlatformId());
        scale.setThirdPartyId(request.getThirdPartyId());
        scale.setSourceType(request.getSourceType());
        
        if (request.getPrice() != null) {
            scale.setPrice(request.getPrice());
        } else {
            scale.setPrice(BigDecimal.ZERO);
        }

        scale.setInstruction(request.getInstruction());
        scale.setAttention(request.getAttention());
        scale.setStatus(StatusEnum.NORMAL.getCode());
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
        if (request.getNameEn() != null) {
            scale.setScaleNameEn(request.getNameEn());
        }
        if (request.getDescription() != null) {
            scale.setDescription(request.getDescription());
        }
        if (request.getCategoryId() != null) {
            scale.setCategoryId(request.getCategoryId());
        }
        if (request.getCoverImage() != null) {
            scale.setCover(request.getCoverImage());
        }
        if (request.getDuration() != null) {
            scale.setDuration(request.getDuration());
        }
        if (request.getAgeRangeMin() != null) {
            scale.setAgeRangeMin(request.getAgeRangeMin());
        }
        if (request.getAgeRangeMax() != null) {
            scale.setAgeRangeMax(request.getAgeRangeMax());
        }
        if (request.getApplicableGender() != null) {
            scale.setApplicableGender(request.getApplicableGender());
        }
        if (request.getTargetAudience() != null) {
            scale.setTargetAudience(request.getTargetAudience());
        }
        if (request.getSourceType() != null) {
            scale.setSourceType(request.getSourceType());
        }
        if (request.getThirdPartyId() != null) {
            scale.setPlatformId(request.getThirdPartyId());
        }
        if (request.getPlatformId() != null) {
            scale.setPlatformId(request.getPlatformId());
        }
        if (request.getPrice() != null) {
            scale.setPrice(request.getPrice());
            scale.setIsFree(request.getPrice().compareTo(BigDecimal.ZERO) == 0 ? 1 : 0);
        }
        if (request.getInstruction() != null) {
            scale.setInstruction(request.getInstruction());
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
        scale.setDeleted(true);
        scale.setUpdateTime(LocalDateTime.now());
        scaleMapper.updateById(scale);
    }

    @Override
    public PageResult<ScaleResponse> getScaleList(PageRequest request, Long categoryId, Integer status) {
        Page<Scale> page = new Page<>(request.getCurrent(), request.getSize());
        LambdaQueryWrapper<Scale> wrapper = new LambdaQueryWrapper<>();
        
        if (categoryId != null) {
            wrapper.eq(Scale::getCategoryId, categoryId);
        }
        if (status != null) {
            wrapper.eq(Scale::getStatus, status);
        }
        
        wrapper.orderByDesc(Scale::getCreateTime);
        IPage<Scale> result = scaleMapper.selectPage(page, wrapper);

        List<ScaleResponse> responseList = result.getRecords().stream().map(scale -> {
            ScaleResponse response = new ScaleResponse();
            BeanUtils.copyProperties(scale, response);
            ScaleCategory scaleCategory = scaleCategoryMapper.selectById(scale.getCategoryId());
            if (scaleCategory != null) {
                response.setCategoryId(scaleCategory.getId());
                response.setCategoryName(scaleCategory.getCategoryName());
            }
            return response;
        }).toList();

        return PageResult.of(responseList, result.getTotal());
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
