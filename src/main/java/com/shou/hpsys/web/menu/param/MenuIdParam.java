package com.shou.hpsys.web.menu.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @description: 菜单Id参数
 * @author: Yaohui Hu
 * @date 2024/3/22 16:44
 */
@Getter
@Setter
public class MenuIdParam {
//    id
    @NotBlank(message = "id不能为空")
    private String id;
}
