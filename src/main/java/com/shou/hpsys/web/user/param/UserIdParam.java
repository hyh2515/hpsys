package com.shou.hpsys.web.user.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @description: 用户Id参数
 * @author: Yaohui Hu
 * @date 2024/3/20 20:43
 */
@Getter
@Setter
public class UserIdParam {
//    id
    @NotBlank(message = "id不能为空")
    private String id;
}
