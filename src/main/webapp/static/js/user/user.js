var pageCurr;
var tableIdx;
layui.use('table', function(){
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;

    // 数据渲染
    tableIdx = table.render({
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
            ,{fixed: 'right', title:'操作', align: 'center', toolbar: '#operate', width:190}
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
            $("[data-field='status']").children().each(function(){
                if($(this).text()==='1'){
                    $(this).text("有效")
                }else if($(this).text()==='3'){
                    $(this).text("失效")
                }
            });
            pageCurr=curr;
        }
    });

    //监听行工具事件
    table.on('tool(user)', function(obj){
        var data = obj.data;
        // console.log(obj);
        // 查看
        if(obj.event === 'detail'){
            layer.msg('ID：'+ data.id + ' 的查看操作');
        }
        // 删除
        else if(obj.event === 'del'){
            layer.confirm('确定删除 '+data.id+' 吗', function(index){
                obj.del();
                layer.close(index);
            });
        }
        // 编辑
        else if(obj.event === 'edit'){
            layer.prompt({
                formType: 2
                ,value: data.email
            }, function(value, index){
                obj.update({
                    email: value
                });
                layer.close(index);
            });
        }
    });

    // 新增
    $('#btn-add').on('click', function () {
        layer.open({
            type: 2,
            title: '新增',
            maxmin: true,
            area: ['420px', '330px'],
            shadeClose: false,
            content: '/user_add'
        });
    });

    // 刷新
    $('#btn-refresh').on('click', function () {
        tableIdx.reload({
            page: {
                curr: pageCurr
            }
        });
    })
});

layui.config({
    base: '/static/js/'
}).use(['form', 'layer', 'store'],function () {
    var form = layui.form;
    var layer = layui.layer;
    var $ = layui.jquery;
    var store = layui.store;

    form.on('submit(add)', function () {
        var user = {
            username: $('#username').val(),
            mobile: $('#mobile').val(),
            password: $('#password').val(),
            status: $('#status').val()
        };
        $.ajax({
            url: store.uri + "/user/add/auth",
            data: user,
            method: 'POST',
            success: function (res) {
                if (res.code === 200){
                    parent.layer.close(parent.layer.getFrameIndex(window.name));
                    console.log(pageCurr);
                    // window.parent.location.reload();
                    // tableIdx.reload({
                    //     page: {
                    //         curr: pageCurr
                    //     }
                    // });
                } else {
                    layer.alert(res.msg)
                }
            }
        })
    });
});