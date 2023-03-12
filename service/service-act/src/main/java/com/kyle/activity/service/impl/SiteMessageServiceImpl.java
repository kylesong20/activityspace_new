package com.kyle.activity.service.impl;

import com.kyle.activity.entity.SiteMessage;
import com.kyle.activity.mapper.SiteMessageMapper;
import com.kyle.activity.service.SiteMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kyle
 * @since 2023-01-04
 */
@Service("siteMessageService")
public class SiteMessageServiceImpl extends ServiceImpl<SiteMessageMapper, SiteMessage> implements SiteMessageService {

}
