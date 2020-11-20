
var baseUrl = "";

// 详情窗口-高度
var detailHeight = '80%';
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
        return null;
    }
}

// 清理对象null值
function reObject(data) {
    for (var obj in data) {
        if (data[obj]===null){
            delete data[obj];
        }
    }
    return data;
}

/**
 * disabled 属性转换
 */
function convertDisabled(el, param) {
    el.each(function () {
        $(this).attr("disabled", param);
    });
}

// 权限
function limit(child){
    if (child == null){
        child = false;
    }
    var param = (child?parent.window:window).location.href.split("?")[1];
    if (null != param) {
        var resourceId = param.split("=")[1];
        $.ajax({
            url: baseUrl+"/power/menu/"+resourceId+"/auth",
            headers: {'token': localStorage.getItem('token')},
            method: 'GET',
            async: false,
            success: function (res) {
                if (res.code === 200){
                    for(var i = 0, len = res.data.length; i < len; i++) {
                        (child?parent:window).$('#'+res.data[i].code).css("display", "inline-block");
                        (child?parent:window).$('.'+res.data[i].code).css("display", "inline-block");
                    }
                } else if (res.code === 403){
                    top.location.href = baseUrl+"/";
                } else {
                    layer.msg(res.msg)
                }
            }
        });
    }
}


// http请求
!function (n) {
    "use strict";

    var http = {
        toAjax: function (params) {
            $.ajax(params);
        },
        get: function (url, data, callback) {
            http.toAjax({
                method: 'GET',
                url: url,
                data: data,
                dataType: 'json',
                header: {'token': localStorage.getItem('token')},
                timeout: 10000,
                cache: false,
                success: function (res) {
                    if (res.code === 200){
                        callback(res);
                    } else if (res.code === 403){
                        top.location.href = baseUrl+"/";
                    }  else {
                        layer.msg(res.msg);
                    }
                },
                error: function (res, type) {

                }
            })
        },
        // 默认表单
        post: function (url, param, callback, type) {
            var headerType;
            if (type === 'json') {
                headerType = {'Content-Type': 'application/json'}
            } else {
                headerType = {'Content-Type': 'application/x-www-form-urlencoded'}
            }
            headerType['token'] = localStorage.getItem('token');
            http.toAjax({
                method: 'POST',
                url: url,
                data: param,
                dataType: 'json',
                headers: headerType,
                timeout: 10000,
                cache: false,
                success: function (res) {
                    if (res.code === 200){
                        callback(res);
                    } else if (res.code === 403){
                        top.location.href = baseUrl+"/";
                    }  else {
                        layer.msg(res.msg);
                    }
                },
                error: function (res, type) {

                }
            })
        },
    };
    "function" == typeof define && define.amd ? define(function () {
        return http
    }) : "object" == typeof module && module.exports ? module.exports = http : n.http = http
}(this);


// 全屏 -----------------------------------------------------------------------

//开始全屏
function full() {
    var docElm = document.documentElement;
    //W3C
    if (docElm.requestFullscreen) {
        docElm.requestFullscreen();
    }
    //FireFox
    else if (docElm.mozRequestFullScreen) {
        docElm.mozRequestFullScreen();
    }
    //Chrome等
    else if (docElm.webkitRequestFullScreen) {
        docElm.webkitRequestFullScreen();
    }
    //IE11
    else if (elem.msRequestFullscreen) {
        elem.msRequestFullscreen();
    }
}

//退出全屏
function exitFull() {
    if (document.exitFullscreen) {
        document.exitFullscreen();
    }
    else if (document.mozCancelFullScreen) {
        document.mozCancelFullScreen();
    }
    else if (document.webkitCancelFullScreen) {
        document.webkitCancelFullScreen();
    }
    else if (document.msExitFullscreen) {
        document.msExitFullscreen();
    }
}

//事件监听
document.addEventListener("fullscreenchange", function () {
    try {
        fullscreenState.innerHTML = (document.fullscreen) ? "" : "not ";
    } catch (e) {}
}, false);
document.addEventListener("mozfullscreenchange", function () {
    fullscreenState.innerHTML = (document.mozFullScreen) ? "" : "not ";
}, false);
document.addEventListener("webkitfullscreenchange", function () {
    fullscreenState.innerHTML = (document.webkitIsFullScreen) ? "" : "not ";
}, false);
document.addEventListener("msfullscreenchange", function () {
    fullscreenState.innerHTML = (document.msFullscreenElement) ? "" : "not ";
}, false);
document.onkeyup = function (e) {
    if (window.event)//如果window.event对象存在，就以此事件对象为准
        e = window.event;
    var key = e.charCode || e.keyCode;
    if (key === 13 || key === 49 || key === 97) {
        full();
    } else if (key === 50 || key === 98) {
        exitFull();
    }
}
