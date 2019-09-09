SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                          `username` varchar(255) NOT NULL COMMENT '账号',
                          `mobile` varchar(32) DEFAULT NULL COMMENT '手机号',
                          `password` varchar(255) DEFAULT NULL COMMENT '密码',
                          `role_id` bigint(20) NOT NULL COMMENT '角色[sys_role]',
                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
                          `status` int(11) NOT NULL COMMENT '状态{1:启用,2:冻结,3:删除}',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=333 DEFAULT CHARSET=utf8mb4;

BEGIN;
INSERT INTO `sys_user` VALUES ('1', '超级管理员', 'admin', 'admin', '1', '2019-06-30 19:26:04', '1');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                          `code` varchar(255) NOT NULL COMMENT '编码',
                          `name` varchar(255) NOT NULL COMMENT '名称',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

BEGIN;
INSERT INTO `sys_role` VALUES ('1', 'admin', '超级管理员');
COMMIT;

-- ----------------------------
--  Table structure for `sys_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                              `code` varchar(255) NOT NULL COMMENT '菜单编码',
                              `pcode` varchar(255) DEFAULT NULL COMMENT '父级菜单编码',
                              `name` varchar(255) NOT NULL COMMENT '菜单名称',
                              `level` int(11) NOT NULL COMMENT '菜单等级{1:一级菜单,2:二级菜单}',
                              `status` int(11) NOT NULL COMMENT '状态{0:失效,1:有效}',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4;

BEGIN;
INSERT INTO `sys_resource` VALUES ('1', 'system', '', '系统配置', '1', '1'), ('2', 'user', 'system', '系统用户', '2', '1'), ('3', 'role', 'system', '角色管理', '2', '1'), ('4', 'resource', 'system', '资源列表', '2', '1'), ('5', 'userLogin', 'system', '凭证记录', '2', '1');
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
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4;

BEGIN;
INSERT INTO `sys_role_resource` VALUES ('58', '1', '2'), ('59', '1', '3'), ('60', '1', '4'), ('61', '1', '5'), ('62', '1', '7'), ('63', '1', '8');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_login`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_login`;
CREATE TABLE `sys_user_login` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                                `user_id` bigint(20) NOT NULL COMMENT '员工[sys_user]',
                                `token` varchar(255) NOT NULL COMMENT '凭证值',
                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
