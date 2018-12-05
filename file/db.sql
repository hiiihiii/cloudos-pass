/*
Navicat MySQL Data Transfer

Source Server         : mysql56
Source Server Version : 50634
Source Host           : localhost:3306
Source Database       : db_cloud_pass

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2018-12-05 15:11:29
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
  `create_type` varchar(100) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_image
-- ----------------------------
INSERT INTO `tl_image` VALUES ('73c1f3a7-a016-4dba-b150-96d2fe02959f', '66908423-e29b-41d4-a716-446655440000', '780e8400-e19b-41d4-a716-446655440000', 'busybox', 'Application', '[\"2.0\",\"3.0\"]', 'busybox是一个命令工具', '{\"2.0\":\"busybox2.0\",\"3.0\":\"busybox3.0\"}', '132.232.140.33/logo/busybox-3.0.PNG', '{\"2.0\":\"132.232.140.33/library/library:2.0\",\"3.0\":\"132.232.140.33/library/library:3.0\"}', '{\"2.0\":{\"volume\":\"/data\",\"cmd\":\"ifconfig\",\"cmdParams\":[\"\"],\"env\":[{\"name\":\"port\",\"value\":\"1234\"}],\"ports\":[{\"portName\":\"busybox\",\"protocol\":\"TCP\",\"containerPort\":\"10081\",\"port\":\"10082\",\"nodePort\":\"10083\"}],\"requests\":{\"cpu\":\"0.1\",\"memory\":\"10MB\"},\"limits\":{\"cpu\":\"0.1\",\"memory\":\"10MB\"}},\"3.0\":{\"volume\":\"\",\"cmd\":\"ifconfig\",\"cmdParams\":[\"\"],\"env\":[],\"ports\":[{\"portName\":\"busybox\",\"protocol\":\"TCP\",\"containerPort\":\"10081\",\"port\":\"10082\",\"nodePort\":\"10083\"}],\"requests\":{\"cpu\":\"0.1\",\"memory\":\"10MB\"},\"limits\":{\"cpu\":\"0.1\",\"memory\":\"20MB\"}}}', '{\"2.0\":\"upload\",\"3.0\":\"upload\"}', '2018-12-05 15:10:10', '2018-12-05 15:10:10');
INSERT INTO `tl_image` VALUES ('469e2eb4-eee0-4e8f-a449-fb33a292af43', '66908423-e29b-41d4-a716-446655440000', '780e8400-e19b-41d4-a716-446655440000', 'hello-world', 'WebServer', '[\"1.0\"]', 'hello-world是一个测试镜像，它很小很小', '{\"1.0\":\"这是1.0版本，没什么特点\"}', '132.232.140.33/logo/hello-world-1.0.PNG', '{\"1.0\":\"132.232.140.33/library/library:1.0\"}', '{\"1.0\":{\"volume\":\"/data\",\"cmd\":\"\",\"cmdParams\":[\"\"],\"env\":[],\"ports\":[{\"portName\":\"hello\",\"protocol\":\"TCP\",\"containerPort\":\"8089\",\"port\":\"8089\",\"nodePort\":\"8080\"}],\"requests\":{\"cpu\":\"0.2\",\"memory\":\"10MB\"},\"limits\":{\"cpu\":\"0.2\",\"memory\":\"20MB\"}}}', '{\"1.0\":\"upload\"}', '2018-12-05 15:04:34', '2018-12-05 15:04:34');

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
-- Table structure for tl_svc
-- ----------------------------
DROP TABLE IF EXISTS `tl_svc`;
CREATE TABLE `tl_svc` (
  `uuid` varchar(36) DEFAULT NULL,
  `deployment_uuid` varchar(36) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_svc
-- ----------------------------

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
