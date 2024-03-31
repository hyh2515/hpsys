package com.shou.hpsys.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaResponse;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.shou.hpsys.common.enums.CommonDeleteFlagEnum;
import com.shou.hpsys.handler.GlobalExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: mvc配置
 * @author: Yaohui Hu
 * @date 2024/3/17 17:30
 */
@Configuration
@MapperScan("com.shou.hpsys.**.mapper")
@Slf4j
public class WebMvcConfigure implements WebMvcConfigurer {

    /**
     * 注册跨域过滤器
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                // 指定拦截路由
                .addInclude("/**")

                // 前置函数：在每次认证函数之前执行
                .setBeforeAuth(obj -> {

                    // ---------- 设置跨域响应头 ----------
                    SaHolder.getResponse()

                            // 是否可以在iframe显示视图： DENY=不可以 | SAMEORIGIN=同域下可以 | ALLOW-FROM uri=指定域名下可以
                            // .setHeader("X-Frame-Options", "SAMEORIGIN")

                            // 是否启用浏览器默认XSS防护： 0=禁用 | 1=启用 | 1; mode=block 启用, 并在检查到XSS攻击时，停止渲染页面
                            .setHeader("X-XSS-Protection", "1; mode=block")
                            // 禁用浏览器内容嗅探
                            .setHeader("X-Content-Type-Options", "nosniff")
                            // 允许指定域访问跨域资源
                            .setHeader("Access-Control-Allow-Origin", "*")
                            // 允许所有请求方式
                            .setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")
                            // 有效时间
                            .setHeader("Access-Control-Max-Age", "3600")
                            // 允许的header参数
                            .setHeader("Access-Control-Allow-Headers", "*");

                    // 如果是预检请求，则立即返回到前端
                    SaRouter.match(SaHttpMethod.OPTIONS)
                            // OPTIONS预检请求，不做处理
                            .free(r -> {})
                            .back();
                })

                // 异常处理
                .setError(e -> {
                    // 由于过滤器中抛出的异常不进入全局异常处理，所以必须提供[异常处理函数]来处理[认证函数]里抛出的异常
                    // 在[异常处理函数]里的返回值，将作为字符串输出到前端，此处统一转为JSON输出前端
                    SaResponse saResponse = SaHolder.getResponse();
                    saResponse.setHeader(Header.CONTENT_TYPE.getValue(), ContentType.JSON + ";charset=" +  CharsetUtil.UTF_8);
                    return GlobalExceptionUtil.getCommonResult((Exception) e);
                });
    }

    /**
     * @description: Redis序列化
     * @param: redisConnectionFactory
     * @return: org.springframework.data.redis.core.RedisTemplate<java.lang.String, java.lang.Object>
     * @author Yaohui Hu
     * @date: 2024/3/17 21:05
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * @description: 自定义公共字段自动注入
     * @author Yaohui Hu
     * @date: 2024/3/17 17:40
     */
    @Component
    public static class CustomMetaObjectHandler implements MetaObjectHandler {
        /** 删除标志 */
        private static final String DELETE_FLAG = "deleteFlag";

        /** 创建人 */
        private static final String CREATE_USER = "createUser";

        /** 创建时间 */
        private static final String CREATE_TIME = "createTime";

        /** 更新人 */
        private static final String UPDATE_USER = "updateUser";

        /** 更新时间 */
        private static final String UPDATE_TIME = "updateTime";

        @Override
        public void insertFill(MetaObject metaObject) {
            try {
                //为空则设置deleteFlag
                Object deleteFlag = metaObject.getValue(DELETE_FLAG);
                if (ObjectUtil.isNull(deleteFlag)) {
                    setFieldValByName(DELETE_FLAG, EnumUtil.toString(CommonDeleteFlagEnum.NOT_DELETE), metaObject);
                }
            } catch (ReflectionException ignored) { }
            try {
                //为空则设置createUser
                Object createUser = metaObject.getValue(CREATE_USER);
                if (ObjectUtil.isNull(createUser)) {
                    setFieldValByName(CREATE_USER, this.getUserId(), metaObject);
                }
            } catch (ReflectionException ignored) { }
            try {
                //为空则设置createTime
                Object createTime = metaObject.getValue(CREATE_TIME);
                if (ObjectUtil.isNull(createTime)) {
                    setFieldValByName(CREATE_TIME, DateTime.now(), metaObject);
                }
            } catch (ReflectionException ignored) { }
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            try {
                //设置updateUser
                setFieldValByName(UPDATE_USER, this.getUserId(), metaObject);
            } catch (ReflectionException ignored) { }
            try {
                //设置updateTime
                setFieldValByName(UPDATE_TIME, DateTime.now(), metaObject);
            } catch (ReflectionException ignored) { }
        }

        /**
         * @description: 获取用户编号
         * @param: 
         * @return: java.lang.String
         * @author Yaohui Hu
         * @date: 2024/3/17 17:40
         */
        private String getUserId() {
            try {
                String loginId = StpUtil.getLoginIdAsString();
                if (ObjectUtil.isNotEmpty(loginId)) {
                    return loginId;
                } else {
                    return "-1";
                }
            } catch (Exception e) {
                return "-1";
            }
        }
    }
}
