package com.shou.hpsys.web.predict.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shou.hpsys.common.exception.CommonException;
import com.shou.hpsys.web.predict.entity.ZipCode;
import com.shou.hpsys.web.predict.mapper.ZipCodeMapper;
import com.shou.hpsys.web.predict.param.ZipCodeAddParam;
import com.shou.hpsys.web.predict.param.ZipCodeIdParam;
import com.shou.hpsys.web.predict.service.ZipCodeService;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 邮政编码实体
 * @author: Yaohui Hu
 * @date 2024/3/28 10:58
 */
@Service
public class ZipCodeServiceImpl extends ServiceImpl<ZipCodeMapper, ZipCode> implements ZipCodeService {

    @Override
    public void add(ZipCodeAddParam zipCodeAddParam) {
        checkParam(zipCodeAddParam);
        ZipCode zipCode = BeanUtil.toBean(zipCodeAddParam, ZipCode.class);
        this.save(zipCode);
    }

    @Override
    public ZipCode detail(ZipCodeIdParam zipCodeIdParam) {
        return queryEntity(zipCodeIdParam.getId());
    }

    @Override
    public ZipCode queryEntity(String id) {
        ZipCode zipCode = this.getById(id);
        if(ObjectUtil.isEmpty(zipCode)) {
            throw  new CommonException("邮政编码不存在，id值为：{}", id);
        }
        return zipCode;
    }

    @Override
    public String getDivisionByCode(String code) {
        ZipCode zipCode = this.getOne(new LambdaQueryWrapper<ZipCode>().eq(ZipCode::getCode, code));
        if(ObjectUtil.isNotEmpty((zipCode))) {
            return zipCode.getDivision();
        }
        return null;
    }

    @Override
    public String getCodeByDivision(String division) {
        ZipCode zipCode = this.getOne(new LambdaQueryWrapper<ZipCode>().eq(ZipCode::getDivision, division));
        if(ObjectUtil.isNotEmpty((zipCode))) {
            return zipCode.getCode();
        }
        return null;
    }

    @Override
    public List<String> getDivisionListByCodeList(List<String> codeList) {
        LambdaQueryWrapper<ZipCode> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(ZipCode::getCode, codeList);
        return this.list(lambdaQueryWrapper)
                .stream()
                .map(ZipCode::getDivision).collect(Collectors.toList());
    }

    @Override
    public List<ZipCode> getAllZipCode() {
        return this.list();
    }

    @Override
    public Pair<Double, Double> getCoordinateByZipCode(String code) {
        ZipCode zipCode = this.getOne(new LambdaQueryWrapper<ZipCode>().eq(ZipCode::getCode, code));
        return new Pair<>(zipCode.getCoordinateX(),zipCode.getCoordinateY());
    }

    private void checkParam(ZipCodeAddParam zipCodeAddParam) {
        if(this.count(new LambdaQueryWrapper<ZipCode>()
                .eq(ZipCode::getDivision, zipCodeAddParam.getDivision())) > 0) {
            throw new CommonException("存在重复地区，地区为：{}", zipCodeAddParam.getDivision());
        }
        if(this.count(new LambdaQueryWrapper<ZipCode>()
                .eq(ZipCode::getCode, zipCodeAddParam.getCode())) > 0) {
            throw new CommonException("存在重复邮政编码，邮政编码为：{}", zipCodeAddParam.getCode());
        }
    }
}
