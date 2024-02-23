package com.weidashan.service;

import com.weidashan.pojo.AppOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author weidashan
 * @since 2024-02-22
 */
public interface IAppOrderService extends IService<AppOrder> {
    List<AppOrder> getAllOrdersByUserId(Long userId);
    List<AppOrder> getAllOrdersByUserIdAndStatus(Long userId, Integer orderStatus);
}
