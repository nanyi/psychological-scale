package com.iotsic.ps.scale.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.utils.EncryptUtils;
import com.iotsic.ps.core.entity.Scale;
import com.iotsic.ps.scale.mapper.ScaleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

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
    public Scale createScale(Map<String, Object> params) {
        String scaleCode = EncryptUtils.generateUUID().substring(0, 12);

        Scale scale = new Scale();
        scale.setScaleCode(scaleCode);
        scale.setScaleName((String) params.get("scaleName"));
        scale.setScaleDesc((String) params.get("scaleDesc"));
        scale.setCategory((Integer) params.get("category"));
        scale.setCover((String) params.get("cover"));
        scale.setEstimatedTime((Integer) params.get("estimatedTime"));
        
        Object priceObj = params.get("price");
        if (priceObj != null) {
            scale.setPrice(new BigDecimal(priceObj.toString()));
        } else {
            scale.setPrice(BigDecimal.ZERO);
        }

        Object ageMin = params.get("ageRangeMin");
        if (ageMin != null) {
            scale.setAgeRangeMin(Integer.valueOf(ageMin.toString()));
        }

        Object ageMax = params.get("ageRangeMax");
        if (ageMax != null) {
            scale.setAgeRangeMax(Integer.valueOf(ageMax.toString()));
        }

        scale.setInstructions((String) params.get("instructions"));
        scale.setAttention((String) params.get("attention"));
        scale.setStatus(0);
        scale.setIsFree(priceObj == null || scale.getPrice().compareTo(BigDecimal.ZERO) == 0 ? 1 : 0);
        scale.setViewCount(0);
        scale.setUseCount(0);
        scale.setCreateTime(LocalDateTime.now());
        scale.setUpdateTime(LocalDateTime.now());

        scaleMapper.insert(scale);
        return scale;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateScale(Long id, Map<String, Object> params) {
        Scale scale = getScaleById(id);

        if (params.containsKey("scaleName")) {
            scale.setScaleName((String) params.get("scaleName"));
        }
        if (params.containsKey("scaleDesc")) {
            scale.setScaleDesc((String) params.get("scaleDesc"));
        }
        if (params.containsKey("category")) {
            scale.setCategory((Integer) params.get("category"));
        }
        if (params.containsKey("cover")) {
            scale.setCover((String) params.get("cover"));
        }
        if (params.containsKey("estimatedTime")) {
            scale.setEstimatedTime((Integer) params.get("estimatedTime"));
        }
        if (params.containsKey("price")) {
            scale.setPrice(new BigDecimal(params.get("price").toString()));
            scale.setIsFree(scale.getPrice().compareTo(BigDecimal.ZERO) == 0 ? 1 : 0);
        }
        if (params.containsKey("instructions")) {
            scale.setInstructions((String) params.get("instructions"));
        }
        if (params.containsKey("attention")) {
            scale.setAttention((String) params.get("attention"));
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
