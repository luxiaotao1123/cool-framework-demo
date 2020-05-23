var pageCurr;
var locNo;
layui.use(['table','laydate', 'form'], function(){
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;
    var layDate = layui.laydate;
    var form = layui.form;

    // 数据渲染
    tableIns = table.render({
        elem: '#stoQue',
        headers: {token: localStorage.getItem('token')},
        url: '/locMast/list/auth',
        page: true,
        limit: 10,
        even: true,
        toolbar: '#toolbar',
        cellMinWidth: 50,
        cols: [[
            {type: 'checkbox'}
            ,{field: 'locNo', align: 'center',title: '库位号',event: 'locDetl'}
            ,{field: 'locType$', align: 'center',title: '库位状态', width: 180,event: 'locDetl'}
            ,{field: 'whsType$', align: 'center',title: '库位类型',event: 'locDetl'}
            ,{field: 'crnNo', align: 'center',title: '堆垛机号',event: 'locDetl'}
            ,{field: 'row1', align: 'center',title: '排',event: 'locDetl'}
            ,{field: 'bay1', align: 'center',title: '列',event: 'locDetl'}
            ,{field: 'lev1', align: 'center',title: '层',event: 'locDetl'}
            ,{field: 'fullPlt', align: 'center',title: '满板',event: 'locDetl', templet:function(row){
                    var html = "<input value='fullPlt' type='checkbox' lay-skin='primary' lay-filter='tableCheckbox' table-index='"+row.LAY_TABLE_INDEX+"'";
                    if(row.fullPlt === 'Y'){html += " checked ";}
                    html += "disabled='disabled' >";
                    return html;
                },width:80}
            ,{field: 'modiUser$', align: 'center',title: '修改人员',event: 'locDetl'}
            ,{field: 'modiTime$', align: 'center',title: '修改时间', width: 180,event: 'locDetl'}
            ,{ fixed: 'right', title:'操作', align: 'center', toolbar: '#operate'}
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
            if (count === 1){
                // locDetl(res.data[0][locNo]);
            }
        }
    });

    // 监听排序事件
    table.on('sort(stoQue)', function (obj) {
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
    table.on('toolbar(stoQue)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event) {
            // 更新库存
            case 'refreshSto': // todo:luxiaotao
                alert("还没做");
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
                        'wrkLastno': exportData,
                        'fields': fields
                    };
                    $.ajax({
                        url: "/wrkLastno/export/auth",
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
    table.on('tool(stoQue)', function(obj) {
        var data = obj.data;
        switch (obj.event) {
            // 查看明细
            case 'locDetl':
                // locDetl(data.locNo);
                if (data.locType.trim() === ''
                    || data.locType.trim() === 'S'
                    || data.locType.trim() === 'D'
                    || data.locType.trim() === 'O')  {
                    layer.msg("此库位的状态不存在物料");
                    return;
                }
                locDetlToLayer(data.locNo);
                break;
        }
    });

    // iframe物料详情
    function locDetlToLayer(val) {
        locNo = val;
        layer.open({
            type: 2,
            title: '工作档明细',
            maxmin: true,
            area: [top.detailWidth, top.detailHeight],
            shadeClose: true,
            content: '../report/locDetl.html',
            success: function(layero, index){
            }
        });
    }
    // div物料详情
    var pageCur;
    function locDetl(locNo){
        $('#detlTable').css("display", 'block');
        // 数据渲染
        tableIns1 = table.render({
            elem: '#locDetlByMap',
            headers: {token: localStorage.getItem('token')},
            url: '/locDetl/list/auth',
            page: true,
            limit: 5,
            skin: 'line',
            where: {loc_no: locNo},
            even: true,
            cellMinWidth: 50,
            cols: [[
                // {type: 'checkbox'}
                {field: 'locNo$', align: 'center',title: '库位号'}
                ,{field: 'matnr', align: 'center',title: '物料'}
                ,{field: 'lgnum', align: 'center',title: '仓库号'}
                ,{field: 'tbnum', align: 'center',title: '转储请求编号'}
                // ,{field: 'tbpos', align: 'center',title: '行项目'}
                ,{field: 'zmatid', align: 'center',title: '物料标签ID'}
                ,{field: 'maktx', align: 'center',title: '物料描述'}
                ,{field: 'werks', align: 'center',title: '工厂'}
                ,{field: 'anfme', align: 'center',title: '数量'}
                ,{field: 'altme', align: 'center',title: '单位'}
                ,{field: 'zpallet', align: 'center',title: '托盘条码'}
                ,{field: 'bname', align: 'center',title: '用户ID'}
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
                pageCur=curr;
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
    }

    // 搜索栏重置事件
    form.on('submit(reset)', function (data) {
        pageCurr = 1;
        clearFormVal($('#search-box'));
        $('#detlTable').css("display", 'none');
        tableReload(false);
    });

    // 搜索栏搜索事件
    form.on('submit(search)', function (data) {
        pageCurr = 1;
        $('#detlTable').css("display", 'none');
        tableReload(false);
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
                if (count === 1){
                    // locDetl(res.data[0][locNo]);
                }
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
});

// 关闭动作
$(document).on('click','#data-detail-close', function () {
    parent.layer.closeAll();
});

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

$('body').keydown(function () {
    if (event.keyCode === 13) {
        $("#search").click();
    }
});
