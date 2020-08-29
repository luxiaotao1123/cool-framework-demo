/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.30 : Database - cool
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/*Table structure for table `sys_api` */

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

/*Data for the table `sys_api` */

/*Table structure for table `sys_config` */

DROP TABLE IF EXISTS `sys_config`;

CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `code` varchar(255) NOT NULL COMMENT '编码',
  `value` varchar(2048) NOT NULL COMMENT '对应值',
  `type` tinyint(4) NOT NULL COMMENT '类型{1:String,2:JSON}',
  `status` tinyint(4) NOT NULL COMMENT '状态{1:正常,0:禁用}',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_config` */

/*Table structure for table `sys_dept` */

DROP TABLE IF EXISTS `sys_dept`;

CREATE TABLE `sys_dept` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '部门编号',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父部门编号',
  `name` varchar(255) NOT NULL COMMENT '部门名称(*)',
  `sort` int(11) DEFAULT NULL COMMENT '显示顺序',
  `leader` bigint(20) DEFAULT NULL COMMENT '负责人[sys_user]',
  `phone` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(4) DEFAULT NULL COMMENT '部门状态{1:正常;0:停用}',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_dept` */

/*Table structure for table `sys_host` */

DROP TABLE IF EXISTS `sys_host`;

CREATE TABLE `sys_host` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(255) NOT NULL COMMENT '商户名称(*)',
  `flag` varchar(255) NOT NULL COMMENT '标识',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) NOT NULL COMMENT '状态{1:正常,0:禁用}',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_host` */

insert  into `sys_host`(`id`,`name`,`flag`,`create_time`,`update_time`,`status`) values (1,'开发组','develop','2019-11-24 15:17:48','2019-11-24 15:17:50',1);

/*Table structure for table `sys_operate_log` */

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
) ENGINE=InnoDB AUTO_INCREMENT=1236 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_operate_log` */

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(255) NOT NULL COMMENT '权限名称(*)',
  `action` varchar(255) NOT NULL COMMENT '接口地址',
  `resource_id` bigint(20) DEFAULT NULL COMMENT '所属菜单[sys_resource]',
  `status` tinyint(4) NOT NULL COMMENT '状态{1:正常,0:禁用}',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_permission` */

/*Table structure for table `sys_resource` */

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
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_resource` */

insert  into `sys_resource`(`id`,`code`,`name`,`resource_id`,`level`,`sort`,`status`) values (1,'index','主页',NULL,1,1,1),(2,'home/console.html','首页',1,2,1,1),(3,'system','系统管理',NULL,1,2,1),(4,'user/user.html','系统用户',3,2,1,1),(5,'role/role.html','角色管理',3,2,2,1),(6,'resource/resource.html','菜单列表',3,2,3,1),(7,'set','个人设置',NULL,1,999,1),(8,'detail.html','基本资料',7,2,1,1),(10,'userLogin/userLogin.html','凭证记录',12,2,3,1),(11,'operateLog/operateLog.html','操作日志',3,2,988,1),(12,'develop','开发专用',NULL,1,5,1),(15,'permission/permission.html','权限控制',12,2,1,1),(16,'api/api.html','接口文档',12,2,2,1),(17,'config/config.html','系统配置',12,2,5,1),(18,'dept/dept.html','部门管理',3,2,2,1),(19,'detail#view','查询',8,3,0,1),(20,'user#view','查询',4,3,0,1),(21,'user#btn-add','新增',4,3,1,1),(22,'user#btn-edit','编辑',4,3,2,1),(23,'user#btn-delete','删除',4,3,3,1),(24,'user#btn-export','导出',4,3,4,1),(25,'role#view','查询',5,3,0,1),(26,'role#btn-add','新增',5,3,1,1),(27,'role#btn-edit','编辑',5,3,2,1),(28,'role#btn-delete','删除',5,3,3,1),(29,'role#btn-export','导出',5,3,4,1),(30,'role#btn-power','权限',5,3,5,1),(31,'resource#view','查询',6,3,0,1),(32,'resource#btn-add','新增',6,3,1,1),(33,'resource#btn-edit','编辑',6,3,2,1),(34,'resource#btn-delete','删除',6,3,3,1),(35,'resource#btn-export','导出',6,3,4,1),(36,'userLogin#view','查询',10,3,0,1),(37,'userLogin#btn-add','新增',10,3,1,1),(38,'userLogin#btn-edit','编辑',10,3,2,1),(39,'userLogin#btn-delete','删除',10,3,3,1),(40,'userLogin#btn-export','导出',10,3,4,1),(41,'operateLog#view','查询',11,3,0,1),(42,'operateLog#btn-add','新增',11,3,1,1),(43,'operateLog#btn-edit','编辑',11,3,2,1),(44,'operateLog#btn-delete','删除',11,3,3,1),(45,'operateLog#btn-export','导出',11,3,4,1),(46,'permission#view','查询',15,3,0,1),(47,'permission#btn-add','新增',15,3,1,1),(48,'permission#btn-edit','编辑',15,3,2,1),(49,'permission#btn-delete','删除',15,3,3,1),(50,'permission#btn-export','导出',15,3,4,1),(51,'api#view','查询',16,3,0,1),(52,'api#btn-add','新增',16,3,1,1),(53,'api#btn-edit','编辑',16,3,2,1),(54,'api#btn-delete','删除',16,3,3,1),(55,'api#btn-export','导出',16,3,4,1),(56,'config#view','查询',17,3,0,1),(57,'config#btn-add','新增',17,3,1,1),(58,'config#btn-edit','编辑',17,3,2,1),(59,'config#btn-delete','删除',17,3,3,1),(60,'config#btn-export','导出',17,3,4,1),(61,'dept#view','查询',18,3,0,1),(62,'dept#ew-tree-table .ew-tree-table-tool .ew-tree-table-tool-item:first-child','新增',18,3,1,1),(63,'dept#btn-edit','编辑',18,3,2,1),(64,'dept#btn-del','删除',18,3,3,1),(65,'#view','查询',2,3,0,1);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `code` varchar(255) NOT NULL COMMENT '编码',
  `name` varchar(255) NOT NULL COMMENT '名称(*)',
  `leader` bigint(20) DEFAULT NULL COMMENT '上级',
  `level` tinyint(4) DEFAULT NULL COMMENT '角色等级{1:一级,2:二级,3:三级,4:四级,5:五级}',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`code`,`name`,`leader`,`level`) values (1,'root','超级管理员',NULL,NULL),(2,'admin','管理员',NULL,NULL);

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` bigint(20) NOT NULL COMMENT '角色[sys_role]',
  `permission_id` bigint(20) NOT NULL COMMENT '权限[sys_permission]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_role_permission` */

/*Table structure for table `sys_role_resource` */

DROP TABLE IF EXISTS `sys_role_resource`;

CREATE TABLE `sys_role_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` bigint(20) NOT NULL COMMENT '角色[sys_role]',
  `resource_id` bigint(20) NOT NULL COMMENT '菜单[sys_resource]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_role_resource` */

insert  into `sys_role_resource`(`id`,`role_id`,`resource_id`) values (118,2,2),(119,2,4),(120,2,5),(121,2,6),(122,2,13),(123,2,8),(144,1,2),(145,1,4),(146,1,5),(147,1,6),(148,1,13),(149,1,15),(150,1,16),(151,1,10),(152,1,17),(153,1,11),(154,1,8);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `host_id` bigint(20) DEFAULT NULL COMMENT '授权商户[sys_host]',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '所属部门[sys_dept]',
  `role_id` bigint(20) NOT NULL COMMENT '角色[sys_role]',
  `username` varchar(255) NOT NULL COMMENT '账号',
  `nickname` varchar(255) DEFAULT NULL COMMENT '名称(*)',
  `mobile` varchar(32) NOT NULL COMMENT '手机号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `avatar` varchar(1024) DEFAULT NULL COMMENT '头像',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别{0:男,1:女,2:未知}',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `status` int(11) NOT NULL COMMENT '状态{1:正常,0:禁用}',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`host_id`,`dept_id`,`role_id`,`username`,`nickname`,`mobile`,`password`,`avatar`,`email`,`sex`,`create_time`,`status`) values (1,1,NULL,1,'超级管理员',NULL,'root','root',NULL,NULL,NULL,'2019-11-16 23:22:08',1),(2,1,NULL,2,'管理员',NULL,'admin','admin',NULL,NULL,NULL,'2019-11-21 19:27:47',1);

/*Table structure for table `sys_user_login` */

DROP TABLE IF EXISTS `sys_user_login`;

CREATE TABLE `sys_user_login` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) NOT NULL COMMENT '员工[sys_user]',
  `token` varchar(255) NOT NULL COMMENT '凭证值(*)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_user_login` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
