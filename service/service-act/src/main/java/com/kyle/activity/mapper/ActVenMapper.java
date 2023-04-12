package com.kyle.activity.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kyle.activity.entity.ActVen;
import com.kyle.activity.entity.Activity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kyle
 * @since 2023-01-08
 */
public interface ActVenMapper extends BaseMapper<ActVen> {

    @Select("select distinct a.* from act_ven av left join activity a on av.act_id = a.id where a.id = #{actId}")
    ActVen actVenList(@Param("actId") String actId);

    @Select("select v.name from act_ven av left join venue v on av.ven_id = v.id where av.act_id = #{actId}")
    List<String> getVenueList(@Param("actId") String actId);

    @Select("select a.*, v.id as venId, av.id as avId from act_ven av left join venue v on v.id = av.ven_id left join activity a on av.act_id = a.id ${ew.customSqlSegment}")
    List<Activity> getActivitiesByVenueId(@Param(Constants.WRAPPER)QueryWrapper<Activity> eq);

    @Select("select a.* ,av.ven_id as venId from act_ven av left join activity a on av.act_id = a.id ${ew.customSqlSegment}")
    List<Activity> getVenueAble(@Param(Constants.WRAPPER)QueryWrapper<Activity> eq);
}
