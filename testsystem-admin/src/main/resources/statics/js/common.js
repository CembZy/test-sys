//jqGrid的配置信息
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';
var baseURL = "../../";

//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
};
T.p = url;

//全局配置
$.ajaxSetup({
    dataType: "json",
    cache: false
});

//重写alert
window.alert = function (msg, callback) {
    parent.layer.alert(msg, function (index) {
        parent.layer.close(index);
        if (typeof(callback) === "function") {
            callback("ok");
        }
    });
}

//重写confirm式样框
window.confirm = function (msg, callback) {
    parent.layer.confirm(msg, {btn: ['确定', '取消']},
        function () {//确定事件
            if (typeof(callback) === "function") {
                callback("ok");
            }
        });
}

//选择一条记录
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }

    var selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        alert("只能选择一条记录");
        return;
    }

    return selectedIDs[0];
}

//选择多条记录
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }

    return grid.getGridParam("selarrrow");
}

function getSelectedRows2() {
    var grid = $("#jqGrid2");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }

    return grid.getGridParam("selarrrow");
}

//判断是否为空
function isBlank(value) {
    return !value || !/\S/.test(value)
}

//获取url参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

Array.prototype.indexOf = function (val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};
Array.prototype.remove = function (val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};

Array.prototype.removeByIndex = function (val) {
    if (val > -1) {
        this.splice(val, 1);
    }
};


//"<p>321<img src="/testSystem/file/upload/image/20191117/afe4990689814803936ac97dbf18641e.png" style="max-width: 100%;">dwqdwqdwq
// <img src="/testSystem/file/upload/image/20191117/0ddafbc5325b4c62823e740efe2e0e76.png" style="max-width: 100%;"></p>"
function delHtmlTag(str) {
    if (str.indexOf("img") != -1) {
        str = str.replace(/<(?!img).*?>/g, "");
        str = str.replace(/style="(.*?)"/g, " style='width:150px;height:120px;' ");
        return str;
    } else {
        return str.replace(/<[^>]+>/g, "");
    }
}

// function delHtmlTag(str) {
//     if (str.indexOf("img") != -1) {
//         debugger;
//         var s1 = str.split("<img")[1];
//         var s2 = s1.split(">")[0];
//         var string = s2.split('style="')[1];
//         var string1 = s2.split('style="')[0];
//         var s3 = "width:150px;height:120px;" + string;
//         var s4 = string1 + 'style="' + s3;
//
//         //img
//         var ss = "<img" + s4 + ">";
//         //img
//         var ss1 = "<img" + s2 + ">";
//         var str1 = str.split(ss)[0] + "</p>";
//         var str2 = "<p>" + str.split(ss1)[1];
//         return str1.replace(/<[^>]+>/g, "") + ss + str2.replace(/<[^>]+>/g, "");
//     } else {
//         return str.replace(/<[^>]+>/g, "");
//     }
// }


function pares2(str) {
    if (str == undefined || str == "") {
        return "无。";//去掉所有的html标记
    } else {
        if (str.indexOf("img") != -1) {
            str = str.replace(/<(?!img).*?>/g, "");
            str = str.replace(/style="(.*?)"/g, " style='width:150px;height:120px;' ");
            return str;
        } else {
            return str.replace(/<[^>]+>/g, "");
        }
    }
}


function setStyWidth() {
    var num = $("html").width() - 17;
    var grid = 'jqGrid';
    $('#gview_' + grid).css({'width': num});
    $('#jqGridPager').css({'width': num});
    $('#gview_' + grid + '>.ui-jqgrid-hdiv,#gview_' + grid + '>.ui-jqgrid-bdiv,#gbox_' + grid).css({'width': num});
}


function setStyWidth2() {
    var num = $("html").width() - 17;
    var grid = 'jqGrid2';
    $('#gview_' + grid).css({'width': num});
    $('#jqGridPager2').css({'width': num});
    $('.ui-jqgrid-htable').css({'width': num});
    $('#jqGrid2').css({'width': num});
    $('#gview_' + grid + '>.ui-jqgrid-hdiv,#gview_' + grid + '>.ui-jqgrid-bdiv,#gbox_' + grid).css({'width': num});
}

function sleep(n) { //n表示的毫秒数
    var start = new Date().getTime();
    while (true) if (new Date().getTime() - start > n) break;
}

function tishi(adminsList, str) {
    if (adminsList == undefined || adminsList == null || adminsList == "") {
        alert(str);
        return;
    }
}


var toHHmmss = function (data) {
    var s;
    var hours = parseInt((data % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    var minutes = parseInt((data % (1000 * 60 * 60)) / (1000 * 60));
    var seconds = (data % (1000 * 60)) / 1000;
    var newVar1 = (hours < 10 ? ('0' + hours) : hours);
    var newVar2 = (minutes < 10 ? ('0' + minutes) : minutes);
    var newVar3 = (seconds < 10 ? ('0' + seconds) : seconds);
    if (newVar1 > 1) {
        if (newVar2 > 0) {
            if (newVar3 > 0) {
                s = newVar + '时' + newVar + '分' + newVar + "秒";
            }
        }
    } else {
        if (newVar2 > 0) {
            if (newVar3 > 0) {
                s = newVar + '分' + newVar + "秒";
            }
        } else {
            if (newVar3 > 0) {
                s = newVar + "秒";
            }
        }
    }

    return s;
};

var MODECONTENT = {
    toHHmmss: toHHmmss,//毫秒整数转化为时分秒（例如：00:30:00）
}

/**
 * 格式化秒
 * @param int  value 总秒数
 * @return string result 格式化后的字符串
 */
function formatSeconds(value) {
    var theTime = parseInt(value);// 需要转换的时间秒
    var theTime1 = 0;// 分
    var theTime2 = 0;// 小时
    if (theTime > 60) {
        theTime1 = parseInt(theTime / 60);
        theTime = parseInt(theTime % 60);
        if (theTime1 > 60) {
            theTime2 = parseInt(theTime1 / 60);
            theTime1 = parseInt(theTime1 % 60);
            if (theTime2 > 24) {
                //大于24小时
                theTime2 = parseInt(theTime2 % 24);
            }
        }
    }
    var result = '';
    if (theTime > 0) {
        result = "" + parseInt(theTime) + "秒";
    }
    if (theTime1 > 0) {
        result = "" + parseInt(theTime1) + "分" + result;
    }
    if (theTime2 > 0) {
        result = "" + parseInt(theTime2) + "时" + result;
    }
    return result;
}

function shows(type) {
    debugger;
    if (type == 1) {
        $("#rrapp").css("overflow", "scroll");
    } else {
        $("#rrapp").css("overflow", "hidden");
        $("html").css("overflow", "hidden");
    }
}
