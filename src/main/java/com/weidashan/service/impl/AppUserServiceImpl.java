package com.weidashan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weidashan.pojo.AppUser;
import com.weidashan.mapper.AppUserMapper;
import com.weidashan.service.IAppUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author weidashan
 * @since 2024-02-22
 */
@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements IAppUserService {

    @Override
    public AppUser getAppUserByEmail(String email) {
        QueryWrapper<AppUser> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        return this.getOne(wrapper);
    }

}
