package com.kyle.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kyle.activity.entity.ActVen;
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
}
