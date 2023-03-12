package com.kyle.organization.mapper;

import com.kyle.organization.entity.Organization;
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

public interface OrganizationMapper extends BaseMapper<Organization> {
    @Select("SELECT introduction FROM organization_introduction WHERE id = #{id}")
    String getIntroduceById(@Param("id") String id);


}
