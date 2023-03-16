package com.kyle.ucenter.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kyle.ucenter.entity.BUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kyle
 * @since 2022-12-29
 */
public interface BUserMapper extends BaseMapper<BUser> {

    @Select("select u.*,(case when o.leader_name is null then '0' else'1'end) as is_leader from `user` u left join organization o   on u.id = o.leader_id ${ew.customSqlSegment}")
    IPage<BUser> listPage(IPage<BUser> venuePage, @Param(Constants.WRAPPER) QueryWrapper<BUser> venueQueryWrapper);
}
