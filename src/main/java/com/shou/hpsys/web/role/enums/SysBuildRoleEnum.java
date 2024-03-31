package com.shou.hpsys.web.role.enums;

import com.shou.hpsys.common.exception.CommonException;
import lombok.Getter;

/**
 * @description: TODO
 * @author: Yaohui Hu
 * @date 2024/3/21 19:32
 */
@Getter
public enum SysBuildRoleEnum {
//    超管角色编码
    BUILD_IN_ROLE_CODE("Admin", "超管"),

    BUILD_INT_ROLE_CODE_USER("User", "普通用户");

    private final String value;

    private final String name;

    SysBuildRoleEnum(String value, String name) {
        this.value=value;
        this.name=name;
    }

    public static void validate(String value) {
        boolean flag = BUILD_IN_ROLE_CODE.getValue().toLowerCase().equals(value) || BUILD_INT_ROLE_CODE_USER.getValue().toLowerCase().equals(value);
        if(!flag) {
            throw new CommonException("不是系统对应的角色：{}", value);
        }
    }
}
