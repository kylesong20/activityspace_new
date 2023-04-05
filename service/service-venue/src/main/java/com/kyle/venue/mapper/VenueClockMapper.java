package com.kyle.venue.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kyle.venue.entity.VenueClock;
import com.kyle.venue.entity.vo.VenueClockCount;
import com.kyle.venue.entity.vo.VenueClockWeek;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    @Select("SELECT\n" +
            "venue_id,\n" +
            "venue_name AS name,\n" +
            "DATE_FORMAT(create_time, '%w') AS day_of_week,\n" +
            "COUNT(1) AS value,\n" +
            "week(create_time) as w,\n" +
            "week(now()) as mw\n" +
            "FROM venue_clock\n" +
            "WHERE venue_id = #{venueId}\n" +
            "GROUP BY venue_id, venue_name, day_of_week\n" +
            "HAVING mw = w\n" +
            "ORDER BY day_of_week;")
    List<VenueClockWeek> venueClockWeek(@Param("venueId") String venueId);
}
