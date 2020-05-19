var pageCurr;
layui.use(['table','laydate', 'form'], function(){
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;
    var layDate = layui.laydate;
    var form = layui.form;

    // 数据渲染
    tableIns = table.render({
        elem: '#wrkMast',
        headers: {token: localStorage.getItem('token')},
        url: '/wrkMast/list/auth',
        page: true,
        limit: 5,
        even: true,
        toolbar: '#toolbar',
        cellMinWidth: 50,
        cols: [[
            {type: 'checkbox'}
            ,{field: 'wrkNo', align: 'center',title: '工作号',width:80}
            ,{field: 'ioTime$', align: 'center',title: '工作时间',width:90}
            ,{field: 'wrkSts$', align: 'center',title: '工作状态',event: 'wrkSts', style: 'cursor:pointer',width:120}
            ,{field: 'ioType$', align: 'center',title: '入出库类型',event: 'ioType', style: 'cursor:pointer'}
            ,{field: 'ioPri', align: 'center',title: '优先级',width:80}
            ,{field: 'crnNo$', align: 'center',title: '堆垛机',event: 'crnNo', style: 'cursor:pointer'}
            ,{field: 'sourceStaNo$', align: 'center',title: '源站',event: 'sourceStaNo', style: 'cursor:pointer',width:60}
            ,{field: 'staNo$', align: 'center',title: '目标站',event: 'staNo', style: 'cursor:pointer',width:80}
            ,{field: 'sourceLocNo$', align: 'center',title: '源库位',event: 'sourceLocNo', style: 'cursor:pointer'}
            ,{field: 'locNo$', align: 'center',title: '目标库位',event: 'locNo', style: 'cursor:pointer'}
            ,{field: 'barcode', align: 'center',title: '条码'}
            // ,{field: 'picking', align: 'center',title: '拣料', templet:function(row){
            //         var html = "<input value='picking' type='checkbox' lay-skin='primary' lay-filter='tableCheckbox' table-index='"+row.LAY_TABLE_INDEX+"'";
            //         if(row.picking === 'Y'){html += " checked ";}
            //         html += ">";
            //         return html;
            //     }}
            // ,{field: 'exitMk', align: 'center',title: '退出', templet:function(row){
            //         var html = "<input value='exitMk' type='checkbox' lay-skin='primary' lay-filter='tableCheckbox' table-index='"+row.LAY_TABLE_INDEX+"'";
            //         if(row.exitMk === 'Y'){html += " checked ";}
            //         html += ">";
            //         return html;
            //     }}
            // ,{field: 'emptyMk', align: 'center',title: '空板', templet:function(row){
            //         var html = "<input value='emptyMk' type='checkbox' lay-skin='primary' lay-filter='tableCheckbox' table-index='"+row.LAY_TABLE_INDEX+"'";
            //         if(row.emptyMk === 'Y'){html += " checked ";}
            //         html += ">";
            //         return html;
            //     }}
            //
            // ,{field: 'crnStrTime$', align: 'center',title: '堆垛机启动时间'}
            // ,{field: 'crnEndTime$', align: 'center',title: '堆垛机停止时间'}
            // ,{field: 'refIotime$', align: 'center',title: '拣料时间'}
            // // ,{field: 'modiUser$', align: 'center',title: '修改人员',event: 'modiUser', style: 'cursor:pointer'}
            // // ,{field: 'modiTime$', align: 'center',title: '修改时间'}
            // ,{field: 'memo', align: 'center',title: '备注'}
            //
            // ,{field: 'fullPlt', align: 'center',title: '满板', templet:function(row){
            //         var html = "<input value='fullPlt' type='checkbox' lay-skin='primary' lay-filter='tableCheckbox' table-index='"+row.LAY_TABLE_INDEX+"'";
            //         if(row.fullPlt === 'Y'){html += " checked ";}
            //         html += ">";
            //         return html;
            //     }}

            ,{fixed: 'right', title:'操作', align: 'center', toolbar: '#operate', width:120}
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

    // 监听头工具栏事件
    table.on('toolbar(wrkMast)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event) {
            case 'addData':
                layer.open({
                    type: 2,
                    title: '新增',
                    maxmin: true,
                    area: [top.detailWidth, top.detailHeight],
                    shadeClose: false,
                    content: 'wrkMast_detail.html',
                    success: function(layero, index){
                        layer.getChildFrame('#data-detail-submit-edit', index).hide();
                    	clearFormVal(layer.getChildFrame('#detail', index));
                        layer.getChildFrame('##dealDownLine', index).hide();layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
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
                            url: "/wrkMast/delete/auth",
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
                        'wrkMast': exportData,
                        'fields': fields
                    };
                    $.ajax({
                        url: "/wrkMast/export/auth",
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
    table.on('tool(wrkMast)', function(obj){
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
                    content: 'wrkMast_detail.html',
                    success: function(layero, index){
                        setFormVal(layer.getChildFrame('#detail', index), data, true);
                        top.convertDisabled(layer.getChildFrame('#data-detail :input', index), true);
                        layer.getChildFrame('#data-detail-submit-save,#data-detail-submit-edit,#prompt', index).hide();
                        layero.find('iframe')[0].contentWindow.layui.form.render('select');
                        layero.find('iframe')[0].contentWindow.layui.form.render('checkbox');
                        layer.getChildFrame('##dealDownLine', index).hide();
                        layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
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
                    content: 'wrkMast_detail.html',
                    success: function(layero, index){
                        layer.getChildFrame('#data-detail-submit-save', index).hide();
                        setFormVal(layer.getChildFrame('#detail', index), data, false);
                        top.convertDisabled(layer.getChildFrame('#data-detail :input', index), false);
                        top.convertDisabled(layer.getChildFrame('#wrkNo', index), true);
                        layero.find('iframe')[0].contentWindow.layui.form.render('select');
                        layero.find('iframe')[0].contentWindow.layui.form.render('checkbox');
                        layer.getChildFrame('##dealDownLine', index).hide();
                        layer.iframeAuto(index);layer.style(index, {top: (($(window).height()-layer.getChildFrame('#data-detail', index).height())/3)+"px"});
                    }
                });
                break;
            case 'wrkSts':
                var param = top.reObject(data).wrkSts;
                if (param === undefined) {
                    layer.msg("无数据");
                } else {
                   layer.open({
                       type: 2,
                       title: '工作详情',
                       maxmin: true,
                       area: [top.detailWidth, top.detailHeight],
                       shadeClose: false,
                       content: '../basWrkStatus/basWrkStatus_detail.html',
                       success: function(layero, index){
                           $.ajax({
                               url: "/basWrkStatus/"+ param +"/auth",
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
            case 'ioType':
                var param = top.reObject(data).ioType;
                if (param === undefined) {
                    layer.msg("无数据");
                } else {
                   layer.open({
                       type: 2,
                       title: '入出库详情',
                       maxmin: true,
                       area: [top.detailWidth, top.detailHeight],
                       shadeClose: false,
                       content: '../basWrkIotype/basWrkIotype_detail.html',
                       success: function(layero, index){
                           $.ajax({
                               url: "/basWrkIotype/"+ param +"/auth",
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
            case 'crnNo':
                var param = top.reObject(data).crnNo;
                if (param === undefined) {
                    layer.msg("无数据");
                } else {
                   layer.open({
                       type: 2,
                       title: '堆详情',
                       maxmin: true,
                       area: [top.detailWidth, top.detailHeight],
                       shadeClose: false,
                       content: '../basCrnp/basCrnp_detail.html',
                       success: function(layero, index){
                           $.ajax({
                               url: "/basCrnp/"+ param +"/auth",
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
            case 'locNo':
                var param = top.reObject(data).locNo;
                if (param === undefined) {
                    layer.msg("无数据");
                } else {
                   layer.open({
                       type: 2,
                       title: '目标详情',
                       maxmin: true,
                       area: [top.detailWidth, top.detailHeight],
                       shadeClose: false,
                       content: '../locMast/locMast_detail.html',
                       success: function(layero, index){
                           $.ajax({
                               url: "/locMast/"+ param +"/auth",
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
            case 'staNo':
                var param = top.reObject(data).staNo;
                if (param === undefined) {
                    layer.msg("无数据");
                } else {
                   layer.open({
                       type: 2,
                       title: '目详情',
                       maxmin: true,
                       area: [top.detailWidth, top.detailHeight],
                       shadeClose: false,
                       content: '../basDevp/basDevp_detail.html',
                       success: function(layero, index){
                           $.ajax({
                               url: "/basDevp/"+ param +"/auth",
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
            case 'sourceStaNo':
                var param = top.reObject(data).sourceStaNo;
                if (param === undefined) {
                    layer.msg("无数据");
                } else {
                   layer.open({
                       type: 2,
                       title: '详情',
                       maxmin: true,
                       area: [top.detailWidth, top.detailHeight],
                       shadeClose: false,
                       content: '../basDevp/basDevp_detail.html',
                       success: function(layero, index){
                           $.ajax({
                               url: "/basDevp/"+ param +"/auth",
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
            case 'sourceLocNo':
                var param = top.reObject(data).sourceLocNo;
                if (param === undefined) {
                    layer.msg("无数据");
                } else {
                   layer.open({
                       type: 2,
                       title: '源详情',
                       maxmin: true,
                       area: [top.detailWidth, top.detailHeight],
                       shadeClose: false,
                       content: '../locMast/locMast_detail.html',
                       success: function(layero, index){
                           $.ajax({
                               url: "/locMast/"+ param +"/auth",
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
            wrkNo: $('#wrkNo').val(),
            invWh: $('#invWh').val(),
            ymd: top.strToDate($('#ymd\\$').val()),
            mk: $('#mk').val(),
            whsType: $('#whsType').val(),
            wrkSts: $('#wrkSts').val(),
            ioType: $('#ioType').val(),
            crnNo: $('#crnNo').val(),
            sheetNo: $('#sheetNo').val(),
            ioPri: $('#ioPri').val(),
            wrkDate: top.strToDate($('#wrkDate\\$').val()),
            locNo: $('#locNo').val(),
            staNo: $('#staNo').val(),
            sourceStaNo: $('#sourceStaNo').val(),
            sourceLocNo: $('#sourceLocNo').val(),
            locSts: $('#locSts').val(),
            picking: $('#picking').val(),
            linkMis: $('#linkMis').val(),
            onlineYn: $('#onlineYn').val(),
            updMk: $('#updMk').val(),
            exitMk: $('#exitMk').val(),
            pltType: $('#pltType').val(),
            emptyMk: $('#emptyMk').val(),
            ioTime: top.strToDate($('#ioTime\\$').val()),
            ctnType: $('#ctnType').val(),
            packed: $('#packed').val(),
            oveMk: $('#oveMk').val(),
            mtnType: $('#mtnType').val(),
            userNo: $('#userNo').val(),
            crnStrTime: top.strToDate($('#crnStrTime\\$').val()),
            crnEndTime: top.strToDate($('#crnEndTime\\$').val()),
            plcStrTime: top.strToDate($('#plcStrTime\\$').val()),
            crnPosTime: top.strToDate($('#crnPosTime\\$').val()),
            loadTime: $('#loadTime').val(),
            expTime: $('#expTime').val(),
            refWrkno: $('#refWrkno').val(),
            refIotime: top.strToDate($('#refIotime\\$').val()),
            modiUser: $('#modiUser').val(),
            modiTime: top.strToDate($('#modiTime\\$').val()),
            appeUser: $('#appeUser').val(),
            appeTime: top.strToDate($('#appeTime\\$').val()),
            pauseMk: $('#pauseMk').val(),
            errorTime: top.strToDate($('#errorTime\\$').val()),
            errorMemo: $('#errorMemo').val(),
            ctnKind: $('#ctnKind').val(),
            manuType: $('#manuType').val(),
            memo: $('#memo').val(),
            scWeight: $('#scWeight').val(),
            logMk: $('#logMk').val(),
            logErrTime: top.strToDate($('#logErrTime\\$').val()),
            logErrMemo: $('#logErrMemo').val(),
            barcode: $('#barcode').val(),
            PdcType: $('#PdcType').val(),
            ctnNo: $('#ctnNo').val(),
            fullPlt: $('#fullPlt').val(),

        };
        $.ajax({
            url: "/wrkMast/"+name+"/auth",
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
        elem: '#ymd\\$',
        type: 'datetime'
    });
    layDate.render({
        elem: '#wrkDate\\$',
        type: 'datetime'
    });
    layDate.render({
        elem: '#ioTime\\$',
        type: 'datetime'
    });
    layDate.render({
        elem: '#crnStrTime\\$',
        type: 'datetime'
    });
    layDate.render({
        elem: '#crnEndTime\\$',
        type: 'datetime'
    });
    layDate.render({
        elem: '#plcStrTime\\$',
        type: 'datetime'
    });
    layDate.render({
        elem: '#crnPosTime\\$',
        type: 'datetime'
    });
    layDate.render({
        elem: '#refIotime\\$',
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
    layDate.render({
        elem: '#logErrTime\\$',
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
