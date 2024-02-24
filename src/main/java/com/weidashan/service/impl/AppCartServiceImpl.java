package com.weidashan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.weidashan.pojo.AppCart;
import com.weidashan.mapper.AppCartMapper;
import com.weidashan.service.IAppCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author weidashan
 * @since 2024-02-24
 */
@Service
public class AppCartServiceImpl extends ServiceImpl<AppCartMapper, AppCart> implements IAppCartService {

    @Override
    public List<AppCart> getAllCartByUserId(Long userId) {

        QueryWrapper<AppCart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);

        return list(wrapper);
    }

    @Override
    public boolean updateProductNumByCartId(Long cartId, Integer productNum) {
        LambdaUpdateWrapper<AppCart> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AppCart::getId, cartId)
                .set(AppCart::getProductNum, productNum);
        return update(wrapper);
    }
}
