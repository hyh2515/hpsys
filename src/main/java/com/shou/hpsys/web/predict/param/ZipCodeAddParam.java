package com.shou.hpsys.web.predict.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 邮政编码增加参数
 * @author: Yaohui Hu
 * @date 2024/3/28 11:00
 */
@Getter
@Setter
public class ZipCodeAddParam {

//    区域
    @NotBlank(message = "区域不能为空")
    private String division;

//    邮政编码
    @NotNull(message = "邮政编码不能为空")
    private String code;
}
