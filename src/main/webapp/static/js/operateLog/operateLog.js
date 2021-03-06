var pageCurr;
layui.use(['table','laydate', 'form'], function(){
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;
    var layDate = layui.laydate;
    var form = layui.form;

    // 数据渲染
    tableIns = table.render({
        elem: '#operateLog',
        headers: {token: localStorage.getItem('token')},
        url: baseUrl+'/operateLog/list/auth',
        page: true,
        limit: 16,
        limits: [16, 30, 50, 100, 200, 500],
        even: true,
        toolbar: '#toolbar',
        cellMinWidth: 50,
        cols: [[
            // {type: 'checkbox'}
            // {field: 'id', title: 'ID', sort: true,align: 'center', width: 80}
            {field: 'action', align: 'center',title: '操作内容'}
            ,{field: 'userId$', align: 'center',title: '用户'}
            ,{field: 'ip', align: 'center',title: '客户端IP'}
            ,{field: 'request', align: 'center',title: '请求数据'}
            ,{field: 'response', align: 'center',title: '响应数据'}
            ,{field: 'createTime$', align: 'center',title: '添加时间'}

            // ,{fixed: 'right', title:'操作', align: 'center', toolbar: '#operate', width:100}
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
            pageCurr=curr;
            limit();
        }
    });

    table.on('edit(operateLog)', function (obj) {
        $('body').keydown(function () {
            if (event.keyCode === 13) {
                layer.confirm('保存修改?', function(){

                });
            }
        });
    });

    // 监听排序事件
    table.on('sort(operateLog)', function (obj) {
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
                pageCurr=curr;
                limit();
            }
        });
    });

    // 监听头工具栏事件
    table.on('toolbar(operateLog)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event) {
            case 'addData':
                layer.open({
                    type: 2,
                    title: '新增',
                    maxmin: true,
                    area: [top.detailWidth, top.detailHeight],
                    shadeClose: false,
                    content: 'operateLog_detail.html',
                    success: function(layero, index){
                    	clearFormVal(layer.getChildFrame('#detail', index));
                        layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
                    }
                });
                break;
            case 'refreshData':
                tableIns.reload({
                    page: {
                        curr: pageCurr
                    }
                });
                limit();
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
                            url: baseUrl+"/operateLog/delete/auth",
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
                layer.confirm('确定导出Excel吗', {shadeClose: true}, function(){
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
                        'operateLog': exportData,
                        'fields': fields
                    };
                    $.ajax({
                        url: baseUrl+"/operateLog/export/auth",
                        headers: {'token': localStorage.getItem('token')},
                        data: JSON.stringify(param),
                        dataType:'json',
                        contentType:'application/json;charset=UTF-8',
                        method: 'POST',
                        success: function (res) {
                            layer.closeAll();
                            if (res.code === 200) {
                                table.exportFile(titles,res.data,'xls');
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
    table.on('tool(operateLog)', function(obj){
        var data = obj.data;
        switch (obj.event) {
            // 详情
            case 'detail':
                layer.open({
                    type: 2,
                    title: '详情',
                    maxmin: true,
                    area: [top.detailWidth, top.detailHeight],
                    shadeClose: false,
                    content: 'operateLog_detail.html',
                    success: function(layero, index){
                        setFormVal(layer.getChildFrame('#detail', index), data, true);
                        top.convertDisabled(layer.getChildFrame('#data-detail :input', index), true);
                        layer.getChildFrame('#data-detail-submit,#prompt', index).hide();
                        layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
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
                    area: [top.detailWidth, top.detailHeight],
                    shadeClose: false,
                    content: 'operateLog_detail.html',
                    success: function(layero, index){
                        setFormVal(layer.getChildFrame('#detail', index), data, false);
                        top.convertDisabled(layer.getChildFrame('#data-detail :input', index), false);
                        layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
                        layero.find('iframe')[0].contentWindow.layui.form.render('select');
                    }
                });
                break;
            case 'userId':
                var param = top.reObject(data).userId;
                if (param === undefined) {
                    layer.msg("无数据");
                } else {
                   layer.open({
                       type: 2,
                       title: '详情',
                       maxmin: true,
                       area: [top.detailHeight, top.detailWidth],
                       shadeClose: false,
                       content: '../user/user_detail.html',
                       success: function(layero, index){
                           $.ajax({
                               url: baseUrl+"/user/"+ param +"/auth",
                               headers: {'token': localStorage.getItem('token')},
                               method: 'GET',
                               success: function (res) {
                                   if (res.code === 200){
                                       setFormVal(layer.getChildFrame('#detail', index), res.data, true);
                                       top.convertDisabled(layer.getChildFrame('#data-detail :input', index), true);
                                       layer.getChildFrame('#data-detail-submit,#prompt', index).hide();
                                       layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
                                       layero.find('iframe')[0].contentWindow.layui.form.render('select');
                                   } else if (res.code === 403){
                                       parent.location.href = "/";
                                   }else {
                                       layer.msg(res.msg)
                                   }
                               }
                           })
                       }
                   });
                }
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
            action: $('#action').val(),
            userId: $('#userId').val(),
            ip: $('#ip').val(),
            request: $('#request').val(),
            response: $('#response').val(),
            createTime: top.strToDate($('#createTime\\$').val()),

        };
        $.ajax({
            url: baseUrl+"/operateLog/edit/auth",
            headers: {'token': localStorage.getItem('token')},
            data: top.reObject(data),
            method: 'POST',
            success: function (res) {
                if (res.code === 200){
                    parent.layer.closeAll();
                    tableReload(true);
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

function setFormVal(el, data, showImg) {
    for (var val in data) {
        var find = el.find(":input[id='" + val + "']");
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

