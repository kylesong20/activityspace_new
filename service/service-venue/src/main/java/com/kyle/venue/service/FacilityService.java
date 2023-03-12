package com.kyle.venue.service;

import com.kyle.venue.entity.Facility;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kyle.venue.entity.vo.FacilityQuery;
import com.kyle.venue.entity.vo.FacilityVenueVo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kyle
 * @since 2022-12-31
 */
public interface FacilityService extends IService<Facility> {

    Map<String, Object> pageFacilityCondition(long current, long limit, FacilityQuery facilityQuery);

    Map<String, Object> pageFacilityVenueCondition(long current, long limit, FacilityVenueVo facilityVenueVo, FacilityQuery facilityQuery);

    FacilityVenueVo getFacilityVenueById(String id);
}
