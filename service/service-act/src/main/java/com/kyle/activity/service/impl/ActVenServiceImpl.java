package com.kyle.activity.service.impl;

import com.kyle.activity.entity.ActVen;
import com.kyle.activity.mapper.ActVenMapper;
import com.kyle.activity.service.ActVenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kyle
 * @since 2023-01-08
 */
@Service("actVenService")
public class ActVenServiceImpl extends ServiceImpl<ActVenMapper, ActVen> implements ActVenService {

}
