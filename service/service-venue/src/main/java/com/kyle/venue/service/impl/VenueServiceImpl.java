package com.kyle.venue.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.venue.entity.Venue;
import com.kyle.venue.entity.vo.VenueGroupVo;
import com.kyle.venue.entity.vo.VenueQuery;
import com.kyle.venue.mapper.VenueMapper;
import com.kyle.venue.service.VenueService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
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
        venueQueryWrapper.eq("v.is_delete",0);
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

    @Value("${user.dir}")
    private String picDir;

    @Override
    public boolean syncMap() {
        List<Venue> list = venueMapper.selectList(new QueryWrapper<>());
        ArrayList<Object> features = new ArrayList<>();
        for (Venue venue : list) {
            if (!StringUtils.isEmpty(venue.getMapJson()))
                features.add(JSON.parseObject(venue.getMapJson()));
        }
        HashMap<String, ArrayList<Object>> map = new HashMap<>();
        map.put("features",features);
        String content = JSON.toJSONString(map, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        // 标记文件生成是否成功
        boolean flag = true;
        // 生成json格式文件
        try {
            // 保证创建一个新文件
            String filePath = picDir + "/service/service-venue/src/main/resources";
            String filePath1 = picDir + "/vue1002/src/geojson";
            String filePath2 = picDir + "/activityspace_front/static/geojson";

            File[] files = new File[]{
                new File(filePath+"/gdkj.json"),
                new File(filePath1+"/gdkj1.json"),
                new File(filePath2+"/gdkj2.json")
            };

            for (File file : files) {
                if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                    file.getParentFile().mkdirs();
                }
                if (file.exists()) { // 如果已存在,删除旧文件
                    file.delete();
                }
                file.createNewFile();
                // 将格式化后的字符串写入文件
                Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
                write.write(content);
                write.flush();
                write.close();
            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
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
