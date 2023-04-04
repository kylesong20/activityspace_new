package com.kyle.venue.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kyle.venue.entity.Venue;
import com.kyle.venue.entity.VenueClock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kyle.venue.entity.vo.VenueClockCount;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kyle
 * @since 2023-04-03
 */
public interface VenueClockMapper extends BaseMapper<VenueClock> {

    @Select("select id,name,count(1) value from (select user_id,venue_id as id,venue_name as name, count(1) as value from venue_clock vc ${ew.customSqlSegment}) a GROUP BY id,name")
//    @Select("select venue_id as id,venue_name as name, count(1) as value from venue_clock vc ${ew.customSqlSegment}")
    List<VenueClockCount> venueClockCount(@Param(Constants.WRAPPER) QueryWrapper<VenueClock> venueQueryWrapper);
}
