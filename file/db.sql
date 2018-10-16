/*
Navicat MySQL Data Transfer

Source Server         : mysql56
Source Server Version : 50634
Source Host           : localhost:3306
Source Database       : db_cloud_pass

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2018-10-16 18:40:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tl_image
-- ----------------------------
DROP TABLE IF EXISTS `tl_image`;
CREATE TABLE `tl_image` (
  `image_uuid` varchar(32) NOT NULL,
  `repo_uuid` varchar(32) NOT NULL,
  `uuid` varchar(32) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `version` varchar(1000) DEFAULT NULL,
  `description` text,
  `v_description` text,
  `logo_url` varchar(1000) DEFAULT NULL,
  `source_url` varchar(1000) DEFAULT NULL,
  `metadata` text,
  `create_type` varchar(10) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_image
-- ----------------------------

-- ----------------------------
-- Table structure for tl_repository
-- ----------------------------
DROP TABLE IF EXISTS `tl_repository`;
CREATE TABLE `tl_repository` (
  `repo_uuid` varchar(32) NOT NULL,
  `user_uuid` varchar(32) NOT NULL,
  `repo_name` varchar(64) DEFAULT NULL,
  `repo_type` varchar(10) DEFAULT NULL,
  `url` varchar(100) NOT NULL,
  `login_name` varchar(100) DEFAULT NULL,
  `login_psd` varchar(100) DEFAULT NULL,
  `project_name` varchar(100) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_repository
-- ----------------------------

-- ----------------------------
-- Table structure for tl_role
-- ----------------------------
DROP TABLE IF EXISTS `tl_role`;
CREATE TABLE `tl_role` (
  `role_uuid` varchar(36) NOT NULL,
  `role_name` varchar(36) DEFAULT NULL,
  `role_desc` text,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_role
-- ----------------------------
INSERT INTO `tl_role` VALUES ('550e8400-e19b-41d4-a716-446655440000', 'public_user', '普通用户', '2018-10-15 16:52:15', '2018-10-15 16:52:15');

-- ----------------------------
-- Table structure for tl_user
-- ----------------------------
DROP TABLE IF EXISTS `tl_user`;
CREATE TABLE `tl_user` (
  `user_uuid` varchar(36) NOT NULL,
  `role_uuid` varchar(36) NOT NULL,
  `username` varchar(36) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `telephone` varchar(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_user
-- ----------------------------
INSERT INTO `tl_user` VALUES ('780e8400-e19b-41d4-a716-446655440000', '550e8400-e19b-41d4-a716-446655440000', 'admin', 'cloud', '1@1', '123', '2018-10-15 16:52:15', '2018-10-15 16:52:15');
