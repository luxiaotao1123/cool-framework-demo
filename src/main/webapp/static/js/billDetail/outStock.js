window.onload = function () {
    var username = localStorage.getItem('username');
    $("#boxing_man_name").html(username);
    $("#order_num").focus();
    $("#mytable").hide();
}

$("#order_num").blur(function () {
    $("#order_num").focus();
});

$("#order_num").keyup(function(event){
    if(event.keyCode==13){
        var url = $("#order_num").val();
        $("#order_num").val("");
        var uuids=  uuid();
        $("#order_num").attr("placeholder",uuids);
        myFunction(url)
    }
});
 function uuid(){
    var S4=function () {
        return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
    };
    return (S4()+S4()+S4()+S4()+S4()+S4()+S4()+S4());
}
function myFunction(url) {
    var boxer = localStorage.getItem('username');
    $.ajax({
        //请求方式
        type: "POST",
        //请求的媒体类型
        //contentType: "application/json;charset=UTF-8",
        //请求地址
        url: "/billDetail/outStock",
        //数据，json字符串
        data: {"url": url,"outStocker":boxer},
        headers: {'token': localStorage.getItem('token')},
        //请求成功
        success: function (result) {
            if (result.code==200) {
                $("#mytable").show();
                $("#name").text(result.data.customer);
                $("#color").text(result.data.color);
                $("#amount").text(result.data.amount);
                $("#createTime").text(result.data.createTime$);
                $("#modelType").text(result.data.modelType);
                $("#seq").text(result.data.seq);
                $("#statistics").text(result.data.count) ;
                $("#boxNumber").text(result.data.boxNumber) ;
                $("#outStockTime").text(result.data.boxingTime) ;
                setTimeout(function () {
                    $("#order_num").attr("placeholder","出库提交成功！");
                },1000);
            }else
            {
                alert(result.msg)
            }
        }
    });
}




