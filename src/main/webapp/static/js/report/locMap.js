layui.use(['table','laydate', 'form'], function(){
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form;

    getLocTable(1);

    function getLocTable(row){
        $.ajax({
            url: "/report/viewLocMapList.action",
            headers: {'token': localStorage.getItem('token')},
            data: {row: row},
            method: 'POST',
            success: function (res) {
                if (res.code === 200) {
                    var tpl = $("#locMapTemplate").html();
                    var template = Handlebars.compile(tpl);
                    var html = template(res.data);
                    $('#locMap').html(html);
                } else if (res.code === 403) {
                    top.location.href = "/";
                } else {
                    layer.msg(res.msg)
                }
            }
        });
    }

    form.on('select(row)', function (data) {
        getLocTable(data.value);
    });
});

var locNo = '';
function locDetl(el) {
    var value = $(el).attr('title');
    var html = $(el).html();
    if (value===null
        ||value === undefined
        || value.trim()===''
        || html.trim()==='S'
        || html.trim()==='D'
        || html.trim()==='O'
    ){

    } else {
        layer.open({
            type: 2,
            title: '库位物料',
            maxmin: true,
            area: [top.detailWidth, top.detailHeight],
            shadeClose: false,
            content: 'locDetl.html',
            success: function(layero, index){
                locNo = value;
            }
        });
    }
}

