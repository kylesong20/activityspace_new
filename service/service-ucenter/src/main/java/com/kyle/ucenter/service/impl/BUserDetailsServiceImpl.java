package com.kyle.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kyle.security.entity.SecurityBUser;
import com.kyle.ucenter.entity.BUser;
import com.kyle.ucenter.service.BUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 自定义userDetailsService - 认证用户详情
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Service("bUserDetailsService")
public class BUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BUserService userService;


    /***
     * 根据账号获取用户信息
     * @param username:
     * @return: org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        BUser user = userService.getOne(new QueryWrapper<BUser>().eq("num", username));

        // 判断用户是否存在
        if (null == user){
            //throw new UsernameNotFoundException("用户名不存在！");
        }
        // 返回UserDetails实现类
        com.kyle.security.entity.BUser curUser = new com.kyle.security.entity.BUser();
        BeanUtils.copyProperties(user,curUser);

        return new SecurityBUser(curUser);
    }

}
