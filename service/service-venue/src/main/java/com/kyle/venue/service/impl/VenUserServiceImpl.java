package com.kyle.venue.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.venue.entity.VenUser;
import com.kyle.venue.entity.vo.VenueGroupVo;
import com.kyle.venue.entity.vo.VenueQuery;
import com.kyle.venue.mapper.VenUserMapper;
import com.kyle.venue.service.VenUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kyle
 * @since 2023-04-11
 */
@Service
public class VenUserServiceImpl extends ServiceImpl<VenUserMapper, VenUser> implements VenUserService {
    @Autowired
    VenUserMapper venUserMapper;

    @Override
    public Map<String, Object> pageVenueStarCondition(long current, long limit, VenueQuery venueQuery, String userId) {
        Page<VenUser> venuePage = new Page<>(current, limit);

        IPage<VenueGroupVo> venueGroupVoIPage = venUserMapper.pageVenueStarCondition(venuePage, userId);

        long total = venueGroupVoIPage.getTotal();
        List<VenueGroupVo> records = venueGroupVoIPage.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }
}
