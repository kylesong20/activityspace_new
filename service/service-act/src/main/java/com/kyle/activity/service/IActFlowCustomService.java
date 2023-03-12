package com.kyle.activity.service;

/**
 * @auther kyle
 * @creat 2023-01-18:12
 */

import java.util.Map;

/**
 * 业务service 的接口 必须实现接口 实现其方法
 */
public interface IActFlowCustomService {
    /**
     * 设置流程变量
     *
     * @param activityId
     * @param aid
     * @param venueIds
     * @return
     */
//    public Map<String, Object> setvariables(String activityId,String userId, String aid, String bid);
    public Map<String, Object> setvariables(String activityId, String userId, String aid, String[] venueIds);


    /**
     * 整个流程开始时需要执行的任务
     * @param id
     */
    public void startRunTask(String id);


    /**
     * 整个流程结束需要执行的任务
     * @param id
     */
    public void endRunTask(String id);
}
