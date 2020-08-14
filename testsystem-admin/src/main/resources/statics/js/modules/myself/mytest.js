$(function () {
    $("#show").show();
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/systest/list2',
        datatype: "json",
        colModel: [
            {label: 'id', sortable: false, name: 'testId', index: 'test_id', width: 80, key: true},
            {label: '名称', sortable: false, name: 'name', index: 'name', width: 180},
            {label: '答题时间', sortable: false, name: 'times', index: 'times', width: 100},
            {label: '总分', sortable: false, name: 'price', index: 'price', width: 100},
            {label: '通过分数', sortable: false, name: 'inprice', index: 'inprice', width: 100},
            {label: '开考时间', sortable: false, name: 'startTime', index: 'start_time', width: 280},
            {label: '结束时间', sortable: false, name: 'endTime', index: 'end_time', width: 280},
            {
                label: '状态', sortable: false, name: 'status', width: 100, formatter: function (value, options, row) {
                    return value === 0 ?
                        '<span class="label label-danger">已开考</span>' :
                        '<span class="label label-success">未开考</span>';
                }
            }
        ],
        viewrecords: true,
        height: 330,
        rowNum: 9,
        rowList: [9, 27, 54, 81],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        postData: {
            "type": "考试"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
    setStyWidth();
});


var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    }
};

var num2 = 0;
var index = 0;
var num22 = 0;
var maps = [];
var flag = true;
var xx = 0;

var yizuo = 0;
var weizuo = 0;

$(document).on("click", "#jiancha", function () {
    vm.showList = true;
    vm.showList2 = false;
    setTimeout(function () {
        $("#show").hide();
    }, 10);
    $("#" + index).hide();
});

$(document).on("click", "#tijiao", function () {
    vm.showList = true;
    vm.showList2 = true;
    setTimeout(function () {
        $("#show").show();
    }, 10);
    vm.up2(index);
});

function create(num) {
    for (var i = 0; i < num; i++) {
        $(document).on("click", "#x" + i, function () {
            vm.showList = false;
            vm.showList2 = true;
            setTimeout(function () {
                $("#show").hide();
            }, 10);
            index = this.id.split("x")[1];
            if (index == num2 - 1) {
                $("#bt1").hide();
                $("#bt2").show();
                $("#bt3").hide();
            }
            if (index == 0) {
                $("#bt1").hide();
                $("#bt2").hide();
                $("#bt3").show();
            }
            $("#" + index).show();
        });
    }
}

var orders = 0;

function timeStamp(StatusMinute) {
    var day = parseInt(StatusMinute / 60 / 24);
    var hour = parseInt(StatusMinute / 60 % 24);
    var min = parseInt(StatusMinute % 60);
    var StatusMinute2 = "";
    if (day > 0) {
        StatusMinute2 = day + "天";
    }
    if (hour > 0) {
        StatusMinute2 += hour + "小时";
    }
    if (min > 0) {
        StatusMinute2 += parseFloat(min) + "分钟";
    }
    return StatusMinute2;
}

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        showList2: true,
        title: null,
        sysTest: {},
        q: {
            name: null
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        info: function () {
            var testId = getSelectedRow();
            if (testId == null) {
                return;
            }
            window.localStorage.setItem("testId", testId);
            $.get(baseURL + "sys/systest/titles2/" + testId, function (r) {
                if (r.code != 0) {
                    alert(r.msg);
                    return;
                } else {
                    vm.showList2 = true;
                    vm.showList = false;
                    $("#show").hide();
                    index = 0;
                    num2 = 0;
                    num22 = 0;
                    yizuo = 0;
                    weizuo = r.num;
                    maps = [];

                    vm.clear();
                    window.localStorage.setItem("testId", r.test.testId);

                    var times = r.test.times * 60;
                    var times2 = new Date().getTime();

                    // var endTime = r.test.endTime;
                    // var endTimes = new Date(endTime).getTime();
                    var endTime = r.test.endTime.toString();
                    var endTimes = new Date(Date.parse(endTime.replace(/-/g, "/"))).getTime();

                    var time;
                    var number = times2 + times;
                    if (number <= endTimes) {
                        time = times * 1000;
                    } else {
                        time = endTimes - times2 * 1000;
                    }
                    orders = r.orders;
                    vm.pinjie(r.subjects, r.test, r.size, r.counts, r.jungles, Math.round(time / 1000));
                    vm.pinjie2(r.subjects, r.test, r.size, r.counts, Math.round(time / 1000));
                    $("#" + index).show();
                }
            });
        },
        location: function (type, sub) {
            var subjectId = window.localStorage.getItem(type);
            if (subjectId == undefined || subjectId.length <= 0) {
                window.localStorage.setItem(type, sub.subjectId);
            } else {
                window.localStorage.setItem(type, subjectId + "," + sub.subjectId);
            }
        },
        clear: function () {
            window.localStorage.removeItem("text");
            window.localStorage.removeItem("radio");
            window.localStorage.removeItem("checkbox");
            window.localStorage.removeItem("testId");
        },
        up: function (index) {
            var item = maps[index];
            var sysAnswerEntity = {
                "testId": window.localStorage.getItem("testId"),
                "subjectId": item.id,
                "answerId": item.id2,
                "orders": orders,
                "content": null
            };
            var name = item.value;
            var content = "";
            if (name.indexOf("单") != -1 || name.indexOf("判断") != -1) {
                content = $("input[name='" + item.id + "']:checked").val();
                sysAnswerEntity.content = content;
            } else if (name.indexOf("多") != -1) {
                $('input[name="' + item.id + '"]:checked').each(function () {
                    content += $(this).val() + ',';
                });
                sysAnswerEntity.content = content;
            } else {
                content = $("#" + item.id).val();
                sysAnswerEntity.content = content;
            }

            $.ajax({
                type: "POST",
                url: baseURL + "sys/sysanswer/save",
                contentType: "application/json",
                data: JSON.stringify(sysAnswerEntity),
                success: function (r) {
                    if (r.code == 0) {
                        item.id2 = r.id;
                        if (r.type == 1) {
                            if (r.typet == 2) {
                                if (content == "" || content == undefined) {
                                    $("#x" + index).attr("class", "btn");
                                } else {
                                    yizuo++;
                                    if (weizuo != 0) {
                                        weizuo--;
                                    }
                                    $("#yizuo").html(yizuo + "道");
                                    $("#weizuo").html(weizuo + "道");
                                    $("#x" + index).attr("class", "btn btn-primary");
                                }
                            } else {
                                if (content == "" || content == undefined) {
                                    if (yizuo != 0) {
                                        yizuo--;
                                    }
                                    weizuo++;
                                    $("#x" + index).attr("class", "btn");
                                    $("#yizuo").html(yizuo + "道");
                                    $("#weizuo").html(weizuo + "道");
                                } else {
                                    $("#x" + index).attr("class", "btn btn-primary");
                                }
                            }
                        } else {
                            if (content == "" || content == undefined) {
                                $("#x" + index).attr("class", "btn");
                            } else {
                                if (r.typet3 == 1) {
                                    yizuo++;
                                    if (weizuo != 0) {
                                        weizuo--;
                                    }
                                    $("#yizuo").html(yizuo + "道");
                                    $("#weizuo").html(weizuo + "道");
                                }
                                $("#x" + index).attr("class", "btn btn-primary");
                            }
                        }
                    }
                }
            });
        },
        up2: function (index) {
            debugger;
            var item = maps[index];
            if (item != undefined) {
                var sysAnswerEntity = {
                    "testId": window.localStorage.getItem("testId"),
                    "subjectId": item.id,
                    "answerId": item.id2,
                    "orders": orders,
                    "content": null
                };
                var name = item.value;
                var content = "";
                if (name.indexOf("单") != -1 || name.indexOf("判断") != -1) {
                    content = $("input[name='" + item.id + "']:checked").val();
                    sysAnswerEntity.content = content;
                } else if (name.indexOf("多") != -1) {
                    $('input[name="' + item.id + '"]:checked').each(function () {
                        content += $(this).val() + ',';
                    });
                    sysAnswerEntity.content = content;
                } else {
                    content = $("#" + item.id).val();
                    sysAnswerEntity.content = content;
                }

                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/sysanswer/save2",
                    contentType: "application/json",
                    data: JSON.stringify(sysAnswerEntity),
                    success: function (r) {
                        if (r.code == 0) {
                            layer.msg('交卷成功!');
                        }
                    }
                });
            }
        },
        next: function () {
            if (index == num2 - 1) {
                return;
            } else {
                $("#" + index++).hide();
                $("#" + index).show();
                if ((index + 1) != num2) {
                    $("#bt1").show();
                    $("#bt2").hide();
                    $("#bt3").hide();
                } else {
                    $("#bt1").hide();
                    $("#bt2").show();
                    $("#bt3").hide();
                }
                vm.up(index - 1);
            }
        },
        on: function () {
            $("#" + index--).hide();
            $("#" + index).show();
            if (index != 0) {
                $("#bt1").show();
                $("#bt2").hide();
                $("#bt3").hide();
            } else {
                $("#bt1").hide();
                $("#bt2").hide();
                $("#bt3").show();
            }
            vm.up(index + 1);
        },
        fanhui: function () {
            vm.showList = false;
            vm.showList2 = true;
            $("#show").hide();
            $("#" + index).show();
        },
        pinjie: function (subjects, test, size, counts, jungles, time) {
            flag = true;
            $(".padding15").empty();
            var times = 0;
            getRandomCode(time);

            //倒计时
            function getRandomCode(time) {
                if (time > 0) {
                    times = time--;
                    $("#times").html(formatSeconds(times));
                    setTimeout(function () {
                        getRandomCode(time);
                    }, 1000);
                } else {
                    if (flag) {
                        vm.up2(index);
                        vm.showList = true;
                        vm.reload();
                        flag = false;
                    }
                }
            }

            var html = " <div class=\"test_paper_top\" ><div class=\"paper_title\">" + test.name + " </div> <div class=\"paper_right\">总共<label>"
                + size + "</label>题共<label>" + test.price + "</label>分<br/>剩余时间：<label id='times'>" + times + "分钟</label> <br/>" +
                "未做题：<label id='weizuo'>" + weizuo + "道</label>&nbsp;已做题：<label id='yizuo'>" + yizuo + "道</label></div> </div>" +
                "<input type=\"button\" class=\"btn btn-warning\" id='tijiao' value=\"提交试卷\"\n" +
                "/>" +
                "<input type=\"button\" class=\"btn btn-warning\" id='jiancha' value=\"检查试题\"\n" +
                "style=\"margin-top: 10px;right: -3%;position: absolute;\"/>";

            if (subjects != null) {
                html += "<div class=\"write_in\">";
                var order = ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十"];
                var num = 0;
                var indext = 0;
                for (var subject in subjects) {
                    var i;
                    var subject2 = subjects[subject];
                    var length = subject2.length;
                    if (subject.indexOf("单") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            var item = {text: null, value: null, id: null, id2: null};
                            item.text = indext;
                            item.value = "单";
                            item.id = sub.subjectId;
                            maps.push(item);
                            indext++;
                            html += " <table style='display: none' id='" + (num2++) + "' class='xx'><tbody><tr> <td  class=\"trmenu\">" + order[num] + "." + subject + "（共" + length + "题,共" + counts[subject] + "分）</td> </tr>";
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td></tr> ";
                            var jungle = jungles[sub.subjectId];
                            var length2 = jungle.length;
                            if (length2 > 0) {
                                var j;
                                for (j = 0; j < length2; j++) {
                                    var obj = jungle[j];
                                    html += "<tr><td><input  type=\"radio\" value='" + obj.name + "' name='" + sub.subjectId + "'><label>" + obj.name + "   " + delHtmlTag(obj.content) + "</label></td></tr>";
                                }
                            }
                            html += "</tbody></table>";
                        }
                    } else if (subject.indexOf("多") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            var item = {text: null, value: null, id: null, id2: null};
                            item.text = indext;
                            item.value = "多";
                            item.id = sub.subjectId;
                            maps.push(item);
                            indext++;
                            html += " <table style='display: none'  class='xx' id='" + (num2++) + "'><tbody><tr> <td  class=\"trmenu\">" + order[num] + "." + subject + "（共" + length + "题,共" + counts[subject] + "分）</td> </tr>";
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td> ";
                            var jungle = jungles[sub.subjectId];
                            var length2 = jungle.length;
                            if (length2 > 0) {
                                var j;
                                for (j = 0; j < length2; j++) {
                                    var obj = jungle[j];
                                    html += "<tr><td><input  type=\"checkbox\"  value='" + obj.name + "' name=\"" + sub.subjectId + "\"><label>" + obj.name + "   " + delHtmlTag(obj.content) + "</label></td></tr>";
                                }
                            }
                            html += "</tbody></table>";
                        }
                    } else if (subject.indexOf("填空") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            var item = {text: null, value: null, id: null, id2: null};
                            item.text = indext;
                            item.value = "其他";
                            item.id = sub.subjectId;
                            maps.push(item);
                            indext++;
                            html += " <table style='display: none'  class='xx' id='" + (num2++) + "'><tbody><tr> <td  class=\"trmenu\">" + order[num] + "." + subject + "（共" + length + "题,共" + counts[subject] + "分）</td> </tr>";
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td> ";
                            html += "<tr><td><input type=\"text\" class=\"form-control\"  placeholder=\"请输入答案，不同部分答案用“/”分割\" id=\"" + sub.subjectId + "\"/></td></tr>";
                            html += "</tbody></table>";
                        }
                    } else if (subject.indexOf("判断") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            var item = {text: null, value: null, id: null, id2: null};
                            item.text = indext;
                            item.value = "判断";
                            item.id = sub.subjectId;
                            maps.push(item);
                            indext++;
                            html += " <table style='display: none'  class='xx' id='" + (num2++) + "'><tbody><tr> <td  class=\"trmenu\">" + order[num] + "." + subject + "（共" + length + "题,共" + counts[subject] + "分）</td> </tr>";
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "。（" + sub.price + "分）</td> ";
                            html += "<tr><td><input name='" + sub.subjectId + "'  value='正确' type=\"radio\"><label>正确</label></td> </tr>";
                            html += "<tr><td><input name='" + sub.subjectId + "'  value='错误' type=\"radio\"><label>错误</label></td> </tr>";
                            html += "</tbody></table>";
                        }
                    } else if (subject.indexOf("打字") != -1 || subject.indexOf("问答") != -1 || subject.indexOf("作文") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            var item = {text: null, value: null, id: null, id2: null};
                            item.text = indext;
                            item.value = "其他";
                            item.id = sub.subjectId;
                            maps.push(item);
                            indext++;
                            html += " <table style='display: none'  class='xx' id='" + (num2++) + "'><tbody><tr> <td  class=\"trmenu\">" + order[num] + "." + subject + "（共" + length + "题,共" + counts[subject] + "分）</td> </tr>";
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td> ";
                            html += "<tr><td><input type=\"text\" class=\"form-control\" id=\"" + sub.subjectId + "\" placeholder=\"请输入答案\"  style=\"height: 200px; width: 80%;\"></td></td> </tr> </tbody></table>";
                            html += "</tbody></table>";
                        }
                    } else if (subject.indexOf("操作") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            var item = {text: null, value: null, id: null, id2: null};
                            item.text = indext;
                            item.value = "其他";
                            item.id = sub.subjectId;
                            maps.push(item);
                            indext++;
                            html += " <table style='display: none'  class='xx' id='" + (num2++) + "'><tbody><tr> <td  class=\"trmenu\">" + order[num] + "." + subject + "（共" + length + "题,共" + counts[subject] + "分）</td> </tr>";
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td> ";
                            html += "<tr><td><span class=\"btn-upload\" style=\"margin-right: 20px;\"><a class=\"btn btn-primary\" href=\"" +
                                sub.file + "\">下载题目</a></span></td></tr>";
                            html += "<tr><td><input type=\"text\" class=\"form-control\" placeholder=\"请输入答案\" id=\"" + sub.subjectId + "\" style=\"height: 200px; width: 80%;\"></td></td> </tr> </tbody></table>";
                            html += "</tbody></table>";
                        }
                    }
                    num++;
                }
                html += "</div>";
            }
            $(".padding15").html(html);
        },
        pinjie2: function (subjects, test, size, counts, time) {
            flag = true;
            $(".padding16").empty();
            var times = 0;
            getRandomCode(time);

            //倒计时
            function getRandomCode(time) {
                if (time > 0) {
                    times = time--;
                    $("#times").html(formatSeconds(times));
                    setTimeout(function () {
                        getRandomCode(time);
                    }, 1000);
                } else {
                    if (flag) {
                        vm.up2(index);
                        vm.showList = true;
                        vm.reload();
                        flag = false;
                    }
                }
            }

            var html = " <div class=\"test_paper_top\" ><div class=\"paper_title\">" + test.name + " </div> <div class=\"paper_right\">总共<label>"
                + size + "</label>题共<label>" + test.price + "</label>分<br/>剩余时间：<label id='times'>" + times + "分钟</label> <br/>" +
                "未做题：<label id='weizuo'>" + weizuo + "道</label>&nbsp;已做题：<label id='yizuo'>" + yizuo + "道</label></div> </div>" +
                "<input type=\"button\" class=\"btn btn-warning\" id='tijiao' value=\"提交试卷\"\n" +
                "style=\"margin-top: 10px;right: -3%;position: absolute;\"/>";

            if (subjects != null) {
                html += "<div class=\"write_in2\">";
                var order = ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十"];
                var num = 0;
                var indext = 0;
                for (var subject in subjects) {
                    var i;
                    var subject2 = subjects[subject];
                    var length = subject2.length;
                    html += " <table  id='" + (num22++) + "'><tbody><tr> <td colspan='20' class=\"trmenu2\">" + order[num] + "." + subject + "（共" + length + "题,共" + counts[subject] + "分）</td> </tr>";
                    if (subject.indexOf("单") != -1) {
                        html += "<tr>";
                        if (length < 20) {
                            for (i = 0; i < length; i++) {
                                if ((i + 1) % 20 != 0) {
                                    html += "<td><input type='button' class='btn' value='" + (i + 1) + "' id='x" + indext + "'/></td>";
                                } else {
                                    html += "</tr><tr><td><input type='button' class='btn' value='" + (i + 1) + "' id='x" + indext + "'/></td>";
                                }
                                indext++;
                            }
                            var index2 = 20 - length;
                            for (var j1 = 0; j1 < index2; j1++) {
                                html += "<td><input type='button' class='btn' style='visibility: hidden;'/></td>";
                                if (j1 == index2 - 1) {
                                    html += "</tr>";
                                }
                            }
                        } else {
                            for (i = 0; i < length; i++) {
                                if ((i + 1) % 20 != 0) {
                                    html += "<td><input type='button' class='btn' value='" + (i + 1) + "' id='x" + indext + "'/></td>";
                                } else {
                                    html += "</tr><tr><td><input type='button' class='btn' value='" + (i + 1) + "' id='x" + indext + "'/></td>";
                                }
                                if (i == length - 1) {
                                    html += "</tr>";
                                }
                                indext++;
                            }
                        }
                    } else if (subject.indexOf("多") != -1) {
                        html += "<tr>";
                        for (i = 0; i < length; i++) {
                            if ((i + 1) % 20 != 0) {
                                html += "<td><input type='button' class='btn' value='" + (i + 1) + "' id='x" + indext + "'/></td>";
                            } else {
                                html += "</tr><tr><td><input type='button' class='btn' value='" + (i + 1) + "' id='x" + indext + "'/></td>";
                            }
                            if (i == length - 1) {
                                html += "</tr>";
                            }
                            indext++;
                        }
                    } else if (subject.indexOf("填空") != -1) {
                        html += "<tr>";
                        for (i = 0; i < length; i++) {
                            if ((i + 1) % 20 != 0) {
                                html += "<td><input type='button' class='btn' value='" + (i + 1) + "' id='x" + indext + "'/></td>";
                            } else {
                                html += "</tr><tr><td><input type='button' class='btn' value='" + (i + 1) + "' id='x" + indext + "'/></td>";
                            }
                            if (i == length - 1) {
                                html += "</tr>";
                            }
                            indext++;
                        }
                    } else if (subject.indexOf("判断") != -1) {
                        html += "<tr>";
                        for (i = 0; i < length; i++) {
                            if ((i + 1) % 20 != 0) {
                                html += "<td><input type='button' class='btn' value='" + (i + 1) + "' id='x" + indext + "'/></td>";
                            } else {
                                html += "</tr><tr><td><input type='button' class='btn' value='" + (i + 1) + "' id='x" + indext + "'/></td>";
                            }
                            if (i == length - 1) {
                                html += "</tr>";
                            }
                            indext++;
                        }
                    } else if (subject.indexOf("问答") != -1) {
                        html += "<tr>";
                        for (i = 0; i < length; i++) {
                            if ((i + 1) % 20 != 0) {
                                html += "<td><input type='button' class='btn' value='" + (i + 1) + "' id='x" + indext + "'/></td>";
                            } else {
                                html += "</tr><tr><td><input type='button' class='btn' value='" + (i + 1) + "' id='x" + indext + "'/></td>";
                            }
                            if (i == length - 1) {
                                html += "</tr>";
                            }
                            indext++;
                        }
                    }
                    else if (subject.indexOf("作文") != -1) {
                        html += "<tr>";
                        for (i = 0; i < length; i++) {
                            if ((i + 1) % 20 != 0) {
                                html += "<td><input type='button' class='btn' value='" + (i + 1) + "' id='x" + indext + "'/></td>";
                            } else {
                                html += "</tr><tr><td><input type='button' class='btn' value='" + (i + 1) + "' id='x" + indext + "'/></td>";
                            }
                            if (i == length - 1) {
                                html += "</tr>";
                            }
                            indext++;
                        }
                    }
                    else if (subject.indexOf("打字") != -1) {
                        html += "<tr>";
                        for (i = 0; i < length; i++) {
                            if ((i + 1) % 20 != 0) {
                                html += "<td><input type='button' class='btn' value='" + (i + 1) + "' id='x" + indext + "'/></td>";
                            } else {
                                html += "</tr><tr><td><input type='button' class='btn' value='" + (i + 1) + "' id='x" + indext + "'/></td>";
                            }
                            if (i == length - 1) {
                                html += "</tr>";
                            }
                            indext++;
                        }
                    } else if (subject.indexOf("操作") != -1) {
                        html += "<tr>";
                        for (i = 0; i < length; i++) {
                            if ((i + 1) % 20 != 0) {
                                html += "<td><input type='button' class='btn' value='" + (i + 1) + "' id='x" + indext + "'/></td>";
                            } else {
                                html += "</tr><tr><td><input type='button' class='btn' value='" + (i + 1) + "' id='x" + indext + "'/></td>";
                            }
                            if (i == length - 1) {
                                html += "</tr>";
                            }
                            indext++;
                        }
                    }
                    num++;
                }
                html += "</tbody></table></div>";
                create(indext);
            }
            $(".padding16").html(html);
        },
        reload: function (event) {
            vm.showList = true;
            vm.showList2 = true;
            $("#show").show();
            vm.clear();
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    "name": vm.q.name,
                    "type": "考试"
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});