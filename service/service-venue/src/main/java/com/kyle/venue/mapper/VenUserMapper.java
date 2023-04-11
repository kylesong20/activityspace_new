package com.kyle.venue.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kyle.venue.entity.VenUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kyle.venue.entity.vo.VenueGroupVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kyle
 * @since 2023-04-11
 */
public interface VenUserMapper extends BaseMapper<VenUser> {
    @Select("select v.* from ven_user vu left join venue v on vu.ven_id = v.id left join user u on vu.user_id = u.id where u.id = #{userId}")
    IPage<VenueGroupVo> pageVenueStarCondition(Page<VenUser> venuePage,@Param("userId") String userId);
}
