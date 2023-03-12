package com.kyle.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.servicebase.exceptionhandler.MyException;
import com.kyle.ucenter.entity.Admin;
import com.kyle.ucenter.entity.vo.LoginQuery;
import com.kyle.ucenter.mapper.AdminMapper;
import com.kyle.ucenter.service.AdminService;
import com.kyle.util.JwtUtils;
import com.kyle.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kyle
 * @since 2022-12-29
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    public String login(LoginQuery loginQuery) {

        String username = loginQuery.getUsername();
        String password = loginQuery.getPassword();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new MyException(20001,"登录失败");
        }

        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("num",username);
        Admin admin = baseMapper.selectOne(wrapper);

        if (admin == null){
            throw new MyException(20001,"找不到该账号");
        }

        if (!MD5.encrypt(password).equals(admin.getPassword())){
            throw new MyException(20001,"密码错误");
        }

        if (admin.getIsDelete()){
            throw new MyException(20001,"用户已注销");
        }

        return JwtUtils.getJwtToken(admin.getId(), admin.getName(),admin.getAvatar());

    }
}
