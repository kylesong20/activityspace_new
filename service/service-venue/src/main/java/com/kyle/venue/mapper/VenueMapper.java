package com.kyle.venue.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kyle.venue.entity.Venue;
import com.kyle.venue.entity.vo.VenueGroupVo;
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
public interface VenueMapper extends BaseMapper<Venue> {
    @Select("select v.*,vg.`name` groupName from venue v join venue_group vg on v.group_id = vg.id ${ew.customSqlSegment}")
    IPage<VenueGroupVo> pageVenueGroupCondition(IPage<VenueGroupVo> venuePage,@Param(Constants.WRAPPER) QueryWrapper<Venue> venueQueryWrapper);

    @Select("select v.*,vg.`name` groupName from venue v,venue_group vg where v.group_id = vg.id and v.id = #{id}  ")
    VenueGroupVo getVenueGroupById(@Param("id") String id);
}
