package com.kyle.venue.service;

import com.kyle.venue.entity.VenueClock;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kyle.venue.entity.vo.VenueClockCount;
import com.kyle.venue.entity.vo.VenueClockWeek;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kyle
 * @since 2023-04-03
 */
public interface VenueClockService extends IService<VenueClock> {

    Map<String, Object> pageClock(long current, long limit, String userId);

    List<VenueClockCount> venueClockCount();

    List<VenueClockWeek> venueClockWeek(String venueId);
}
