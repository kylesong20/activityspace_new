package com.kyle.activity.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kyle.activity.entity.ActVen;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    @Select("select distinct a.*" +
            "from act_ven av left join activity a on av.act_id = a.id ${ew.customSqlSegment}")
    ActVen actVenList(@Param(Constants.WRAPPER) QueryWrapper<ActVen> act_id);

    @Select("select v.name from act_ven av left join venue v on av.ven_id = v.id ${ew.customSqlSegment}")
    List<String> getVenueList(@Param(Constants.WRAPPER) QueryWrapper<ActVen> eq);
}
