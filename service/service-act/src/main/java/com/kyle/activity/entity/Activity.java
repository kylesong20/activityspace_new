package com.kyle.activity.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kyle.util.IPUtils;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author kyle
 * @since 2023-01-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Activity对象", description="")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private String name;

    private String beginTime;

    private String endTime;

    private String reson;

    private String state;

    private String userId;

    private String img;

    private String externalLinks;

    private String open;

    @TableField(exist = false)
    private Boolean applied;
    @TableField(exist = false)
    private String venId;
    @TableField(exist = false)
    private String avId;

    public String getImg() {
        return "http://"+IPUtils.getServerIP() +":8001"+ img;
    }

}
