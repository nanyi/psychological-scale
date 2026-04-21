package com.iotsic.ps.thirdparty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iotsic.ps.thirdparty.entity.ThirdPartyCallback;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CallbackMapper extends BaseMapper<ThirdPartyCallback> {
}
