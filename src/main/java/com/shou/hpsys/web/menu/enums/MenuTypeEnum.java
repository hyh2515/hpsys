package com.shou.hpsys.web.menu.enums;

import com.shou.hpsys.common.exception.CommonException;
import lombok.Getter;

/**
 * @description: 菜单类型枚举
 * @author: Yaohui Hu
 * @date 2024/3/22 21:31
 */
@Getter
public enum MenuTypeEnum {

//    目录
    CATALOG("CATALOG"),

//    组件
    MENU("MENU"),

//    内链
    IFRAME("IFRAME"),

//    外链
    LINK("LINK");

    private final String value;

    MenuTypeEnum(String value) {
        this.value = value;
    }

    public static void validate(String value) {
        boolean flag = CATALOG.getValue().equals(value) || MENU.getValue().equals(value) || IFRAME.getValue().equals(value) ||
                LINK.getValue().equals(value);
        if(!flag) {
            throw new CommonException("不支持的菜单类型：{}", value);
        }
    }
}
