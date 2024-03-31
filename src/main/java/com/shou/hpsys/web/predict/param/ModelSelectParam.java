package com.shou.hpsys.web.predict.param;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: 模型选择
 * @author: Yaohui Hu
 * @date 2024/3/29 11:05
 */
@Getter
@Setter
public class ModelSelectParam {

    /**
     * 模型选择
     */
    private String modelSelect;

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
