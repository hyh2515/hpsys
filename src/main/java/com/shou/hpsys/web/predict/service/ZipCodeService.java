package com.shou.hpsys.web.predict.service;

import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shou.hpsys.web.predict.entity.ZipCode;
import com.shou.hpsys.web.predict.param.ZipCodeAddParam;
import com.shou.hpsys.web.predict.param.ZipCodeIdParam;

import java.util.List;

/**
 * @description: 邮政编码Service
 * @author: Yaohui Hu
 * @date 2024/3/28 10:56
 */
public interface ZipCodeService extends IService<ZipCode> {

    /**
     * @description: 增加邮政编码
     * @param: zipCodeAddParam
     * @return: void
     * @author Yaohui Hu
     * @date: 2024/3/28 11:13
     */
    void add(ZipCodeAddParam zipCodeAddParam);

    /**
     * @description: 邮政编码详情
     * @param: zipCodeIdParam
     * @return: com.shou.hpsys.web.predict.entity.ZipCode
     * @author Yaohui Hu
     * @date: 2024/3/28 11:18
     */
    public ZipCode detail(ZipCodeIdParam zipCodeIdParam);

    /**
     * @description: 邮政编码详情
     * @param: id
     * @return: com.shou.hpsys.web.predict.entity.ZipCode
     * @author Yaohui Hu
     * @date: 2024/3/28 11:18
     */
    public ZipCode queryEntity(String id);

    /**
     * @description: 根据邮政编码查询地区
     * @param: code
     * @return: java.lang.String
     * @author Yaohui Hu
     * @date: 2024/3/28 11:19
     */
    public String getDivisionByCode(String code);

    /**
     * @description: 根据地区查询邮政编码
     * @param: division
     * @return: java.lang.String
     * @author Yaohui Hu
     * @date: 2024/3/28 11:20
     */
    public String getCodeByDivision(String division);

    public List<String> getDivisionListByCodeList(List<String> codeList);

    /**
     * @description: 获取全部邮政编码
     * @param:
     * @return: java.lang.String
     * @author Yaohui Hu
     * @date: 2024/3/28 12:38
     */
    public List<ZipCode> getAllZipCode();

    public Pair<Double, Double> getCoordinateByZipCode(String code);
}
