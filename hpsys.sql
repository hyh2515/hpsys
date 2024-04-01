/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.31 : Database - hpsys
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`hpsys` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `hpsys`;

/*Table structure for table `community` */

DROP TABLE IF EXISTS `community`;

CREATE TABLE `community` (
  `id` varchar(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '小区名',
  `coord_x` double DEFAULT NULL COMMENT 'x坐标',
  `coord_y` double DEFAULT NULL COMMENT 'y坐标',
  `delete_flag` varchar(255) DEFAULT NULL COMMENT '删除标志',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `house_info` */

DROP TABLE IF EXISTS `house_info`;

CREATE TABLE `house_info` (
  `id` varchar(20) NOT NULL,
  `average` int DEFAULT NULL COMMENT '均价',
  `coordinate_x` double DEFAULT NULL COMMENT '经度',
  `coordinate_y` double DEFAULT NULL COMMENT '纬度',
  `decoration_condition` int DEFAULT NULL COMMENT '装修情况',
  `deed` varchar(3) DEFAULT NULL COMMENT '房本备件',
  `elevator` varchar(3) DEFAULT NULL COMMENT '配备电梯',
  `facility0` varchar(3) DEFAULT NULL COMMENT '购物',
  `facility1` varchar(3) DEFAULT NULL COMMENT '教育',
  `facility2` varchar(3) DEFAULT NULL COMMENT '交通',
  `facility3` varchar(3) DEFAULT NULL COMMENT '健身',
  `facility4` varchar(3) DEFAULT NULL COMMENT '绿化',
  `facility5` varchar(3) DEFAULT NULL COMMENT '医疗',
  `level` tinyint DEFAULT NULL COMMENT '所在楼层',
  `total` int DEFAULT NULL COMMENT '楼层总数',
  `framework` tinyint DEFAULT NULL COMMENT '建筑结构',
  `house_term` tinyint DEFAULT NULL COMMENT '房屋年限',
  `ownership` tinyint DEFAULT NULL COMMENT '产权所属',
  `price` int DEFAULT NULL COMMENT '房屋总价',
  `purpose` tinyint DEFAULT NULL COMMENT '房屋用途',
  `apt` tinyint DEFAULT NULL COMMENT '每层楼住户数',
  `lift` tinyint DEFAULT NULL COMMENT '电梯数',
  `district` varchar(255) DEFAULT NULL COMMENT '区域编码',
  `rights` int DEFAULT NULL COMMENT '交易权属',
  `scale` double DEFAULT NULL COMMENT '建筑面积/㎡',
  `structure` varchar(3) DEFAULT NULL COMMENT '户型结构',
  `bath` tinyint DEFAULT NULL COMMENT '浴室数',
  `kitchen` tinyint DEFAULT NULL COMMENT '厨房数',
  `room` tinyint DEFAULT NULL COMMENT '卧室数',
  `saloon` tinyint DEFAULT NULL COMMENT '客厅数',
  `delete_flag` varchar(255) DEFAULT NULL COMMENT '删除标志',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `id` varchar(20) NOT NULL,
  `parent_id` varchar(255) DEFAULT NULL COMMENT '父ID',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `code` varchar(255) DEFAULT NULL COMMENT '编码',
  `path` varchar(255) DEFAULT NULL COMMENT '路径',
  `menu_type` varchar(255) DEFAULT NULL COMMENT '菜单模型',
  `component` varchar(255) DEFAULT NULL COMMENT '组件',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `visible` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '是否可见',
  `sort_code` int DEFAULT NULL COMMENT '排序码',
  `delete_flag` varchar(255) DEFAULT NULL COMMENT '删除标志',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `relation` */

DROP TABLE IF EXISTS `relation`;

CREATE TABLE `relation` (
  `id` varchar(20) NOT NULL,
  `object_id` varchar(255) DEFAULT NULL COMMENT '对象ID',
  `target_id` varchar(255) DEFAULT NULL COMMENT '目标ID',
  `category` varchar(255) DEFAULT NULL COMMENT '类别',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` varchar(20) NOT NULL,
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `code` varchar(255) DEFAULT NULL COMMENT '编码',
  `sort_code` int DEFAULT NULL COMMENT '排序码',
  `delete_flag` varchar(255) DEFAULT NULL COMMENT '删除标志',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建用户',
  `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(20) DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(20) NOT NULL,
  `avatar` longtext COMMENT '头像',
  `account` varchar(255) DEFAULT NULL COMMENT '签名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `age` varchar(255) DEFAULT NULL COMMENT '年龄',
  `mailing_address` text COMMENT '通信地址',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `login_ip` varchar(255) DEFAULT NULL COMMENT '最新登录ip',
  `sort_code` int DEFAULT NULL COMMENT '排序码',
  `delete_flag` varchar(255) DEFAULT NULL COMMENT '删除标志',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(20) DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `zip_code` */

DROP TABLE IF EXISTS `zip_code`;

CREATE TABLE `zip_code` (
  `id` varchar(20) NOT NULL,
  `division` varchar(255) DEFAULT NULL COMMENT '地区',
  `code` varchar(255) DEFAULT NULL COMMENT '邮政编码',
  `coordinate_x` double DEFAULT NULL COMMENT '经度',
  `coordinate_y` double DEFAULT NULL COMMENT '纬度',
  `delete_flag` varchar(255) DEFAULT NULL COMMENT '删除标志',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
