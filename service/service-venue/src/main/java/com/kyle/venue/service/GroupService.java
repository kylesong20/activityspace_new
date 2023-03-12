package com.kyle.venue.service;

import com.kyle.venue.entity.Group;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kyle.venue.entity.vo.GroupQuery;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kyle
 * @since 2022-12-31
 */
public interface GroupService extends IService<Group> {

    Map<String, Object> pageGroupCondition(long current, long limit, GroupQuery groupQuery);
}
