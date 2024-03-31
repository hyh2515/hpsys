package com.shou.hpsys.web.predict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shou.hpsys.web.predict.param.DistrictAreaParam;
import com.shou.hpsys.web.predict.entity.HouseInfo;
import com.shou.hpsys.web.predict.param.HouseInfoAddParam;
import com.shou.hpsys.web.predict.result.DistrictPriceResult;
import com.shou.hpsys.web.predict.result.HouseAvailStatResult;
import com.shou.hpsys.web.predict.result.HouseAverPriceStatResult;

import java.util.List;

/**
 * @description: 房屋信息Service
 * @author: Yaohui Hu
 * @date 2024/3/28 15:19
 */
public interface HouseInfoService extends IService<HouseInfo> {

    /**
     * @description: 增加房屋信息
     * @param: houseInfoAddParam
     * @return: void
     * @author Yaohui Hu
     * @date: 2024/3/28 20:20
     */
    public void add(HouseInfoAddParam houseInfoAddParam);

    /**
     * @description: 获取房源统计信息
     * @param:
     * @return: java.util.List<com.shou.hpsys.web.predict.result.HouseAvailStatResult>
     * @author Yaohui Hu
     * @date: 2024/3/28 20:20
     */
    public List<HouseAvailStatResult> getAllHouseAvailStatList();

    /**
     * @description: 获取均价统计信息
     * @param:
     * @return: java.util.List<com.shou.hpsys.web.predict.result.HouseAverPriceStatResult>
     * @author Yaohui Hu
     * @date: 2024/3/28 20:20
     */
    public List<HouseAverPriceStatResult> getAllHouseAverPriceStatList();

    /**
     * @description: 获取房屋信息详情
     * @param:
     * @return: com.shou.hpsys.web.predict.entity.HouseInfo
     * @author Yaohui Hu
     * @date: 2024/3/29 10:20
     */
    public HouseInfo queryEntity(String id);

    /**
     * @description: 获取地区信息
     * @param: districtAreaParam
     * @return: java.util.List<com.shou.hpsys.web.predict.result.DistrictPriceResult>
     * @author Yaohui Hu
     * @date: 2024/3/29 21:11
     */
    public List<DistrictPriceResult> getDistrictInfo(DistrictAreaParam districtAreaParam);

    /**
     * @description: 返回全部房屋信息
     * @param:
     * @return: java.util.List<com.shou.hpsys.web.predict.entity.HouseInfo>
     * @author Yaohui Hu
     * @date: 2024/3/29 21:10
     */
    public List<HouseInfo> listHouseInfo();
}
