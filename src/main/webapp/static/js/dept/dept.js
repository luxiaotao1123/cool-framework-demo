var pageCurr;
var tableRender;
layui.config({
    base: baseUrl + "/static/layui/lay/modules/"  // 配置模块所在的目录
}).use(['table','laydate', 'form','treeTable'], function(){
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;
    var layDate = layui.laydate;
    var form = layui.form;
    var treetable = layui.treeTable;

    tableRender = function() {
        var tableIdx = treetable.render({
            elem: '#dept',
            url: baseUrl+'/dept/list/auth',
            headers: {token: localStorage.getItem('token')},
            height: 'full-200',
            tree: {
                iconIndex: 1,           // 折叠图标显示在第几列
                isPidData: true,        // 是否是id、pid形式数据
                idName: 'id',  // id字段名称
                pidName: 'parentId'     // pid字段名称
            },
            toolbar: 'default',
            cols: [[
                {type: 'checkbox'}
                // ,{field: 'id', align: 'center',title: '部门编号'}
                ,{field: 'name', align: 'left',title: '部门名称', width: 300}
                // ,{field: 'parentId$', align: 'left',title: '父部门', width: 300}
                ,{field: 'leader$', align: 'center',title: '负责人'}
                ,{field: 'phone', align: 'center',title: '联系电话'}
                ,{field: 'email', align: 'center',title: '邮箱'}
                ,{field: 'sort', align: 'center',title: '显示顺序'}
                ,{field: 'status$', align: 'center',title: '部门状态', templet: '#statusTpl'}
                ,{align: 'center', toolbar: '#tbBar', title: '操作', width: 120}
                ,{field: 'createBy', align: 'center',title: '创建者', hide: true}
                ,{field: 'createTime$', align: 'center',title: '创建时间', hide: true}
                ,{field: 'updateBy', align: 'center',title: '更新者', hide: true}
                ,{field: 'updateTime$', align: 'center',title: '更新时间', hide: true}
            ]],
            done: function () {
                tableIdx.expandAll();
                $(".ew-tree-table .ew-tree-table-tool .ew-tree-table-tool-item").css("display", "none");
                limit()
            }
        });

    }
    tableRender();


    // 头部工具栏点击事件
    treetable.on('toolbar(dept)', function (obj) {
        switch (obj.event) {
            case 'add':
                layer.open({
                    type: 2,
                    title: '新增',
                    maxmin: true,
                    area: ['40%', top.detailHeight],
                    content: 'dept_detail.html',
                    success: function(layero, index){
                        layer.getChildFrame('#data-detail-submit-edit', index).hide();
                        clearFormVal(layer.getChildFrame('#detail', index));
                        layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
                    }
                });
                break;
            case 'delete':
                layer.msg('禁用');
                break;
            case 'update':
                layer.msg('禁用');
                break;
        }
    });

    // 工具列点击事件
    treetable.on('tool(dept)', function (obj) {
        var data = obj.data;
        var event = obj.event;
        if (event === 'del') {
            layer.confirm('确定删除'+data.name+'部门吗', function(){
                $.ajax({
                    url: baseUrl+"/dept/delete/auth",
                    headers: {'token': localStorage.getItem('token')},
                    data: {param: JSON.stringify(data)},
                    method: 'POST',
                    traditional:true,
                    success: function (res) {
                        if (res.code === 200){
                            layer.closeAll();
                            layer.msg(res.msg)
                            tableRender();
                        } else if (res.code === 403){
                            top.location.href = baseUrl+"/";
                        } else {
                            layer.msg(res.msg)
                        }
                    }
                })
            });
        } else if (event === 'edit') {
            layer.open({
                type: 2,
                title: '修改',
                maxmin: true,
                area: ['40%', top.detailHeight],
                content: 'dept_detail.html',
                success: function(layero, index){
                    layer.getChildFrame('#data-detail-submit-save', index).hide();
                    setFormVal(layer.getChildFrame('#detail', index), data, false);
                    top.convertDisabled(layer.getChildFrame('#data-detail :input', index), false);
                    top.convertDisabled(layer.getChildFrame('#id', index), true);
                    layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
                    layero.find('iframe')[0].contentWindow.layui.form.render('select');
                    layero.find('iframe')[0].contentWindow.layui.form.render('checkbox');
                }
            });
        }
    });

    // 监听行工具事件
    table.on('tool(dept)', function(obj){
        var data = obj.data;
        switch (obj.event) {
            // 编辑
            case 'edit':
                layer.open({
                    type: 2,
                    title: '修改',
                    maxmin: true,
                    area: ['40%', top.detailHeight],
                    content: 'dept_detail.html',
                    success: function(layero, index){
                        layer.getChildFrame('#data-detail-submit-save', index).hide();
                        setFormVal(layer.getChildFrame('#detail', index), data, false);
                        top.convertDisabled(layer.getChildFrame('#data-detail :input', index), false);
                        top.convertDisabled(layer.getChildFrame('#id', index), true);
                        layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
                        layero.find('iframe')[0].contentWindow.layui.form.render('select');
                        layero.find('iframe')[0].contentWindow.layui.form.render('checkbox');
                    }
                });
                break;
        }
    });

    // 数据保存动作
    form.on('submit(save)', function () {
        if (banMsg != null){
            layer.msg(banMsg);
            return;
        }
        method("add");
    });

    // 数据修改动作
    form.on('submit(edit)', function () {
        method("update")
    });

    function method(name){
        var index = layer.load(1, {
            shade: [0.5,'#000'] //0.1透明度的背景
        });
        var data = {
//            id: $('#id').val(),
            id: $('#id').val(),
            parentId: $('#parentId').val(),
            name: $('#name').val(),
            sort: $('#sort').val(),
            leader: $('#leader').val(),
            phone: $('#phone').val(),
            email: $('#email').val(),
            status: $('#status').val(),
            createBy: $('#createBy').val(),
            createTime: top.strToDate($('#createTime\\$').val()),
            updateBy: $('#updateBy').val(),
            updateTime: top.strToDate($('#updateTime\\$').val()),

        };
        $.ajax({
            url: baseUrl+"/dept/"+name+"/auth",
            headers: {'token': localStorage.getItem('token')},
            data: top.reObject(data),
            method: 'POST',
            success: function (res) {
                if (res.code === 200){
                    parent.layer.closeAll();
                    parent.tableRender();
                    $("#data-detail :input").each(function () {
                        $(this).val("");
                    });
                } else if (res.code === 403){
                    top.location.href = baseUrl+"/";
                }else {
                    layer.msg(res.msg)
                }
                layer.close(index);
            }
        })
    }

    // 复选框事件
    form.on('checkbox(detailCheckbox)', function (data) {
        var el = data.elem;
        if (el.checked) {
            $(el).val('Y');
        } else {
            $(el).val('N');
        }
    });

    // 时间选择器
    layDate.render({
        elem: '#createTime\\$',
        type: 'datetime'
    });
    layDate.render({
        elem: '#updateTime\\$',
        type: 'datetime'
    });


});

// 关闭动作
$(document).on('click','#data-detail-close', function () {
    parent.layer.closeAll();
});


function setFormVal(el, data, showImg) {
    for (var val in data) {
        var find = el.find(":input[id='" + val + "']");
        if (find[0]!=null){
            if (find[0].type === 'checkbox'){
                if (data[val]==='Y'){
                    find.attr("checked","checked");
                    find.val('Y');
                } else {
                    find.remove("checked");
                    find.val('N');
                }
                continue;
            }
        }
        find.val(data[val]);
        if (showImg){
            var next = find.next();
            if (next.get(0)){
                if (next.get(0).localName === "img") {
                    find.hide();
                    next.attr("src", data[val]);
                    next.show();
                }
            }
        }
    }
}

