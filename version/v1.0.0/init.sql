SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', '超级管理员', 'admin', 'admin', '1', '2019-11-16 23:22:08', '1');
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', 'admin', '超级管理员');
COMMIT;

-- ----------------------------
--  Table structure for `sys_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                              `code` varchar(255) NOT NULL COMMENT '菜单编码(*)',
                              `pcode` varchar(255) DEFAULT NULL COMMENT '父级菜单编码',
                              `name` varchar(255) NOT NULL COMMENT '菜单名称',
                              `level` int(11) NOT NULL COMMENT '菜单等级{1:一级菜单,2:二级菜单}',
                              `status` int(11) NOT NULL COMMENT '状态{0:失效,1:有效}',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_resource`
-- ----------------------------
BEGIN;
INSERT INTO `sys_resource` VALUES ('1', 'index', null, '主页', '1', '1'), ('2', 'home', 'index', '数据统计', '2', '1'), ('3', 'system', null, '系统', '1', '1'), ('4', 'user', 'system', '系统用户', '2', '1'), ('5', 'role', 'system', '角色管理', '2', '1'), ('6', 'resource', 'system', '资料列表', '2', '1'), ('7', 'userLogin', 'system', '凭证记录', '2', '1'), ('8', 'operateLog', 'system', '操作日志', '2', '1'), ('9', 'merchant', null, '商户', '1', '1'), ('10', 'host', 'merchant', '商户管理', '2', '1'), ('999', 'set', null, '设置', '1', '1'), ('1000', 'detail', 'set', '基本资料', '2', '1');
COMMIT;


-- ----------------------------
--  Table structure for `sys_role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                                   `role_id` bigint(20) NOT NULL COMMENT '角色',
                                   `resource_id` bigint(20) NOT NULL COMMENT '资源',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_role_resource`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_resource` VALUES ('1', '1', '1'), ('2', '1', '2'), ('3', '1', '3'), ('4', '1', '4'), ('5', '1', '5'), ('6', '1', '6'), ('7', '1', '7'), ('8', '1', '8'), ('9', '1', '9'), ('10', '1', '10'), ('11', '1', '999'), ('12', '1', '1000');
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;


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

SET FOREIGN_KEY_CHECKS = 1;
