package com.shou.hpsys.web.predict.service;

import com.shou.hpsys.web.predict.entity.HouseInfo;
import com.shou.hpsys.web.predict.param.ModelSelectParam;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

/**
 * @description: 预测Service
 * @author: Yaohui Hu
 * @date 2024/3/28 14:44
 */
public interface PredictService {

    /**
     * @description: 预测信息
     * @param:
     * @return: java.lang.Double
     * @author Yaohui Hu
     * @date: 2024/3/29 1:34
     */
    public List<Double> prediction(ModelSelectParam modelSelectParam) throws JAXBException, IOException, SAXException;
}
