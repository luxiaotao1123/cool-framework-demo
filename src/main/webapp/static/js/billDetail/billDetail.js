var pageCurr;
layui.use(['table','laydate', 'form'], function(){
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;
    var layDate = layui.laydate;
    var form = layui.form;
    var selectIds=[];
    var spell=[];
    // 数据渲染
    tableIns = table.render({
        elem: '#billDetail',
        headers: {token: localStorage.getItem('token')},
        url: '/billDetail/list/auth',
        page: true,
        limit: 16,
        toolbar: '#toolbar',
        cellMinWidth: 50,
        cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'id', title: 'ID', sort: true,align: 'center', fixed: 'left', width: 80}
            ,{field: 'billId$', align: 'center',title: '所属订单',event: 'billId', style: 'text-decoration: underline;cursor:pointer'}
            ,{field: 'amount', align: 'center',title: '数量'}
            ,{field: 'boxNumber', align: 'center',title: '箱号'}
            ,{field: 'boxer', align: 'center',title: '装箱员'}
            ,{field: 'outStocker', align: 'center',title: '出库员'}
            ,{field: 'createTime$', align: 'center',title: '添加时间'}
            ,{field: 'status$', align: 'center',title: '状态'}
            ,{field: 'spellStatus$', align: 'center',title: '拼单状态'}
            ,{fixed: 'right', title:'操作', align: 'center', toolbar: '#operate', width:200}
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
    table.on('toolbar(billDetail)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event) {
            case 'addData':
                layer.open({
                    type: 2,
                    title: '新增',
                    maxmin: true,
                    area: [top.detailWidth, top.detailHeight],
                    shadeClose: false,
                    content: '/billDetail_detail',
                    success: function(layero, index){
                    	clearFormVal(layer.getChildFrame('#detail', index));
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
                            url: "/billDetail/delete/auth",
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
                        'billDetail': exportData,
                        'fields': fields
                    };
                    $.ajax({
                        url: "/billDetail/export/auth",
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
            // 补印编辑
            case 'repairPrint':
                selectIds=[];
                var data = checkStatus.data;
                data.map(function (track) {
                    selectIds.push(track.id);
                });
                if (selectIds.length === 0){
                    layer.msg('请选择数据');
                }
                else{
                    layer.open({
                        type: 2,
                        title: '补印',
                        maxmin: false,
                        area:["400px","500px"],
                        shadeClose: false,
                        content: '/repairPrint',
                        success: function(layero, index){
                            var div = layero.find('iframe').contents().find("#ids_1");
                            div.val(selectIds);

                        }
                    });
                }
                break;

            // 拼单
            case 'spellList':
                spell=[];
                var data = checkStatus.data;
                data.map(function (track) {
                    spell.push(track.id);
                });
                if (spell.length === 0){
                    layer.msg('请选择数据');
                }
                else{
                    // layer.open({
                    //     type: 2,
                    //     title: '拼单',
                    //     maxmin: false,
                    //     area:["400px","500px"],
                    //     shadeClose: false,
                    //     content: '/repairPrint',
                    //     success: function(layero, index){
                    //         var div = layero.find('iframe').contents().find("#ids_1");
                    //         div.val(selectIds);
                    //
                    //     }
                    // });、

                    layer.open({
                        type: 1
                        ,title: '拼单' //不显示标题栏
                        ,closeBtn: false
                        ,area: '300px;'
                        ,shade: 0.8
                        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
                        ,btn: ['确认', '取消']
                        ,btnAlign: 'c'

                        ,moveType: 1 //拖拽模式，0或者1
                        ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">确保您选择的箱号为同一个订单,否则拼单无效! <br></div>'
                        ,success: function(layero){
                            var btn = layero.find('.layui-layer-btn');
                            //btn.find()
                            // btn.find('.layui-layer-btn0').attr({
                            //     href: 'billDetail/spellList'
                            //     ,target: '_self'
                            // });
                            btn.click(function () {
                                var datas= spell;
                                $.ajax({
                                    url: "/billDetail/spellList",
                                    headers: {'token': localStorage.getItem('token')},
                                    data:{"ids":datas.toString()},
                                    method: 'POST',
                                    success: function (res) {

                                        if (res.code === 200){

                                            var bill = res.data;
                                            console.log(res);
                                            var tpl   =  $("#newsListTemplate2").html();
                                            var template = Handlebars.compile(tpl);
                                            var html = template(bill);
                                            $("#box").html(html);

                                            $('#box').css("display", "block");
                                            $('#box').print();
                                            $('#box').css("display", "none");
                                        } else if (res.code === 403){
                                            top.location.href = "/";
                                        }else {
                                            layer.msg(res.msg)
                                        }
                                        layer.close();
                                    }
                                })
                            })

                        }
                    });
                }
            // ,offset: function(othis){
            //     var type = othis.data('type')
            //         ,text = othis.text();
            //
            // }
                break;

        }
    });

    // 监听行工具事件
    table.on('tool(billDetail)', function(obj){
        var data = obj.data;
        switch (obj.event) {
            // 查看
            case 'detail':
                layer.open({
                    type: 2,
                    title: '查看',
                    maxmin: true,
                    area: [top.detailWidth, top.detailHeight],
                    shadeClose: false,
                    content: '/billDetail_detail',
                    success: function(layero, index){
                        setFormVal(layer.getChildFrame('#detail', index), data, true);
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
                    area: [top.detailWidth, top.detailHeight],
                    shadeClose: false,
                    content: '/billDetail_detail',
                    success: function(layero, index){
                        setFormVal(layer.getChildFrame('#detail', index), data, false);
                        top.convertDisabled(layer.getChildFrame('#data-detail :input', index), false);
                        detailScreen(index);
                        layero.find('iframe')[0].contentWindow.layui.form.render('select');
                    }
                });
                break;
            case 'billId':
                var param = top.reObject(data).billId;
                if (param === undefined) {
                    layer.msg("无数据");
                } else {
                   layer.open({
                       type: 2,
                       title: '所属详情',
                       maxmin: true,
                       area: [top.detailHeight, top.detailWidth],
                       shadeClose: false,
                       content: 'bill_detail',
                       success: function(layero, index){
                           $.ajax({
                               url: "/bill/"+ param +"/auth",
                               headers: {'token': localStorage.getItem('token')},
                               method: 'GET',
                               success: function (res) {
                                   if (res.code === 200){
                                       setFormVal(layer.getChildFrame('#detail', index), res.data, true);
                                       top.convertDisabled(layer.getChildFrame('#data-detail :input', index), true);
                                       layer.getChildFrame('#data-detail-submit', index).hide();
                                       detailScreen(index);
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
            // 补印编辑
            case 'repairPrint':
                selectIds=[];
                    layer.open({
                        type: 2,
                        title: '补印',
                        maxmin: false,
                        area:["400px","500px"],
                        shadeClose: false,
                        content: '/repairPrint',
                        success: function(layero, index){
                            // setFormVal(layer.getChildFrame('#createTime_detail', index), data, false);
                            // top.convertDisabled(layer.getChildFrame('#ids_1 :input', index), true);
                            var div = layero.find('iframe').contents().find("#ids_1");
                            div.val(data.id);
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
            billId: $('#billId').val(),
            amount: $('#amount').val(),
            boxNumber: $('#boxNumber').val(),
            boxer: $('#boxer').val(),
            createTime: top.strToDate($('#createTime\\$').val()),
            status: $('#status').val(),

        };
        $.ajax({
            url: "/billDetail/edit/auth",
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
                    top.location.href = "/";
                }else {
                    layer.msg(res.msg)
                }
                layer.close(index);
            }
        })
    });

    form.on('submit(editCreateTime)', function () {
        var index = layer.load(1, {
            shade: [0.5,'#000'] //0.1透明度的背景
        });
        var data = {

             ids:$("#ids_1").val(),// top.strToDate($('#ids1').text()),
            createTime: top.strToDate($('#createTime').val()),
        };

        $.ajax({
            url: "/billDetail/updateCreate",
            headers: {'token': localStorage.getItem('token')},
            data: top.reObject(data),
            method: 'POST',
            async:false,//取消异步请求
            success: function (res) {

                if (res.code === 200){
                    tableReload(true);
                    $.ajax({
                        url: "/billDetail/print",
                        headers: {'token': localStorage.getItem('token')},
                        data: {id: $("#ids_1").val()},
                        method: 'POST',
                        async:false,//取消异步请求
                        success: function (res) {
                            if (res.code === 200) {

                                 layer.closeAll();
                                var bill = res.data;
                                var tpl   =  $("#newsListTemplate2").html();
                                var template = Handlebars.compile(tpl);
                                var html = template(bill);
                                $("#box").html(html);

                                $('#box').css("display", "block");
                                $('#box').print();
                                $('#box').css("display", "none");
                            } else if (res.code === 403) {
                                top.location.href = "/";
                            } else {

                                layer.msg(res.msg)
                            }
                        }
                    });
                } else if (res.code === 403){
                    top.location.href = "/";
                }else {
                    $("#ids_1").val("")
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

    // // 时间选择器
    // layDate.render({
    //     elem: '#createTime\\$',
    //     type: 'date'
    // });
    // 时间选择器
    layDate.render({
        elem: '#createTime',
        type: 'date'
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

