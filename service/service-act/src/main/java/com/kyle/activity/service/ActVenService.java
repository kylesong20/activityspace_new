package com.kyle.activity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kyle.activity.entity.ActVen;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kyle
 * @since 2023-01-08
 */
public interface ActVenService extends IService<ActVen> {

    ActVen actVenList(String activityId);
}
