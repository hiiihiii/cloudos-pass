/*
Navicat MySQL Data Transfer

Source Server         : mysql56
Source Server Version : 50634
Source Host           : localhost:3306
Source Database       : db_cloud_pass

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2019-04-09 17:55:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tl_deployment
-- ----------------------------
DROP TABLE IF EXISTS `tl_deployment`;
CREATE TABLE `tl_deployment` (
  `deploy_uuid` varchar(36) NOT NULL,
  `deploy_name` varchar(64) DEFAULT NULL,
  `user_uuid` varchar(36) NOT NULL,
  `deploy_type` varchar(10) DEFAULT NULL,
  `template_id` varchar(36) DEFAULT NULL,
  `image_uuid` varchar(36) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_deployment
-- ----------------------------
INSERT INTO `tl_deployment` VALUES ('501755c9-a233-4287-8a12-ead4e0e767eb', 'tl-test', '780e8400-e19b-41d4-a716-446655440000', 'docker', '', 'c39a169a-5ca8-4dda-b45d-a871ea50be4a', '部署一个应用到Kubernetes集群中', '2019-03-22 21:59:48', '2019-03-22 21:59:48');

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
  `version` varchar(64) DEFAULT NULL,
  `description` text,
  `v_description` text,
  `logo_url` varchar(1000) DEFAULT NULL,
  `source_url` varchar(1000) DEFAULT NULL,
  `metadata` text,
  `create_type` varchar(64) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deploycount` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_image
-- ----------------------------
INSERT INTO `tl_image` VALUES ('fca073cf-c292-4d59-8764-88c9902608b6', '66908423-e29b-41d4-a716-446655440000', '780e8400-e19b-41d4-a716-446655440000', 'hello-world', 'Application', '[\"1.0\"]', 'Hello World 中文意思是『你好,世界』。因为《The C Programming Language》中使用它做为第一个演示程序，非常著名，所以后来的程序员在学习编程或进行设备调试时延续了这一', '{\"1.0\":\"该版本用来测试上传镜像\"}', '132.232.93.3/logo/hello-world-1.0.png', '{\"1.0\":\"132.232.93.3/library/hello-world:1.0\"}', '{\"1.0\":{\"volume\":\"\",\"cmd\":\"\",\"cmdParams\":[],\"env\":[],\"ports\":[{\"portName\":\"test\",\"protocol\":\"TCP\",\"containerPort\":\"8089\",\"port\":\"30040\",\"nodePort\":\"30040\"}],\"requests\":{\"cpu\":\"0.3\",\"memory\":\"40Mi\"},\"limits\":{\"cpu\":\"0.5\",\"memory\":\"80Mi\"}}}', '{\"1.0\":\"upload\"}', '2019-03-22 21:18:14', '2019-03-22 21:18:14', '0');
INSERT INTO `tl_image` VALUES ('c39a169a-5ca8-4dda-b45d-a871ea50be4a', '66908423-e29b-41d4-a716-446655440000', '780e8400-e19b-41d4-a716-446655440000', 'busybox', 'Application', '[\"2.0\"]', 'BusyBox 是一个集成了三百多个最常用Linux命令和工具的软件。BusyBox 包含了一些简单的工具，例如ls、cat和echo等等。', '{\"2.0\":\"\"}', '132.232.93.3/logo/busybox-2.0.png', '{\"2.0\":\"132.232.93.3/library/busybox:2.0\"}', '{\"2.0\":{\"volume\":\"/data\",\"cmd\":\"tail\",\"cmdParams\":[],\"env\":[],\"ports\":[{\"portName\":\"busy\",\"protocol\":\"TCP\",\"containerPort\":\"8090\",\"port\":\"30041\",\"nodePort\":\"30043\"}],\"requests\":{\"cpu\":\"0.2\",\"memory\":\"20Mi\"},\"limits\":{\"cpu\":\"0.2\",\"memory\":\"20Mi\"}}}', '{\"2.0\":\"upload\"}', '2019-03-22 21:27:06', '2019-03-22 21:27:06', '0');

-- ----------------------------
-- Table structure for tl_log
-- ----------------------------
DROP TABLE IF EXISTS `tl_log`;
CREATE TABLE `tl_log` (
  `uuid` varchar(36) DEFAULT NULL,
  `user_uuid` varchar(36) DEFAULT NULL,
  `username` varchar(64) DEFAULT NULL,
  `resourceId` varchar(36) DEFAULT NULL,
  `resourceType` varchar(64) DEFAULT NULL,
  `operation` varchar(100) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `isDeleted` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_log
-- ----------------------------
INSERT INTO `tl_log` VALUES ('15b660b7-0841-4b37-b502-ce0e3f779177', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-22 21:30:57', '0');
INSERT INTO `tl_log` VALUES ('0cf3388d-2306-452e-856f-df77870fad12', '780e8400-e19b-41d4-a716-446655440000', 'admin', 'b30cb782-e29c-4863-b2fc-0f18c4c7eb34', 'Template', '增加应用模板', '2019-03-22 21:35:08', '0');
INSERT INTO `tl_log` VALUES ('75182fbb-da48-495b-aebc-7924cf17aabe', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-22 21:37:51', '0');
INSERT INTO `tl_log` VALUES ('7aa9057b-1393-4af9-ba81-18049b2b4823', '780e8400-e19b-41d4-a716-446655440000', 'admin', 'b30cb782-e29c-4863-b2fc-0f18c4c7eb34', 'Template', '发布应用模板', '2019-03-22 21:39:16', '0');
INSERT INTO `tl_log` VALUES ('2acb2f56-dd45-4782-a2a7-f4907820ccd5', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-22 21:47:50', '0');
INSERT INTO `tl_log` VALUES ('753249e8-32ab-459c-a51e-3012410daa42', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-22 22:19:08', '0');
INSERT INTO `tl_log` VALUES ('7f8b6953-8ad9-467d-8c94-5178d6b39303', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-22 22:19:21', '0');
INSERT INTO `tl_log` VALUES ('cacd7de5-4d8d-4d28-81d6-e275db07382d', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-22 22:19:44', '0');
INSERT INTO `tl_log` VALUES ('bcd4fa4e-1280-4335-b89d-4e4851c6afed', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-22 22:21:24', '0');
INSERT INTO `tl_log` VALUES ('3fb6afdd-5fe0-41c7-85d8-99527c71caa0', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-24 21:53:43', '0');
INSERT INTO `tl_log` VALUES ('70706e08-17df-4222-96af-3569ab06bbf2', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 10:33:50', '0');
INSERT INTO `tl_log` VALUES ('7f74c51a-633f-47c6-ad0e-785ca75c809f', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 10:35:37', '0');
INSERT INTO `tl_log` VALUES ('6200d771-b4ee-403a-b109-8b9eb7e031c8', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 11:05:30', '0');
INSERT INTO `tl_log` VALUES ('43f2ac6c-cacc-41e8-b61c-145e5e5ce431', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 11:07:03', '0');
INSERT INTO `tl_log` VALUES ('115189a8-a4a3-47c7-b6d0-9bb1e3673124', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 11:13:27', '0');
INSERT INTO `tl_log` VALUES ('933df4b6-3fbd-40f9-9378-e14b684f9184', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 11:14:59', '0');
INSERT INTO `tl_log` VALUES ('7dfb989f-5d72-494d-8e60-15b72c58342c', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 11:15:25', '0');
INSERT INTO `tl_log` VALUES ('394a6c19-60ce-47a9-ba7c-fc329e1929e1', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 11:16:44', '0');
INSERT INTO `tl_log` VALUES ('96beebe0-912d-41de-8d35-84701a2cedba', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 11:30:06', '0');
INSERT INTO `tl_log` VALUES ('6574ca6a-c424-4ffc-88df-4769d0748f78', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 13:48:43', '0');
INSERT INTO `tl_log` VALUES ('baed1538-86f9-4abe-8104-0ec26c8c7d3c', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 13:58:13', '0');
INSERT INTO `tl_log` VALUES ('4b068bbd-f670-499a-bb05-2eec039b9448', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 13:58:37', '0');
INSERT INTO `tl_log` VALUES ('8590fd3a-c842-4b3c-8c1a-eb6ae7ca0e80', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 14:09:19', '0');
INSERT INTO `tl_log` VALUES ('84750430-5e30-4898-bdb4-98beb9e5e5b8', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 14:11:10', '0');
INSERT INTO `tl_log` VALUES ('b745ce81-b9db-4470-a48b-e2ee1a5ff50a', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 14:15:35', '0');
INSERT INTO `tl_log` VALUES ('436920e0-4d44-486e-b051-089507fb79e5', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 14:24:27', '0');
INSERT INTO `tl_log` VALUES ('068278b4-c33c-4783-9a90-e5910079aa36', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 14:34:39', '0');
INSERT INTO `tl_log` VALUES ('058e8518-e9b6-4fab-88de-3a88636db054', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 14:35:22', '0');
INSERT INTO `tl_log` VALUES ('0eddf0df-cbeb-427a-b134-95764c46a3f0', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 14:45:10', '0');
INSERT INTO `tl_log` VALUES ('60541b3d-4178-4ad6-a8c3-f72f3c6d3204', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 14:46:03', '0');
INSERT INTO `tl_log` VALUES ('a2cde366-f67f-4a9b-b6f0-2180f5563112', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 15:01:02', '0');
INSERT INTO `tl_log` VALUES ('ca3c535f-0e76-4724-bc80-490feb266e7f', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 16:03:08', '0');
INSERT INTO `tl_log` VALUES ('73fa972c-9856-4af3-892b-6eae4266f741', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-25 17:35:49', '0');
INSERT INTO `tl_log` VALUES ('6f0e4a77-c3d8-4d4c-8420-a78a11f2a83a', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 09:15:17', '0');
INSERT INTO `tl_log` VALUES ('8622dec4-96f5-45c9-ba96-42e033488edc', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 09:19:09', '0');
INSERT INTO `tl_log` VALUES ('a0796f3b-68cf-469c-97c2-199a77898d5c', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 09:19:40', '0');
INSERT INTO `tl_log` VALUES ('b62f13c3-6903-4e11-97c0-dca2f335fae9', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 09:21:58', '0');
INSERT INTO `tl_log` VALUES ('da201fe6-0f5c-4330-b1b3-5d39aa1028fd', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 09:23:54', '0');
INSERT INTO `tl_log` VALUES ('c7014cd0-67de-4541-8776-52c74863cbb0', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 10:09:06', '0');
INSERT INTO `tl_log` VALUES ('01dfd3fd-5198-4f4b-826f-1a1830f345c3', '781b9bc5-8682-4ac4-89f1-b0e1a3fbf31d', 'test', '781b9bc5-8682-4ac4-89f1-b0e1a3fbf31d', 'User', '增加用户', '2019-03-26 10:10:16', '0');
INSERT INTO `tl_log` VALUES ('cd9190e2-1aa9-4861-8572-f5b75659f5af', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 10:15:25', '0');
INSERT INTO `tl_log` VALUES ('ccdcd7b8-2dbb-4728-b4c8-fe528949a676', '91cbc71e-3d85-4d62-9ac0-e9cfc60ffce9', 'test', '91cbc71e-3d85-4d62-9ac0-e9cfc60ffce9', 'User', '增加用户', '2019-03-26 10:15:56', '0');
INSERT INTO `tl_log` VALUES ('5a3e6aed-6411-4b91-95d4-ce9b6c41a28e', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 10:18:02', '0');
INSERT INTO `tl_log` VALUES ('07ee5ee2-f207-4a6e-ba5d-6c4d2ab42028', '90b8aa01-ab82-4179-a654-40665e04d22e', 'test', '90b8aa01-ab82-4179-a654-40665e04d22e', 'User', '增加用户', '2019-03-26 10:18:33', '0');
INSERT INTO `tl_log` VALUES ('06c568d9-ca74-427f-8bce-0fa140b0c240', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 10:29:51', '0');
INSERT INTO `tl_log` VALUES ('b072444c-729b-47f2-b966-478e1c665e37', 'a7021994-24f0-433f-aca5-ca7f24bcad10', 'test', 'a7021994-24f0-433f-aca5-ca7f24bcad10', 'User', '增加用户', '2019-03-26 10:30:24', '0');
INSERT INTO `tl_log` VALUES ('a3b950c7-5c9b-4e44-bedf-e8f4851e5c95', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 10:32:58', '0');
INSERT INTO `tl_log` VALUES ('5d0dde4f-614d-4dbf-b671-a13896c7db44', '032f9c86-8af0-4a20-b52f-f08472a0dc40', 'test', '032f9c86-8af0-4a20-b52f-f08472a0dc40', 'User', '增加用户', '2019-03-26 10:33:24', '0');
INSERT INTO `tl_log` VALUES ('c1684dc9-f214-4ada-bc67-ef2eb81ad43f', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 10:42:28', '0');
INSERT INTO `tl_log` VALUES ('f461c1f5-b698-4d7a-a52b-e8388dc638d9', '4d8c9e6b-6796-4244-be21-7a2d6556b099', 'test', '4d8c9e6b-6796-4244-be21-7a2d6556b099', 'User', '增加用户', '2019-03-26 10:43:08', '0');
INSERT INTO `tl_log` VALUES ('f9de1711-b7e6-4daa-87c6-1783c431a925', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 10:44:52', '0');
INSERT INTO `tl_log` VALUES ('b2e10ecc-c129-4095-8d92-fb3ce247b430', '780e8400-e19b-41d4-a716-446655440000', 'admin', '4d8c9e6b-6796-4244-be21-7a2d6556b099', 'User', '删除用户', '2019-03-26 10:45:03', '0');
INSERT INTO `tl_log` VALUES ('fc5e0984-5499-4f7e-b0b9-6bfd8adac8ef', '7d445c95-ca49-425b-8856-67e8a784ec64', 'test', '7d445c95-ca49-425b-8856-67e8a784ec64', 'User', '增加用户', '2019-03-26 10:45:27', '0');
INSERT INTO `tl_log` VALUES ('116d179a-5c3c-4183-aaff-4bda2ed48afe', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 10:53:51', '0');
INSERT INTO `tl_log` VALUES ('399cdb7b-60f5-4ecd-9dca-7c425d221959', '780e8400-e19b-41d4-a716-446655440000', 'admin', '7d445c95-ca49-425b-8856-67e8a784ec64', 'User', '删除用户', '2019-03-26 10:54:00', '0');
INSERT INTO `tl_log` VALUES ('fd0b5776-c3b1-43c7-8abe-0e1356026338', '05061e37-7334-439e-9c3a-b7e6a74fd51a', 'test', '05061e37-7334-439e-9c3a-b7e6a74fd51a', 'User', '增加用户', '2019-03-26 10:54:21', '0');
INSERT INTO `tl_log` VALUES ('ba4b0db0-73f3-48d5-8eab-08de115d13b3', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 10:56:47', '0');
INSERT INTO `tl_log` VALUES ('0830fe03-33dc-4e4e-bb7f-6eeb26f953a5', '780e8400-e19b-41d4-a716-446655440000', 'admin', '05061e37-7334-439e-9c3a-b7e6a74fd51a', 'User', '删除用户', '2019-03-26 10:56:54', '0');
INSERT INTO `tl_log` VALUES ('129929fb-80c1-46df-9d70-6778c5f627d7', 'bb523dfa-9bd1-43e1-a279-5b273a814e37', 'test', 'bb523dfa-9bd1-43e1-a279-5b273a814e37', 'User', '增加用户', '2019-03-26 10:57:18', '0');
INSERT INTO `tl_log` VALUES ('e55c7948-dd21-432d-8536-6694836c7c81', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 11:04:51', '0');
INSERT INTO `tl_log` VALUES ('cc6025d7-771d-4307-853e-b60a4c95f9db', '780e8400-e19b-41d4-a716-446655440000', 'admin', 'bb523dfa-9bd1-43e1-a279-5b273a814e37', 'User', '删除用户', '2019-03-26 11:04:58', '0');
INSERT INTO `tl_log` VALUES ('2b337103-7757-42e4-865c-40eef905adbf', '31208926-66fb-49df-aa8b-bc6cb7505f60', 'test', '31208926-66fb-49df-aa8b-bc6cb7505f60', 'User', '增加用户', '2019-03-26 11:05:17', '0');
INSERT INTO `tl_log` VALUES ('56be0930-596e-4082-bf23-16054455ef77', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 11:11:13', '0');
INSERT INTO `tl_log` VALUES ('6426ab45-da83-4ace-adf7-d9274d5bea71', '780e8400-e19b-41d4-a716-446655440000', 'admin', '31208926-66fb-49df-aa8b-bc6cb7505f60', 'User', '删除用户', '2019-03-26 11:11:19', '0');
INSERT INTO `tl_log` VALUES ('c4ffaaf4-976b-429e-b496-4f1c25b488a8', '0b757751-fea4-48b3-ab86-3d08b147ffbc', 'test', '0b757751-fea4-48b3-ab86-3d08b147ffbc', 'User', '增加用户', '2019-03-26 11:11:38', '0');
INSERT INTO `tl_log` VALUES ('5ec8b769-b0c5-4b40-ae7c-126cc6fff7f2', '780e8400-e19b-41d4-a716-446655440000', 'admin', '0b757751-fea4-48b3-ab86-3d08b147ffbc', 'User', '删除用户', '2019-03-26 11:13:53', '0');
INSERT INTO `tl_log` VALUES ('73c7ffe7-f6bf-4a3d-8896-557e5766e246', '0b05ca18-8c57-41ce-84cf-ba2e4f715d8a', 'test', '0b05ca18-8c57-41ce-84cf-ba2e4f715d8a', 'User', '增加用户', '2019-03-26 11:14:07', '0');
INSERT INTO `tl_log` VALUES ('700c00ab-34c3-4627-ab9c-8dd10abb9de8', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 11:17:42', '0');
INSERT INTO `tl_log` VALUES ('735afcde-f3e1-492a-bd02-49bbcebbd0d3', '780e8400-e19b-41d4-a716-446655440000', 'admin', '0b05ca18-8c57-41ce-84cf-ba2e4f715d8a', 'User', '删除用户', '2019-03-26 11:17:48', '0');
INSERT INTO `tl_log` VALUES ('604f7264-bb0a-4f14-b73f-e43b91b6033c', 'c6b177bc-9c00-41b9-9c86-372db8f85f17', 'test', 'c6b177bc-9c00-41b9-9c86-372db8f85f17', 'User', '增加用户', '2019-03-26 11:18:08', '0');
INSERT INTO `tl_log` VALUES ('f2e2c13c-5364-4398-b443-16ac909137cc', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 11:19:12', '0');
INSERT INTO `tl_log` VALUES ('fe9ae9e9-5184-4975-9c9a-91bc834bced4', '780e8400-e19b-41d4-a716-446655440000', 'admin', 'c6b177bc-9c00-41b9-9c86-372db8f85f17', 'User', '删除用户', '2019-03-26 11:19:19', '0');
INSERT INTO `tl_log` VALUES ('282fc369-3f01-4db3-a117-e91649005b50', '7a5a01e2-34c5-43ee-9243-557ba71fd9d0', 'test', '7a5a01e2-34c5-43ee-9243-557ba71fd9d0', 'User', '增加用户', '2019-03-26 11:19:39', '0');
INSERT INTO `tl_log` VALUES ('a4e45df8-e7b1-4182-88b3-0a53cd8f686c', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 11:21:02', '0');
INSERT INTO `tl_log` VALUES ('3505cb39-78e4-4ed1-9858-af75e80c9328', '780e8400-e19b-41d4-a716-446655440000', 'admin', '7a5a01e2-34c5-43ee-9243-557ba71fd9d0', 'User', '删除用户', '2019-03-26 11:21:11', '0');
INSERT INTO `tl_log` VALUES ('aad28566-5466-49d8-9634-a3ac1f4ac38c', 'cf1c4797-616f-46af-9898-80050b212b3d', 'test', 'cf1c4797-616f-46af-9898-80050b212b3d', 'User', '增加用户', '2019-03-26 11:21:34', '0');
INSERT INTO `tl_log` VALUES ('b26979fe-d7c1-4337-a81c-367f057c2631', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 11:24:13', '0');
INSERT INTO `tl_log` VALUES ('6293a649-16fe-4b69-8e25-95a56b5cea8d', '780e8400-e19b-41d4-a716-446655440000', 'admin', 'cf1c4797-616f-46af-9898-80050b212b3d', 'User', '删除用户', '2019-03-26 11:24:20', '0');
INSERT INTO `tl_log` VALUES ('59c10030-1082-43a9-90ca-5061ec24c0ac', 'c61f23ed-d8c6-4310-a200-acbb6ef0c8d0', 'test', 'c61f23ed-d8c6-4310-a200-acbb6ef0c8d0', 'User', '增加用户', '2019-03-26 11:24:40', '0');
INSERT INTO `tl_log` VALUES ('d178906e-dad1-437a-b0a6-67412d98c3be', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 11:27:42', '0');
INSERT INTO `tl_log` VALUES ('a1c31ff5-4fde-4895-8905-9a507e6cf6c0', '780e8400-e19b-41d4-a716-446655440000', 'admin', 'c61f23ed-d8c6-4310-a200-acbb6ef0c8d0', 'User', '删除用户', '2019-03-26 11:27:51', '0');
INSERT INTO `tl_log` VALUES ('c0c01660-8bcc-4c01-a725-928d13c1003f', 'dd5e27cd-47d7-4cc4-92fa-e3dfaa41639a', 'test', 'dd5e27cd-47d7-4cc4-92fa-e3dfaa41639a', 'User', '增加用户', '2019-03-26 11:28:02', '0');
INSERT INTO `tl_log` VALUES ('4e1b0170-5e00-416a-b3aa-9bedb75cc193', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 11:32:01', '0');
INSERT INTO `tl_log` VALUES ('0d1098d0-3f57-41eb-a4c8-c27077fb6937', '780e8400-e19b-41d4-a716-446655440000', 'admin', 'dd5e27cd-47d7-4cc4-92fa-e3dfaa41639a', 'User', '删除用户', '2019-03-26 11:32:06', '0');
INSERT INTO `tl_log` VALUES ('9b2bb15a-ed76-4a39-bea0-a351322c0630', 'a817f537-03de-4fc9-9d57-cc8bb9712043', 'test', 'a817f537-03de-4fc9-9d57-cc8bb9712043', 'User', '增加用户', '2019-03-26 11:32:25', '0');
INSERT INTO `tl_log` VALUES ('a765d8ba-9ff0-4c3c-871b-d0017069d00d', '76be6285-2e14-4ef6-ac7b-2f23d2579bfb', 'test', '76be6285-2e14-4ef6-ac7b-2f23d2579bfb', 'User', '增加用户', '2019-03-26 11:36:23', '0');
INSERT INTO `tl_log` VALUES ('2a38304a-012a-47b0-a7fb-160ad8a8d26a', '780e8400-e19b-41d4-a716-446655440000', 'admin', '76be6285-2e14-4ef6-ac7b-2f23d2579bfb', 'User', '删除用户', '2019-03-26 11:36:45', '0');
INSERT INTO `tl_log` VALUES ('f56b6aa1-8aad-47fa-ae7b-da3e877e474a', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 11:40:37', '0');
INSERT INTO `tl_log` VALUES ('a489cd13-102a-4102-b3ce-304b655beeeb', 'b7f0bea8-1a21-4a5b-87d1-6be6885b03e5', 'test', 'b7f0bea8-1a21-4a5b-87d1-6be6885b03e5', 'User', '增加用户', '2019-03-26 11:40:49', '0');
INSERT INTO `tl_log` VALUES ('041f3001-e904-4b52-b16f-b28ec7c33e2d', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 15:16:29', '0');
INSERT INTO `tl_log` VALUES ('e651b915-f588-4226-95af-b32ef204ee25', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 16:29:42', '0');
INSERT INTO `tl_log` VALUES ('97344cec-3883-4107-9649-6eaf0a72ebaa', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 16:35:33', '0');
INSERT INTO `tl_log` VALUES ('b3751843-7c84-4632-b730-4a3e2d070a6c', '36c64d9c-b11b-414e-bc5e-244ff4fff908', 'test', '36c64d9c-b11b-414e-bc5e-244ff4fff908', 'User', '增加用户', '2019-03-26 16:36:02', '0');
INSERT INTO `tl_log` VALUES ('161ef209-29e9-452a-aeda-ede7bc424b55', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 16:39:51', '0');
INSERT INTO `tl_log` VALUES ('d9ba12d6-b7c1-48a6-9324-2e9c97421550', '780e8400-e19b-41d4-a716-446655440000', 'admin', '36c64d9c-b11b-414e-bc5e-244ff4fff908', 'User', '删除用户', '2019-03-26 16:41:02', '0');
INSERT INTO `tl_log` VALUES ('fa25e986-4264-49a9-ba5b-f6bcf46b1318', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 16:43:45', '0');
INSERT INTO `tl_log` VALUES ('2f69cb93-a5a6-4948-a9f6-52ef3c5b2009', 'd3323ebe-0a58-44a4-a8cb-5fc4eca6dcdb', 'test', 'd3323ebe-0a58-44a4-a8cb-5fc4eca6dcdb', 'User', '增加用户', '2019-03-26 16:44:07', '0');
INSERT INTO `tl_log` VALUES ('f0ef5ba5-f9bc-4988-b7e4-a2356834a982', '780e8400-e19b-41d4-a716-446655440000', 'admin', 'd3323ebe-0a58-44a4-a8cb-5fc4eca6dcdb', 'User', '删除用户', '2019-03-26 16:46:13', '0');
INSERT INTO `tl_log` VALUES ('6fa20eef-5ace-46af-901b-b97cbbdfd969', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 16:46:39', '0');
INSERT INTO `tl_log` VALUES ('4d8f9a98-78e7-4145-a741-b28e4ae69cb2', 'c9225349-1628-4701-af9c-3b1691bf1e4e', 'test', 'c9225349-1628-4701-af9c-3b1691bf1e4e', 'User', '增加用户', '2019-03-26 16:47:36', '0');
INSERT INTO `tl_log` VALUES ('31baaadb-9fb3-4832-8df3-7f7e39fdaa82', '780e8400-e19b-41d4-a716-446655440000', 'admin', 'c9225349-1628-4701-af9c-3b1691bf1e4e', 'User', '删除用户', '2019-03-26 16:49:21', '0');
INSERT INTO `tl_log` VALUES ('914920a3-2352-4166-9ee1-1d8a596e75d4', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 16:55:28', '0');
INSERT INTO `tl_log` VALUES ('8e2b0932-5cb6-4ab9-b640-6ffca5e10065', 'a8723714-b0c0-4b2a-ac65-0c31b8486e58', 'test', 'a8723714-b0c0-4b2a-ac65-0c31b8486e58', 'User', '增加用户', '2019-03-26 16:55:51', '0');
INSERT INTO `tl_log` VALUES ('805c3561-11ef-4fbc-9f81-425005bbb83d', '780e8400-e19b-41d4-a716-446655440000', 'admin', 'a8723714-b0c0-4b2a-ac65-0c31b8486e58', 'User', '删除用户', '2019-03-26 16:57:25', '0');
INSERT INTO `tl_log` VALUES ('5c572de1-3620-49b9-94d3-cc70148c67e1', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 17:00:17', '0');
INSERT INTO `tl_log` VALUES ('64085b3b-8b3a-497e-8b83-c347d320681e', '27e288a7-5b60-4057-a33b-d79fd1d20518', 'test', '27e288a7-5b60-4057-a33b-d79fd1d20518', 'User', '增加用户', '2019-03-26 17:00:36', '0');
INSERT INTO `tl_log` VALUES ('6a319e6b-90a8-4092-b595-a3bdd6c4556d', '780e8400-e19b-41d4-a716-446655440000', 'admin', '27e288a7-5b60-4057-a33b-d79fd1d20518', 'User', '删除用户', '2019-03-26 17:02:34', '0');
INSERT INTO `tl_log` VALUES ('903b8ee1-a9b9-47c5-87e7-767ac876188e', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 17:08:25', '0');
INSERT INTO `tl_log` VALUES ('670a0fa7-a40d-4bf5-8f19-c9da3afb2a62', '2f66a792-9d2c-4fc9-9122-02e9b5dd94d3', 'test', '2f66a792-9d2c-4fc9-9122-02e9b5dd94d3', 'User', '增加用户', '2019-03-26 17:08:46', '0');
INSERT INTO `tl_log` VALUES ('d2650c55-7cd8-40ea-a292-eb12c5097562', '780e8400-e19b-41d4-a716-446655440000', 'admin', '2f66a792-9d2c-4fc9-9122-02e9b5dd94d3', 'User', '删除用户', '2019-03-26 17:09:14', '0');
INSERT INTO `tl_log` VALUES ('8e824da0-8d9c-42cd-a97a-f2e04cef1594', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 17:11:34', '0');
INSERT INTO `tl_log` VALUES ('d93b3c5f-08e0-484f-b085-3bc4c8fd4acd', '947706b9-6c14-4037-a700-95b4ee6c2834', 'test', '947706b9-6c14-4037-a700-95b4ee6c2834', 'User', '增加用户', '2019-03-26 17:11:56', '0');
INSERT INTO `tl_log` VALUES ('506122a8-0442-47d5-ad3d-52960c28b90c', '780e8400-e19b-41d4-a716-446655440000', 'admin', '947706b9-6c14-4037-a700-95b4ee6c2834', 'User', '删除用户', '2019-03-26 17:12:25', '0');
INSERT INTO `tl_log` VALUES ('3d361134-caf5-4b5f-811c-4b3b7ef63094', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 17:14:16', '0');
INSERT INTO `tl_log` VALUES ('33ea1a5e-bee5-4324-af69-16512568ebdc', '42013769-bf3f-4227-9ee4-5fbb791c8dbf', 'test', '42013769-bf3f-4227-9ee4-5fbb791c8dbf', 'User', '增加用户', '2019-03-26 17:14:36', '0');
INSERT INTO `tl_log` VALUES ('c0e3b7e3-eac6-4011-a475-405e2f5a90c8', '780e8400-e19b-41d4-a716-446655440000', 'admin', '42013769-bf3f-4227-9ee4-5fbb791c8dbf', 'User', '删除用户', '2019-03-26 17:15:00', '0');
INSERT INTO `tl_log` VALUES ('956e83ea-46a8-4227-9ccf-509c8c0f221c', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 17:17:18', '0');
INSERT INTO `tl_log` VALUES ('3d967550-0989-4007-bac9-bfaf938fa940', 'ba280ec4-253b-44ae-923c-90dd39d473f7', 'test', 'ba280ec4-253b-44ae-923c-90dd39d473f7', 'User', '增加用户', '2019-03-26 17:17:36', '0');
INSERT INTO `tl_log` VALUES ('8da22138-f736-4c6e-a634-c6f3b2148c7f', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 17:24:15', '0');
INSERT INTO `tl_log` VALUES ('4b224278-368e-47a0-a73a-e321e30696d4', '780e8400-e19b-41d4-a716-446655440000', 'admin', 'ba280ec4-253b-44ae-923c-90dd39d473f7', 'User', '删除用户', '2019-03-26 17:26:07', '0');
INSERT INTO `tl_log` VALUES ('d97a6a23-d59a-4a46-b498-43de41ea9a58', '780e8400-e19b-41d4-a716-446655440000', 'admin', 'ba280ec4-253b-44ae-923c-90dd39d473f7', 'User', '删除用户', '2019-03-26 17:26:45', '0');
INSERT INTO `tl_log` VALUES ('505e2c22-3f75-4b9e-ae1e-c7fa158aa809', '780e8400-e19b-41d4-a716-446655440000', 'admin', 'ba280ec4-253b-44ae-923c-90dd39d473f7', 'User', '删除用户', '2019-03-26 17:27:40', '0');
INSERT INTO `tl_log` VALUES ('c2678bab-b7f5-4df1-a8af-fef347e3370b', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 17:28:45', '0');
INSERT INTO `tl_log` VALUES ('f491871e-2f36-4980-a396-81e4f3da05c7', '780e8400-e19b-41d4-a716-446655440000', 'admin', 'ba280ec4-253b-44ae-923c-90dd39d473f7', 'User', '删除用户', '2019-03-26 17:29:12', '0');
INSERT INTO `tl_log` VALUES ('746c2116-d9d2-4c79-bc2b-14f117e619c1', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 17:32:52', '0');
INSERT INTO `tl_log` VALUES ('f0ae55dd-f498-4f75-99fd-6012704e877c', '780e8400-e19b-41d4-a716-446655440000', 'admin', 'ba280ec4-253b-44ae-923c-90dd39d473f7', 'User', '删除用户', '2019-03-26 17:34:19', '0');
INSERT INTO `tl_log` VALUES ('b4d0cfd5-ad59-4f6e-95b0-eff8a7748c8a', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 17:35:34', '0');
INSERT INTO `tl_log` VALUES ('c19eff89-2ebc-4959-ac12-ec8be34ba2fc', '780e8400-e19b-41d4-a716-446655440000', 'admin', 'ba280ec4-253b-44ae-923c-90dd39d473f7', 'User', '删除用户', '2019-03-26 17:36:17', '0');
INSERT INTO `tl_log` VALUES ('c760832f-89cc-4929-b258-fe586477f66c', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 17:37:28', '0');
INSERT INTO `tl_log` VALUES ('6c479494-930c-4a0d-b4c8-3a60b2261c79', '780e8400-e19b-41d4-a716-446655440000', 'admin', 'ba280ec4-253b-44ae-923c-90dd39d473f7', 'User', '删除用户', '2019-03-26 17:37:52', '0');
INSERT INTO `tl_log` VALUES ('e9ee9a1f-16c4-44ac-a1f7-a585ec9945ed', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 19:51:16', '0');
INSERT INTO `tl_log` VALUES ('a694ca9a-b245-451e-a125-839936688d19', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 19:53:52', '0');
INSERT INTO `tl_log` VALUES ('aec54123-136a-4b48-9854-75d495fd1bc1', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 19:56:59', '0');
INSERT INTO `tl_log` VALUES ('77c78335-dae5-4e20-9922-2f45c7a4fb78', '780e8400-e19b-41d4-a716-446655440000', 'admin', 'ba280ec4-253b-44ae-923c-90dd39d473f7', 'User', '删除用户', '2019-03-26 19:57:54', '0');
INSERT INTO `tl_log` VALUES ('02fbae32-8dd6-43ba-8221-2f5737eaa604', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 20:41:18', '0');
INSERT INTO `tl_log` VALUES ('d433e421-4e1c-407b-95ab-f1024d1e662e', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 20:44:13', '0');
INSERT INTO `tl_log` VALUES ('1ce4faa1-b7b5-4614-a739-4d5c7099b547', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 20:46:57', '0');
INSERT INTO `tl_log` VALUES ('50428f64-f89e-4500-9772-c0c9674b75b4', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 20:49:46', '0');
INSERT INTO `tl_log` VALUES ('6850a15d-4d62-4879-864c-4eb5fcfffdf5', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 20:50:25', '0');
INSERT INTO `tl_log` VALUES ('b6db45f4-6dfc-43ad-9b39-dd4b41343a84', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 21:00:06', '0');
INSERT INTO `tl_log` VALUES ('867b5220-03fa-4e08-8f5b-75e859882811', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 21:03:39', '0');
INSERT INTO `tl_log` VALUES ('606e4837-231d-4c1a-af73-9a696324347b', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 21:05:14', '0');
INSERT INTO `tl_log` VALUES ('c243842d-bbfe-4057-b846-b46b3e7371e5', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 21:10:07', '0');
INSERT INTO `tl_log` VALUES ('a2e389ff-5d79-47b6-b235-06e59c85d953', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-26 21:13:37', '0');
INSERT INTO `tl_log` VALUES ('c86f73a0-6d40-4c7f-aecc-1ce358115e79', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 09:27:53', '0');
INSERT INTO `tl_log` VALUES ('6ef78e81-9e1b-4797-919e-bf5dd0d10468', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 09:35:50', '0');
INSERT INTO `tl_log` VALUES ('a1d9115d-37fd-40b7-886a-1918bf1a1e04', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 09:36:17', '0');
INSERT INTO `tl_log` VALUES ('f8dcdbe5-99b6-4d47-930c-9b8ffd221807', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 09:38:13', '0');
INSERT INTO `tl_log` VALUES ('a2359ea6-a303-4ecf-954c-63e6d02b4d2b', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 09:40:45', '0');
INSERT INTO `tl_log` VALUES ('ec0337ab-ba39-43c8-9cb8-d2aaba05ad58', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 10:15:26', '0');
INSERT INTO `tl_log` VALUES ('e0bdaad6-5876-4c56-b9c8-9c258b5a2cf8', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 10:19:53', '0');
INSERT INTO `tl_log` VALUES ('70eceafb-acdc-44a7-a020-93bb68ac26b2', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 10:23:34', '0');
INSERT INTO `tl_log` VALUES ('8dcbbf1e-b6bc-4d8b-aa3c-94f98f26a0de', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 10:27:14', '0');
INSERT INTO `tl_log` VALUES ('78c6e09f-36ba-457a-ae66-b133967dfe18', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 10:42:48', '0');
INSERT INTO `tl_log` VALUES ('7bbf7f65-81c8-4090-bb1e-17e2fbd19148', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 10:45:28', '0');
INSERT INTO `tl_log` VALUES ('91bd3a84-d8f1-4a7a-83ed-cd1146ddfa80', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 10:45:38', '0');
INSERT INTO `tl_log` VALUES ('90d0de91-caeb-4c90-ab48-a5d39a9d697c', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 11:01:51', '0');
INSERT INTO `tl_log` VALUES ('4963ab5d-0fc2-4318-ae3a-7c8801e59b48', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 11:03:55', '0');
INSERT INTO `tl_log` VALUES ('c90a7566-5860-4d89-96a0-b02cff22400e', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 11:06:24', '0');
INSERT INTO `tl_log` VALUES ('56562d6d-ed01-47d9-bdfa-72d86513a4eb', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 11:11:07', '0');
INSERT INTO `tl_log` VALUES ('4f331c50-530e-4d71-a922-6519cf2e8788', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 11:14:25', '0');
INSERT INTO `tl_log` VALUES ('027ef9e1-bd11-4287-8306-d262ab9aca41', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 11:17:06', '0');
INSERT INTO `tl_log` VALUES ('2c28e430-7eec-43c6-ac25-b4c556e55796', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 11:21:21', '0');
INSERT INTO `tl_log` VALUES ('ab61351c-e269-40de-8703-81191e846012', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 11:24:45', '0');
INSERT INTO `tl_log` VALUES ('7a4c41bf-9b5e-498f-a679-6b5dde78e07a', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 13:09:41', '0');
INSERT INTO `tl_log` VALUES ('557dc8f1-6b60-4d19-9255-25f326471cab', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 13:11:38', '0');
INSERT INTO `tl_log` VALUES ('727c0a40-e3e7-4ce8-8e84-58edb4c49a83', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 13:14:07', '0');
INSERT INTO `tl_log` VALUES ('cb77bf76-1d1b-4c1c-a1a9-84c8c9b71f16', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 13:16:06', '0');
INSERT INTO `tl_log` VALUES ('72a9c15b-67e5-4842-80a1-ed86a8e3aae5', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 13:40:00', '0');
INSERT INTO `tl_log` VALUES ('f99197ed-a411-4489-91a2-5267e3f1edbb', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 13:45:32', '0');
INSERT INTO `tl_log` VALUES ('6279c4c0-0411-4f09-b3a6-d2785605a183', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 14:05:25', '0');
INSERT INTO `tl_log` VALUES ('b8f4fceb-7617-4bcc-aa87-4f5c689622bb', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 14:16:12', '0');
INSERT INTO `tl_log` VALUES ('8784a44e-8a71-42f5-967c-9ed3d9af28ea', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 14:19:20', '0');
INSERT INTO `tl_log` VALUES ('744e4fb1-313e-4ca1-9764-5b69563fcf29', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 14:42:13', '0');
INSERT INTO `tl_log` VALUES ('37b7e75c-86a0-46a3-a760-0c98a90b74a7', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 14:42:34', '0');
INSERT INTO `tl_log` VALUES ('3157c762-1ba4-4dcf-9b56-d7c3484b0cc4', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 14:45:42', '0');
INSERT INTO `tl_log` VALUES ('348757d9-43dd-41b5-95c3-cf9ef2df1692', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 14:54:26', '0');
INSERT INTO `tl_log` VALUES ('3036ed7c-3a86-41a2-bb5c-90cce359bf95', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 14:59:32', '0');
INSERT INTO `tl_log` VALUES ('0f90c122-47c5-4de9-bd44-52fe68e7b78f', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 15:04:50', '0');
INSERT INTO `tl_log` VALUES ('6c55aecd-27a6-4acf-8031-d3b941beed8b', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 15:25:35', '0');
INSERT INTO `tl_log` VALUES ('8a56a8cc-bc0e-406f-b4be-7d141c8e095d', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 15:29:01', '0');
INSERT INTO `tl_log` VALUES ('827d62c9-c6ff-4987-8ae9-a00137a390d7', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 15:39:43', '0');
INSERT INTO `tl_log` VALUES ('88a47d97-d0e4-4ae6-9edc-429a26df6384', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-27 15:46:43', '0');
INSERT INTO `tl_log` VALUES ('6ab5c264-447f-4d76-aa04-652f2d3f4d28', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-02 20:37:21', '0');
INSERT INTO `tl_log` VALUES ('187c78ed-2021-49ad-9137-4e16d498bd2c', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 08:28:12', '0');
INSERT INTO `tl_log` VALUES ('1496a158-6f32-4508-9d6e-621143fadbae', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 08:53:19', '0');
INSERT INTO `tl_log` VALUES ('c81dfd72-169f-4bae-99b2-a6c0c6e701a4', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 08:55:18', '0');
INSERT INTO `tl_log` VALUES ('df29900c-407e-4af1-87dc-16652939795a', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 08:57:05', '0');
INSERT INTO `tl_log` VALUES ('d587791a-8a7e-4285-9083-5e6493d2d50c', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 09:00:19', '0');
INSERT INTO `tl_log` VALUES ('bb47f105-0b0a-4ee4-9df0-5cb9a4349771', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 09:04:44', '0');
INSERT INTO `tl_log` VALUES ('9dd6b474-3f91-49f4-95cf-7552a950aed8', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 09:10:11', '0');
INSERT INTO `tl_log` VALUES ('fbb1e87b-d549-42f5-8fba-f0c76e7cb7f4', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 09:16:34', '0');
INSERT INTO `tl_log` VALUES ('125eac39-0a33-409b-bc34-410eda932d89', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 09:25:37', '0');
INSERT INTO `tl_log` VALUES ('bd454861-ad10-4c37-9966-94fa1e332b86', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 09:31:12', '0');
INSERT INTO `tl_log` VALUES ('f162df49-8abf-44c9-9abd-a407a4ff4e8b', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 09:32:21', '0');
INSERT INTO `tl_log` VALUES ('b0d588e8-2847-4207-b0b8-e7a6d0533f23', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 09:35:31', '0');
INSERT INTO `tl_log` VALUES ('50ea2128-76e7-4224-8a2b-fa460ee21ea2', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 09:35:49', '0');
INSERT INTO `tl_log` VALUES ('bbc13243-be5a-40d8-b8cd-6a2d2dd6a2bc', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 09:41:03', '0');
INSERT INTO `tl_log` VALUES ('6c3c6faa-ec4d-4364-9b78-92b27b3d3777', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 09:45:03', '0');
INSERT INTO `tl_log` VALUES ('8e26dcb9-066c-43b9-8ed6-1388099cde53', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 09:52:02', '0');
INSERT INTO `tl_log` VALUES ('f3ec34e5-ade9-45e4-918e-ba417b233bf6', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 09:55:33', '0');
INSERT INTO `tl_log` VALUES ('b9001477-4959-46cd-b461-c6e42327c7b5', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 10:33:54', '0');
INSERT INTO `tl_log` VALUES ('c89a46d5-de63-4b92-8fef-502f72bbf914', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 10:46:41', '0');
INSERT INTO `tl_log` VALUES ('a2db0a04-c9c5-4257-953e-5f7d6a1af9aa', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 10:58:53', '0');
INSERT INTO `tl_log` VALUES ('c4b8ada4-db91-4fbe-90be-b5d5417e7b1b', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 11:03:49', '0');
INSERT INTO `tl_log` VALUES ('23ca6403-1335-4b62-b970-04d828a2ca34', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 11:05:37', '0');
INSERT INTO `tl_log` VALUES ('6c534790-4956-48b8-b0c3-2fd7a82ddd2b', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 11:12:49', '0');
INSERT INTO `tl_log` VALUES ('c8716814-9a42-4af3-80d4-d0010c825c23', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 11:13:06', '0');
INSERT INTO `tl_log` VALUES ('ead6ebe9-ff25-41e0-88e7-00abdbf26776', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 11:14:15', '0');
INSERT INTO `tl_log` VALUES ('5f3e0c7e-81f1-4418-97f1-cdc926c11916', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 11:25:19', '0');
INSERT INTO `tl_log` VALUES ('9fcbf191-663e-4ba7-8fc0-61d455095067', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 11:30:21', '0');
INSERT INTO `tl_log` VALUES ('81e94775-3f79-4cd4-82aa-f22dc59e6d0f', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 11:38:04', '0');
INSERT INTO `tl_log` VALUES ('773748ed-8670-4fe9-a44a-35629a796c6a', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 11:42:02', '0');
INSERT INTO `tl_log` VALUES ('7a55491b-7931-4c11-a3ad-f481b12c8ea4', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 12:44:17', '0');
INSERT INTO `tl_log` VALUES ('9b475c9c-d033-44f8-9153-fad990aded9e', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 12:45:43', '0');
INSERT INTO `tl_log` VALUES ('6ca46a95-bde0-4e93-9088-4c821671a6e5', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 12:45:59', '0');
INSERT INTO `tl_log` VALUES ('858edb04-2e7a-45d6-81e8-5d00de6b4654', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 13:07:41', '0');
INSERT INTO `tl_log` VALUES ('d2d81d9d-ba8d-4960-90c1-353424c28839', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 13:11:31', '0');
INSERT INTO `tl_log` VALUES ('fcd9020b-154f-4f5c-8a23-70bfe8f8380b', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 13:13:49', '0');
INSERT INTO `tl_log` VALUES ('acf9cc4e-700c-4d8f-b5ec-cba4d9fd7375', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 13:16:40', '0');
INSERT INTO `tl_log` VALUES ('a8aa02e7-c04d-4e5f-ac74-4f8fb1d3ac45', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 13:22:52', '0');
INSERT INTO `tl_log` VALUES ('a63ecc0b-c73e-4e1e-a7b0-84176fffa0cd', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 13:24:33', '0');
INSERT INTO `tl_log` VALUES ('dbac5b8b-8380-4b39-9a3f-a516e6f9a80d', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 13:29:45', '0');
INSERT INTO `tl_log` VALUES ('851b0e6b-5473-46b6-8ee8-42c0f6e9b202', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 13:32:29', '0');
INSERT INTO `tl_log` VALUES ('21e14e51-6f72-4490-bdfa-b106d131e5d1', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 13:35:44', '0');
INSERT INTO `tl_log` VALUES ('08678d2e-f31c-4c12-89d0-0677ce42bd99', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 13:39:17', '0');
INSERT INTO `tl_log` VALUES ('68b3144b-535e-411e-a0ef-9c6b57ccfb44', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 13:44:13', '0');
INSERT INTO `tl_log` VALUES ('b0f0656b-0455-48bb-ae51-c302c406429b', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 13:46:47', '0');
INSERT INTO `tl_log` VALUES ('5ca2cf95-3775-4ffa-bf27-038407ba61eb', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 13:55:59', '0');
INSERT INTO `tl_log` VALUES ('270a1a27-768b-4df7-b3ac-bafb7b89a2c9', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 14:02:47', '0');
INSERT INTO `tl_log` VALUES ('9f1c40ba-25d5-486e-ba9d-995dea340bc5', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 14:03:07', '0');
INSERT INTO `tl_log` VALUES ('b88a3231-fa83-449c-8fc1-80a78173afd3', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 14:04:39', '0');
INSERT INTO `tl_log` VALUES ('0ada507f-012b-4b85-b653-c61030592ad2', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 14:07:53', '0');
INSERT INTO `tl_log` VALUES ('acb350b9-44bd-4d87-b971-14e2926dea1c', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 14:14:19', '0');
INSERT INTO `tl_log` VALUES ('626cf6bd-c630-4378-9e5e-cf3709346592', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 14:17:58', '0');
INSERT INTO `tl_log` VALUES ('a5003690-1900-4fcf-af20-fb18c533b3a0', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 14:19:03', '0');
INSERT INTO `tl_log` VALUES ('50d990cd-ec7c-4e5d-a1e4-7287ea59ee7e', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 14:21:04', '0');
INSERT INTO `tl_log` VALUES ('ea1163e1-4f31-427e-8fd0-af8977ef6dcd', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 14:33:09', '0');
INSERT INTO `tl_log` VALUES ('c4409d5f-1933-403c-a596-299b4458d01e', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 14:38:05', '0');
INSERT INTO `tl_log` VALUES ('a9cb6aed-392c-428e-9673-b67246b614a2', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 14:42:14', '0');
INSERT INTO `tl_log` VALUES ('9aceef45-4a03-4e59-8657-a19f0e429e19', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 14:43:42', '0');
INSERT INTO `tl_log` VALUES ('cbf5a3cc-5eeb-4729-b012-d51419a47b7e', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 14:46:25', '0');
INSERT INTO `tl_log` VALUES ('3665df0b-deb5-470c-af61-c3ab7dcf0343', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 14:49:42', '0');
INSERT INTO `tl_log` VALUES ('f4657a6c-9cac-40f8-960c-aba7343c3092', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 14:55:31', '0');
INSERT INTO `tl_log` VALUES ('6210f337-8665-40e1-b649-ccd2f0f3714c', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 15:01:45', '0');
INSERT INTO `tl_log` VALUES ('33875c96-05f1-4d19-a8c1-e022ae5d34be', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 15:04:59', '0');
INSERT INTO `tl_log` VALUES ('678bfcf1-3460-46f5-a725-9e6219b9bc7e', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 15:05:11', '0');
INSERT INTO `tl_log` VALUES ('b7de5efc-ce81-4651-97c0-5383bcde4eb4', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 15:12:34', '0');
INSERT INTO `tl_log` VALUES ('13cdbe82-6fc6-48a4-bc20-cf8b2c6efce4', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 15:20:06', '0');
INSERT INTO `tl_log` VALUES ('593602a1-0fd8-476d-9078-155459c49b1a', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 15:20:21', '0');
INSERT INTO `tl_log` VALUES ('17d6218f-2f78-459a-b45e-271212aefe02', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 15:21:56', '0');
INSERT INTO `tl_log` VALUES ('a2b64ea4-3c67-41fa-b3f8-fc2c9d7d83b6', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 15:26:33', '0');
INSERT INTO `tl_log` VALUES ('4cb97aa0-4b0e-4b2a-9b3d-066cdab6aaea', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 15:29:02', '0');
INSERT INTO `tl_log` VALUES ('20ce329a-a01c-4230-ac9f-d3da179c5f0e', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 15:31:07', '0');
INSERT INTO `tl_log` VALUES ('3abfa14e-f871-4351-8ab7-9c5262ccfed3', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 15:35:27', '0');
INSERT INTO `tl_log` VALUES ('8a99367d-9d15-44b3-a18d-921c3554d997', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 15:41:21', '0');
INSERT INTO `tl_log` VALUES ('cb24724e-788a-4599-84fd-44883f69b026', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 16:08:37', '0');
INSERT INTO `tl_log` VALUES ('95941ee5-add6-4cfd-9567-61e78ee98ce0', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 16:34:31', '0');
INSERT INTO `tl_log` VALUES ('0a843b0a-9f18-4cce-8e7c-8da34f0ea8fa', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 16:36:21', '0');
INSERT INTO `tl_log` VALUES ('acb2d142-9647-49ba-abef-8189173bad04', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 16:38:26', '0');
INSERT INTO `tl_log` VALUES ('9af41bcd-56d3-4b44-813e-d549b2338932', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-03 16:40:43', '0');
INSERT INTO `tl_log` VALUES ('adc114b6-cae7-48a4-85e4-cc5405f80abb', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 08:38:41', '0');
INSERT INTO `tl_log` VALUES ('53856411-df36-479a-ae98-89af1e088e5a', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 08:44:59', '0');
INSERT INTO `tl_log` VALUES ('eccd4c1f-9878-4665-b919-c6925caf7ec8', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 09:54:29', '0');
INSERT INTO `tl_log` VALUES ('c236116c-8402-44ad-8ff2-342f20b90e02', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 09:57:25', '0');
INSERT INTO `tl_log` VALUES ('72d313e3-5c5f-498c-81a0-3110e5f85a76', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 10:02:12', '0');
INSERT INTO `tl_log` VALUES ('b7764e27-d786-40a9-8fc4-6a1944d56301', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 10:14:43', '0');
INSERT INTO `tl_log` VALUES ('0ab3dec5-b3cb-43da-9cd8-c1ac70de415f', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 10:20:10', '0');
INSERT INTO `tl_log` VALUES ('0cf38923-be3a-4109-a393-1abc8c2dd4d7', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 10:20:44', '0');
INSERT INTO `tl_log` VALUES ('873ffd21-85e5-43ab-a08d-6ca0e8aa5eaa', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 10:25:50', '0');
INSERT INTO `tl_log` VALUES ('928a4dbf-de16-4e87-8516-b68a6b271965', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 11:13:06', '0');
INSERT INTO `tl_log` VALUES ('9caacca3-e3f2-46d5-a284-2cea2dbf84f0', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 11:15:41', '0');
INSERT INTO `tl_log` VALUES ('72d1356b-9c23-4d2d-8ba2-43d905876e8e', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 11:24:36', '0');
INSERT INTO `tl_log` VALUES ('28f65e35-7001-49ca-b266-898d2a6eb409', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 11:27:12', '0');
INSERT INTO `tl_log` VALUES ('2831e002-c5c8-414d-acac-6ba0158a8456', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 15:05:04', '0');
INSERT INTO `tl_log` VALUES ('68626340-f171-475a-8650-a7b20062298e', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 15:10:56', '0');
INSERT INTO `tl_log` VALUES ('cfd48307-e5d3-4d44-a862-97b4ada86d3b', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 15:44:26', '0');
INSERT INTO `tl_log` VALUES ('e9eff79e-3cdc-4073-aa28-35e493c01b92', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 16:21:36', '0');
INSERT INTO `tl_log` VALUES ('7b38afea-c45f-4547-b4cf-b17a228f0a15', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 16:26:16', '0');
INSERT INTO `tl_log` VALUES ('d5aa3001-511e-4428-8098-b4fa6e8e6151', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 16:34:01', '0');
INSERT INTO `tl_log` VALUES ('99bd11c5-d726-4430-bce8-5111b8cf9e8c', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 16:36:59', '0');
INSERT INTO `tl_log` VALUES ('7488bf2d-9f16-41d9-9e44-df77ca9b8e4e', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 16:41:09', '0');
INSERT INTO `tl_log` VALUES ('1a932d41-a13a-40a9-b41a-48a02ca8c4b1', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 16:56:04', '0');
INSERT INTO `tl_log` VALUES ('82404bf3-cbd4-4c5f-bfed-6af3c739e87e', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 16:58:43', '0');
INSERT INTO `tl_log` VALUES ('743da9f6-3ac3-4544-8b5e-b465a0bce808', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 17:07:00', '0');
INSERT INTO `tl_log` VALUES ('25497d96-1fd8-4599-8972-6a36a01c3089', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-04 17:08:21', '0');
INSERT INTO `tl_log` VALUES ('42d9d69e-37fc-4bd5-83ca-e907b3cc97c5', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 08:38:52', '0');
INSERT INTO `tl_log` VALUES ('20a96ae3-ab29-4d96-b3a4-3034cc9a9d56', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 08:43:31', '0');
INSERT INTO `tl_log` VALUES ('2fe54b3f-9cb8-4073-bb10-59b6888fc884', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 08:45:42', '0');
INSERT INTO `tl_log` VALUES ('d58b5006-926a-4bd0-9e7c-c0d90347283e', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 08:47:47', '0');
INSERT INTO `tl_log` VALUES ('89209b4d-66dd-48ad-b9e7-fb044eede4a8', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 08:59:28', '0');
INSERT INTO `tl_log` VALUES ('5119f802-3fe4-4de5-9ac2-85ef2778f6f5', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 09:01:38', '0');
INSERT INTO `tl_log` VALUES ('09dcd0a1-3637-4e7b-bb45-c143b905b67d', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 09:03:40', '0');
INSERT INTO `tl_log` VALUES ('75d71b3e-dcfd-4f3b-9182-3372c4baf193', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 09:11:45', '0');
INSERT INTO `tl_log` VALUES ('aff7c8ea-0d9f-4875-83f3-0ca9a15eba95', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 09:17:13', '0');
INSERT INTO `tl_log` VALUES ('2399357f-ac42-41c1-94a2-da6056c81a8a', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 09:21:24', '0');
INSERT INTO `tl_log` VALUES ('833ada8d-ebb7-4dc9-861f-8add463a37ab', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 09:26:07', '0');
INSERT INTO `tl_log` VALUES ('662219b2-6576-4961-b6b1-7e935dc5699b', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 09:30:10', '0');
INSERT INTO `tl_log` VALUES ('717066dc-9ad9-43e2-a625-69d1205e9293', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 09:41:03', '0');
INSERT INTO `tl_log` VALUES ('2bf63454-f19e-4064-826c-0983441eb200', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 09:41:34', '0');
INSERT INTO `tl_log` VALUES ('8b00f950-c32b-4fa8-9d28-f8eeaf749b43', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 09:44:44', '0');
INSERT INTO `tl_log` VALUES ('280c4185-64e4-4559-9d51-3b1e03cb30f2', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 09:46:45', '0');
INSERT INTO `tl_log` VALUES ('c89e889c-9c7a-4941-825d-aa822929d5b3', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 09:52:33', '0');
INSERT INTO `tl_log` VALUES ('d693ad15-f6e8-4a12-8173-afd5d5b8e38f', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 10:06:27', '0');
INSERT INTO `tl_log` VALUES ('362d89b3-ce14-40b4-acf0-6f522aa7c5d5', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 10:33:31', '0');
INSERT INTO `tl_log` VALUES ('238aaaf2-8571-4089-919c-de865fb88fdc', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 10:35:13', '0');
INSERT INTO `tl_log` VALUES ('0e4d71f5-16cd-4d84-9da9-e9797a0fef13', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 10:41:48', '0');
INSERT INTO `tl_log` VALUES ('9b547345-79f8-48eb-8508-6b9c9e8e4639', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 10:44:45', '0');
INSERT INTO `tl_log` VALUES ('b1db2a3c-b264-4ae5-b404-d48596fe7cd9', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-05 10:48:51', '0');
INSERT INTO `tl_log` VALUES ('a69c2c74-2053-42a4-8856-86fcdc651ff3', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-08 09:14:28', '0');
INSERT INTO `tl_log` VALUES ('7edb03e0-e893-4a38-8f92-a8152bef8a2a', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-08 09:22:05', '0');
INSERT INTO `tl_log` VALUES ('38b305fe-cc08-427f-9f27-608c522519ff', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-08 09:30:36', '0');
INSERT INTO `tl_log` VALUES ('39c4141a-c0cf-454c-851d-2a87b5852c38', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-08 09:36:10', '0');
INSERT INTO `tl_log` VALUES ('9da65cff-9f16-4f27-a69d-68dcfb10933f', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-08 09:42:15', '0');
INSERT INTO `tl_log` VALUES ('2857bfd2-f7a6-4153-845d-00c9d37140b0', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-08 09:53:29', '0');
INSERT INTO `tl_log` VALUES ('1dc3fbce-7746-4408-8b35-3af2230cddd4', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-08 09:54:44', '0');
INSERT INTO `tl_log` VALUES ('ddfd8ce5-23b3-4f90-b465-8cfd8867d363', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-08 09:56:11', '0');
INSERT INTO `tl_log` VALUES ('05e9b00c-be15-4bb6-93f4-4da859538d9a', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-08 10:00:05', '0');
INSERT INTO `tl_log` VALUES ('4e3a6c97-eaf0-4e12-9adf-07d9a6cd561e', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-08 14:49:44', '0');
INSERT INTO `tl_log` VALUES ('6dbfc52b-5e5b-4cba-bacd-82f99af8f42f', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-08 15:24:48', '0');
INSERT INTO `tl_log` VALUES ('caa749ab-19a9-48c2-ad68-1407843e7bbc', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-08 15:26:37', '0');
INSERT INTO `tl_log` VALUES ('b393b41f-c83f-4a80-a2f1-6906fec06eb2', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-08 15:30:21', '0');
INSERT INTO `tl_log` VALUES ('cadfb72d-33cc-4364-a071-3a5d9dc94f39', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-08 18:46:56', '0');
INSERT INTO `tl_log` VALUES ('4197f559-8fbe-471d-9adb-0341f5643f70', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-09 17:26:37', '0');
INSERT INTO `tl_log` VALUES ('ab772730-db13-4bcf-a97b-0d965d487c90', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-09 17:38:29', '0');
INSERT INTO `tl_log` VALUES ('e6897a72-60de-4714-96b2-785b4749e258', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-04-09 17:41:42', '0');

-- ----------------------------
-- Table structure for tl_namespace
-- ----------------------------
DROP TABLE IF EXISTS `tl_namespace`;
CREATE TABLE `tl_namespace` (
  `uuid` varchar(36) NOT NULL,
  `namespace` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_namespace
-- ----------------------------

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
INSERT INTO `tl_pod` VALUES ('15075e7c-adc5-4354-a966-7b2761088985', 'fe4b4b91-cc9a-4fb5-bb12-dc7f5da13b58', '511e69ef-ce3f-46eb-9c53-0cf2dfee8039', 'deploy-hello-rc-nb2vj', 'default', '132.232.56.2', null, '0', '132.232.93.3/library/hello-world:1.0', 'Pending', '2019-04-03 09:54:59');

-- ----------------------------
-- Table structure for tl_rc
-- ----------------------------
DROP TABLE IF EXISTS `tl_rc`;
CREATE TABLE `tl_rc` (
  `uuid` varchar(36) DEFAULT NULL,
  `deployment_uuid` varchar(36) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `desiredCount` varchar(255) DEFAULT NULL,
  `namespace` varchar(64) DEFAULT NULL,
  `replicas` varchar(100) DEFAULT NULL,
  `selector` text,
  `template` text,
  `content` text,
  `scaleType` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_rc
-- ----------------------------
INSERT INTO `tl_rc` VALUES ('fe4b4b91-cc9a-4fb5-bb12-dc7f5da13b58', '501755c9-a233-4287-8a12-ead4e0e767eb', 'tom', null, 'default', null, '{\"service\":\"tom\",\"namespace\":\"default\"}', '{\"metadata\":{\"generation\":0,\"finalizers\":[],\"resourceVersion\":\"\",\"annotations\":null,\"generateName\":\"\",\"deletionTimestamp\":\"\",\"labels\":{\"service\":\"tom\",\"namespace\":\"default\"},\"ownerReferences\":[],\"selfLink\":\"\",\"deletionGracePeriodSeconds\":0,\"uid\":\"\",\"initializers\":null,\"clusterName\":\"\",\"creationTimestamp\":\"\",\"name\":\"\",\"namespace\":\"default\",\"additionalProperties\":{}},\"additionalProperties\":{},\"spec\":{\"dnsPolicy\":\"\",\"nodeName\":\"\",\"terminationGracePeriodSeconds\":0,\"dnsConfig\":null,\"hostNetwork\":false,\"serviceAccountName\":\"\",\"imagePullSecrets\":[],\"priorityClassName\":\"\",\"hostAliases\":[],\"securityContext\":null,\"nodeSelector\":null,\"hostname\":\"\",\"tolerations\":[],\"automountServiceAccountToken\":false,\"schedulerName\":\"\",\"activeDeadlineSeconds\":0,\"hostIPC\":false,\"volumes\":[],\"serviceAccount\":\"\",\"priority\":0,\"restartPolicy\":\"Always\",\"subdomain\":\"\",\"containers\":[{\"volumeDevices\":[],\"image\":\"132.232.93.3/library/busybox:2.0\",\"imagePullPolicy\":\"IfNotPresent\",\"livenessProbe\":null,\"stdin\":false,\"terminationMessagePolicy\":\"\",\"terminationMessagePath\":\"\",\"workingDir\":\"\",\"resources\":{\"additionalProperties\":{},\"requests\":{\"memory\":{\"amount\":\"20Mi\",\"format\":\"\",\"additionalProperties\":{}},\"cpu\":{\"amount\":\"0.2\",\"format\":\"\",\"additionalProperties\":{}}},\"limits\":{\"memory\":{\"amount\":\"20Mi\",\"format\":\"\",\"additionalProperties\":{}},\"cpu\":{\"amount\":\"0.2\",\"format\":\"\",\"additionalProperties\":{}}}},\"securityContext\":null,\"env\":[],\"ports\":[{\"protocol\":\"\",\"hostIP\":\"\",\"name\":\"busy\",\"containerPort\":8090,\"hostPort\":0,\"additionalProperties\":{}}],\"command\":[\"-f\",\"/dev/null\"],\"volumeMounts\":[],\"args\":[\"-f\",\"/dev/null\"],\"lifecycle\":null,\"name\":\"busybox\",\"tty\":false,\"readinessProbe\":null,\"additionalProperties\":{},\"stdinOnce\":false,\"envFrom\":[]}],\"additionalProperties\":{},\"initContainers\":[],\"affinity\":null,\"hostPID\":false}}', null, null, '2019-03-22 21:59:48', '2019-03-22 21:59:48');

-- ----------------------------
-- Table structure for tl_repository
-- ----------------------------
DROP TABLE IF EXISTS `tl_repository`;
CREATE TABLE `tl_repository` (
  `repo_uuid` varchar(36) NOT NULL,
  `user_uuid` varchar(36) NOT NULL,
  `repo_name` varchar(64) NOT NULL,
  `repo_type` varchar(10) NOT NULL,
  `url` varchar(100) NOT NULL,
  `login_name` varchar(100) NOT NULL,
  `login_psd` varchar(255) NOT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_repository
-- ----------------------------
INSERT INTO `tl_repository` VALUES ('550e8423-e29b-41d4-a716-446655440000', '780e8400-e19b-41d4-a716-446655440000', 'admin_project', 'private', 'https://132.232.93.3', 'admin', 'Harbor12345', 'admin_project', '2019-03-07 13:21:32', '2019-03-07 13:21:32');
INSERT INTO `tl_repository` VALUES ('757b446e-3e6c-4505-918b-84a8e0c876c8', 'ba280ec4-253b-44ae-923c-90dd39d473f7', 'test_project', 'private', 'http://132.232.93.3', 'admin', 'Harbor12345', 'test_project', '2019-03-26 17:17:36', '2019-03-26 17:17:36');
INSERT INTO `tl_repository` VALUES ('66908423-e29b-41d4-a716-446655440000', '780e8400-e19b-41d4-a716-446655440000', 'library', 'public', 'http://132.232.93.3', 'admin', 'Harbor12345', 'library', '2019-03-27 10:16:58', '2019-03-27 10:16:58');

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
  `name` varchar(100) DEFAULT NULL,
  `namespace` varchar(64) DEFAULT NULL,
  `ip` varchar(64) DEFAULT NULL,
  `ports` text,
  `selector` text,
  `type` varchar(100) DEFAULT NULL,
  `content` text,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_svc
-- ----------------------------
INSERT INTO `tl_svc` VALUES ('511e69ef-ce3f-46eb-9c53-0cf2dfee8039', '501755c9-a233-4287-8a12-ead4e0e767eb', 'tom', 'default', '132.232.56.2', '[{\"protocol\":\"TCP\",\"port\":30041,\"name\":\"busy\",\"additionalProperties\":{},\"nodePort\":30043,\"targetPort\":{\"intVal\":8090,\"strVal\":\"\",\"kind\":0,\"additionalProperties\":{}}}]', '{\"service\":\"tom\",\"namespace\":\"default\"}', 'NodeType', null, '2019-03-22 21:59:48', '2019-03-22 21:59:48');

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
  `topSort` varchar(1000) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `isPublish` varchar(10) DEFAULT NULL,
  `deploy_count` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_template
-- ----------------------------
INSERT INTO `tl_template` VALUES ('b30cb782-e29c-4863-b2fc-0f18c4c7eb34', '780e8400-e19b-41d4-a716-446655440000', 'test-template', '132.232.93.3/templatelogo/test-template.png', '将hello-world和busybox编排在一起，这二者之间没有任何联系。但这样可以减少用户部署的次数。', '{}', '[{\"appName\":\"busybox\",\"version\":\"2.0\",\"logo_url\":\"132.232.93.3/logo/busybox-2.0.png\",\"source_url\":\"132.232.93.3/library/busybox:2.0\",\"metadata\":{\"volume\":\"/data\",\"cmd\":\"tail\",\"cmdParams\":[],\"env\":[],\"ports\":[{\"portName\":\"busy\",\"protocol\":\"TCP\",\"containerPort\":\"8090\",\"port\":\"30041\",\"nodePort\":\"30043\"}],\"requests\":{\"cpu\":\"0.2\",\"memory\":\"20Mi\"},\"limits\":{\"cpu\":\"0.2\",\"memory\":\"20Mi\"}}},{\"appName\":\"hello-world\",\"version\":\"1.0\",\"logo_url\":\"132.232.93.3/logo/hello-world-1.0.png\",\"source_url\":\"132.232.93.3/library/hello-world:1.0\",\"metadata\":{\"volume\":\"\",\"cmd\":\"\",\"cmdParams\":[],\"env\":[],\"ports\":[{\"portName\":\"test\",\"protocol\":\"TCP\",\"containerPort\":\"8089\",\"port\":\"30040\",\"nodePort\":\"30040\"}],\"requests\":{\"cpu\":\"0.3\",\"memory\":\"40Mi\"},\"limits\":{\"cpu\":\"0.5\",\"memory\":\"80Mi\"}}}]', null, '2019-03-22 21:39:16', '2019-03-22 21:39:16', '1', '0');

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
INSERT INTO `tl_user` VALUES ('6579cc5e-8999-41a5-85eb-df632e45a5b3', '550e8400-e19b-41d4-a716-446655441111', 'test8', '12345', '1@1.com', '12345678901', '2018-12-11 16:39:54', '2018-12-11 16:39:54');
INSERT INTO `tl_user` VALUES ('7380825e-0bef-4907-bbe8-aae68c064e1b', '550e8400-e19b-41d4-a716-446655441111', 'test9', '12345', '1@1.com', '12345678901', '2018-12-11 16:40:19', '2018-12-11 16:40:19');
INSERT INTO `tl_user` VALUES ('ba280ec4-253b-44ae-923c-90dd39d473f7', '550e8400-e19b-41d4-a716-446655441111', 'test', '123', '1@1.com', '123', '2019-03-26 17:17:36', '2019-03-26 17:17:36');
