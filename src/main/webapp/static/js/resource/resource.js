var pageCurr;
layui.config({
    base: '/static/js/'
}).use(['table','laydate', 'form', 'store'], function(){
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;
    var store = layui.store;
    var layDate = layui.laydate;
    var form = layui.form;

    // 数据渲染
    tableIns = table.render({
        elem: '#resource',
        headers: {token: localStorage.getItem('token')},
        url: '/resource/list/auth',
        page: true,
        limit: 16,
        toolbar: '#toolbar',
        cellMinWidth: 50,
        cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'id', title: 'ID', sort: true,align: 'center', fixed: 'left', width: 80}
            ,{field: 'code', align: 'center',title: '菜单编码'}
            ,{field: 'pcode', align: 'center',title: '父级菜单'}
            ,{field: 'name', align: 'center',title: '菜单名称'}
            ,{field: 'level$', align: 'center',title: '菜单等级'}
            ,{field: 'status$', align: 'center',title: '状态'}

            ,{fixed: 'right', title:'操作', align: 'center', toolbar: '#operate', width:150}
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
                top.location.href = "/";
            }
            pageCurr=curr;
        }
    });

    // 监听头工具栏事件
    table.on('toolbar(resource)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event) {
            case 'addData':
                layer.open({
                    type: 2,
                    title: '新增',
                    maxmin: true,
                    area: [top.detailHeight, top.detailWidth],
                    shadeClose: false,
                    content: '/resource_detail',
                    success: function(layero, index){
                    	clearFormVal(layer.getChildFrame('#detail', index));
                        layer.getChildFrame('#data-detail-submit', index).text("添加");
                        detailScreen(index);
                    }
                });
                break;
            case 'refreshData':
                tableIns.reload({
                    page: {
                        curr: pageCurr
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
                            url: store.uri + "/resource/delete/auth",
                            headers: {'token': localStorage.getItem('token')},
                            data: {ids: ids},
                            method: 'POST',
                            traditional:true,
                            success: function (res) {
                                if (res.code === 200){
                                    layer.closeAll();
                                    tableReload(false);
                                } else if (res.code === 403){
                                    top.location.href = "/";
                                } else {
                                    layer.msg(res.msg)
                                }
                            }
                        })
                    });
                }
                break;
            case 'exportData':
                var titles=[];
                var fields=[];
                obj.config.cols[0].map(function (col) {
                    if (col.type === 'normal' && col.hide === false && col.toolbar == null) {
                        titles.push(col.title);
                        fields.push(col.field);
                    }
                });
                var exportData = {};
                $.each($('#search-box [name]').serializeArray(), function() {
                    exportData[this.name] = this.value;
                });
                var param = {
                    'resource': exportData,
                    'fields': fields
                };
                $.ajax({
                    url: store.uri + "/resource/export/auth",
                    headers: {'token': localStorage.getItem('token')},
                    data: JSON.stringify(param),
                    dataType:'json',
                    contentType:'application/json;charset=UTF-8',
                    method: 'POST',
                    success: function (res) {
                        if (res.code === 200) {
                            table.exportFile(titles,res.data,'xls');
                        } else if (res.code === 403) {
                            top.location.href = "/";
                        } else {
                            layer.msg(res.msg)
                        }
                    }
                });
                break;
        }
    });

    // 监听行工具事件
    table.on('tool(resource)', function(obj){
        var data = obj.data;
        switch (obj.event) {
            // 查看
            case 'detail':
                layer.open({
                    type: 2,
                    title: '查看',
                    maxmin: true,
                    area: [top.detailHeight, top.detailWidth],
                    shadeClose: false,
                    content: '/resource_detail',
                    success: function(layero, index){
                        setFormVal(layer.getChildFrame('#detail', index), data);
                        top.convertDisabled(layer.getChildFrame('#data-detail :input', index), true);
                        layer.getChildFrame('#data-detail-submit', index).hide();
                        detailScreen(index);
                        layero.find('iframe')[0].contentWindow.layui.form.render('select');
                    }
                });
                break;
            // 编辑
            case 'edit':
                layer.open({
                    type: 2,
                    title: '修改',
                    maxmin: true,
                    area: [top.detailHeight, top.detailWidth],
                    shadeClose: false,
                    content: '/resource_detail',
                    success: function(layero, index){
                        layer.getChildFrame('#data-detail-submit', index).text("修改");
                        setFormVal(layer.getChildFrame('#detail', index), data);
                        top.convertDisabled(layer.getChildFrame('#data-detail :input', index), false);
                        detailScreen(index);
                        layero.find('iframe')[0].contentWindow.layui.form.render('select');
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
            code: $('#code').val(),
            pcode: $('#pcode').val(),
            name: $('#name').val(),
            level: $('#level').val(),
            status: $('#status').val(),

        };
        $.ajax({
            url: store.uri + "/resource/edit/auth",
            headers: {'token': localStorage.getItem('token')},
            data: data,
            method: 'POST',
            success: function (res) {
                if (res.code === 200){
                    parent.layer.closeAll();
                    tableReload(true);
                    $("#data-detail :input").each(function () {
                        $(this).val("");
                    });
                } else if (res.code === 403){
                    top.location.href = "/";
                }else {
                    layer.msg(res.msg)
                }
                layer.close(index);
            }
        })
    });

    // 搜索栏事件
    form.on('submit(search)', function (data) {
        tableReload(false);
    });

    // 时间选择器


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
                top.location.href = "/";
            }
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
        }
    });
}

function setFormVal(el, data) {
    for (var val in data) {
        el.find(":input[id='" + val + "']").val(data[val]);
    }
}

function clearFormVal(el) {
    $(':input', el)
        .val('')
        .removeAttr('checked')
        .removeAttr('selected');
}

function detailScreen(index) {
    var detail = layer.getChildFrame('#data-detail', index);
    var height = detail.height()+60;
    if (height > ($(window).height()*0.9)) {
        height = ($(window).height()*0.9);
    }
    layer.style(index, {
        top: (($(window).height()-height)/3)+"px",
        height: height+'px'
    });
    $(".layui-layer-shade").remove();
}
