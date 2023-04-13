package com.kyle.ucenter.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.kyle.util.IPUtils;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author kyle
 * @since 2022-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="")
@TableName("user")
public class BUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    private Boolean isDelete;

    private String name;

    private String password;

    private String avatar;

    private String num;

    private String organizationId;

    private String organizationName;

    @TableField(exist = false)
    private char isLeader;

//    public String getAvatar() {
//        return "http://"+IPUtils.getServerIP()+ ":8001" + avatar;
//    }
//    public String getAvatarEnd() {
//        return avatar;
//    }
}
