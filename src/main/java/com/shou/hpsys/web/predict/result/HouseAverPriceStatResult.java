package com.shou.hpsys.web.predict.result;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: 房屋均价统计结果
 * @author: Yaohui Hu
 * @date 2024/3/28 20:16
 */
@Getter
@Setter
public class HouseAverPriceStatResult {

//    地区
    private String division;

//    房屋均价
    private Double averPrice;
}
