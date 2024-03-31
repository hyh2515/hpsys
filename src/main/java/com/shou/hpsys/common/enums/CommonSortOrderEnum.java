package com.shou.hpsys.common.enums;

import com.shou.hpsys.common.exception.CommonException;
import lombok.Getter;

/**
 * @description: 通用排序枚举
 * @author: Yaohui Hu
 * @date 2024/3/17 15:49
 */
@Getter
public enum CommonSortOrderEnum {

//    升序
    ASC("ASCEND"),
//    降序
    DESC("DESCEND");

    private final String value;

    CommonSortOrderEnum(String value) {
        this.value = value;
    }

    public static void validate(String value) {
        boolean flag = ASC.getValue().toLowerCase().equals(value) || DESC.getValue().toLowerCase().equals(value);
        if(!flag) {
            throw new CommonException("不支持该排序方式：{}", value);
        }
    }
}
