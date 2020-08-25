var pageCurr;
var tableData;
layui.use(['table','laydate', 'form'], function(){
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;
    var layDate = layui.laydate;
    var form = layui.form;

    // 数据渲染
    tableIns = table.render({
        elem: '#user',
        headers: {token: localStorage.getItem('token')},
        url: baseUrl+'/user/list/auth',
        page: true,
        limit: 16,
        limits: [16, 30, 50, 100, 200, 500],
        toolbar: '#toolbar',
        even: true,
        cellMinWidth: 50,
        cols: [[
            {type: 'checkbox'}
            // ,{field: 'id', title: 'ID', sort: true,align: 'center', width: 80}
            // ,{field: 'hostName', align: 'center',title: '授权商户'}
            ,{field: 'nickname', align: 'center',title: '名称'}
            ,{field: 'username', align: 'center',title: '登录账号'}
            ,{field: 'mobile', align: 'center',title: '联系方式'}
            // ,{field: 'password', align: 'center',title: '密码'}
            ,{field: 'deptName', align: 'center',title: '所属部门'}
            ,{field: 'roleName', align: 'center',title: '角色'}
            ,{field: 'email', align: 'center',title: '邮箱'}
            ,{field: 'sex$', align: 'center',title: '性别'}
            ,{field: 'createTime$', align: 'center',title: '注册时间', hide: true}
            ,{field: 'status$', align: 'center',title: '状态', templet: '#statusTpl', width: 120, unresize: true}

            ,{fixed: 'right', title:'操作', align: 'center', toolbar: '#operate', width:170}
        ]],
        request: {
            pageName: 'curr',
            pageSize: 'limit'
        },
        parseData: function (res) {
            return {
                'code': res.code,
                'msg': res.msg,
                'count': res.data.total,
                'data': res.data.records
            }
        },
        response: {
            statusCode: 200
        },
        done: function(res, curr, count) {
            if (res.code === 403) {
                top.location.href = baseUrl+"/";
            }
            tableData = table.cache.user;
            pageCurr=curr;
            limit();
        }
    });

    // 修改状态
    form.on('switch(statusSwitch)', function (obj) {
        var index  = obj.othis.parents('tr').attr("data-index");
        var data = tableData[index];
        data[this.name] = obj.elem.checked?1:0;
        http.post(baseUrl+"/user/edit/auth", {id: data.id, status: data[this.name]}, function (res) {
            layer.msg(res.msg);
        })
    })

    // 监听排序事件
    table.on('sort(user)', function (obj) {
        var searchData = {};
        $.each($('#search-box [name]').serializeArray(), function() {
            searchData[this.name] = this.value;
        });
        searchData['orderByField'] = obj.field;
        searchData['orderByType'] = obj.type;
        tableIns.reload({
            where: searchData,
            page: {
                curr: 1
            },
            done: function (res, curr, count) {
                if (res.code === 403) {
                    top.location.href = baseUrl+"/";
                }
                tableData = table.cache.user;
                pageCurr=curr;
                limit();
            }
        });
    });

    // 监听头工具栏事件
    table.on('toolbar(user)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event) {
            case 'addData':
                layer.open({
                    type: 2,
                    title: '新增',
                    maxmin: true,
                    area: ['30%', top.detailHeight],
                    shadeClose: true,
                    content: 'user_detail.html',
                    success: function(layero, index){
                    	clearFormVal(layer.getChildFrame('#detail', index));
                        layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
                    }
                });
                break;
            case 'deleteData':
                var data = checkStatus.data;
                var ids=[];
                data.map(function (track) {
                    ids.push(track.id);
                });
                if (ids.length === 0){
                    layer.msg('请选择数据');
                } else {
                    layer.confirm('确定删除'+(ids.length===1?'此':ids.length)+'条数据吗', function(){
                        $.ajax({
                            url: baseUrl+"/user/delete/auth",
                            headers: {'token': localStorage.getItem('token')},
                            data: {ids: ids},
                            method: 'POST',
                            traditional:true,
                            success: function (res) {
                                if (res.code === 200){
                                    layer.closeAll();
                                    tableReload(false);
                                } else if (res.code === 403){
                                    top.location.href = baseUrl+"/";
                                } else {
                                    layer.msg(res.msg)
                                }
                            }
                        })
                    });
                }
                break;
            case 'exportData':
                layer.confirm('确定导出Excel吗', {shadeClose: true}, function() {
                    var titles = [];
                    var fields = [];
                    obj.config.cols[0].map(function (col) {
                        if (col.type === 'normal' && col.hide === false && col.toolbar == null) {
                            titles.push(col.title);
                            fields.push(col.field);
                        }
                    });
                    var exportData = {};
                    $.each($('#search-box [name]').serializeArray(), function () {
                        exportData[this.name] = this.value;
                    });
                    var param = {
                        'user': exportData,
                        'fields': fields
                    };
                    $.ajax({
                        url: baseUrl+"/user/export/auth",
                        headers: {'token': localStorage.getItem('token')},
                        data: JSON.stringify(param),
                        dataType: 'json',
                        contentType: 'application/json;charset=UTF-8',
                        method: 'POST',
                        success: function (res) {
                            layer.closeAll();
                            if (res.code === 200) {
                                table.exportFile(titles, res.data, 'xls');
                            } else if (res.code === 403) {
                                top.location.href = baseUrl+"/";
                            } else {
                                layer.msg(res.msg)
                            }
                        }
                    });
                });
                break;
        }
    });

    // 监听行工具事件
    table.on('tool(user)', function(obj){
        var data = obj.data;
        switch (obj.event) {
            // 编辑
            case 'edit':
                layer.open({
                    type: 2,
                    title: '修改',
                    maxmin: true,
                    area: ['30%', top.detailHeight],
                    shadeClose: true,
                    content: 'user_detail.html',
                    success: function(layero, index){
                        layer.getChildFrame('#password', index).parent().parent().hide();
                        setFormVal(layer.getChildFrame('#detail', index), data);
                        top.convertDisabled(layer.getChildFrame('#data-detail :input', index), false);
                        layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
                        layero.find('iframe')[0].contentWindow.layui.form.render('select');
                    }
                });
                break;
            // 重置密码
            case 'resetPwd':
                layer.open({
                    type: 1,
                    title: '重置密码',
                    offset: '150px',
                    area: ['360px'],
                    shade: 0.1,
                    shadeClose: true,
                    content: $("#resetpwd-window"),
                    success: function(layero, index){
                        layer.iframeAuto(index);
                        $('#resetUserId').val(data.id);
                    }
                });
                break;
        }
    });

    // 数据修改动作
    form.on('submit(edit)', function () {
        var index = layer.load(1, {
            shade: [0.5,'#000'] //0.1透明度的背景
        });
        var data = {
            id: $('#id').val(),
            hostId: $('#hostId').val(),
            roleId: $('#roleId').val(),
            deptId: $('#deptId').val(),
            username: $('#username').val(),
            nickname: $('#nickname').val(),
            mobile: $('#mobile').val(),
            password: hex_md5($('#password').val()),
            avatar: $('#avatar').val(),
            email: $('#email').val(),
            sex: $('#sex').val(),
            createTime: top.strToDate($('#createTime\\$').val()),
            status: $('#status').val(),

        };
        $.ajax({
            url: baseUrl+"/user/edit/auth",
            headers: {'token': localStorage.getItem('token')},
            data: top.reObject(data),
            method: 'POST',
            success: function (res) {
                if (res.code === 200){
                    parent.layer.closeAll();
                    parent.$(".layui-laypage-btn")[0].click();
                    limit();
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
    });

    // 搜索栏搜索事件
    form.on('submit(search)', function (data) {
        pageCurr = 1;
        tableReload(false);
    });

    // 搜索栏重置事件
    form.on('submit(reset)', function (data) {
        pageCurr = 1;
        clearFormVal($('#search-box'));
        tableReload(false);
    });

    // 时间选择器
    layDate.render({
        elem: '#createTime\\$',
        type: 'datetime'
    });
    layDate.render({
        elem: '.layui-laydate-range'
        ,type: 'datetime'
        ,range: true
    });

    // 重置密码
    form.on('submit(savePwd)', function (data) {
        $.ajax({
            url: baseUrl+"/user/edit/auth",
            headers: {'token': localStorage.getItem('token')},
            data: {
                id: data.field.resetUserId,
                password: hex_md5(data.field.resetPassword)
            },
            method: 'POST',
            success: function (res) {
                if (res.code === 200){
                    layer.closeAll();
                    layer.msg("重置密码成功");
                } else if (res.code === 403){
                    top.location.href = baseUrl+"/";
                }else {
                    layer.msg(res.msg)
                }
            }
        })
    })

    $('#cancel').click(function () {
        layer.closeAll();
    })
});

// 关闭动作
$(document).on('click','#data-detail-close', function () {
    parent.layer.closeAll();
});

function tableReload(child) {
    var searchData = {};
    $.each($('#search-box [name]').serializeArray(), function() {
        searchData[this.name] = this.value;
    });
    (child ? parent.tableIns : tableIns).reload({
        where: searchData,
        page: {
            curr: pageCurr
        },
        done: function (res, curr, count) {
            if (res.code === 403) {
                top.location.href = baseUrl+"/";
            }
            tableData = table.cache.user;
            pageCurr=curr;
            if (res.data.length === 0 && count !== 0) {
                tableIns.reload({
                    where: searchData,
                    page: {
                        curr: pageCurr-1
                    }
                });
                pageCurr -= 1;
            }
            limit(child);
        }
    });
}

function setFormVal(el, data) {
    for (var val in data) {
        el.find(":input[id='" + val + "']").val(data[val]);
    }
}

$('body').keydown(function () {
    if (event.keyCode === 13) {
        $("#search").click();
    }
});

