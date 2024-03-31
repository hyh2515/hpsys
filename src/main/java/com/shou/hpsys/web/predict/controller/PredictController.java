package com.shou.hpsys.web.predict.controller;

import com.shou.hpsys.common.pojo.CommonResult;
import com.shou.hpsys.web.predict.param.ModelSelectParam;
import com.shou.hpsys.web.predict.service.PredictService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

/**
 * @description: 预测Controller
 * @author: Yaohui Hu
 * @date 2024/3/29 11:00
 */
@RestController
@Validated
public class PredictController {

    @Resource
    private PredictService predictService;

    @PostMapping("predict/getPredictPrice")
    public CommonResult<List<Double>> getPredictPrice(@RequestBody @Valid ModelSelectParam modelSelectParam) throws JAXBException, IOException, SAXException {
        return CommonResult.data(predictService.prediction(modelSelectParam));
    }
}
