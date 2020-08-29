var pageCurr;
layui.use(['table','laydate', 'form', 'upload'], function(){
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;
    var layDate = layui.laydate;
    var form = layui.form;
    var upload = layui.upload;

    // 数据渲染
    tableIns = table.render({
        elem: '#mate',
        headers: {token: localStorage.getItem('token')},
        url: baseUrl+'/mate/list/auth',
        page: true,
        limit: 16,
        limits: [16, 30, 50, 100, 200, 500],
        even: true,
        toolbar: '#toolbar',
        cellMinWidth: 50,
        cols: [[
            {type: 'checkbox'}
//            ,{field: 'id', title: 'ID', sort: true,align: 'center', fixed: 'left', width: 80}
            ,{field: 'billId', align: 'center',title: '单据编号'}
            ,{field: 'billTime$', align: 'center',title: '单据日期'}
            ,{field: 'supplier', align: 'center',title: '供应商'}
            ,{field: 'code', align: 'center',title: '商品编码'}
            ,{field: 'name', align: 'center',title: '商品名称'}
            ,{field: 'amount', align: 'center',title: '数量'}
            ,{field: 'leadTime$', align: 'center',title: '交货时间'}
            ,{field: 'pakinAmount', align: 'center',title: '已入库数量'}
            ,{field: 'notpakAmount', align: 'center',title: '未入库数量'}
            ,{field: 'content', align: 'center',title: '商品行备注'}
            ,{field: 'state', align: 'center',title: '审核状态'}
            ,{field: 'status', align: 'center',title: '关闭状态'}
            ,{field: 'pakStatus', align: 'center',title: '入库状态'}
            ,{field: 'billMemo', align: 'center',title: '单据备注'}
            ,{field: 'unit', align: 'center',title: '单位'}
            ,{field: 'createBy$', align: 'center',title: '创建者',hide:true}
            ,{field: 'createTime$', align: 'center',title: '创建时间',hide:true}
            ,{field: 'updateBy$', align: 'center',title: '修改人员',hide:true}
            ,{field: 'updateTime$', align: 'center',title: '修改时间',hide:true}
            ,{field: 'memo', align: 'center',title: '备注',hide:true}

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
                top.location.href = baseUrl+"/";
            }
            pageCurr=curr;
            limit();
            form.on('checkbox(tableCheckbox)', function (data) {
                var _index = $(data.elem).attr('table-index')||0;
                if(data.elem.checked){
                    res.data[_index][data.value] = 'Y';
                }else{
                    res.data[_index][data.value] = 'N';
                }
            });
        }
    });

    // 监听排序事件
    table.on('sort(locMast)', function (obj) {
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
    table.on('toolbar(mate)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event) {
            case 'addData':
                layer.open({
                    type: 2,
                    title: '新增',
                    maxmin: true,
                    area: [top.detailWidth, top.detailHeight],
                    content: 'mate_detail.html',
                    success: function(layero, index){
                        layer.getChildFrame('#data-detail-submit-edit', index).hide();
                    	clearFormVal(layer.getChildFrame('#detail', index));
                        layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
                    }
                });
                break;
            case 'deleteData':
                var data = checkStatus.data;
                if (data.length === 0){
                    layer.msg('请选择数据');
                } else {
                    layer.confirm('确定删除'+(data.length===1?'此':data.length)+'条数据吗', function(){
                        $.ajax({
                            url: baseUrl+"/mate/delete/auth",
                            headers: {'token': localStorage.getItem('token')},
                            data: {param: JSON.stringify(data)},
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
                        'mate': exportData,
                        'fields': fields
                    };
                    $.ajax({
                        url: baseUrl+"/mate/export/auth",
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
            // 导入
            case 'intoData':
                layer.open({
                    type: 1,
                    title: '数据导入',
                    shadeClose: true,
                    content: $('#importDataDiv'),
                    success: function(layero, index){
                        uploader.reload();
                    },
                    end: function () {
                        $('#uploadDesc').show();
                        $('#uploadDemoView').hide();
                        $('#fileMame').html("");
                    }
                });
                break;
        }
    });

    // 导入excel
    var uploader = upload.render({
        elem: '#uploadEx'
        , url: baseUrl + '/mate/import/auth'
        , headers: {token: localStorage.getItem('token')}
        , accept: 'file'
        , exts: 'xls|excel|xlsx'
        , auto: false
        , bindAction: '#uploadDo'
        , before: function(obj){
            layer.closeAll();
            layer.load(1, {shade: [0.1,'#fff']});
        }
        , choose: function(obj){
            $('#uploadDesc').hide();
            $('#uploadDemoView').show();
            obj.preview(function(index, file, result){
                $('#fileMame').html(file.name);
            });
        }
        , done: function (res) {
            limit();
            $('#uploadDesc').show();
            $('#uploadDemoView').hide();
            $('#fileMame').html("");
            layer.closeAll('loading');
            layer.msg(res.msg);
            tableReload(false);
        }
        , error: function(index, upload){
            layer.closeAll('loading');
        }
    })


    // 监听行工具事件
    table.on('tool(mate)', function(obj){
        var data = obj.data;
        switch (obj.event) {
            // 编辑
            case 'edit':
                layer.open({
                    type: 2,
                    title: '修改',
                    maxmin: true,
                    area: [top.detailWidth, top.detailHeight],
                    content: 'mate_detail.html',
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
            billId: $('#billId').val(),
            billTime: top.strToDate($('#billTime\\$').val()),
            supplier: $('#supplier').val(),
            code: $('#code').val(),
            name: $('#name').val(),
            amount: $('#amount').val(),
            leadTime: top.strToDate($('#leadTime\\$').val()),
            pakinAmount: $('#pakinAmount').val(),
            notpakAmount: $('#notpakAmount').val(),
            content: $('#content').val(),
            state: $('#state').val(),
            status: $('#status').val(),
            pakStatus: $('#pakStatus').val(),
            billMemo: $('#billMemo').val(),
            unit: $('#unit').val(),
            createBy: $('#createBy').val(),
            createTime: top.strToDate($('#createTime\\$').val()),
            updateBy: $('#updateBy').val(),
            updateTime: top.strToDate($('#updateTime\\$').val()),
            memo: $('#memo').val(),

        };
        $.ajax({
            url: baseUrl+"/mate/"+name+"/auth",
            headers: {'token': localStorage.getItem('token')},
            data: top.reObject(data),
            method: 'POST',
            success: function (res) {
                if (res.code === 200){
                    parent.layer.closeAll();
                    parent.$(".layui-laypage-btn")[0].click();
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
        elem: '#billTime\\$',
        type: 'datetime'
    });
    layDate.render({
        elem: '#leadTime\\$',
        type: 'datetime'
    });
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
        height = ($(window).height()*0.8);
    }
    layer.style(index, {
//        top: (($(window).height()-height)/3)+"px",
        height: height+'px'
    });
}

$('body').keydown(function () {
    if (event.keyCode === 13) {
        $("#search").click();
    }
});
