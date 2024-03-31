hpsys
├─hpsys-admin-web              -- 前端设计(vite框架+vue)
│  │  index.html               -- 主页面
│  └─src
│      │  App.vue
│      │  hpsys.js             -- 前端全局js
│      │  main.js
│      ├─api                   -- 连接后端的api接口
│      ├─components            -- 组件，页面中复用
│      ├─config                -- 基础配置，如项目名称、后端接口地址、项目logo地址等
│      ├─layout                -- 整体布局
│      ├─router                -- 路由跳转
│      ├─store                 -- 状态管理，使用pinia持久化存储
│      ├─style                 -- 页面设计
│      ├─utils                 -- js工具文件夹，供其他文件调用
│      └─views                 -- 展示视图，页面内容
│          ├─house             -- 住房信息，展示房价形势和预测的页面
│          ├─index             -- 首页
│          ├─login             -- 登录页面
│          ├─module            -- 算法模型管理（弃用）
│          └─user              -- 用户管理
└─src                          -- Java后端
   └─main
      ├─java
      │  └─com
      │      └─shou
      │          └─hpsys
      │              │  HpsysApplication.java  -- 启动类
      │              │  
      │              ├─common                  -- 通用文件夹
      │              │  ├─annotation           -- 通用注解
      │              │  ├─cache                -- 通用缓存，存储到redis中
      │              │  ├─enums                -- 通用枚举
      │              │  ├─exception            -- 通用异常
      │              │  ├─handler              -- 包含加密算法（SM4国密加密）
      │              │  ├─page                 -- 通用分页（前端展示信息使用分页方式）
      │              │  ├─pojo                 -- 通用类（供实体，结果集继承）
      │              │  └─utils                -- 通用工具类
      │              ├─config                  -- 接口配置
      │              ├─handler                 -- 包含前端传递的异常处理类
      │              └─web                     -- 前后端链接
      │                  ├─login               -- 处理前端登录业务
      │                  ├─menu                -- 菜单业务
      │                  ├─predict             -- 预测和房价信息
      │                  ├─relation            -- 数据库实体关系
      │                  ├─role                -- 角色管理
      │                  └─user                -- 用户管理
      └─resources                              -- 资源文件夹
          │  application.yml                   -- 项目配置
          │  logback-spring.xml                -- 日志配置
          └─pmml                               -- python训练的模型，使用PMML文件形式保存
                 lasso_model.pmml
                 line_model.pmml
                 ridge_model.pmml
