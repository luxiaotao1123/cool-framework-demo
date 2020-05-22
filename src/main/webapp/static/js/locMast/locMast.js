var pageCurr;
layui.use(['table','laydate', 'form'], function(){
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;
    var layDate = layui.laydate;
    var form = layui.form;

    // 数据渲染
    tableIns = table.render({
        elem: '#locMast',
        headers: {token: localStorage.getItem('token')},
        url: '/locMast/list/auth',
        page: true,
        limit: 16,
        even: true,
        toolbar: '#toolbar',
        cellMinWidth: 50,
        cols: [[
            {type: 'checkbox', fixed: 'left'}
//            ,{field: 'id', title: 'ID', sort: true,align: 'center', fixed: 'left', width: 80}
            ,{field: 'locNo', align: 'center',title: '库位号',sort:true}
            ,{field: 'locType$', align: 'center',title: '库位状态',width:200}
            ,{field: 'whsType$', align: 'center',title: '库位类型'}
            // ,{field: 'pltType', align: 'center',title: ''}
            // ,{field: 'ctnType', align: 'center',title: ''}
            // ,{field: 'locSts', align: 'center',title: ''}
            // ,{field: 'sheetNo', align: 'center',title: ''}
            ,{field: 'crnNo', align: 'center',title: '堆垛机号'}
            ,{field: 'row1', align: 'center',title: '排'}
            ,{field: 'bay1', align: 'center',title: '列'}
            ,{field: 'lev1', align: 'center',title: '层', sort:true}
            ,{field: 'fullPlt', align: 'center',title: '满板', templet:function(row){
                    var html = "<input value='fullPlt' type='checkbox' lay-skin='primary' lay-filter='tableCheckbox' table-index='"+row.LAY_TABLE_INDEX+"'";
                    if(row.fullPlt === 'Y'){html += " checked ";}
                    html += ">";
                    return html;
                },width:80}
            // ,{field: 'outEnable', align: 'center',title: ''}
            // ,{field: 'ioTime$', align: 'center',title: ''}
            // ,{field: 'firstTime$', align: 'center',title: ''}
            // ,{field: 'modiUser$', align: 'center',title: '修改人员',event: 'modiUser', style: 'text-decoration: underline;cursor:pointer'}
            // ,{field: 'modiTime$', align: 'center',title: '修改时间'}
            // ,{field: 'appeUser$', align: 'center',title: '创建者',event: 'appeUser', style: 'text-decoration: underline;cursor:pointer'}
            // ,{field: 'appeTime$', align: 'center',title: '添加时间'}
            // ,{field: 'errorTime$', align: 'center',title: ''}
            // ,{field: 'errorMemo', align: 'center',title: ''}
            // ,{field: 'ctnKind', align: 'center',title: ''}
            // ,{field: 'scWeight', align: 'center',title: ''}
            // ,{field: 'invWh', align: 'center',title: ''}
            // ,{field: 'mk', align: 'center',title: ''}
            // ,{field: 'barcode', align: 'center',title: ''}
            // ,{field: 'PdcType', align: 'center',title: ''}
            // ,{field: 'ctnNo', align: 'center',title: ''}

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
                    top.location.href = "/";
                }
                pageCurr=curr;
                limit();
            }
        });
    });

    // 监听头工具栏事件
    table.on('toolbar(locMast)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event) {
            case 'addData':
                layer.open({
                    type: 2,
                    title: '新增',
                    maxmin: true,
                    area: [top.detailWidth, top.detailHeight],
                    shadeClose: false,
                    content: 'locMast_detail.html',
                    success: function(layero, index){
                        layer.getChildFrame('#data-detail-submit-edit', index).hide();
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
                if (data.length === 0){
                    layer.msg('请选择数据');
                } else {
                    layer.confirm('确定删除'+(data.length===1?'此':data.length)+'条数据吗', function(){
                        $.ajax({
                            url: "/locMast/delete/auth",
                            headers: {'token': localStorage.getItem('token')},
                            data: {param: JSON.stringify(data)},
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
                layer.confirm('确定导出Excel吗', function(){
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
                        'locMast': exportData,
                        'fields': fields
                    };
                    $.ajax({
                        url: "/locMast/export/auth",
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
                                top.location.href = "/";
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
    table.on('tool(locMast)', function(obj){
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
                    content: 'locMast_detail.html',
                    success: function(layero, index){
                        setFormVal(layer.getChildFrame('#detail', index), data, true);
                        top.convertDisabled(layer.getChildFrame('#data-detail :input', index), true);
                        layer.getChildFrame('#data-detail-submit-save,#prompt', index).hide();
                        layer.getChildFrame('#data-detail-submit-edit', index).hide();
                        layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
                        layero.find('iframe')[0].contentWindow.layui.form.render('select');
                        layero.find('iframe')[0].contentWindow.layui.form.render('checkbox');
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
                    content: 'locMast_detail.html',
                    success: function(layero, index){
                        layer.getChildFrame('#data-detail-submit-save', index).hide();
                        setFormVal(layer.getChildFrame('#detail', index), data, false);
                        top.convertDisabled(layer.getChildFrame('#data-detail :input', index), false);
                        top.convertDisabled(layer.getChildFrame('#locNo', index), true);
                        layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
                        layero.find('iframe')[0].contentWindow.layui.form.render('select');
                        layero.find('iframe')[0].contentWindow.layui.form.render('checkbox');
                    }
                });
                break;
            case 'whsType':
                var param = top.reObject(data).whsType;
                if (param === undefined) {
                    layer.msg("无数据");
                } else {
                   layer.open({
                       type: 2,
                       title: '库位详情',
                       maxmin: true,
                       area: [top.detailWidth, top.detailHeight],
                       shadeClose: false,
                       content: '../basWhs/basWhs_detail.html',
                       success: function(layero, index){
                           $.ajax({
                               url: "/basWhs/"+ param +"/auth",
                               headers: {'token': localStorage.getItem('token')},
                               method: 'GET',
                               success: function (res) {
                                   if (res.code === 200){
                                       setFormVal(layer.getChildFrame('#detail', index), res.data, true);
                                       top.convertDisabled(layer.getChildFrame('#data-detail :input', index), true);
                                       layer.getChildFrame('#data-detail-submit-save,#data-detail-submit-edit,#prompt', index).hide();
                                       layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
                                       layero.find('iframe')[0].contentWindow.layui.form.render('select');
                                       layero.find('iframe')[0].contentWindow.layui.form.render('checkbox');
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
            case 'locType':
                var param = top.reObject(data).locType;
                if (param === undefined) {
                    layer.msg("无数据");
                } else {
                   layer.open({
                       type: 2,
                       title: '库位详情',
                       maxmin: true,
                       area: [top.detailWidth, top.detailHeight],
                       shadeClose: false,
                       content: '../basLocType/basLocType_detail.html',
                       success: function(layero, index){
                           $.ajax({
                               url: "/basLocType/"+ param +"/auth",
                               headers: {'token': localStorage.getItem('token')},
                               method: 'GET',
                               success: function (res) {
                                   if (res.code === 200){
                                       setFormVal(layer.getChildFrame('#detail', index), res.data, true);
                                       top.convertDisabled(layer.getChildFrame('#data-detail :input', index), true);
                                       layer.getChildFrame('#data-detail-submit-save,#data-detail-submit-edit,#prompt', index).hide();
                                       layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
                                       layero.find('iframe')[0].contentWindow.layui.form.render('select');
                                       layero.find('iframe')[0].contentWindow.layui.form.render('checkbox');
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
            case 'modiUser':
                var param = top.reObject(data).modiUser;
                if (param === undefined) {
                    layer.msg("无数据");
                } else {
                   layer.open({
                       type: 2,
                       title: '修改详情',
                       maxmin: true,
                       area: [top.detailWidth, top.detailHeight],
                       shadeClose: false,
                       content: '../user/user_detail.html',
                       success: function(layero, index){
                           $.ajax({
                               url: "/user/"+ param +"/auth",
                               headers: {'token': localStorage.getItem('token')},
                               method: 'GET',
                               success: function (res) {
                                   if (res.code === 200){
                                       setFormVal(layer.getChildFrame('#detail', index), res.data, true);
                                       top.convertDisabled(layer.getChildFrame('#data-detail :input', index), true);
                                       layer.getChildFrame('#password,#createTime\\$,#status', index).parent().parent().hide();
                                       layer.getChildFrame('#data-detail-submit,#prompt', index).hide();
                                       layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
                                       layero.find('iframe')[0].contentWindow.layui.form.render('select');
                                       layero.find('iframe')[0].contentWindow.layui.form.render('checkbox');
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
            case 'appeUser':
                var param = top.reObject(data).appeUser;
                if (param === undefined) {
                    layer.msg("无数据");
                } else {
                   layer.open({
                       type: 2,
                       title: '创详情',
                       maxmin: true,
                       area: [top.detailWidth, top.detailHeight],
                       shadeClose: false,
                       content: '../user/user_detail.html',
                       success: function(layero, index){
                           $.ajax({
                               url: "/user/"+ param +"/auth",
                               headers: {'token': localStorage.getItem('token')},
                               method: 'GET',
                               success: function (res) {
                                   if (res.code === 200){
                                       setFormVal(layer.getChildFrame('#detail', index), res.data, true);
                                       top.convertDisabled(layer.getChildFrame('#data-detail :input', index), true);
                                       layer.getChildFrame('#data-detail-submit', index).hide();
                                       layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
                                       layero.find('iframe')[0].contentWindow.layui.form.render('select');
                                       layero.find('iframe')[0].contentWindow.layui.form.render('checkbox');
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
            locNo: $('#locNo').val(),
            whsType: $('#whsType').val(),
            pltType: $('#pltType').val(),
            ctnType: $('#ctnType').val(),
            locSts: $('#locSts').val(),
            sheetNo: $('#sheetNo').val(),
            crnNo: $('#crnNo').val(),
            row1: $('#row1').val(),
            bay1: $('#bay1').val(),
            lev1: $('#lev1').val(),
            fullPlt: $('#fullPlt').val(),
            locType: $('#locType').val(),
            outEnable: $('#outEnable').val(),
            ioTime: top.strToDate($('#ioTime\\$').val()),
            firstTime: top.strToDate($('#firstTime\\$').val()),
            modiUser: $('#modiUser').val(),
            modiTime: top.strToDate($('#modiTime\\$').val()),
            appeUser: $('#appeUser').val(),
            appeTime: top.strToDate($('#appeTime\\$').val()),
            errorTime: top.strToDate($('#errorTime\\$').val()),
            errorMemo: $('#errorMemo').val(),
            ctnKind: $('#ctnKind').val(),
            scWeight: $('#scWeight').val(),
            invWh: $('#invWh').val(),
            mk: $('#mk').val(),
            barcode: $('#barcode').val(),
            PdcType: $('#PdcType').val(),
            ctnNo: $('#ctnNo').val(),

        };
        $.ajax({
            url: "/locMast/"+name+"/auth",
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
                    top.location.href = "/";
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
        elem: '#ioTime\\$',
        type: 'datetime'
    });
    layDate.render({
        elem: '#firstTime\\$',
        type: 'datetime'
    });
    layDate.render({
        elem: '#modiTime\\$',
        type: 'datetime'
    });
    layDate.render({
        elem: '#appeTime\\$',
        type: 'datetime'
    });
    layDate.render({
        elem: '#errorTime\\$',
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
