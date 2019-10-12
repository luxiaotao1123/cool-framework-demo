// 详情窗口-高度
var detailHeight = '90%';
// 详情窗口-宽度
var detailWidth = '90%';

// 非空判断
function isEmpty(obj){
    return typeof obj == "undefined" || obj == null || obj === "";
}

// 时间 ==>> 字符串
function dateToStr(date) {
    var time = new Date(date);
    var y = time.getFullYear();
    var M = time.getMonth() + 1;
    M = M < 10 ? ("0" + M) : M;
    var d = time.getDate();
    d = d < 10 ? ("0" + d) : d;
    var h = time.getHours();
    h = h < 10 ? ("0" + h) : h;
    var m = time.getMinutes();
    m = m < 10 ? ("0" + m) : m;
    var s = time.getSeconds();
    s = s < 10 ? ("0" + s) : s;
    return y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s;
}

// 字符串 ===>> 时间
function strToDate(str) {
    var t = Date.parse(str);
    if (!isNaN(t)) {
        return new Date(Date.parse(str.replace(/-/g, "/")));
    } else {
        return new Date();
    }
}

/**
 * disabled 属性转换
 */
function convertDisabled(el, param) {
    el.each(function () {
        $(this).attr("disabled", param);
    });
}

/**
 * 搜索自动补全 -- input
 */
function autoLoad(val) {
    var inputDomVal = document.querySelector("input[data-key="+val+"]").value;
    var selectDom = document.querySelector("select[data-key="+val+"Select]");
    selectDom.length = 0;
    selectDom.style.display='none';
    if (isEmpty(inputDomVal)){
        return;
    }
    $.ajax({
        url: "/"+val+"/auth",
        headers: {'token': localStorage.getItem('token')},
        data: {condition: inputDomVal},
        method: 'POST',
        traditional:true,
        success: function (res) {
            if (res.code === 200){
                var list = res.data;
                for (var i=0;i<list.length;i++){
                    var option = new Option(list[i].value+"#"+list[i].id, i);
                    option.title = list[i].id;
                    selectDom.options[i] = option;
                }
                if(list.length>0){
                    selectDom.style.display='block';
                }else{
                    selectDom.style.display='none';
                }
            } else if (res.code === 403){
                top.location.href = "/";
            } else {
                layer.msg(res.msg)
            }
        }
    });
}

/**
 * 搜索自动补全 -- select
 */
function confirmed(val){
    var inputDom = $("input[data-key="+val.substring(0,val.length - 6)+"]");
    var selectDom = $("select[data-key="+val+"]");

    selectDom.dblclick(function(){
        inputDom.focus();
        selectDom.css("display","none");
    });
    inputDom.val($("option:selected").attr("title"));
}
