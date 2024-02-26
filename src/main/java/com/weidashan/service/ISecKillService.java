package com.weidashan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weidashan.pojo.SecKill;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 秒杀表 服务类
 * </p>
 *
 * @author weidashan
 * @since 2024-02-25
 */
public interface ISecKillService extends IService<SecKill> {
    IPage<SecKill> page(Integer pageNo, Integer pageSize);
}
