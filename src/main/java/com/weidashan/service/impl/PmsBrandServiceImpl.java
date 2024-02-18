package com.weidashan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weidashan.pojo.PmsBrand;
import com.weidashan.mapper.PmsBrandMapper;
import com.weidashan.service.IPmsBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author weidashan
 * @since 2023-10-26
 */
@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements IPmsBrandService {
    @Override
    public IPage<PmsBrand> page(Integer pageNo, Integer pageSize, String name) {
        QueryWrapper<PmsBrand> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(name)) {
            wrapper.like("name",name);
        }
        return this.page(new Page<>(pageNo,pageSize),wrapper);
    }
    @Override
    public List<PmsBrand> getAll() {
        QueryWrapper<PmsBrand> wrapper = new QueryWrapper<>();
        wrapper.eq("active",1);
        return this.list(wrapper);
    }
}
