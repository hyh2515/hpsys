package com.shou.hpsys.web.predict.enums;

import lombok.Getter;

/**
 * @description: 模型参数
 * @author: Yaohui Hu
 * @date 2024/3/29 11:11
 */
@Getter
public enum ModelEnum {

    LINEAR("LINEAR"),
    RIDGE("RIDGE"),
    LASSO("LASSO");

    private final String value;

    ModelEnum(String value) {
        this.value = value;
    }
}
