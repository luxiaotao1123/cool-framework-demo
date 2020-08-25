// 生产
// var baseUrl = "/pms";
// 测试
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

/*************************************************************************************************************/
/********************************************** 物料业务抽象 ***************************************************/
/*************************************************************************************************************/

function arrRemove(arr, key, val) {
    for(let i=arr.length-1; i>=0; i--){
        if(arr[i][key] === val){
            arr.splice(i,1);
        }
    }
}

var matCols = [
    {field: 'matNo', align: 'center',title: '商品编码'}
    ,{field: 'barcode', align: 'center',title: '条形码'}
    ,{field: 'matName', align: 'center',title: '商品名称'}
    ,{field: 'str1', align: 'center',title: '基本单位'}
    ,{field: 'str2', align: 'center',title: '商品类别'}
    ,{field: 'str3', align: 'center',title: '规格型号'}
    ,{field: 'str4', align: 'center',title: '状态', hide: true}
    ,{field: 'str5', align: 'center',title: '助记码'}
    ,{field: 'str6', align: 'center',title: '默认供应商'}
    ,{field: 'str7', align: 'center',title: '默认仓库'}
    // ,{field: 'str8', align: 'center',title: '品牌'}
    // ,{field: 'str9', align: 'center',title: '采购员'}
    // ,{field: 'str10', align: 'center',title: '产地'}
    // ,{field: 'str11', align: 'center',title: '序列号管理'}
    // ,{field: 'str12', align: 'center',title: '批次管理'}
    // ,{field: 'str13', align: 'center',title: '保质期单位'}
    // ,{field: 'str14', align: 'center',title: '保质期管理'}
    // ,{field: 'str15', align: 'center',title: '保质期'}
    // ,{field: 'str16', align: 'center',title: '可销售'}
    ,{field: 'str17', align: 'center',title: '可采购', hide: true}
    ,{field: 'str18', align: 'center',title: '可为子件', hide: true}
    // ,{field: 'str19', align: 'center',title: '可为组件'}
    // ,{field: 'str20', align: 'center',title: '辅助属性管理'}
    ,{field: 'str21', align: 'center',title: '成本计算方法', hide: true}
    ,{field: 'str22', align: 'center',title: '采购单位', hide: true}
    ,{field: 'str23', align: 'center',title: '销售单位', hide: true}
    // ,{field: 'num1', align: 'center',title: '预警天数'}
    // ,{field: 'num2', align: 'center',title: '最低库存数量'}
    // ,{field: 'num3', align: 'center',title: '最高库存数量'}
    // ,{field: 'num4', align: 'center',title: '安全库存'}
    // ,{field: 'num5', align: 'center',title: '税率'}
]

var detlCols = [
    {field: 'matnr', align: 'center',title: '商品编码'}
    ,{field: 'maktx', align: 'center',title: '商品名称'}
    ,{field: 'lgnum', align: 'center',title: '规格'}
    ,{field: 'type', align: 'center',title: '商品类别'}
    // ,{field: 'mnemonic', align: 'center',title: '助记码'}
    // ,{field: 'supplier', align: 'center',title: '供应商'}
    // ,{field: 'warehouse', align: 'center',title: '仓库'}
    // ,{field: 'brand', align: 'center',title: '品牌'}
    ,{field: 'anfme', align: 'center',title: '数量'}
    ,{field: 'altme', align: 'center',title: '单位'}
    ,{field: 'zpallet', align: 'center',title: '托盘条码'}
]

