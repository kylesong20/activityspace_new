package com.kyle.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.activity.entity.Activity;
import com.kyle.activity.entity.ActivityApply;
import com.kyle.activity.mapper.ActivityApplyMapper;
import com.kyle.activity.mapper.ActivityMapper;
import com.kyle.activity.service.ActivityService;
import com.kyle.activity.entity.vo.ActivityQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kyle
 * @since 2023-01-04
 */
@Service("activityService")
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
    @Autowired
    private ActivityApplyMapper activityApplyMapper;
    @Autowired
    private ActivityMapper activityMapper;
    /**
     * 设置出差申请的 流程变量
     *
     * @param activityId
     * @param aid
     * @return
     */
    @Override
    public Map<String, Object> setvariables(String activityId, String userId, String aid, String[] venueIds) {
        Activity activity = getById(activityId);

        //设置流程变量
        Map<String,Object> variables = new HashMap<>();

        //设置活动时长
        String endTime = activity.getEndTime();
        String beginTime = activity.getBeginTime();
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        try {
            Date beginDate = sdf.parse(beginTime);
            Date endDate = sdf.parse(endTime);
            long diff = endDate.getTime() - beginDate.getTime();
            long hours = diff / (1000 * 60 * 60);
            long minutes = diff/(60 * 1000) % 60;
            if (minutes!=0){
                hours += 1;
            }
            variables.put("hours",hours);

            //设置审批到期时间 activity.beginTime
            variables.put("dueDate",beginDate);
            //设置活动开始时间
            variables.put("begin",beginDate);
            //设置活动结束时间
            variables.put("end",endDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }



        variables.put("assignee0",userId);
        variables.put("assignee1",aid);
        variables.put("venueIds",venueIds);
        variables.put("activity",activity);
        return variables;
    }

    @Override
    public void startRunTask(String id) {
        UpdateWrapper<Activity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id).set("state","1");
        update(updateWrapper);
    }

    @Override
    public void endRunTask(String id) {
        UpdateWrapper<Activity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id).set("state","2");
        update(updateWrapper);
    }

    @Override
    public Map<String, Object> pageActivityCondition(long current, long limit, ActivityQuery activityQuery,String userId) {
        if (StringUtils.isEmpty(activityQuery.getOrganizationId())){
            String orgId = activityMapper.getOrgIdByUserId(userId);
            activityQuery.setOrganizationId(orgId);
        }else {
            activityQuery.setOrganizationId(null);
        }
        Page<Activity> activityPage = new Page<>(current, limit);
        QueryWrapper<Activity> activityQueryWrapper = pageCondition(activityQuery);
        activityQueryWrapper.ne("state",0);
//        page(activityPage,activityQueryWrapper);
        IPage<Activity> page = activityMapper.getPage(activityPage, activityQueryWrapper);
        long total = page.getTotal();
        List<Activity> records = page.getRecords();

        List<ActivityApply> applies = activityApplyMapper.selectList(new QueryWrapper<ActivityApply>().eq("user_id", userId));
        for (Activity activity : records) {
            for (ActivityApply apply : applies) {
                if (Objects.equals(activity.getId(), apply.getActId())){
                    activity.setApplied(true);
                    break;
                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }

    @Override
    public Map<String, Object> pageUserActivityCondition(long current, long limit, ActivityQuery activityQuery,String userId) {
        Page<Activity> activityPage = new Page<>(current, limit);
        QueryWrapper<Activity> venueQueryWrapper = pageCondition(activityQuery);
        venueQueryWrapper.eq("user_id", userId);
        page(activityPage,venueQueryWrapper);

        long total = activityPage.getTotal();
        List<Activity> records = activityPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }
    private QueryWrapper<Activity> pageCondition(ActivityQuery activityQuery){

        //构建条件
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();

        //动态SQL
        String name = activityQuery.getName();
        String state = activityQuery.getState();
        String begin = activityQuery.getBegin();

        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(activityQuery.getOpen())){
            wrapper.eq("open",activityQuery.getOpen());
        }
        if (!StringUtils.isEmpty(activityQuery.getOrganizationId())){
            wrapper.eq("u.organization_id",activityQuery.getOrganizationId());
        }
        if (!StringUtils.isEmpty(state)){
            if (!StringUtils.isEmpty(activityQuery.getSelectType())&&"LE".equals(activityQuery.getSelectType())){
                wrapper.le("state",state);
            }else {
                wrapper.eq("state",state);
            }
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("begin_time",begin);
        }

        wrapper.orderByDesc("create_time");

        return wrapper;
    }
}
