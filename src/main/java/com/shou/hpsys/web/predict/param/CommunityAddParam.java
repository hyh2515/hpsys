package com.shou.hpsys.web.predict.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @description: 小区增加参数
 * @author: Yaohui Hu
 * @date 2024/3/28 12:01
 */
@Getter
@Setter
public class CommunityAddParam {

//    小区名
    @NotNull(message = "name不能为空")
    private String name;

//    X坐标
    @NotNull(message = "CoordX不能为空")
    private double CoordX;

//    Y坐标
    @NotNull(message = "CoordY不能为空")
    private double CoordY;
}
