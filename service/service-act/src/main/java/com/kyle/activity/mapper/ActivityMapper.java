package com.kyle.activity.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kyle.activity.entity.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kyle
 * @since 2023-01-04
 */
public interface ActivityMapper extends BaseMapper<Activity> {
    @Select("select o.id from user u left join organization o on u.organization_id = o.id where u.id = #{userId}")
    String getOrgIdByUserId(@Param("userId") String userId);

    @Select("select a.* from activity a left join user u on a.user_id = u.id ${ew.customSqlSegment}")
    IPage<Activity> getPage(Page<Activity> activityPage, @Param(Constants.WRAPPER) QueryWrapper<Activity> activityQueryWrapper);
}
