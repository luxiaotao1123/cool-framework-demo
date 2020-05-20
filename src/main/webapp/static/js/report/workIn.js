var pageCurr;
layui.use(['table','laydate', 'form'], function(){
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;
    var layDate = layui.laydate;
    var form = layui.form;


    // 数据渲染
    tableIns = table.render({
        elem: '#workIn',
        headers: {token: localStorage.getItem('token')},
        url: '/report/viewWorkInList.action',
        page: true,
        limit: 10,
        toolbar: '#toolbar',
        cellMinWidth: 50,
        cols: [[
            {type: 'checkbox'}
            ,{field: 'io_time', align: 'center', title: '入库日期', sort: true}
            ,{field: 'loc_no', align: 'center',title: '库位号'}
            // ,{field: 'crn_str_time', align: 'center',title: '堆垛机启动时间'}
            // ,{field: 'crn_end_time', align: 'center',title: '堆垛机停止时间'}
            ,{field: 'matnr', align: 'center',title: '物料'}
            ,{field: 'maktx', align: 'center',title: '物料描述'}
            ,{field: 'lgnum', align: 'center',title: '仓库号'}
            ,{field: 'tbnum', align: 'center',title: '请求编号'}
            ,{field: 'zmatid', align: 'center',title: '物料标签ID'}
            ,{field: 'werks', align: 'center',title: '工厂'}
            ,{field: 'anfme', align: 'center',title: '数量'}
            ,{field: 'altme', align: 'center',title: '单位'}
            ,{field: 'zpallet', align: 'center',title: '托盘码'}
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
            pageCurr=curr;
            limit();
        }
    });

    // 监听头工具栏事件
    table.on('toolbar(workIn)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event) {
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
                    var param = {
                        'fields': fields
                    };
                    $.ajax({
                        url: "/report/viewWorkInExport.action",
                        headers: {'token': localStorage.getItem('token')},
                        data: JSON.stringify(param),
                        dataType:'json',
                        contentType:'application/json;charset=UTF-8',
                        method: 'POST',
                        success: function (res) {
                            console.log(res);
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

    // 搜索栏搜索事件
    form.on('submit(search)', function (data) {
        pageCurr = 1;
        tableReload(false);
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
