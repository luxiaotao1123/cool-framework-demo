layui.use(['form', 'tree'], function() {
    var form = layui.form;
    var tree = layui.tree;
    var $ = layui.jquery;
    var layer = layui.layer;

    // 权限分配树形图
    var powerTree = tree.render({
        elem: '#power-tree',
        id: 'powerTree',
        showCheckbox: true,
        data: parent.powerTreeData,
        isJump: true
    });

    loadPower();
    function loadPower(){
        $.ajax({
            url: baseUrl+"/power/"+parent.roleId+"/auth",
            headers: {'token': localStorage.getItem('token')},
            method: 'GET',
            beforeSend: function () {
                layer.load(1, {shade: [0.1,'#fff']});
            },
            success: function (res) {
                if (res.code === 200){
                    tree.setChecked('powerTree', res.data);
                } else if (res.code === 403){
                    top.location.href = baseUrl+"/";
                } else {
                    layer.msg(res.msg)
                }
                layer.closeAll("loading");
            }
        });
    }

    // 数据修改动作
    form.on('submit(save)', function () {
        var param = [];
        var checkData = tree.getChecked('powerTree');
        checkData.map(function (obj) {
            obj.children.map(function (resource) {

                var childrens = [];
                resource.children.map(function (resource) {
                    childrens.push(resource.id);
                });
                var one = {
                    'two': resource.id,
                    'three': childrens
                };
                param.push(one);
            })
        });
        $.ajax({
            url: baseUrl+"/power/auth",
            traditional: true,
            headers: {'token': localStorage.getItem('token')},
            data: {
                'roleId': parent.roleId,
                'powers': JSON.stringify(param)
            },
            method: 'POST',
            success: function (res) {
                if (res.code === 200){
                    parent.layer.closeAll();
                    parent.layer.msg(res.msg);
                } else if (res.code === 403){
                    top.location.href = baseUrl+"/";
                } else {
                    layer.msg(res.msg)
                }
            }
        });
    });
});

// 关闭动作
$(document).on('click','#data-detail-close', function () {
    parent.layer.closeAll();
});