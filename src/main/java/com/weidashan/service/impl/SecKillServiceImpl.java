package com.weidashan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weidashan.pojo.SecKill;
import com.weidashan.mapper.SecKillMapper;
import com.weidashan.pojo.UmsUser;
import com.weidashan.service.ISecKillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 秒杀表 服务实现类
 * </p>
 *
 * @author weidashan
 * @since 2024-02-25
 */
@Service
public class SecKillServiceImpl extends ServiceImpl<SecKillMapper, SecKill> implements ISecKillService {

    @Override
    public IPage<SecKill> page(Integer pageNo, Integer pageSize) {
        QueryWrapper<SecKill> wrapper = new QueryWrapper<>();
        return this.page(new Page<>(pageNo,pageSize),wrapper);
    }

    @Override
    public SecKill getSecKillByStockId(Long stockId) {
        QueryWrapper<SecKill> wrapper = new QueryWrapper<>();
        wrapper.eq("stock_id",stockId);
        return this.getOne(wrapper);
    }
}
