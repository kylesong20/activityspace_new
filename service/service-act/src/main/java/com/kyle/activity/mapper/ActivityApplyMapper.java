package com.kyle.activity.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kyle.activity.entity.ActivityApply;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kyle
 * @since 2023-04-06
 */
public interface ActivityApplyMapper extends BaseMapper<ActivityApply> {
    @Select("SELECT aa.*,u.num userNum ,u.name userName ,a.name actName ,a.begin_time beginTime ,a.end_time endTime " +
            "FROM activity_apply aa left join user u on aa.user_id = u.id left join activity a on aa.act_id = a.id ")
    IPage<ActivityApply> getPage(Page<ActivityApply> venuePage, @Param(Constants.WRAPPER) QueryWrapper<ActivityApply> wrapper);
}
