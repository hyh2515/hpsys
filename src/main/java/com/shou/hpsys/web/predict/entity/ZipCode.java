package com.shou.hpsys.web.predict.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.shou.hpsys.common.pojo.CommonEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 邮政编码实体
 * @author: Yaohui Hu
 * @date 2024/3/28 10:48
 */
@Getter
@Setter
@TableName("ZIP_CODE")
public class ZipCode extends CommonEntity {

//    id
    private String id;

//    区域
    private String division;

//    邮政编码
    private  String code;

//    经度
    private Double coordinateX;

//    纬度
    private Double coordinateY;
}
