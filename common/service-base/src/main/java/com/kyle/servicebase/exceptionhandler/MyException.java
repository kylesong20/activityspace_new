package com.kyle.servicebase.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther kyle
 * @creat 2022-11-16:48
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    private String msg;

    @Override
    public String toString() {
        return "MyException{" +
                "message=" + this.getMessage() +
                ", code=" + code +
                '}';
    }
}
