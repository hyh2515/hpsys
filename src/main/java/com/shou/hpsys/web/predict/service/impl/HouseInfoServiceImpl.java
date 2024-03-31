package com.shou.hpsys.web.predict.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shou.hpsys.common.exception.CommonException;
import com.shou.hpsys.web.predict.entity.ZipCode;
import com.shou.hpsys.web.predict.param.DistrictAreaParam;
import com.shou.hpsys.web.predict.entity.HouseInfo;
import com.shou.hpsys.web.predict.mapper.HouseInfoMapper;
import com.shou.hpsys.web.predict.param.HouseInfoAddParam;
import com.shou.hpsys.web.predict.result.DistrictPriceResult;
import com.shou.hpsys.web.predict.result.HouseAvailStatResult;
import com.shou.hpsys.web.predict.result.HouseAverPriceStatResult;
import com.shou.hpsys.web.predict.service.HouseInfoService;
import com.shou.hpsys.web.predict.service.ZipCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 房价信息Service实现
 * @author: Yaohui Hu
 * @date 2024/3/28 15:23
 */
@Service
public class HouseInfoServiceImpl extends ServiceImpl<HouseInfoMapper, HouseInfo> implements HouseInfoService {


    @Resource
    ZipCodeService zipCodeService;

    @Override
    public void add(HouseInfoAddParam houseInfoAddParam) {
        if(ObjectUtil.isNotEmpty((houseInfoAddParam))) {
            HouseInfo houseInfo = BeanUtil.toBean(houseInfoAddParam, HouseInfo.class);
            this.save(houseInfo);
        } else {
            throw new CommonException("房屋信息为空");
        }
    }

    @Override
    public List<HouseAvailStatResult> getAllHouseAvailStatList() {
        // 统计每个地区编码出现的次数
        Map<String, Integer> codeMap = new HashMap<>();
        List<String> zipCodeList = zipCodeService.getAllZipCode()
                .stream().map(ZipCode::getCode).collect(Collectors.toList());
        for(String zipCode : zipCodeList) {
            codeMap.put(zipCode, getAvailCountByZipCode(zipCode).intValue());
        }
        // 将地区代码转为地区编码
        List<HouseAvailStatResult> houseAvailStatResultList = new LinkedList<>();
        for(Map.Entry<String, Integer> entry : codeMap.entrySet()) {
            String division = zipCodeService.getDivisionByCode(entry.getKey());
            if(Objects.equals(division, "310000")) { // 表示暂无数据
                continue;
            }
            HouseAvailStatResult houseAvailStatResult = new HouseAvailStatResult();
            houseAvailStatResult.setDivision(division);
            houseAvailStatResult.setTotal(entry.getValue());
            houseAvailStatResultList.add(houseAvailStatResult);
        }
        return houseAvailStatResultList;
    }

    private Long getAvailCountByZipCode(String zipCode) {
        return this.count(new LambdaQueryWrapper<HouseInfo>().eq(HouseInfo::getDistrict, zipCode));
    }

    @Override
    public List<HouseAverPriceStatResult> getAllHouseAverPriceStatList() {
        List<HouseAverPriceStatResult> houseAverPriceStatResultList = new LinkedList<>();
        List<String> zipCodeList = zipCodeService.getAllZipCode()
                .stream().map(ZipCode::getCode).collect(Collectors.toList());
        DecimalFormat df = new DecimalFormat("0.00");
        for(String zipCode:zipCodeList) {
            String division = zipCodeService.getDivisionByCode(zipCode);
            if(Objects.equals(division, "310000")) {
                continue;
            }
            HouseAverPriceStatResult houseAverPriceStatResult = new HouseAverPriceStatResult();
            houseAverPriceStatResult.setAverPrice(
                    Double.parseDouble(df.format(getAverageByZipCode(zipCode))));
            houseAverPriceStatResult.setDivision(division);
            houseAverPriceStatResultList.add(houseAverPriceStatResult);
        }
        return houseAverPriceStatResultList;
    }

    private Double getAverageByZipCode(String zipCode) {
        LambdaQueryWrapper<HouseInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(HouseInfo::getAverage).eq(HouseInfo::getDistrict, zipCode);
        return this.listObjs(lambdaQueryWrapper, obj -> (Integer)obj)
                .stream().mapToDouble(i -> i).average().orElse(0.0);
    }

    @Override
    public HouseInfo queryEntity(String id) {
        HouseInfo houseInfo = this.getById(id);
        if(ObjectUtil.isEmpty(houseInfo)) {
            throw new CommonException("房屋信息不存在，id值为：{}", id);
        }
        return houseInfo;
    }

    @Override
    public List<DistrictPriceResult> getDistrictInfo(DistrictAreaParam districtAreaParam) {
        LambdaQueryWrapper<HouseInfo> lambdaQueryWrapper = new LambdaQueryWrapper<HouseInfo>();
        lambdaQueryWrapper.eq(HouseInfo::getDistrict, districtAreaParam.getZipCode());
        List<DistrictPriceResult> districtPriceResultList = new LinkedList<>();
        List<HouseInfo> houseInfoList = this.list(lambdaQueryWrapper);
        for(HouseInfo houseInfo : houseInfoList) {
            DistrictPriceResult districtPriceResult = new DistrictPriceResult();
            districtPriceResult.setPrice(houseInfo.getPrice());
            districtPriceResult.setScale(houseInfo.getScale());
            districtPriceResultList.add(districtPriceResult);
        }
        return districtPriceResultList;
    }

    @Override
    public List<HouseInfo> listHouseInfo() {
        return this.list().subList(0, 1000);
    }
}
