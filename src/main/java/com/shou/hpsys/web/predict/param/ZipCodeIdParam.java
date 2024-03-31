package com.shou.hpsys.web.predict.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @description: 邮政编码Id参数
 * @author: Yaohui Hu
 * @date 2024/3/28 11:15
 */
@Getter
@Setter
public class ZipCodeIdParam {

//    id
    @NotBlank(message = "id不能为空")
    private String id;
}
