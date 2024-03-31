package com.shou.hpsys.web.predict.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.shou.hpsys.common.pojo.CommonEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 房屋信息实体
 * @author: Yaohui Hu
 * @date 2024/3/28 15:07
 */
@Getter
@Setter
@TableName("HOUSE_INFO")
public class HouseInfo extends CommonEntity {
    /**
     * id
     */
    private String id;

    /**
     * 均价
     */
    private Integer average;

    /**
     * 经度
     */
    private Double coordinateX;

    /**
     * 纬度
     */
    private Double coordinateY;

    /**
     * 装修情况
     */
    private Integer decorationCondition;

    /**
     * 房本备件
     */
    private String deed;

    /**
     * 配备电梯
     */
    private String elevator;

    /**
     * 购物
     */
    private String facility0;

    /**
     * 教育
     */
    private String facility1;

    /**
     * 交通
     */
    private String facility2;

    /**
     * 健身
     */
    private String facility3;

    /**
     * 绿化
     */
    private String facility4;

    /**
     * 医疗
     */
    private String facility5;

    /**
     * 所在楼层
     */
    private Integer level;

    /**
     * 楼层总数
     */
    private Integer total;

    /**
     * 建筑结构
     */
    private Integer framework;

    /**
     * 房屋年限
     */
    private Integer houseTerm;

    /**
     * 产权所属
     */
    private Integer ownership;

    /**
     * 房屋总价
     */
    private Integer price;

    /**
     * 房屋用途
     */
    private Integer purpose;

    /**
     * 每层楼住户数
     */
    private Integer apt;

    /**
     * 电梯数
     */
    private Integer lift;

    /**
     * 区域编码
     */
    private String district;

    /**
     * 交易权属
     */
    private Integer rights;

    /**
     * 建筑面积/㎡
     */
    private Double scale;

    /**
     * 户型结构
     */
    private String structure;

    /**
     * 浴室数
     */
    private Integer bath;

    /**
     * 厨房数
     */
    private Integer kitchen;

    /**
     * 卧室数
     */
    private Integer room;

    /**
     * 客厅数
     */
    private Integer saloon;
}
