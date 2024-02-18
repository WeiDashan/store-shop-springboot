package com.weidashan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weidashan.pojo.PmsStock;
import com.weidashan.mapper.PmsStockMapper;
import com.weidashan.service.IPmsStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * sku库存表 服务实现类
 * </p>
 *
 * @author weidashan
 * @since 2023-11-06
 */
@Service
public class PmsStockServiceImpl extends ServiceImpl<PmsStockMapper, PmsStock> implements IPmsStockService {

    @Override
    public boolean removeByProductId(Long productId) {
        QueryWrapper<PmsStock> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id",productId);
        return remove(wrapper);
    }

    @Override
    public List<PmsStock> getStockByProductId(Long productId) {
        QueryWrapper<PmsStock> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id",productId);
        return list(wrapper);
    }
}
