/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 127.0.0.1
 Source Database       : cool

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : utf-8

 Date: 11/21/2019 21:52:12 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_api`
-- ----------------------------
DROP TABLE IF EXISTS `sys_api`;
CREATE TABLE `sys_api` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                         `namespace` varchar(255) NOT NULL COMMENT '命名空间',
                         `oauth` tinyint(4) NOT NULL COMMENT '授权{0:无需授权,1:需要授权}',
                         `request` longtext COMMENT '请求结构',
                         `response` longtext COMMENT '响应结构',
                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
                         `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                         `status` tinyint(4) NOT NULL COMMENT '状态{1:有效,0:禁用}',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `sys_host`
-- ----------------------------
DROP TABLE IF EXISTS `sys_host`;
CREATE TABLE `sys_host` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                          `name` varchar(255) NOT NULL COMMENT '商户名称(*)',
                          `flag` varchar(255) NOT NULL COMMENT '标识',
                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
                          `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                          `status` int(11) NOT NULL COMMENT '状态{1:正常,0:禁用}',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `sys_operate_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_operate_log`;
CREATE TABLE `sys_operate_log` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                                 `action` varchar(255) NOT NULL COMMENT '访问地址',
                                 `user_id` bigint(20) NOT NULL COMMENT '用户[sys_user]',
                                 `ip` varchar(63) NOT NULL COMMENT '客户端IP',
                                 `request` longtext NOT NULL COMMENT '请求数据',
                                 `response` longtext NOT NULL COMMENT '响应数据',
                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                                `name` varchar(255) NOT NULL COMMENT '权限名称(*)',
                                `action` varchar(255) NOT NULL COMMENT '接口地址',
                                `resource_id` bigint(20) DEFAULT NULL COMMENT '所属菜单[sys_resource]',
                                `status` tinyint(4) NOT NULL COMMENT '状态{1:正常,0:禁用}',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `sys_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                              `code` varchar(255) NOT NULL COMMENT '菜单编码',
                              `name` varchar(255) NOT NULL COMMENT '菜单名称(*)',
                              `resource_id` bigint(20) DEFAULT NULL COMMENT '父级菜单[sys_resource]',
                              `level` tinyint(4) NOT NULL COMMENT '菜单等级{1:一级菜单,2:二级菜单}',
                              `sort` int(11) DEFAULT NULL COMMENT '排序',
                              `status` tinyint(4) NOT NULL COMMENT '状态{1:正常,0:禁用}',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_resource`
-- ----------------------------
BEGIN;
INSERT INTO `sys_resource` VALUES ('1', 'index', '主页', null, '1', '1', '1'), ('2', 'home', '首页', '1', '2', '1', '1'), ('3', 'system', '系统', null, '1', '2', '1'), ('4', 'user', '系统用户', '3', '2', '1', '1'), ('5', 'role', '角色管理', '3', '2', '2', '1'), ('6', 'resource', '菜单列表', '3', '2', '3', '1'), ('7', 'set', '设置', null, '1', '999', '1'), ('8', 'detail', '基本资料', '7', '2', '1', '1'), ('10', 'userLogin', '凭证记录', '14', '2', '3', '1'), ('11', 'operateLog', '操作日志', '14', '2', '988', '1'), ('12', 'merchant', '商户', null, '1', '5', '1'), ('13', 'host', '商户管理', '12', '2', null, '1'), ('14', 'develop', '开发', null, '1', '998', '1'), ('15', 'permission', '权限控制', '14', '2', '1', '1'), ('16', 'api', '接口文档', '14', '2', '2', '1');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                          `code` varchar(255) NOT NULL COMMENT '编码',
                          `name` varchar(255) NOT NULL COMMENT '名称(*)',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', 'root', '超级管理员'), ('2', 'admin', '管理员');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                                     `role_id` bigint(20) NOT NULL COMMENT '角色[sys_role]',
                                     `permission_id` bigint(20) NOT NULL COMMENT '权限[sys_permission]',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `sys_role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                                   `role_id` bigint(20) NOT NULL COMMENT '角色[sys_role]',
                                   `resource_id` bigint(20) NOT NULL COMMENT '菜单[sys_resource]',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_role_resource`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_resource` VALUES ('118', '2', '2'), ('119', '2', '4'), ('120', '2', '5'), ('121', '2', '6'), ('122', '2', '13'), ('123', '2', '8'), ('124', '1', '2'), ('125', '1', '4'), ('126', '1', '5'), ('127', '1', '6'), ('128', '1', '13'), ('129', '1', '15'), ('130', '1', '16'), ('131', '1', '10'), ('132', '1', '11'), ('133', '1', '8');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                          `username` varchar(255) NOT NULL COMMENT '账号(*)',
                          `mobile` varchar(32) NOT NULL COMMENT '手机号',
                          `password` varchar(255) DEFAULT NULL COMMENT '密码',
                          `role_id` bigint(20) NOT NULL COMMENT '角色[sys_role]',
                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
                          `status` int(11) NOT NULL COMMENT '状态{1:启用,2:冻结,3:删除}',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', '超级管理员', 'root', 'root', '1', '2019-11-16 23:22:08', '1'), ('2', '管理员', 'admin', 'admin', '2', '2019-11-21 19:27:47', '1');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_login`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_login`;
CREATE TABLE `sys_user_login` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                                `user_id` bigint(20) NOT NULL COMMENT '员工[sys_user]',
                                `token` varchar(255) NOT NULL COMMENT '凭证值(*)',
                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
