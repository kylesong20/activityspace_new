package com.kyle.venue.service;

import com.kyle.venue.entity.VenUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kyle.venue.entity.vo.VenueQuery;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kyle
 * @since 2023-04-11
 */
public interface VenUserService extends IService<VenUser> {

    Map<String, Object> pageVenueStarCondition(long current, long limit, VenueQuery venueQuery, String userId);
}
