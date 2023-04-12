package com.kyle.ucenter.entity.vo;

import lombok.Data;

@Data
public class PasswordChange {
    private String oldValue;
    private String newValue;
    private String newValue2;
}
