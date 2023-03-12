package com.kyle.activity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author kyle
 * @since 2023-01-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ActVen对象", description="")
public class ActVen implements Serializable {

    private static final long serialVersionUID = 1L;

    public ActVen(String actId,String venId){
        this.actId = actId;
        this.venId = venId;
    }

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String actId;

    private String venId;



}
