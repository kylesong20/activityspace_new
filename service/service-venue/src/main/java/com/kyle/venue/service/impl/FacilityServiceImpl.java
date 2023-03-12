package com.kyle.venue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.venue.entity.Facility;
import com.kyle.venue.entity.vo.FacilityQuery;
import com.kyle.venue.entity.vo.FacilityVenueVo;
import com.kyle.venue.mapper.FacilityMapper;
import com.kyle.venue.service.FacilityService;
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
public class FacilityServiceImpl extends ServiceImpl<FacilityMapper, Facility> implements FacilityService {

    @Override
    public Map<String, Object> pageFacilityCondition(long current, long limit, FacilityQuery facilityQuery) {
        Page<Facility> facilityPage = new Page<>(current, limit);
        QueryWrapper<Facility> facilityQueryWrapper = pageCondition(facilityQuery);

        page(facilityPage,facilityQueryWrapper);

        long total = facilityPage.getTotal();
        List<Facility> records = facilityPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }

    @Resource
    FacilityMapper facilityMapper;

    @Override
    public Map<String, Object> pageFacilityVenueCondition(long current, long limit, FacilityVenueVo facilityVenueVo, FacilityQuery facilityQuery) {
        Page<FacilityVenueVo> facilityPage = new Page<>(current, limit);
        QueryWrapper<Facility> facilityQueryWrapper = pageCondition(facilityQuery);
        IPage<FacilityVenueVo> facilityVenueVoIPage = facilityMapper.pageVenueGroupCondition(facilityPage, facilityQueryWrapper);

        long total = facilityVenueVoIPage.getTotal();
        List<FacilityVenueVo> records = facilityVenueVoIPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }

    @Override
    public FacilityVenueVo getFacilityVenueById(String id) {
        return facilityMapper.getVenueGroupById(id);
    }

    private QueryWrapper<Facility> pageCondition(FacilityQuery facilityQuery){

        //构建条件
        QueryWrapper<Facility> wrapper = new QueryWrapper<>();

        //动态SQL
        String name = facilityQuery.getName();
        String state = facilityQuery.getState();
        String venueId = facilityQuery.getVenueId();
        String begin = facilityQuery.getBegin();
        String end = facilityQuery.getEnd();

        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(state)){
            wrapper.eq("state",state);
        }
        if (!StringUtils.isEmpty(venueId)){
            wrapper.eq("venue_id",venueId);
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
