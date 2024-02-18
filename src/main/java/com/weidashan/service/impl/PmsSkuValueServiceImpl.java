package com.weidashan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weidashan.pojo.PmsSkuValue;
import com.weidashan.mapper.PmsSkuValueMapper;
import com.weidashan.service.IPmsSkuValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品sku值 服务实现类
 * </p>
 *
 * @author weidashan
 * @since 2023-11-06
 */
@Service
public class PmsSkuValueServiceImpl extends ServiceImpl<PmsSkuValueMapper, PmsSkuValue> implements IPmsSkuValueService {

    @Override
    public boolean removeByProductId(Long productId) {
        QueryWrapper<PmsSkuValue> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id",productId);
        return remove(wrapper);
    }

    @Override
    public List<PmsSkuValue> getSkuValuesByProductId(Long productId) {
        QueryWrapper<PmsSkuValue> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id",productId);
        return list(wrapper);
    }
}
