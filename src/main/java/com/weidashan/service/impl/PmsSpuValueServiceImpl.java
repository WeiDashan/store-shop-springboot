package com.weidashan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weidashan.pojo.PmsSpuValue;
import com.weidashan.mapper.PmsSpuValueMapper;
import com.weidashan.service.IPmsSpuValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品spu值 服务实现类
 * </p>
 *
 * @author weidashan
 * @since 2023-11-06
 */
@Service
public class PmsSpuValueServiceImpl extends ServiceImpl<PmsSpuValueMapper, PmsSpuValue> implements IPmsSpuValueService {

    @Override
    public boolean removeByProductId(Long productId) {
        QueryWrapper<PmsSpuValue> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id",productId);
        return remove(wrapper);
    }

    @Override
    public List<PmsSpuValue> getSpuValuesByProductId(Long productId) {
        QueryWrapper<PmsSpuValue> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id",productId);
        return list(wrapper);
    }


}
