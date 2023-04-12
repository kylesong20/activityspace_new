package com.kyle.organization.mapper;

import com.kyle.organization.entity.Organization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kyle.security.entity.BUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kyle
 * @since 2022-12-29
 */

public interface OrganizationMapper extends BaseMapper<Organization> {
    @Select("SELECT introduction FROM organization_introduction WHERE id = #{id}")
    String getIntroduceById(@Param("id") String id);


    @Select("select o.*,oi.introduction from organization o left join organization_introduction oi on o.introduction_id = oi.id where o.id = #{id}")
    Organization getOrganizationIntroduce(@Param("id")String id);

    @Select("select id,name ,num,create_time from user where organization_id = #{id}")
    List<Map<String,Object>> getOrganizationMember(@Param("id")String id);
}
