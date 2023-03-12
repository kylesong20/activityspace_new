package com.kyle.ucenter.service;

import com.kyle.ucenter.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kyle.ucenter.entity.vo.LoginQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kyle
 * @since 2022-12-29
 */
public interface AdminService extends IService<Admin> {

    String login(LoginQuery loginQuery);
}
