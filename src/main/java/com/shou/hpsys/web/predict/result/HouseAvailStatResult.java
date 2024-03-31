package com.shou.hpsys.web.predict.result;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: 房源信息结果
 * @author: Yaohui Hu
 * @date 2024/3/28 18:49
 */
@Getter
@Setter
public class HouseAvailStatResult {

    //    地区
    private String division;

    //    房源数量
    private Integer total;
}
