package com.kyle.venue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.venue.entity.Venue;
import com.kyle.venue.entity.vo.VenueGroupVo;
import com.kyle.venue.entity.vo.VenueQuery;
import com.kyle.venue.mapper.VenueMapper;
import com.kyle.venue.service.VenueService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kyle
 * @since 2022-12-31
 */
@Service
public class VenueServiceImpl extends ServiceImpl<VenueMapper, Venue> implements VenueService {

    @Override
    public Map<String, Object> pageVenueCondition(long current, long limit, VenueQuery venueQuery) {
        Page<Venue> venuePage = new Page<>(current, limit);
        QueryWrapper<Venue> venueQueryWrapper = pageCondition(venueQuery);

        page(venuePage,venueQueryWrapper);

        long total = venuePage.getTotal();
        List<Venue> records = venuePage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }

    @Resource
    VenueMapper venueMapper;

    @Override
    public Map<String, Object> pageVenueGroupCondition(long current, long limit, VenueGroupVo venueGroupVo, VenueQuery venueQuery) {
        Page<VenueGroupVo> venuePage = new Page<>(current, limit);
        QueryWrapper<Venue> venueQueryWrapper = pageCondition(venueQuery);
        IPage<VenueGroupVo> venueGroupVoIPage = venueMapper.pageVenueGroupCondition(venuePage, venueQueryWrapper);

        long total = venueGroupVoIPage.getTotal();
        List<VenueGroupVo> records = venueGroupVoIPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }

    @Override
    public VenueGroupVo getVenueGroupById(String id) {
        return venueMapper.getVenueGroupById(id);
    }

    private QueryWrapper<Venue> pageCondition(VenueQuery venueQuery){

        //构建条件
        QueryWrapper<Venue> wrapper = new QueryWrapper<>();

        //动态SQL
        String num = venueQuery.getNum();
        String name = venueQuery.getName();
        String state = venueQuery.getState();
        String groupId = venueQuery.getGroupId();
        String begin = venueQuery.getBegin();
        String end = venueQuery.getEnd();

        if (!StringUtils.isEmpty(num)){
            wrapper.like("num",num);
        }
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(state)){
            wrapper.eq("state",state);
        }
        if (!StringUtils.isEmpty(groupId)){
            wrapper.eq("group_id",groupId);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("create_time",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("create_time",end);
        }

        wrapper.orderByDesc("create_time");

        return wrapper;
    }
}
