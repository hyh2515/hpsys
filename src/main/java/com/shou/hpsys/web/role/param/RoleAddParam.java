package com.shou.hpsys.web.role.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 角色添加参数
 * @author: Yaohui Hu
 * @date 2024/3/21 19:12
 */
@Getter
@Setter
public class RoleAddParam {

//    名称
    @NotBlank(message = "name不能为空")
    private String name;

//    排序码
    @NotNull(message = "sortCode不能为空")
    private Integer sortCode;
}
