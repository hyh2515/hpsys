package com.shou.hpsys.web.relation.enums;

import lombok.Getter;

/**
 * @description: TODO
 * @author: Yaohui Hu
 * @date 2024/3/23 14:10
 */
@Getter
public enum RelationCategoryEnum {
//    用户拥有角色
    USER_ROLE("USER_ROLE"),
//    用户拥有菜单
    USER_MENU("USER_MENU"),
//    角色拥有资源
    ROLE_MENU("ROLE_MENU");

    private final String value;

    RelationCategoryEnum(String value) {
        this.value = value;
    }
}
