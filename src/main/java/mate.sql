-- save mate record
-- mysql
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'mate/mate.html', 'mate管理', null , '2', null , '1');

insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'mate#view', '查询', '', '3', '0', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'mate#btn-add', '新增', '', '3', '1', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'mate#btn-edit', '编辑', '', '3', '2', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'mate#btn-delete', '删除', '', '3', '3', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'mate#btn-export', '导出', '', '3', '4', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'mate#btn-into', '导入', '', '3', '5', '1');

-- sqlserver
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'mate/mate.html', N'mate管理', null, '2', null, '1');

insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'mate#view', N'查询', '', '3', '0', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'mate#btn-add', N'新增', '', '3', '1', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'mate#btn-edit', N'编辑', '', '3', '2', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'mate#btn-delete', N'删除', '', '3', '3', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'mate#btn-export', N'导出', '', '3', '4', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'mate#btn-into', N'导入', '', '3', '5', '1');
