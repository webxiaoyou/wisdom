/*
 Navicat Premium Data Transfer

 Source Server         : 123
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : wisdom

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 19/10/2023 15:25:59
*/
-- 创建数据库 wisdom
CREATE DATABASE IF NOT EXISTS wisdom CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 切换到 wisdom 数据库
USE wisdom;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- 字典数据表 sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`
(
    `dict_code`   bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
    `dict_sort`   int NULL DEFAULT 0 COMMENT '字典排序',
    `dict_label`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典标签',
    `dict_value`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典键值',
    `dict_type`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
    `css_class`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
    `list_class`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
    `is_default`  char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
    `status`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2023-04-16 12:30:15', '超级管理员', '2023-10-29 16:41:43', '性别未知');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2023-04-16 12:30:15', '超级管理员', '2023-10-29 16:53:11', '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '通知');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '公告');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '新增操作');
INSERT INTO `sys_dict_data` VALUES (19, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '修改操作');
INSERT INTO `sys_dict_data` VALUES (20, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '删除操作');
INSERT INTO `sys_dict_data` VALUES (21, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '授权操作');
INSERT INTO `sys_dict_data` VALUES (22, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '导出操作');
INSERT INTO `sys_dict_data` VALUES (23, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '导入操作');
INSERT INTO `sys_dict_data` VALUES (24, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '强退操作');
INSERT INTO `sys_dict_data` VALUES (25, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '生成操作');
INSERT INTO `sys_dict_data` VALUES (26, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '清空操作');
INSERT INTO `sys_dict_data` VALUES (27, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (28, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (29, 99, '其他', '0', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-04-16 12:30:15', '', NULL, '其他操作');
INSERT INTO `sys_dict_data` VALUES (31, 1, '图片[\"png\", \"jpg\", \"jpeg\"] ', '[\"png\", \"jpg\", \"jpeg\"] ', 'upload_file', NULL, NULL, 'N', '0', '超级管理员', '2023-11-08 20:04:28', '超级管理员', '2023-11-08 20:21:35', NULL);
INSERT INTO `sys_dict_data` VALUES (32, 1, '文档[\"doc\", \"xls\", \"ppt\", \"txt\", \"pdf\"]', '[\"doc\", \"xls\", \"ppt\", \"txt\", \"pdf\"]', 'upload_file', NULL, NULL, 'N', '0', '超级管理员', '2023-11-08 20:05:03', '超级管理员', '2023-11-08 20:05:03', NULL);

-- ----------------------------
-- OSS对象存储表 sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss`  (
    `oss_id` bigint NOT NULL AUTO_INCREMENT COMMENT '对象存储主键',
    `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件名',
    `original_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '原名',
    `file_suffix` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件后缀名',
    `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'URL地址',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '上传人',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新人',
    `service` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'minio' COMMENT '服务商',
    PRIMARY KEY (`oss_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 158 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'OSS对象存储表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- oss对象存储配置表 sys_oss_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss_config`;
CREATE TABLE `sys_oss_config`  (
    `oss_config_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主建',
    `config_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '配置key',
    `access_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'accessKey',
    `secret_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '秘钥',
    `bucket_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '桶名称',
    `prefix` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '前缀',
    `endpoint` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '访问站点',
    `domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '自定义域名',
    `is_https` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否https（Y=是,N=否）',
    `region` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '域',
    `access_policy` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '桶权限类型(0=private 1=public 2=custom)',
    `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态（0=正常,1=停用）',
    `ext1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '扩展字段',
    `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`oss_config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '对象存储配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- oss对象存储配置表 sys_oss_config
-- ----------------------------
INSERT INTO `sys_oss_config` VALUES (1, 'minio', 'AyjH7pZEbwWGUi6mxvzr', 'YkKQbBGhbzHrjg4nxCfTT44MAvE0R4q8H0fSsdvz', 'wisdom', 'wisdom', '192.168.159.134:9000', '', 'N', '', '1', '0', '', 'admin', '2023-04-16 12:30:15', '超级管理员', '2023-11-08 14:33:33', '');
INSERT INTO `sys_oss_config` VALUES (2, 'qiniu', '72H7N5piahr9WGlT3uS3DmjMy8OYxH6eDkPQSdbl', 'ay2KvxLmGjrkx7uWoG6BINIFxMgYFGHC4As-wLt4', 'ruoyi-tour', '', 's3-cn-east-2.qiniucs.com', 'ru9yjqu6r.bkt.clouddn.com', 'N', '', '1', '1', '', 'admin', '2023-04-16 12:30:15', '超级管理员', '2023-11-07 23:09:20', '');
INSERT INTO `sys_oss_config` VALUES (3, 'aliyun', 'XXXXXXXXXXXXXXX', 'XXXXXXXXXXXXXXX', 'ruoyi', '', 'oss-cn-beijing.aliyuncs.com', '', 'N', '', '1', '1', '', 'admin', '2023-04-16 12:30:15', 'admin', '2023-11-08 14:13:58', NULL);
INSERT INTO `sys_oss_config` VALUES (4, 'qcloud', 'XXXXXXXXXXXXXXX', 'XXXXXXXXXXXXXXX', 'ruoyi-1250000000', '', 'cos.ap-beijing.myqcloud.com', '', 'N', 'ap-beijing', '1', '1', '', 'admin', '2023-04-16 12:30:15', 'admin', '2023-11-07 23:06:33', NULL);

-- ----------------------------
-- 字典类型表 sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`
(
    `dict_id`     bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
    `dict_name`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典名称',
    `dict_type`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
    `status`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`dict_id`) USING BTREE,
    UNIQUE INDEX `dict_type`(`dict_type` ASC) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', NULL, '2023-04-16 12:30:15', NULL, '2023-10-23 20:49:45', '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', NULL, '2023-04-16 12:30:15', NULL, '2023-10-29 16:41:29', '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', NULL, '2023-04-16 12:30:15', NULL, '2023-04-16 13:41:12', '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', NULL, '2023-04-16 12:30:15', NULL, '2023-04-16 13:41:12', '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', NULL, '2023-04-16 12:30:15', NULL, '2023-04-16 13:41:12', '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', NULL, '2023-04-16 12:30:15', NULL, '2023-04-16 13:41:12', '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', NULL, '2023-04-16 12:30:15', NULL, '2023-04-16 13:41:12', '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', NULL, '2023-04-16 12:30:15', NULL, '2023-04-16 13:41:12', '登录状态列表');
INSERT INTO `sys_dict_type` VALUES (11, '文件上传类型', 'upload_file', '0', '超级管理员', '2023-11-08 20:02:38', '超级管理员', '2023-11-08 20:02:38', '上传文件的类型如 [\'jpg\',\'png\',\'gif\'] 等\n');

-- ----------------------------
-- 菜单权限表 sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `menu_name`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
    `parent_id`   bigint NULL DEFAULT 0 COMMENT '父菜单ID',
    `order_num`   int NULL DEFAULT 0 COMMENT '显示顺序',
    `path`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由地址',
    `component`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
    `is_cache`    int                                                          NOT NULL DEFAULT 1 COMMENT '是否缓存（0缓存 1不缓存）',
    `is_frame`    int NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
    `menu_type`   char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `visible`     char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
    `status`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
    `perms`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
    `icon`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
    `del_flag`    char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;


INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, 1, 1, 'M', '0', '0', '', 'MenuFoldOutlined', '0', '2021-11-12 10:46:19', '超级管理员', '2023-11-10 09:46:38', '系统管理目录', '0');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', 0, 1, 'C', '0', '0', 'system:user:list', 'UsergroupAddOutlined', '0', '2021-11-12 10:46:19', '超级管理员', '2023-11-10 09:46:10', '用户管理菜单修改', '0');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', 1, 1, 'C', '0', '0', 'system:role:list', 'UserOutlined', '0', '2021-11-12 10:46:19', '超级管理员', '2023-11-10 09:46:26', '角色管理菜单', '0');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menus', 'system/menu/index', 0, 1, 'C', '0', '0', 'system:menu:list', 'MenuFoldOutlined', '0', '2021-11-12 10:46:19', '超级管理员', '2023-11-10 09:47:04', '菜单管理菜单', '0');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 4, 'dict', 'system/dict/index', 0, 1, 'C', '0', '0', 'system:dict:list', 'AlignCenterOutlined', NULL, '2023-09-25 11:48:30', NULL, '2023-10-24 18:28:44', '字典管理菜单', '0');
INSERT INTO `sys_menu` VALUES (1001, '用户查询', 100, 1, '', '', 1, 1, 'F', '0', '0', 'system:user:query', '#', '0', '2021-11-12 10:46:19', '0', NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1002, '用户新增', 100, 2, '', '', 1, 1, 'F', '0', '0', 'system:user:add', '#', '0', '2021-11-12 10:46:19', '0', NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1003, '用户修改', 100, 3, '', '', 1, 1, 'F', '0', '0', 'system:user:edit', '#', '0', '2021-11-12 10:46:19', '0', NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1004, '用户删除', 100, 4, '', '', 1, 1, 'F', '0', '0', 'system:user:remove', '#', '0', '2021-11-12 10:46:19', '0', NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1005, '用户导出', 100, 5, '', '', 1, 1, 'F', '0', '0', 'system:user:export', '#', '0', '2021-11-12 10:46:19', '0', NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1006, '用户导入 ', 100, 6, '', '', 1, 1, 'F', '0', '0', 'system:user:import', '#', '0', '2021-11-12 10:46:19', '0', NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1007, '重置密码', 100, 7, '', '', 1, 1, 'F', '0', '0', 'system:user:resetPwd', '#', '0', '2021-11-12 10:46:19', '0', NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1008, '角色查询', 101, 1, '', '', 1, 1, 'F', '0', '0', 'system:role:query', '#', '0', '2021-11-12 10:46:19', '0', NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1009, '角色新增', 101, 2, '', '', 1, 1, 'F', '0', '0', 'system:role:add', '#', '0', '2021-11-12 10:46:19', '0', NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1010, '角色修改', 101, 3, '', '', 1, 1, 'F', '0', '0', 'system:role:edit', '#', '0', '2021-11-12 10:46:19', '0', NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1011, '角色删除', 101, 4, '', '', 1, 1, 'F', '0', '0', 'system:role:remove', '#', '0', '2021-11-12 10:46:19', '0', NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1012, '角色导出', 101, 5, '', '', 1, 1, 'F', '0', '0', 'system:role:export', '#', '0', '2021-11-12 10:46:19', '0', NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1013, '菜单查询', 102, 1, '', '', 1, 1, 'F', '0', '0', 'system:menu:query', '#', '0', '2021-11-12 10:46:19', '0', NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1014, '菜单新增', 102, 2, '', '', 1, 1, 'F', '0', '0', 'system:menu:add', '#', '0', '2021-11-12 10:46:19', '0', NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1015, '菜单修改', 102, 3, '', '', 1, 1, 'F', '0', '0', 'system:menu:edit', '#', '0', '2021-11-12 10:46:19', '0', NULL, '', '0');
INSERT INTO `sys_menu` VALUES (1016, '菜单删除', 102, 4, '', '', 1, 1, 'F', '0', '0', 'system:menu:remove', '#', '0', '2021-11-12 10:46:19', '0', NULL, '', '0');
INSERT INTO `sys_menu` VALUES (2041, '岗位管理', 1, 5, 'post', 'system/post/index', 1, 1, 'C', '0', '0', 'system:post:list', 'ApartmentOutlined', NULL, '2023-09-25 11:48:30', NULL, '2023-11-10 10:01:02', '岗位管理', '0');
INSERT INTO `sys_menu` VALUES (2046, 'cccccc', 0, 1, 'index1', NULL, 0, 1, 'M', '0', '0', NULL, 'AlertOutlined', '超级管理员', '2023-10-17 00:44:38', '超级管理员', '2023-11-04 09:50:38', '1', '0');
INSERT INTO `sys_menu` VALUES (2047, '百度', 0, 1, 'www.baidu.com', NULL, 0, 0, 'M', '0', '0', NULL, 'AlibabaOutlined', '超级管理员', '2023-10-23 00:47:17', '超级管理员', '2023-11-10 14:49:36', '', '0');
INSERT INTO `sys_menu` VALUES (2048, '我是菜单', 2047, 1, 'myText1222', NULL, 0, 1, 'M', '0', '0', NULL, 'AlibabaOutlined', '超级管理员', '2023-10-23 00:49:42', '超级管理员', '2023-10-23 11:09:55', '', '1');
INSERT INTO `sys_menu` VALUES (2049, '字典查询', 105, 1, '', NULL, 0, 1, 'F', '0', '0', 'system:dict:query', '#', '超级管理员', '2023-10-23 18:10:56', '超级管理员', '2023-10-23 18:10:56', '', '0');
INSERT INTO `sys_menu` VALUES (2050, '字典新增', 105, 2, '', NULL, 0, 1, 'F', '0', '0', 'system:dict:add', '#', '超级管理员', '2023-10-23 18:12:06', '超级管理员', '2023-10-23 18:12:06', '', '0');
INSERT INTO `sys_menu` VALUES (2051, '字典修改', 105, 3, '', NULL, 0, 1, 'F', '0', '0', 'system:dict:edit', '#', '超级管理员', '2023-10-23 18:12:26', '超级管理员', '2023-10-23 18:12:26', '', '0');
INSERT INTO `sys_menu` VALUES (2052, '字典删除', 105, 4, '', NULL, 0, 1, 'F', '0', '0', 'system:dict:remove', '#', '超级管理员', '2023-10-23 18:12:56', '超级管理员', '2023-10-23 18:13:20', '', '0');
INSERT INTO `sys_menu` VALUES (2053, '岗位查询', 2041, 1, '', NULL, 0, 1, 'F', '0', '0', 'system:post:query', '#', '超级管理员', '2023-10-24 18:56:39', '超级管理员', '2023-10-24 18:56:39', '', '0');
INSERT INTO `sys_menu` VALUES (2054, '岗位新增', 2041, 2, '', NULL, 0, 1, 'F', '0', '0', 'system:post:add', '#', '超级管理员', '2023-10-24 18:57:09', '超级管理员', '2023-10-24 18:57:09', '', '0');
INSERT INTO `sys_menu` VALUES (2055, '岗位修改', 2041, 3, '', NULL, 0, 1, 'F', '0', '0', 'system:post:edit', '#', '超级管理员', '2023-10-24 18:57:27', '超级管理员', '2023-10-24 18:57:27', '', '0');
INSERT INTO `sys_menu` VALUES (2056, '岗位删除', 2041, 4, '', NULL, 0, 1, 'F', '0', '0', 'system:post:remove', '#', '超级管理员', '2023-10-24 18:57:52', '超级管理员', '2023-10-24 18:57:52', '', '0');
INSERT INTO `sys_menu` VALUES (2057, '文件管理', 1, 7, 'oss', 'system/oss/index', 0, 1, 'C', '0', '0', 'system:oss:list', 'FolderOpenOutlined', '超级管理员', '2023-10-31 14:06:48', '超级管理员', '2023-11-10 10:01:53', '', '0');
INSERT INTO `sys_menu` VALUES (2058, '文件查询', 2057, 1, '', NULL, 0, 1, 'F', '0', '0', 'system:oss:query', '#', '超级管理员', '2023-10-31 14:14:31', '超级管理员', '2023-11-01 23:32:12', '错', '0');
INSERT INTO `sys_menu` VALUES (2059, '配置添加', 2057, 4, '', NULL, 0, 1, 'F', '0', '0', 'system:oss:add', 'AlertOutlined', '超级管理员', '2023-10-31 14:14:58', '超级管理员', '2023-10-31 14:23:45', '', '0');
INSERT INTO `sys_menu` VALUES (2060, '文件下载', 2057, 2, '', NULL, 0, 1, 'F', '0', '0', 'system:oss:download', '#', '超级管理员', '2023-10-31 14:16:28', '超级管理员', '2023-10-31 14:20:19', '', '0');
INSERT INTO `sys_menu` VALUES (2061, '配置编辑', 2057, 5, '', '', 0, 1, 'F', '1', '0', 'system:oss:edit', '#', '超级管理员', '2023-10-31 14:20:06', '超级管理员', '2023-11-04 10:44:59', '', '0');
INSERT INTO `sys_menu` VALUES (2062, '文件删除', 2057, 3, '', NULL, 0, 1, 'F', '0', '0', 'system:oss:remove', '#', '超级管理员', '2023-10-31 14:21:27', '超级管理员', '2023-10-31 14:21:27', '', '0');
INSERT INTO `sys_menu` VALUES (2063, 'ccccs', 2057, 1111, '', NULL, 0, 1, 'F', '0', '0', 'system:oss:ad', '#', '超级管理员', '2023-11-02 00:20:06', '超级管理员', '2023-11-02 00:20:12', '', '1');
INSERT INTO `sys_menu` VALUES (2064, '11111', 2057, 11111, '', NULL, 0, 1, 'F', '0', '0', '1111', '#', '超级管理员', '2023-11-02 00:22:20', '超级管理员', '2023-11-02 00:22:26', '', '1');

-- ----------------------------
-- 登录日志表 sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
      `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
      `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户账号',
      `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录IP地址',
      `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录地点',
      `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '浏览器类型',
      `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作系统',
      `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
      `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '提示消息',
      `login_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
      PRIMARY KEY (`info_id`) USING BTREE,
      INDEX `idx_sys_logininfor_s`(`status` ASC) USING BTREE,
      INDEX `idx_sys_logininfor_lt`(`login_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;



-- ----------------------------
-- 岗位信息表 sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`
(
    `post_id`     bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `post_code`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
    `post_name`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
    `post_sort`   int                                                          NOT NULL COMMENT '显示顺序',
    `status`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL COMMENT '状态（0正常 1停用）',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '岗位信息表' ROW_FORMAT = DYNAMIC;

INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, '0', 'admin', '2023-04-16 12:30:15', '', NULL, '');
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', 2, '0', 'admin', '2023-04-16 12:30:15', '', NULL, '');
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, '0', 'admin', '2023-04-16 12:30:15', '', NULL, '');
INSERT INTO `sys_post` VALUES (4, 'user', '普通员工', 4, '0', 'admin', '2023-04-16 12:30:15', '', NULL, '');
-- ----------------------------
-- 角色信息表 sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name`   varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '角色名称',
    `role_key`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
    `role_sort`   int                                                           NOT NULL COMMENT '显示顺序',
    `status`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NOT NULL COMMENT '角色状态（0正常 1停用）',
    `del_flag`    char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `extension1`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展字段 1',
    `extension2`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展字段 2',
    `extension3`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展字段 3',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '0', '0', NULL, '2023-09-24 23:40:21', NULL, '2023-09-24 23:40:27', '超级管理员', '', NULL, '');
INSERT INTO `sys_role` VALUES (2, '普通管理员', 'common', 2, '0', '0', NULL, '2023-09-24 23:40:21', NULL, '2023-09-26 22:29:25', '普通管理员', '', NULL, '');
-- ----------------------------
-- 角色和菜单关联表 sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `role_id` bigint NOT NULL COMMENT '角色ID',
    `menu_id` bigint NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`role_id`, `menu_id`) USING BTREE,
    INDEX     `menu_`(`menu_id` ASC) USING BTREE,
    CONSTRAINT `menu_` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `menu_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- 用户信息表 sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`               bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `account`          varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号',
    `name`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
    `password`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `email`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
    `age`              int NULL DEFAULT NULL COMMENT '年龄',
    `sex`              char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '性别(0男 1女 2未知)',
    `avatar`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
    `phone`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
    `role_id`          int NULL DEFAULT NULL COMMENT '角色 0超级管理员 ， 1管理员， 2 普通账号',
    `status`           char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    `del_flag`         char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `supervisor_id`    int NULL DEFAULT NULL COMMENT '上级员工ID',
    `position`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位',
    `salary`           decimal(10, 2) NULL DEFAULT NULL COMMENT '工资',
    `hire_date`        datetime NULL DEFAULT NULL COMMENT '入职日期',
    `termination_date` datetime NULL DEFAULT NULL COMMENT '离职日期',
    `work_location`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作地点',
    `login_ip`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
    `login_date`       datetime NULL DEFAULT NULL COMMENT '最后登录时间',
    `create_by`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time`      datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time`      datetime NULL DEFAULT NULL COMMENT '更新时间',
    `remark`           varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `extension1`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展字段 1',
    `extension2`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展字段 2',
    `extension3`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展字段 3',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

INSERT INTO `sys_user` VALUES (1,'superAdmin', '超级管理员', '$2a$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn.JeVcj2NdeZy', '1', 1, '0', '', '0', 1, '0', '0', NULL, '超级管理员', 0.00, NULL, NULL, NULL, '', NULL, '', NULL, '', NULL, NULL, NULL, NULL, NULL);
-- ----------------------------
-- 用户与岗位关联表 sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`
(
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `post_id` bigint NOT NULL COMMENT '岗位ID',
    PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = DYNAMIC;

INSERT INTO `sys_user_post` VALUES (1, 1);
-- ----------------------------
-- 用户和角色关联表 sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `role_id` bigint NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
    INDEX     `fk_role_id`(`role_id` ASC) USING BTREE,
    CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

INSERT INTO `sys_user_role` VALUES (1, 1);
-- ----------------------------
-- 删除字典数据触发器
-- ----------------------------
DROP TRIGGER IF EXISTS `delete_dict_data_trigger`;
DELIMITER;;
CREATE TRIGGER `delete_dict_data_trigger`
    AFTER DELETE
    ON `sys_dict_type`
    FOR EACH ROW
BEGIN
    DELETE FROM sys_dict_data WHERE dict_type = OLD.dict_type;
END
;;
DELIMITER;

-- ----------------------------
-- 删除菜单触发器
-- ----------------------------
DROP TRIGGER IF EXISTS `deleteRoleMenu`;
DELIMITER;;
CREATE TRIGGER `deleteRoleMenu`
    BEFORE DELETE
    ON `sys_menu`
    FOR EACH ROW
BEGIN
    DELETE FROM sys_role_menu WHERE menu_id = OLD.id;
END
;;
DELIMITER;

-- ----------------------------
-- 删除角色触发器
-- ----------------------------
DROP TRIGGER IF EXISTS `deleteRolesMenu`;
DELIMITER;;
CREATE TRIGGER `deleteRolesMenu`
    BEFORE DELETE
    ON `sys_role`
    FOR EACH ROW
BEGIN
    DELETE FROM sys_role_menu WHERE role_id = OLD.id;
END
;;
DELIMITER;

-- ----------------------------
-- 删除用户触发器
-- ----------------------------
DROP TRIGGER IF EXISTS `deleteUserRole`;
DELIMITER;;
CREATE TRIGGER `deleteUserRole`
    BEFORE DELETE
    ON `sys_user`
    FOR EACH ROW
BEGIN
    DELETE FROM sys_user_role WHERE user_id = OLD.id;
END
;;
DELIMITER;
SET
FOREIGN_KEY_CHECKS = 1;
