package com.kyle.venue.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kyle.venue.entity.Facility;
import com.kyle.venue.entity.vo.FacilityVenueVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kyle
 * @since 2022-12-31
 */
public interface FacilityMapper extends BaseMapper<Facility> {
    @Select("select vf.*,v.`name` venueName from venue v,venue_facility vf where vf.venue_id = v.id ${ew.customSqlSegment}")
    IPage<FacilityVenueVo> pageVenueGroupCondition(IPage<FacilityVenueVo> venuePage, @Param(Constants.WRAPPER) QueryWrapper<Facility> venueQueryWrapper);

    @Select("select vf.*,v.`name` venueName from venue v,venue_facility vf where vf.venue_id = v.id and vf.id = #{id} ")
    FacilityVenueVo getVenueGroupById(@Param("id") String id);
}
