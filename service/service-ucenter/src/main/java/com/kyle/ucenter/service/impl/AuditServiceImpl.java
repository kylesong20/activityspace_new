package com.kyle.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.ucenter.entity.Audit;
import com.kyle.ucenter.entity.vo.AuditQuery;
import com.kyle.ucenter.mapper.AuditMapper;
import com.kyle.ucenter.service.AuditService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kyle
 * @since 2022-12-30
 */
@Service
public class AuditServiceImpl extends ServiceImpl<AuditMapper, Audit> implements AuditService {

    @Override
    public Map<String, Object> pageAuditCondition(long current, long limit, AuditQuery auditQuery) {
        Page<Audit> auditPage = new Page<>(current, limit);

        //构建条件
        QueryWrapper<Audit> wrapper = new QueryWrapper<>();

        //动态SQL
        String num = auditQuery.getNum();
        String name = auditQuery.getName();
        String grade = auditQuery.getGrade();
        String begin = auditQuery.getBegin();
        String end = auditQuery.getEnd();

        if (!StringUtils.isEmpty(num)){
            wrapper.like("num",num);
        }
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(grade)){
            wrapper.eq("grade",grade);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("create_time",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("create_time",end);
        }

        wrapper.orderByDesc("create_time");

        page(auditPage,wrapper);

        long total = auditPage.getTotal();
        List<Audit> records = auditPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }
}
