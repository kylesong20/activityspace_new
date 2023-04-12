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

    @Select("SELECT v.id AS id, v.name AS name, COUNT(vc.user_id) AS value, vc.user_id AS user_id\n" +
            "FROM venue v\n" +
            "LEFT JOIN venue_clock vc ON v.id = vc.venue_id AND vc.create_time BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d 23:59:59')\n" +
            "GROUP BY v.id, v.name, vc.user_id")
//    @Select("select venue_id as id,venue_name as name, count(1) as value from venue_clock vc ${ew.customSqlSegment}")
    List<VenueClockCount> venueClockCount(@Param(Constants.WRAPPER) QueryWrapper<VenueClock> venueQueryWrapper);

    @Select("SELECT \n" +
            "venue_id, \n" +
            "venue_name AS name,\n" +
            "DATE_FORMAT(create_time, '%w') AS day_of_week,\n" +
            "COUNT(1) AS value, \n" +
            "week(create_time) as w, \n" +
            "week(now()) as mw \n" +
            "FROM venue_clock\n" +
            "WHERE venue_id = #{venueId} \n" +
            "AND week(create_time) = week(now()) \n" +
            "GROUP BY venue_id, venue_name,\n" +
            "DATE_FORMAT(create_time, '%w'),\n" +
            "week(create_time), \n" +
            "week(now()) ORDER BY day_of_week;")
    List<VenueClockWeek> venueClockWeek(@Param("venueId") String venueId);

    @Select("SELECT \n" +
            "DATE_FORMAT(create_time, '%w') AS day_of_week,\n" +
            "COUNT(1) AS value, \n" +
            "week(create_time) as w, \n" +
            "week(now()) as mw \n" +
            "FROM venue_clock\n" +
            "where week(create_time) = week(now()) \n" +
            "GROUP BY DATE_FORMAT(create_time, '%w'),\n" +
            "week(create_time), \n" +
            "week(now()) ORDER BY day_of_week;")
    List<VenueClockWeek> venueClockWeekAll();
}
