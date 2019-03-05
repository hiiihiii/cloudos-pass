/*
Navicat MySQL Data Transfer

Source Server         : mysql56
Source Server Version : 50634
Source Host           : localhost:3306
Source Database       : db_cloud_pass

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2019-03-05 18:04:25
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
  `description` varchar(1000) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_deployment
-- ----------------------------
INSERT INTO `tl_deployment` VALUES ('36a4c6cb-4fbb-459d-8be7-711444fed8e8', 'deploy-hello', '780e8400-e19b-41d4-a716-446655440000', 'docker', '', '1d7eeb1c-156b-4f2d-98ca-c3a62983abca', 'dfafdsfdf', '2019-02-23 15:09:24', '2019-02-23 15:09:24');

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
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deploycount` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_image
-- ----------------------------
INSERT INTO `tl_image` VALUES ('4ba1a7aa-462f-4a0b-a8f1-ec85369e4e1c', '66908423-e29b-41d4-a716-446655440000', '780e8400-e19b-41d4-a716-446655440000', 'busybox', 'Application', '[\"2.0\"]', 'dfafdfa', '{\"2.0\":\"fafdas\"}', '132.232.93.3/logo/busybox-2.0.PNG', '{\"2.0\":\"132.232.93.3/library/busybox:2.0\"}', '{\"2.0\":{\"volume\":\"/data\",\"cmd\":\"tail\",\"cmdParams\":[\"/dev/null\",\"-f\"],\"env\":[],\"ports\":[{\"portName\":\"test\",\"protocol\":\"TCP\",\"containerPort\":\"10000\",\"port\":\"20002\",\"nodePort\":\"20002\"}],\"requests\":{\"cpu\":\"0.1\",\"memory\":\"10Mi\"},\"limits\":{\"cpu\":\"0.1\",\"memory\":\"10Mi\"}}}', '{\"2.0\":\"upload\"}', '2019-02-22 16:44:13', '2019-02-22 16:44:13', null);
INSERT INTO `tl_image` VALUES ('1d7eeb1c-156b-4f2d-98ca-c3a62983abca', '66908423-e29b-41d4-a716-446655440000', '780e8400-e19b-41d4-a716-446655440000', 'hello-world', 'WebServer', '[\"1.0\"]', 'dfdasfdfd', '{\"1.0\":\"fdfdfdfd\"}', '132.232.93.3/logo/hello-world-1.0.PNG', '{\"1.0\":\"132.232.93.3/library/hello-world:1.0\"}', '{\"1.0\":{\"volume\":\"\",\"cmd\":\"\",\"cmdParams\":[],\"env\":[],\"ports\":[{\"portName\":\"test\",\"protocol\":\"TCP\",\"containerPort\":\"8090\",\"port\":\"10090\",\"nodePort\":\"10090\"}],\"requests\":{\"cpu\":\"0.2\",\"memory\":\"20Mi\"},\"limits\":{\"cpu\":\"0.2\",\"memory\":\"20Mi\"}}}', '{\"1.0\":\"upload\"}', '2019-02-23 13:40:45', '2019-02-23 13:40:45', null);

-- ----------------------------
-- Table structure for tl_log
-- ----------------------------
DROP TABLE IF EXISTS `tl_log`;
CREATE TABLE `tl_log` (
  `uuid` varchar(36) DEFAULT NULL,
  `user_uuid` varchar(36) DEFAULT NULL,
  `username` varchar(64) DEFAULT NULL,
  `resourceId` varchar(255) DEFAULT NULL,
  `resourceType` varchar(64) DEFAULT NULL,
  `operation` varchar(1000) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `isDeleted` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_log
-- ----------------------------
INSERT INTO `tl_log` VALUES ('bdaaad9f-43dc-4ee7-b75a-903685faa5f3', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-02 21:46:57', '0');
INSERT INTO `tl_log` VALUES ('3a1dd0b4-ea0b-48a6-b474-e2f761f0fe43', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-02 22:00:58', '0');
INSERT INTO `tl_log` VALUES ('2b805228-fa98-4cb1-becd-5375ba0fe1f2', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-02 22:02:11', '0');
INSERT INTO `tl_log` VALUES ('248476fc-1913-4831-9edb-ee0476daac25', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-02 22:06:49', '0');
INSERT INTO `tl_log` VALUES ('cf122f4d-3a2a-4537-8e38-187037e5eaa0', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-02 22:08:50', '0');
INSERT INTO `tl_log` VALUES ('909f176d-18c2-4998-87c2-0f80013169c7', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-02 22:09:11', '0');
INSERT INTO `tl_log` VALUES ('bc4d26df-ba53-463e-9e0e-cf64d1d88b59', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-02 22:11:37', '0');
INSERT INTO `tl_log` VALUES ('e55c8921-2ec9-4b0f-aacc-feba79b0a5a9', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-03 10:46:29', '0');
INSERT INTO `tl_log` VALUES ('9ed0eb95-e14c-4755-91e7-f6210a05eecb', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-03 10:55:53', '0');
INSERT INTO `tl_log` VALUES ('f8cea0b4-70c2-4f8b-a79e-e68a4eacbaef', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-04 11:21:59', '0');
INSERT INTO `tl_log` VALUES ('0f74769a-4b30-49b9-8e2e-756a6161d8c2', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-04 11:24:55', '0');
INSERT INTO `tl_log` VALUES ('ccc26513-3c8b-4892-959c-0189bd1bb716', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-04 13:50:55', '0');
INSERT INTO `tl_log` VALUES ('2958691c-a4a0-4716-a1b4-add4df05f3d6', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-04 21:07:42', '0');
INSERT INTO `tl_log` VALUES ('f49eeff4-1afc-4501-b8a9-5a7c3c78400f', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-05 13:38:57', '0');
INSERT INTO `tl_log` VALUES ('c009f4c7-99c5-4ed4-93a2-da608586974a', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-05 13:45:33', '0');
INSERT INTO `tl_log` VALUES ('59f9787f-7954-43bf-a166-5ffa74d7ff0b', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-05 13:52:23', '0');
INSERT INTO `tl_log` VALUES ('a8f2b0e6-2522-4336-be80-f37512936ec7', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-05 13:54:58', '0');
INSERT INTO `tl_log` VALUES ('70a22e1f-d81b-43ff-a671-2b8d0e03e94a', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-05 14:09:32', '0');
INSERT INTO `tl_log` VALUES ('5c6bbad5-9b11-4b4a-a0f6-d33198e29832', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-05 14:11:29', '0');
INSERT INTO `tl_log` VALUES ('d9d7fedb-cf87-43fe-8f94-a31226d5d9a9', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-05 14:18:08', '0');
INSERT INTO `tl_log` VALUES ('61fa0e57-557e-4ce9-a6af-7447ad47942b', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-05 14:27:11', '0');
INSERT INTO `tl_log` VALUES ('93f5a23d-d64a-40d9-a73b-14efce8f6f18', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-05 14:36:35', '0');
INSERT INTO `tl_log` VALUES ('f06e0b69-c8e1-4aa5-be61-797ed522dcf9', '780e8400-e19b-41d4-a716-446655440000', 'admin', '780e8400-e19b-41d4-a716-446655440000', 'User', '登录', '2019-03-05 15:32:32', '0');

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
  `name` varchar(100) DEFAULT NULL,
  `desiredCount` varchar(255) DEFAULT NULL,
  `namespace` varchar(64) DEFAULT NULL,
  `replicas` varchar(100) DEFAULT NULL,
  `selector` text,
  `template` text,
  `content` text,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_rc
-- ----------------------------
INSERT INTO `tl_rc` VALUES ('3f799508-5ebd-4860-9b78-ba9a3504e122', '36a4c6cb-4fbb-459d-8be7-711444fed8e8', 'deploy-hello-rc', null, 'default', '2', '{\"deployment\":\"deploy-hello\"}', '{\"metadata\":{\"generation\":0,\"finalizers\":[],\"resourceVersion\":\"\",\"annotations\":null,\"generateName\":\"\",\"deletionTimestamp\":\"\",\"labels\":{\"deployment\":\"deploy-hello\"},\"ownerReferences\":[],\"selfLink\":\"\",\"deletionGracePeriodSeconds\":0,\"uid\":\"\",\"initializers\":null,\"clusterName\":\"\",\"creationTimestamp\":\"\",\"name\":\"\",\"namespace\":\"default\",\"additionalProperties\":{}},\"additionalProperties\":{},\"spec\":{\"dnsPolicy\":\"\",\"nodeName\":\"\",\"terminationGracePeriodSeconds\":0,\"dnsConfig\":null,\"hostNetwork\":false,\"serviceAccountName\":\"\",\"imagePullSecrets\":[],\"priorityClassName\":\"\",\"hostAliases\":[],\"securityContext\":null,\"nodeSelector\":null,\"hostname\":\"\",\"tolerations\":[],\"automountServiceAccountToken\":false,\"schedulerName\":\"\",\"activeDeadlineSeconds\":0,\"hostIPC\":false,\"volumes\":[],\"serviceAccount\":\"\",\"priority\":0,\"restartPolicy\":\"Always\",\"subdomain\":\"\",\"containers\":[{\"volumeDevices\":[],\"image\":\"132.232.93.3/library/hello-world:1.0\",\"imagePullPolicy\":\"IfNotPresent\",\"livenessProbe\":null,\"stdin\":false,\"terminationMessagePolicy\":\"\",\"terminationMessagePath\":\"\",\"workingDir\":\"\",\"resources\":{\"additionalProperties\":{},\"requests\":{\"memory\":{\"amount\":\"20Mi\",\"format\":\"\",\"additionalProperties\":{}},\"cpu\":{\"amount\":\"0.2\",\"format\":\"\",\"additionalProperties\":{}}},\"limits\":{\"memory\":{\"amount\":\"20Mi\",\"format\":\"\",\"additionalProperties\":{}},\"cpu\":{\"amount\":\"0.2\",\"format\":\"\",\"additionalProperties\":{}}}},\"securityContext\":null,\"env\":[],\"ports\":[{\"protocol\":\"\",\"hostIP\":\"\",\"name\":\"test\",\"containerPort\":8090,\"hostPort\":0,\"additionalProperties\":{}}],\"command\":[],\"volumeMounts\":[],\"args\":[],\"lifecycle\":null,\"name\":\"hello-world\",\"tty\":false,\"readinessProbe\":null,\"additionalProperties\":{},\"stdinOnce\":false,\"envFrom\":[]}],\"additionalProperties\":{},\"initContainers\":[],\"affinity\":null,\"hostPID\":false}}', null, '2019-03-05 15:45:35', '2019-03-05 15:45:35');

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
INSERT INTO `tl_repository` VALUES ('550e8423-e29b-41d4-a716-446655440000', '780e8400-e19b-41d4-a716-446655440000', 'admin_project', 'private', 'https://132.232.93.3', 'admin', 'Harbor12345', null, '2019-02-18 15:59:21', '2019-02-18 15:59:21');
INSERT INTO `tl_repository` VALUES ('66908423-e29b-41d4-a716-446655440000', '780e8400-e19b-41d4-a716-446655440000', 'library', 'public', 'https://132.232.93.3', 'admin', 'Harbor12345', null, '2019-02-18 15:59:28', '2019-02-18 15:59:28');

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
INSERT INTO `tl_svc` VALUES ('e7e8a7bf-fad0-4569-8f8f-d26785f2c3a2', '36a4c6cb-4fbb-459d-8be7-711444fed8e8', 'deploy-hello-svc', 'default', '132.232.56.2', '[{\"protocol\":\"TCP\",\"port\":30090,\"name\":\"test\",\"additionalProperties\":{},\"nodePort\":30090,\"targetPort\":{\"intVal\":8090,\"strVal\":\"\",\"kind\":0,\"additionalProperties\":{}}}]', '{\"deployment\":\"deploy-hello\"}', 'NodeType', null, '2019-02-23 17:17:09', '2019-02-23 17:17:09');

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
