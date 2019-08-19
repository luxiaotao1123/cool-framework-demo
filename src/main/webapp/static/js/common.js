// 详情窗口-高度
var detailHeight = '90%';
// 详情窗口-宽度
var detailWidth = '90%';


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
    var str = y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s;
    return str;
}