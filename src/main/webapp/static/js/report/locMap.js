var pageCurr;
layui.use(['table','laydate', 'form'], function(){
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;
    var layDate = layui.laydate;
    var form = layui.form;

    $.ajax({
        url: "/report/viewLocMapList.action",
        headers: {'token': localStorage.getItem('token')},
        data: {row: 1},
        method: 'POST',
        success: function (res) {
            console.log(res);
            if (res.code === 200) {
                //用jQuery获取模板
                var tpl = $("#locMapTemplate").html();
                //预编译模板
                var template = Handlebars.compile(tpl);
                //匹配json内容
                var html = template(res.data);
                console.log(html);
                //输入模板
                $('#test').html(html);
            } else if (res.code === 403) {
                top.location.href = "/";
            } else {
                layer.msg(res.msg)
            }
        }
    });

    // 数据渲染
    // tableIns = table.render({
    //     elem: '#locMap',
    //     headers: {token: localStorage.getItem('token')},
    //     url: '/report/viewLocMapList.action',
    //     page: true,
    //     limit: 10,
    //     toolbar: '#toolbar',
    //     cellMinWidth: 50,
    //     cols: [[
    //         {type: 'checkbox'}
    //         ,{field: 'ymd', align: 'center', title: '日期', sort: true}
    //         ,{field: 'source_sta_no', align: 'center',title: '站点'}
    //         ,{field: 'sto_qty', align: 'center',title: '入库次数'}
    //         ,{field: 'ret_qty', align: 'center',title: '出库次数'}
    //         ,{field: 'total_qty', align: 'center',title: '入出总数'}
    //     ]],
    //     request: {
    //         pageName: 'curr',
    //         pageSize: 'limit'
    //     },
    //     parseData: function (res) {
    //         return {
    //             'code': res.code,
    //             'msg': res.msg,
    //             'count': res.data.total,
    //             'data': res.data.records
    //         }
    //     },
    //     response: {
    //         statusCode: 200
    //     },
    //     done: function(res, curr, count) {
    //         if (res.code === 403) {
    //             top.location.href = "/";
    //         }
    //         pageCurr=curr;
    //         limit();
    //     }
    // });

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
