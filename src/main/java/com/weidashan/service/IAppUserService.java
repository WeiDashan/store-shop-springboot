package com.weidashan.service;

import com.weidashan.pojo.AppUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author weidashan
 * @since 2024-02-22
 */
public interface IAppUserService extends IService<AppUser> {
    AppUser getAppUserByEmail(String email);
}
