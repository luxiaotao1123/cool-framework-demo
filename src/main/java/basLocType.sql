-- save basLocType record
-- mysql
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'basLocType/basLocType.html', 'basLocType管理', null , '2', null , '1');

insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'basLocType#view', '查看', '', '3', '0', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'basLocType#btn-add', '新增', '', '3', '1', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'basLocType#btn-edit', '编辑', '', '3', '2', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'basLocType#btn-delete', '删除', '', '3', '3', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'basLocType#btn-export', '导出', '', '3', '4', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'basLocType#btn-into', '导入', '', '3', '5', '1');

-- sqlserver
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'basLocType/basLocType.html', N'basLocType管理', null, '2', null, '1');

insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'basLocType#view', N'查看', '', '3', '0', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'basLocType#btn-add', N'新增', '', '3', '1', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'basLocType#btn-edit', N'编辑', '', '3', '2', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'basLocType#btn-delete', N'删除', '', '3', '3', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'basLocType#btn-export', N'导出', '', '3', '4', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'basLocType#btn-into', N'导入', '', '3', '5', '1');
