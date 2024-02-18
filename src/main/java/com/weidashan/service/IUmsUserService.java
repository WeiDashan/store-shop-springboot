package com.weidashan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weidashan.pojo.UmsUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author weidashan
 * @since 2023-10-22
 */
public interface IUmsUserService extends IService<UmsUser> {
    IPage<UmsUser> page(Integer pageNo, Integer pageSize, String name);
    List<UmsUser> getAll();
    Map<String,Object> login(String username, String password) throws Exception;

    UmsUser getUserByLoginName(String loginName);
}
