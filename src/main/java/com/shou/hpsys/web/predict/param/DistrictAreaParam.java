package com.shou.hpsys.web.predict.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @description: 地区编码参数
 * @author: Yaohui Hu
 * @date 2024/3/29 12:39
 */
@Getter
@Setter
public class DistrictAreaParam {

//    地区编码
    @NotNull(message = "zipCode不能为空")
    private String zipCode;
}
