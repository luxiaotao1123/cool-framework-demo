-- save basDevp record
-- mysql
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'basDevp/basDevp.html', 'basDevp管理', null , '2', null , '1');

insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'basDevp#view', '查看', '', '3', '0', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'basDevp#btn-add', '新增', '', '3', '1', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'basDevp#btn-edit', '编辑', '', '3', '2', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'basDevp#btn-delete', '删除', '', '3', '3', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'basDevp#btn-export', '导出', '', '3', '4', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'basDevp#btn-into', '导入', '', '3', '5', '1');

-- sqlserver
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'basDevp/basDevp.html', N'basDevp管理', null, '2', null, '1');

insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'basDevp#view', N'查看', '', '3', '0', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'basDevp#btn-add', N'新增', '', '3', '1', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'basDevp#btn-edit', N'编辑', '', '3', '2', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'basDevp#btn-delete', N'删除', '', '3', '3', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'basDevp#btn-export', N'导出', '', '3', '4', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'basDevp#btn-into', N'导入', '', '3', '5', '1');
