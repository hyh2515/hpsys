package com.shou.hpsys.web.role.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @description: 角色Id参数
 * @author: Yaohui Hu
 * @date 2024/3/17 17:13
 */
@Getter
@Setter
public class RoleIdParam {

    //    id
    @NotBlank(message = "id不能为空")
    private String id;
}
