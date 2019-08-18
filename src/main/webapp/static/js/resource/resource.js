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
        headers: {token: sessionStorage.getItem('token')},
        url: '/resource/list/auth',
        page: true,
        toolbar: '#toolbar',
        cellMinWidth: 50,
        cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'id', title: 'ID', sort: true,align: 'center', fixed: 'left', width: 80}
            ,{field: 'name', align: 'center',title: '名称'}
            ,{field: 'url', align: 'center',title: '路径'}
            ,{field: 'status', align: 'center',title: '状态'}

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
                parent.location.href = "/";
            }
            enumConvert(false);
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
                    	$(".layui-layer-shade").remove();
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
                                } else if (res.code === 403){
                                    parent.location.href = "/";
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
                        detailScreen(index);
                        $(".layui-layer-shade").remove();
                        setFormVal(layer.getChildFrame('#detail', index), data);
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
                        detailScreen(index);
                        $(".layui-layer-shade").remove();
                        setFormVal(layer.getChildFrame('#detail', index), data);
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
            name: $('#name').val(),
            url: $('#url').val(),
            status: $('#status').val(),

        };
        $.ajax({
            url: store.uri + "/resource/edit/auth",
            headers: {
                'token': sessionStorage.getItem('token')
            },
            data: data,
            method: 'POST',
            success: function (res) {
                if (res.code === 200){
                    parent.layer.closeAll();
                    tableReload(null, true);
                    $("#data-detail :input").each(function () {
                        $(this).val("");
                    });
                } else if (res.code === 403){
                    parent.location.href = "/";
                }else {
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

function tableReload(data, child) {
    (child ? parent.tableIns : tableIns).reload({
        where: data,
        page: {
            curr: pageCurr
        },
        done: function (res, curr, count) {
            if (res.code === 403) {
                parent.location.href = "/";
            }
            enumConvert(true);
            pageCurr=curr;
        }
    });
}

function enumConvert(child) {
	var my$ = (child ? parent.$ : this.$);
    my$("[data-field='status']").children().each(function(){
        if(my$(this).text()==='0'){
            my$(this).text("失效")
        }
        if(my$(this).text()==='1'){
            my$(this).text("有效")
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
}
