package com.shou.hpsys.common.annotation;

import java.lang.annotation.*;

/**
 * @description: TODO
 * @author: Yaohui Hu
 * @date 2024/3/22 19:48
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommonLog {

    /**
     * @description: 日志名称
     * @param: 
     * @return: java.lang.String
     * @author Yaohui Hu
     * @date: 2024/3/22 19:49
     */
    String value() default "未命名";
}
