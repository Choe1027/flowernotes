/*
Navicat MySQL Data Transfer

Source Server         : 本地测试
Source Server Version : 50640
Source Host           : localhost:3306
Source Database       : flowernotes

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2018-07-27 17:44:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `province_code` varchar(32) DEFAULT NULL COMMENT '省级编码',
  `city_code` varchar(32) DEFAULT NULL COMMENT '区级编码',
  `county_code` varchar(32) DEFAULT NULL COMMENT '县级编码',
  `town_code` varchar(32) DEFAULT NULL COMMENT '城镇编码',
  `address_detail` varchar(255) DEFAULT NULL COMMENT '地址详情',
  `additional_info` varchar(255) DEFAULT NULL COMMENT '补填信息',
  `isdefault` int(4) DEFAULT NULL COMMENT '是否默认地址',
  `receiver_name` varchar(64) DEFAULT NULL COMMENT '收货人名',
  `receiver_mobile` varchar(64) DEFAULT NULL COMMENT '收货人电话',
  `create_time` bigint(22) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of address
-- ----------------------------

-- ----------------------------
-- Table structure for back_user
-- ----------------------------
DROP TABLE IF EXISTS `back_user`;
CREATE TABLE `back_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL COMMENT '昵称',
  `account` varchar(64) DEFAULT NULL COMMENT '账号',
  `password` varchar(80) DEFAULT NULL COMMENT '密码',
  `head` varchar(255) DEFAULT NULL COMMENT '头像url',
  `status` int(4) DEFAULT NULL COMMENT '状态 0 启用 1禁用',
  `create_time` bigint(22) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of back_user
-- ----------------------------

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level` int(4) DEFAULT NULL COMMENT '级别',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `pid` int(11) DEFAULT NULL COMMENT '父级id',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `state` int(4) DEFAULT NULL COMMENT '状态 0 启用，1 禁用',
  `type` int(4) DEFAULT NULL COMMENT '品类类型，',
  `create_time` bigint(22) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of category
-- ----------------------------

-- ----------------------------
-- Table structure for city_areas
-- ----------------------------
DROP TABLE IF EXISTS `city_areas`;
CREATE TABLE `city_areas` (
  `city_code` varchar(32) DEFAULT NULL,
  `city_name` varchar(32) DEFAULT NULL,
  `parent_code` varchar(32) DEFAULT NULL,
  `level` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of city_areas
-- ----------------------------

-- ----------------------------
-- Table structure for city_covered
-- ----------------------------
DROP TABLE IF EXISTS `city_covered`;
CREATE TABLE `city_covered` (
  `city_code` varchar(32) DEFAULT NULL,
  `city_name` varchar(32) DEFAULT NULL,
  `parent_code` varchar(32) DEFAULT NULL,
  `level` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of city_covered
-- ----------------------------

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_template_id` int(11) DEFAULT NULL COMMENT '模板id',
  `name` varchar(255) DEFAULT NULL COMMENT '优惠券名称',
  `desc` varchar(255) DEFAULT NULL COMMENT '优惠券描述',
  `type` varchar(255) DEFAULT NULL COMMENT '优惠券类型',
  `require_money` double(10,2) DEFAULT NULL COMMENT '使用金额限制',
  `num` int(10) DEFAULT NULL COMMENT '数量',
  `start_time` bigint(22) DEFAULT NULL COMMENT '生效时间',
  `end_time` bigint(22) DEFAULT NULL COMMENT '过期时间',
  `create_time` bigint(22) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of coupon
-- ----------------------------

-- ----------------------------
-- Table structure for coupon_template
-- ----------------------------
DROP TABLE IF EXISTS `coupon_template`;
CREATE TABLE `coupon_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '优惠卷名',
  `desc` varchar(255) DEFAULT NULL COMMENT '优惠券描述',
  `type` varchar(255) DEFAULT NULL COMMENT '优惠券类型',
  `require_money` double(10,2) DEFAULT NULL COMMENT '需要满足金额',
  `state` int(4) DEFAULT NULL COMMENT '状态0 启用，1禁用',
  `create_time` bigint(22) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of coupon_template
-- ----------------------------

-- ----------------------------
-- Table structure for deliervery_man
-- ----------------------------
DROP TABLE IF EXISTS `deliervery_man`;
CREATE TABLE `deliervery_man` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL COMMENT '配送原名称',
  `mobile` varchar(64) DEFAULT NULL COMMENT '配送员手机号',
  `create_time` bigint(22) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of deliervery_man
-- ----------------------------

-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict` (
  `id` int(11) NOT NULL,
  `desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of dict
-- ----------------------------

-- ----------------------------
-- Table structure for dict_data
-- ----------------------------
DROP TABLE IF EXISTS `dict_data`;
CREATE TABLE `dict_data` (
  `id` int(11) NOT NULL,
  `dict_id` int(11) DEFAULT NULL COMMENT '字典id',
  `dict_value` varchar(255) NOT NULL COMMENT '字典值',
  `order_no` int(10) DEFAULT NULL COMMENT '排序号',
  `create_time` bigint(22) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of dict_data
-- ----------------------------

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) DEFAULT NULL COMMENT '品类id',
  `name` varchar(200) DEFAULT NULL COMMENT '商品名称',
  `main_url` varchar(255) DEFAULT NULL COMMENT '主图url',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `old_price` double(10,2) DEFAULT NULL COMMENT '原价',
  `now_price` double(10,2) DEFAULT NULL COMMENT '现价',
  `stock_num` int(10) DEFAULT NULL COMMENT '库存',
  `type` int(4) DEFAULT NULL COMMENT '商品类型',
  `state` int(4) DEFAULT NULL COMMENT '状态 0 启用 1禁用',
  `sold_num` int(22) DEFAULT NULL COMMENT '已售数量',
  `order_no` int(11) DEFAULT NULL COMMENT '排序号',
  `init_price` double(10,2) DEFAULT NULL COMMENT '起拍金额',
  `price_step` double(10,2) DEFAULT NULL COMMENT '加价步调',
  `current_price` double(10,2) DEFAULT NULL COMMENT '当前价格',
  `start_time` bigint(22) DEFAULT NULL COMMENT '开始时间',
  `end_time` bigint(22) DEFAULT NULL COMMENT '结束时间',
  `create_time` bigint(22) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of goods
-- ----------------------------

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL COMMENT '等级名称',
  `required_score` int(20) DEFAULT NULL COMMENT '所需积分',
  `desc` varchar(255) DEFAULT NULL COMMENT '等级描述',
  `create_time` bigint(22) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of grade
-- ----------------------------

-- ----------------------------
-- Table structure for main_notice
-- ----------------------------
DROP TABLE IF EXISTS `main_notice`;
CREATE TABLE `main_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(4) DEFAULT NULL COMMENT '通知类型',
  `title` varchar(64) DEFAULT NULL COMMENT '通知标题',
  `content` varchar(255) DEFAULT NULL COMMENT '通知内容',
  `state` int(4) DEFAULT NULL COMMENT '状态 0启用 1停用',
  `create_time` bigint(22) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of main_notice
-- ----------------------------

-- ----------------------------
-- Table structure for module
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL COMMENT '模块名称',
  `pid` int(11) DEFAULT NULL COMMENT '父级id',
  `type` int(4) DEFAULT NULL COMMENT '0 菜单 1 按钮',
  `url` varchar(255) DEFAULT NULL COMMENT '连接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of module
-- ----------------------------

-- ----------------------------
-- Table structure for navigation
-- ----------------------------
DROP TABLE IF EXISTS `navigation`;
CREATE TABLE `navigation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL COMMENT '图片链接',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` bigint(22) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of navigation
-- ----------------------------

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(11) NOT NULL,
  `ordercode` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `type` int(4) DEFAULT NULL COMMENT '订单种类',
  `address_deatil` varchar(400) DEFAULT NULL COMMENT '地址详情',
  `receiver_name` varchar(64) DEFAULT NULL COMMENT '收货人名称',
  `receiver_mobile` varchar(64) DEFAULT NULL COMMENT '收货人电话',
  `coupon_id` int(11) DEFAULT NULL COMMENT '用户优惠券id',
  `coupon_money` double(10,2) DEFAULT NULL COMMENT '优惠券金额',
  `total_money` double(10,2) DEFAULT NULL COMMENT '总金额',
  `real_pay_money` double(10,2) DEFAULT NULL COMMENT '实付金额',
  `province_code` varchar(32) DEFAULT NULL COMMENT '省级编码',
  `city_code` varchar(32) DEFAULT NULL COMMENT '区编码',
  `county_code` varchar(32) DEFAULT NULL COMMENT '县编码',
  `town_code` varchar(32) DEFAULT NULL COMMENT '城镇编码',
  `payment_type` int(4) DEFAULT NULL COMMENT '支付方式， 1、 线下支付 2、微信支付 3、余额支付',
  `status` int(4) DEFAULT NULL COMMENT '订单状态：0.待支付 1.待送货 2.送货中 3.未评价 4.已评价 -1取消',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `send_time_start` bigint(22) DEFAULT NULL COMMENT '配送时间段开始',
  `send_time_end` bigint(22) DEFAULT NULL COMMENT '配送时间段结束',
  `create_time` bigint(22) DEFAULT NULL COMMENT '创建时间',
  `pay_time` bigint(22) DEFAULT NULL COMMENT '支付时间',
  `arrive_time` bigint(22) DEFAULT NULL COMMENT '送达时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of order
-- ----------------------------

-- ----------------------------
-- Table structure for order_good
-- ----------------------------
DROP TABLE IF EXISTS `order_good`;
CREATE TABLE `order_good` (
  `id` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `good_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of order_good
-- ----------------------------

-- ----------------------------
-- Table structure for recharge_record
-- ----------------------------
DROP TABLE IF EXISTS `recharge_record`;
CREATE TABLE `recharge_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `payment_type` int(4) DEFAULT NULL COMMENT '支付方式',
  `rechare_money` double(32,2) DEFAULT NULL COMMENT '充值金额',
  `handsel_money` double(32,2) DEFAULT NULL COMMENT '返充金额',
  `create_time` bigint(22) DEFAULT NULL COMMENT '充值时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of recharge_record
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `level` int(4) DEFAULT NULL COMMENT '权限级别',
  `state` int(4) DEFAULT NULL COMMENT '状态 0 启用 1禁用',
  `create_time` bigint(22) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for role_module
-- ----------------------------
DROP TABLE IF EXISTS `role_module`;
CREATE TABLE `role_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `module_id` int(11) DEFAULT NULL,
  `create_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role_module
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(32) DEFAULT NULL COMMENT '真实名称',
  `score` int(20) DEFAULT NULL COMMENT '用户积分',
  `mobile` varchar(64) DEFAULT NULL COMMENT '手机号码',
  `head` varchar(255) DEFAULT NULL COMMENT '头像',
  `grade_name` int(4) DEFAULT NULL COMMENT '用户等级',
  `grade_id` int(11) DEFAULT NULL COMMENT '用户等级id',
  `is_vip` int(4) DEFAULT NULL COMMENT '是否vip 1 是 0不是',
  `balance` double(16,2) DEFAULT NULL COMMENT '余额',
  `create_time` bigint(22) DEFAULT NULL COMMENT '创建时间',
  `last_login_time` bigint(22) DEFAULT NULL COMMENT '上次登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_coupon
-- ----------------------------
DROP TABLE IF EXISTS `user_coupon`;
CREATE TABLE `user_coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `coupon_id` int(11) DEFAULT NULL COMMENT '优惠券id',
  `status` int(4) DEFAULT NULL COMMENT '优惠券状态，0未使用 1已使用 -1 已过期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_coupon
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `create_time` bigint(22) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_role
-- ----------------------------