package com.iotsic.ps.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iotsic.ps.order.entity.Cart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper extends BaseMapper<Cart> {
}
