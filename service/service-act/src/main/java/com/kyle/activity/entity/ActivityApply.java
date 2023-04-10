package com.kyle.activity.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ActivityApply对象", description="")
public class ActivityApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String actId;

    private String userId;

    @TableField(exist = false)
    private String userNum;

    @TableField(exist = false)
    private String userName;

    private Date clockTime;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(exist = false)
    private String actName;

    @TableField(exist = false)
    private String beginTime;

    @TableField(exist = false)
    private String endTime;

    @TableField(exist = false)
    private String state;

}
