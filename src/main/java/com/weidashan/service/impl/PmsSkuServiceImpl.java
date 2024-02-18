package com.weidashan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weidashan.pojo.PmsSku;
import com.weidashan.mapper.PmsSkuMapper;
import com.weidashan.service.IPmsSkuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * sku属性表 服务实现类
 * </p>
 *
 * @author weidashan
 * @since 2023-11-01
 */
@Service
public class PmsSkuServiceImpl extends ServiceImpl<PmsSkuMapper, PmsSku> implements IPmsSkuService {
    @Override
    public List<PmsSku> list(Long categoryId) {
        QueryWrapper<PmsSku> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id",categoryId);
        return this.list(wrapper);
    }

    @Override
    public List<PmsSku> getByCategory(Long[] categoryIds) {
        QueryWrapper<PmsSku> wrapper = new QueryWrapper<>();
        wrapper.in("category_id", Arrays.asList(categoryIds));
        return this.list(wrapper);
    }
}
