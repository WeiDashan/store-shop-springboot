package com.weidashan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weidashan.pojo.AppOrder;
import com.weidashan.mapper.AppOrderMapper;
import com.weidashan.service.IAppOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author weidashan
 * @since 2024-02-22
 */
@Service
public class AppOrderServiceImpl extends ServiceImpl<AppOrderMapper, AppOrder> implements IAppOrderService {

    @Override
    public List<AppOrder> getAllOrdersByUserId(Long userId) {
        QueryWrapper<AppOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return list(wrapper);
    }

    @Override
    public List<AppOrder> getAllOrdersByUserIdAndStatus(Long userId, Integer orderStatus) {
        QueryWrapper<AppOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId).eq("order_status", orderStatus);
        return list(wrapper);
    }

    @Override
    public AppOrder getOrderBySecKillIdAndUserId(Long secKillId, Long userId) {
        QueryWrapper<AppOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("sec_kill_id", secKillId).eq("user_id", userId);
        return getOne(wrapper);
    }
}
