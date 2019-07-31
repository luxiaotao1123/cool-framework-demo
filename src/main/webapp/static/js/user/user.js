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
        elem: '#user',
        headers: {token: sessionStorage.getItem('token')},
        url: '/user/list/auth',
        page: true,
        toolbar: '#toolbar',
        cellMinWidth: 50,
        cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'id', title: 'ID', sort: true,align: 'center', fixed: 'left', width: 80}
            ,{field: 'username', align: 'center',title: '用户名'}
            ,{field: 'mobile',align: 'center', title: '联系方式'}
            ,{field: 'createTime',align: 'center', title: '注册时间', sort: true}
            ,{field: 'status', align: 'center',title: '状态', sort: true}
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
            enumConvert();
            pageCurr=curr;
        }
    });

    // 监听头工具栏事件
    table.on('toolbar(user)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event) {
            case 'addData':
                clearFormVal('#detail');
                layer.open({
                    type: 1,
                    title: '新增',
                    maxmin: true,
                    area: ['420px', '330px'],
                    shadeClose: false,
                    content: $('#data-add'),
                    success: function(){
                        $(".layui-layer-shade").remove();
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
                            url: store.uri + "/user/delete/auth",
                            headers: {
                                'token': sessionStorage.getItem('token')
                            },
                            data: {ids: ids},
                            method: 'POST',
                            traditional:true,
                            success: function (res) {
                                if (res.code === 200){
                                    layer.closeAll();
                                    tableReload();
                                } else {
                                    layer.alert(res.msg)
                                }
                            }
                        })
                    });
                }
                break;
        }
    });

    // 监听行工具事件
    table.on('tool(user)', function(obj){
        var data = obj.data;
        // 查看
        if(obj.event === 'detail'){
            layer.open({
                type: 1,
                title: '新增',
                maxmin: true,
                area: ['420px', '330px'],
                shadeClose: false,
                content: $('#data-add'),
                success: function(){
                    $(".layui-layer-shade").remove();
                    setFormVal($('#detail'), data);
                }
            });
        }
        // 编辑
        else if(obj.event === 'edit'){
            layer.open({
                type: 1,
                title: '新增',
                maxmin: true,
                area: ['420px', '330px'],
                shadeClose: false,
                content: $('#data-add'),
                success: function(){
                    $(".layui-layer-shade").remove();
                    setFormVal($('#detail'), data);
                }
            });
        }
    });

    form.on('submit(add)', function () {
        var index = layer.load(1, {
            shade: [0.5,'#000'] //0.1透明度的背景
        });
        var data = {
            username: $('#username').val(),
            mobile: $('#mobile').val(),
            password: $('#password').val(),
            status: $('#status').val()
        };
        $.ajax({
            url: store.uri + "/user/add/auth",
            headers: {
                'token': sessionStorage.getItem('token')
            },
            data: data,
            method: 'POST',
            success: function (res) {
                if (res.code === 200){
                    layer.closeAll();
                    tableReload();
                    $("#data-add :input").each(function () {
                        $(this).val("");
                    });
                } else {
                    layer.alert(res.msg)
                }
                layer.close(index);
            }
        })
    });

    // 搜索栏事件
    form.on('submit(search)', function (data) {
        tableReload(data.field);
    });

    // 时间选择器
    layDate.render({
        elem: '#startTime'
    });
    layDate.render({
        elem: '#endTime'
    });

});

function tableReload(data) {
    tableIns.reload({
        where: data,
        page: {
            curr: pageCurr
        },
        done: function (res, curr, count) {
            enumConvert();
            pageCurr=curr;
        }
    });
}

function enumConvert() {
    $("[data-field='status']").children().each(function(){
        if($(this).text()==='1'){
            $(this).text("有效")
        }
        if($(this).text()==='2'){
            $(this).text("失效")
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