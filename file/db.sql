/*
Navicat MySQL Data Transfer

Source Server         : mysql56
Source Server Version : 50634
Source Host           : localhost:3306
Source Database       : db_cloud_pass

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2018-12-11 17:34:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tl_deployment
-- ----------------------------
DROP TABLE IF EXISTS `tl_deployment`;
CREATE TABLE `tl_deployment` (
  `deploy_uuid` varchar(36) NOT NULL,
  `deploy_name` varchar(128) DEFAULT NULL,
  `user_uuid` varchar(36) NOT NULL,
  `deploy_type` varchar(32) DEFAULT NULL,
  `template_id` varchar(36) DEFAULT NULL,
  `image_uuid` varchar(36) DEFAULT NULL,
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
  `rc_uuid` varchar(36) NOT NULL,
  `svc_uuid` varchar(36) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `namespace` varchar(64) DEFAULT NULL,
  `hostIP` varchar(128) DEFAULT NULL,
  `podIP` varchar(128) DEFAULT NULL,
  `restartCount` varchar(10) DEFAULT NULL,
  `image` varchar(1000) DEFAULT NULL,
  `status` varchar(64) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_pod
-- ----------------------------

-- ----------------------------
-- Table structure for tl_rc
-- ----------------------------
DROP TABLE IF EXISTS `tl_rc`;
CREATE TABLE `tl_rc` (
  `uuid` varchar(36) DEFAULT NULL,
  `deployment_uuid` varchar(36) DEFAULT NULL,
  `content` text,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_rc
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
INSERT INTO `tl_role` VALUES ('550e8400-e19b-41d4-a716-446655440000', 'admin_user', '云管理员', '2018-12-10 09:31:44', '2018-12-10 09:31:44');
INSERT INTO `tl_role` VALUES ('550e8400-e19b-41d4-a716-446655441111', 'public_user', '普通用户', '2018-12-10 09:31:44', '2018-12-10 09:31:44');

-- ----------------------------
-- Table structure for tl_svc
-- ----------------------------
DROP TABLE IF EXISTS `tl_svc`;
CREATE TABLE `tl_svc` (
  `uuid` varchar(36) DEFAULT NULL,
  `deployment_uuid` varchar(36) DEFAULT NULL,
  `content` text,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_svc
-- ----------------------------

-- ----------------------------
-- Table structure for tl_template
-- ----------------------------
DROP TABLE IF EXISTS `tl_template`;
CREATE TABLE `tl_template` (
  `uuid` varchar(36) NOT NULL,
  `user_uuid` varchar(36) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `logo_url` varchar(1000) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `relation` varchar(1000) DEFAULT NULL,
  `config` text,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `isPublish` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_template
-- ----------------------------
INSERT INTO `tl_template` VALUES ('e1b8f39f-34f4-44b0-a8e5-07811b778ec8', '780e8400-e19b-41d4-a716-446655440000', 'template-test', '132.232.140.33/templatelogo/template-test.jpg', '测试添加镜像模板', '{\"hello-world\":{\"busybox\":\"test\"}}', '[{\"appName\":\"hello-world\",\"version\":\"1.0\",\"logo_url\":\"132.232.140.33/logo/hello-world-1.0.PNG\",\"source_url\":\"132.232.140.33/library/library:1.0\",\"metadata\":{\"volume\":\"/data\",\"cmd\":\"\",\"cmdParams\":[\"\"],\"env\":[],\"ports\":[{\"portName\":\"hello\",\"protocol\":\"TCP\",\"containerPort\":\"8089\",\"port\":\"8089\",\"nodePort\":\"8080\"}],\"requests\":{\"cpu\":\"0.2\",\"memory\":\"10MB\"},\"limits\":{\"cpu\":\"0.2\",\"memory\":\"20MB\"}}},{\"appName\":\"busybox\",\"version\":\"2.0\",\"logo_url\":\"132.232.140.33/logo/busybox-3.0.PNG\",\"source_url\":\"132.232.140.33/library/library:2.0\",\"metadata\":{\"volume\":\"/data\",\"cmd\":\"ifconfig\",\"cmdParams\":[\"\"],\"env\":[{\"name\":\"port\",\"value\":\"1234\"}],\"ports\":[{\"portName\":\"busybox\",\"protocol\":\"TCP\",\"containerPort\":\"10081\",\"port\":\"10082\",\"nodePort\":\"10083\"}],\"requests\":{\"cpu\":\"0.1\",\"memory\":\"10MB\"},\"limits\":{\"cpu\":\"0.1\",\"memory\":\"10MB\"}}}]', '2018-12-11 13:20:56', '2018-12-11 13:20:56', '1');

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
INSERT INTO `tl_user` VALUES ('780e8400-e19b-41d4-a716-446655440000', '550e8400-e19b-41d4-a716-446655440000', 'admin', 'cloud', '1@1.com', '15708207554', '2018-12-11 13:56:18', '2018-12-11 13:56:18');
INSERT INTO `tl_user` VALUES ('59d15c3a-acc9-4f18-a671-8f3810483440', '550e8400-e19b-41d4-a716-446655440000', 'test1', '12345', '1@1.com', '1234567890', '2018-12-11 16:37:13', '2018-12-11 16:37:13');
INSERT INTO `tl_user` VALUES ('d787d9f0-8944-426d-8ecb-50a3a3e64fbd', '550e8400-e19b-41d4-a716-446655441111', 'test2', 'cloud', '1@1.com', '12345678901', '2018-12-11 16:37:37', '2018-12-11 16:37:37');
INSERT INTO `tl_user` VALUES ('b9bac095-1cb9-41dd-8474-24e29790f219', '550e8400-e19b-41d4-a716-446655441111', 'test3', '12345', '1@1.com', '1234567890', '2018-12-11 16:37:58', '2018-12-11 16:37:58');
INSERT INTO `tl_user` VALUES ('c2927ba6-7623-4c3e-8868-29d1918b125c', '550e8400-e19b-41d4-a716-446655441111', 'test4', '12345', '1@1.com', '12345678901', '2018-12-11 16:38:21', '2018-12-11 16:38:21');
INSERT INTO `tl_user` VALUES ('b534642d-2168-4cba-9eda-3f010e098bef', '550e8400-e19b-41d4-a716-446655441111', 'test5', '1234567', '1@1.com', '12345678901', '2018-12-11 16:38:47', '2018-12-11 16:38:47');
INSERT INTO `tl_user` VALUES ('6f4c61e0-0c1a-438c-b5c5-17843a308e50', '550e8400-e19b-41d4-a716-446655441111', 'test6', '12345', '1@1.com', '12345678901', '2018-12-11 16:39:11', '2018-12-11 16:39:11');
INSERT INTO `tl_user` VALUES ('6e42d0d6-eb79-4cb9-accb-9e88a854e9f4', '550e8400-e19b-41d4-a716-446655441111', 'test7', '123456', '1@1.com', '12345678901', '2018-12-11 16:39:34', '2018-12-11 16:39:34');
INSERT INTO `tl_user` VALUES ('6579cc5e-8999-41a5-85eb-df632e45a5b3', '550e8400-e19b-41d4-a716-446655441111', 'test8', '12345', '1@1.com', '12345678901', '2018-12-11 16:39:54', '2018-12-11 16:39:54');
INSERT INTO `tl_user` VALUES ('7380825e-0bef-4907-bbe8-aae68c064e1b', '550e8400-e19b-41d4-a716-446655441111', 'test9', '12345', '1@1.com', '12345678901', '2018-12-11 16:40:19', '2018-12-11 16:40:19');
INSERT INTO `tl_user` VALUES ('e84e6c3d-f38f-4234-8a58-55067bb4242b', '550e8400-e19b-41d4-a716-446655441111', 'test10', '12345', '1@1.com', '12345678901', '2018-12-11 16:40:41', '2018-12-11 16:40:41');
