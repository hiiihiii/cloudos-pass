/*
Navicat MySQL Data Transfer

Source Server         : mysql56
Source Server Version : 50634
Source Host           : localhost:3306
Source Database       : db_cloud_pass

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2018-11-27 17:23:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tl_deployment
-- ----------------------------
DROP TABLE IF EXISTS `tl_deployment`;
CREATE TABLE `tl_deployment` (
  `deploy_uuid` varchar(36) NOT NULL,
  `user_uuid` varchar(36) NOT NULL,
  `template_id` varchar(36) DEFAULT NULL,
  `image_uuid` varchar(36) DEFAULT NULL,
  `deploy_type` varchar(32) DEFAULT NULL,
  `deploy_name` varchar(128) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_deployment
-- ----------------------------

-- ----------------------------
-- Table structure for tl_image
-- ----------------------------
DROP TABLE IF EXISTS `tl_image`;
CREATE TABLE `tl_image` (
  `image_uuid` varchar(36) NOT NULL,
  `repo_uuid` varchar(36) NOT NULL,
  `uuid` varchar(36) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `tag` varchar(64) DEFAULT NULL,
  `version` varchar(1000) DEFAULT NULL,
  `description` text,
  `v_description` text,
  `logo_url` varchar(1000) DEFAULT NULL,
  `source_url` varchar(1000) DEFAULT NULL,
  `metadata` text,
  `create_type` varchar(1000) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_image
-- ----------------------------
INSERT INTO `tl_image` VALUES ('28fe9d90-0b74-410e-9820-5b4fbd8e6844', '550e8423-e29b-41d4-a716-446655440000', '780e8400-e19b-41d4-a716-446655440000', 'busybox', 'Application', '2.0', 'BusyBox 是一个集成了三百多个最常用Linux命令和工具的软件', '2.0', '132.232.140.33/var/ftp/logo/busybox-2.0.PNG', '132.232.140.33/admin_project/busybox:2.0', '{\"volume\":\"/data\",\"cmd\":\"ifconfig\",\"cmdParams\":[\"-a\"],\"env\":[{\"envKey\":\"port\",\"envValue\":\"1234\"}],\"ports\":[{\"portName\":\"busybox\",\"protocol\":\"TCP\",\"containerPort\":\"10090\",\"port\":\"10091\",\"targetPort\":\"10092\"}],\"requests\":{\"cpu\":\"0.2\",\"memory\":\"20MB\"},\"limits\":{\"cpu\":\"0.4\",\"memory\":\"40MB\"}}', 'upload', '2018-11-27 10:56:05', '2018-11-27 10:56:05');

-- ----------------------------
-- Table structure for tl_pod
-- ----------------------------
DROP TABLE IF EXISTS `tl_pod`;
CREATE TABLE `tl_pod` (
  `uuid` varchar(36) NOT NULL,
  `deploy_uuid` varchar(36) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `namespace` varchar(64) DEFAULT NULL,
  `hostIP` varchar(128) DEFAULT NULL,
  `podIP` varchar(128) DEFAULT NULL,
  `restartCount` varchar(10) DEFAULT NULL,
  `image` varchar(1000) DEFAULT NULL,
  `status` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_pod
-- ----------------------------

-- ----------------------------
-- Table structure for tl_repository
-- ----------------------------
DROP TABLE IF EXISTS `tl_repository`;
CREATE TABLE `tl_repository` (
  `repo_uuid` varchar(36) NOT NULL,
  `user_uuid` varchar(36) NOT NULL,
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
INSERT INTO `tl_repository` VALUES ('550e8423-e29b-41d4-a716-446655440000', '780e8400-e19b-41d4-a716-446655440000', 'admin_project', 'private', 'https://132.232.140.33', 'admin', 'Harbor12345', null, '2018-11-15 16:52:15', '2018-11-15 16:52:15');
INSERT INTO `tl_repository` VALUES ('66908423-e29b-41d4-a716-446655440000', '780e8400-e19b-41d4-a716-446655440000', 'library', 'public', 'https://132.232.140.33', 'admin', 'Harbor12345', null, '2018-11-15 16:52:15', '2018-11-15 16:52:15');

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
