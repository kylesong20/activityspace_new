package com.kyle.venue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kyle.venue.entity.Venue;
import com.kyle.venue.entity.vo.VenueGroupVo;
import com.kyle.venue.entity.vo.VenueQuery;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kyle
 * @since 2022-12-31
 */
public interface VenueService extends IService<Venue> {

    Map<String, Object> pageVenueCondition(long current, long limit, VenueQuery venueQuery);

    Map<String, Object> pageVenueGroupCondition(long current, long limit, VenueGroupVo venueGroupVo, VenueQuery venueQuery);

    VenueGroupVo getVenueGroupById(String id);

    boolean syncMap();
}
