-- save rowLastno record
-- mysql
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'rowLastno/rowLastno.html', 'rowLastno管理', null , '2', null , '1');

insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'rowLastno#view', '界面', '', '3', '0', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'rowLastno#btn-add', '新增', '', '3', '1', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'rowLastno#btn-edit', '编辑', '', '3', '2', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'rowLastno#btn-delete', '删除', '', '3', '3', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'rowLastno#btn-export', '导出', '', '3', '4', '1');
insert into `sys_resource` ( `code`, `name`, `resource_id`, `level`, `sort`, `status`) values ( 'rowLastno#btn-into', '导入', '', '3', '5', '1');

-- sqlserver
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'rowLastno/rowLastno.html', N'rowLastno管理', null, '2', null, '1');

insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'rowLastno#view', N'界面', '', '3', '0', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'rowLastno#btn-add', N'新增', '', '3', '1', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'rowLastno#btn-edit', N'编辑', '', '3', '2', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'rowLastno#btn-delete', N'删除', '', '3', '3', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'rowLastno#btn-export', N'导出', '', '3', '4', '1');
insert [dbo].[sys_resource] ( [code], [name], [resource_id], [level], [sort], [status]) values ( N'rowLastno#btn-into', N'导入', '', '3', '5', '1');
