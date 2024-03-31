package com.shou.hpsys.web.user.enums;

import lombok.Getter;

/**
 * @description: TODO
 * @author: Yaohui Hu
 * @date 2024/3/21 14:00
 */
@Getter
public enum SysBuildUserEnum {
//    超管账户
    BUILD_IN_USER_ACCOUNT("Admin", "超管");

    private final String value;

    private final String name;

    SysBuildUserEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
