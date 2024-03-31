package com.shou.hpsys.common.handler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.shou.hpsys.common.utils.CommonCryptogramUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @description: TODO
 * @author: Yaohui Hu
 * @date 2024/3/17 15:59
 */
public class CommonSm4CbcTypeHandler<T> extends BaseTypeHandler {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, CommonCryptogramUtil.doSm4CbcEncrypt((String)o));
    }

    @SuppressWarnings("ALL")
    @Override
    public T getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String sValue = resultSet.getString(s);
        // 可能是空字符串
        return StringUtils.isBlank(sValue) ? (T)sValue : (T)CommonCryptogramUtil.doSm4CbcDecrypt(sValue);
    }

    @SuppressWarnings("ALL")
    @Override
    public T getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String sValue = resultSet.getString(i);
        return StringUtils.isBlank(sValue) ? (T)sValue : (T)CommonCryptogramUtil.doSm4CbcDecrypt(sValue);
    }

    @SuppressWarnings("ALL")
    @Override
    public T getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String sValue = callableStatement.getString(i);
        return StringUtils.isBlank(sValue) ? (T)sValue : (T)CommonCryptogramUtil.doSm4CbcDecrypt(sValue);
    }
}
