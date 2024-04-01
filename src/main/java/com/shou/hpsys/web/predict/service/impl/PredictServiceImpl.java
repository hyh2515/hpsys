package com.shou.hpsys.web.predict.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.shou.hpsys.common.exception.CommonException;
import com.shou.hpsys.web.predict.entity.ZipCode;
import com.shou.hpsys.web.predict.enums.ModelEnum;
import com.shou.hpsys.web.predict.param.ModelSelectParam;
import com.shou.hpsys.web.predict.service.PredictService;
import com.shou.hpsys.web.predict.service.ZipCodeService;
import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.*;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @description: 预测Service实现
 * @author: Yaohui Hu
 * @date 2024/3/29 1:35
 */
@Service
public class PredictServiceImpl implements PredictService {

    @Resource
    ZipCodeService zipCodeService;

    public static Evaluator evaluator;

    public static void initModel(String modelSelect) throws IOException, JAXBException, org.xml.sax.SAXException {

        String filename;
        if(modelSelect.equals(ModelEnum.LINEAR.getValue())) {
            filename = "src/main/resources/pmml/line_model.pmml";
        } else if(modelSelect.equals(ModelEnum.RIDGE.getValue())) {
            filename = "src/main/resources/pmml/xgb_model.pmml";
        } else if(modelSelect.equals(ModelEnum.LASSO.getValue())) {
            filename = "src/main/resources/pmml/random_forest_model.pmml";
        } else {
            throw new CommonException("无该模型，该模型名为：{}", modelSelect);
        }

        File file = new File(filename);
        InputStream inputStream = Files.newInputStream(file.toPath());
        PMML pmml = org.jpmml.model.PMMLUtil.unmarshal(inputStream);
        ModelEvaluatorBuilder modelEvaluatorBuilder = new ModelEvaluatorBuilder(pmml);
        evaluator = modelEvaluatorBuilder.build();
        evaluator.verify();
    }

    @Override
    public List<Double> prediction(ModelSelectParam modelSelectParam) throws JAXBException, IOException, SAXException {
        modelSelectParam.setCoordinateX(
                zipCodeService.getCoordinateByZipCode(modelSelectParam.getDistrict()).getKey());
        modelSelectParam.setCoordinateY(
                zipCodeService.getCoordinateByZipCode(modelSelectParam.getDistrict()).getValue());

        JSONObject feature = JSONUtil.parseObj(modelSelectParam);
        DecimalFormat df = new DecimalFormat("0.00");
        List<Double> priceList = new LinkedList<>();
        List<String> modelList = new ArrayList<>();
        modelList.add(ModelEnum.LINEAR.getValue());
        modelList.add(ModelEnum.RIDGE.getValue());
        modelList.add(ModelEnum.LASSO.getValue());

        for(String model: modelList) {
            initModel(model);
            List<? extends InputField> inputFields = evaluator.getInputFields();
            Map<FieldName, FieldValue> arguments = new LinkedHashMap<>();
            for(InputField inputField: inputFields){
                FieldName inputName = inputField.getName();
                String name = inputName.getValue();
                Object rawValue = Double.parseDouble(feature.getStr(name));
                FieldValue inputValue = inputField.prepare(rawValue);
                arguments.put(inputName, inputValue);
            }
            Map<FieldName, ?> results = evaluator.evaluate(arguments);
            priceList.add(Double.parseDouble((df.format(results.get(FieldName.create("price"))))));
        }
        return priceList;
    }
}
