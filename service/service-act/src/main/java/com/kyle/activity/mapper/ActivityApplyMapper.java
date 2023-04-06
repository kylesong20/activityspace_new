package com.kyle.activity.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kyle.activity.entity.ActivityApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kyle
 * @since 2023-04-06
 */
public interface ActivityApplyMapper extends BaseMapper<ActivityApply> {
    @Select("SELECT aa.*,u.num ,u.name FROM activity_apply aa left join user u on aa.user_id = u.id")
    void getPage(Page<ActivityApply> venuePage,@Param(Constants.WRAPPER) QueryWrapper<ActivityApply> wrapper);
}
