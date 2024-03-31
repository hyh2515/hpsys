package com.shou.hpsys.web.predict.controller;

import com.shou.hpsys.common.pojo.CommonResult;
import com.shou.hpsys.web.predict.entity.HouseInfo;
import com.shou.hpsys.web.predict.param.DistrictAreaParam;
import com.shou.hpsys.web.predict.entity.ZipCode;
import com.shou.hpsys.web.predict.result.DistrictPriceResult;
import com.shou.hpsys.web.predict.result.HouseAvailStatResult;
import com.shou.hpsys.web.predict.result.HouseAverPriceStatResult;
import com.shou.hpsys.web.predict.service.HouseInfoService;
import com.shou.hpsys.web.predict.service.ZipCodeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @description: 房屋信息Controller
 * @author: Yaohui Hu
 * @date 2024/3/28 19:16
 */
@RestController
@Validated
public class HouseInfoController {

    @Resource
    private HouseInfoService houseInfoService;

    @Resource
    private ZipCodeService zipCodeService;

    /**
     * @description: 获取房源统计信息
     * @param:
     * @return: com.shou.hpsys.common.pojo.CommonResult<java.util.List < com.shou.hpsys.web.predict.result.HouseAvailStatResult>>
     * @author Yaohui Hu
     * @date: 2024/3/28 23:11
     */
    @GetMapping("/houseInfo/availStat")
    public CommonResult<List<HouseAvailStatResult>> availStat() {
        return CommonResult.data(houseInfoService.getAllHouseAvailStatList());
    }

    /**
     * @description: 获取均价统计信息
     * @param:
     * @return: com.shou.hpsys.common.pojo.CommonResult<java.util.List < com.shou.hpsys.web.predict.result.HouseAverPriceStatResult>>
     * @author Yaohui Hu
     * @date: 2024/3/28 23:11
     */
    @GetMapping("/houseInfo/averPrice")
    public CommonResult<List<HouseAverPriceStatResult>> averPrice() {
        return CommonResult.data(houseInfoService.getAllHouseAverPriceStatList());
    }

    @GetMapping("houseInfo/listZipCode")
    public CommonResult<List<ZipCode>> listZipCode() {
        return CommonResult.data(zipCodeService.getAllZipCode());
    }

    @PostMapping("houseInfo/getDistrictAverage")
    public CommonResult<List<DistrictPriceResult>> getDistrictAverage(@RequestBody @Valid DistrictAreaParam districtAreaParam) {
        return CommonResult.data(houseInfoService.getDistrictInfo(districtAreaParam));
    }

    @GetMapping("houseInfo/listHouseInfo")
    public CommonResult<List<HouseInfo>> listHouseInfo() {
        return CommonResult.data(houseInfoService.listHouseInfo());
    }
}
