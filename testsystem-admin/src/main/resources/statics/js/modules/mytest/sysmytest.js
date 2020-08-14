$(function () {
    $("#show").show();
    vm.showList = true;
    vm.showList2 = false;
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/sysmytest/list',
        datatype: "json",
        colModel: [
            {label: 'id', sortable: false, name: 'mytestId', index: 'mytest_id', width: 80, key: true},
            {label: '试卷名称', sortable: false, name: 'testName', index: 'test_id', width: 250},
            {label: '参考人数', sortable: false, name: 'num1', index: 'num1', width: 100},
            {label: '平均得分', sortable: false, name: 'price1', index: 'price1', width: 100}
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
var flag = false;

function show2(mytestId) {
    flag = true;
    vm.getDept();
    vm.showList = true;
    $("#show").hide();
    $("#ss").hide();
    $("#jqGrid2").jqGrid({
        url: baseURL + 'sys/sysmytest/list3',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'mytestId', index: 'mytest_id', width: 80, key: true},
            {label: '答题人', name: 'userName', index: 'user_id', width: 100},
            {label: '试卷名称', name: 'testName', index: 'test_id', width: 150},
            {label: '类型', name: 'type', index: 'type', width: 100},
            {label: '得分', name: 'price', index: 'price', width: 80},
            {
                label: '是否通过', name: 'inprice', width: 100, formatter: function (value, options, row) {
                    return value === "未通过" ?
                        '<span class="label label-danger">未通过</span>' :
                        '<span class="label label-success">已通过</span>';
                }
            },
            {
                label: '是否评卷', name: 'pingjuan', width: 100, formatter: function (value, options, row) {
                    return value === 0 ?
                        '<span class="label label-danger">未评卷</span>' :
                        '<span class="label label-success">已评卷</span>';
                }
            },
            {label: '提交时间', name: 'date', index: 'date', width: 200}
        ],
        viewrecords: true,
        height: 330,
        rowNum: 9,
        rowList: [9, 27, 54, 81],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager2",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        postData: {
            "mytestId": mytestId
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid2").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
    vm.showList2 = true;
    setStyWidth2();
}


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
var ztree;

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        showList2: false,
        title: null,
        q: {
            deptName: null,
            deptId: null,
            testName: null,
            userName: null
        },
        sysMytest: {
            deptId: 0,
            deptName: null
        }
    },
    methods: {
        output: function () {
            var mytestIds = getSelectedRows2();
            if (mytestIds == null) {
                return;
            }
            var id = "";
            for (var i = 0; i < mytestIds.length; i++) {
                id += mytestIds[i] + ",";
            }
            location.href = baseURL + "sys/sysmytest/outport?mytestIds=" + id;
        },
        query: function () {
            vm.reload1();
        },
        reload2: function () {
            vm.showList = true;
            vm.showList2 = true;
            setTimeout(function () {
                $("#show").hide();
            }, 1);
        },
        query1: function () {
            vm.reload();
        },
        fanhui: function () {
            vm.reload();
        },
        gets: function () {
            var mytestId = getSelectedRow();
            if (mytestId == null) {
                return;
            }
            if (!flag) {
                show2(mytestId);
            } else {
                vm.reload1();
            }
        },
        add: function () {
            vm.info();
        },
        update: function (event) {
            var mytestId = getSelectedRow();
            if (mytestId == null) {
                return;
            }
            vm.title = "评卷";

            vm.getInfo(mytestId);
        },
        getDept: function () {
            //加载部门树
            $.get(baseURL + "sys/dept/list", function (r) {
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.q.deptId);
                if (node != null) {
                    ztree.selectNode(node);
                    vm.q.deptName = node.name;
                }
            })
        },
        deptTree: function () {
            layer.open({
                type: 1,
                offset: '0px',
                skin: 'layui-layer-molv',
                title: "选择部门",
                area: ['280px', '400px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.q.deptId = node[0].deptId;
                    vm.q.deptName = node[0].name;

                    layer.close(index);
                }
            });
        },
        info: function () {
            var mytestId = getSelectedRow();
            if (mytestId == null) {
                return;
            }
            $.get(baseURL + "sys/sysmytest/titles/" + mytestId, function (r) {
                if (r.code != 0) {
                    alert(r.msg);
                } else {
                    vm.showList = false;
                    vm.title = "评卷";
                    vm.clear();
                    window.localStorage.setItem("mytestId", mytestId);
                    window.localStorage.setItem("testId", r.test.testId);
                    vm.pinjie2(r.subjects, r.test, r.size, r.counts, r.jungles, r.price);
                }
            });
        },
        info2: function (mytestId) {
            $.get(baseURL + "sys/sysmytest/titles2/" + mytestId, function (r) {
                if (r.code != 0) {
                    alert(r.msg);
                } else {
                    vm.showList = false;
                    vm.showList2 = false;
                    $("#show").hide();
                    $("#ss").show();
                    vm.clear();
                    window.localStorage.setItem("mytestId", mytestId);
                    window.localStorage.setItem("testId", r.test.testId);
                    vm.pinjie2(r.subjects, r.test, r.size, r.counts, r.jungles, r.price);
                }
            });
        },
        location: function (answerId) {
            var answer = window.localStorage.getItem("answer");
            if (answer == undefined || answer.length <= 0) {
                window.localStorage.setItem("answer", answerId);
            } else {
                window.localStorage.setItem("answer", answer + "," + answerId);
            }
        },
        clear: function () {
            window.localStorage.removeItem("answer");
            window.localStorage.removeItem("mytestId");
            window.localStorage.removeItem("testId");
        },
        pinjie: function (subjects, test, size, counts, jungles, price) {
            $(".padding15").empty();

            var html = " <div class=\"test_paper_top\" ><div class=\"paper_title\">" + test.name + " </div> <div class=\"paper_right\">得分：<label>"
                + price + "</label>分<label></div></div>";

            if (subjects != null) {
                html += "<div class=\"write_in\">";
                var order = ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十"];
                var num = 0;
                for (var subject in subjects) {
                    var i;
                    var subject2 = subjects[subject];
                    var length = subject2.length;
                    html += " <table > <tbody><tr> <td  class=\"trmenu\">" + order[num++] + "." + subject + "（共" + length + "题,共" + counts[subject] + "分）</td> </tr>";
                    if (subject.indexOf("单") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td></tr> ";
                            var jungle = jungles[sub.subjectId];
                            var length2 = jungle.length;
                            if (length2 > 0) {
                                var j;
                                for (j = 0; j < length2; j++) {
                                    var obj = jungle[j];
                                    if (obj.name == sub.answers) {
                                        html += "<tr><td><input  name='" + sub.subjectId + "' type=\"radio\" checked><label>" + obj.name + "   " + delHtmlTag(obj.content) + "</label></td></tr>";
                                    } else {
                                        html += "<tr><td><input  name='" + sub.subjectId + "' type=\"radio\"><label>" + obj.name + "   " + delHtmlTag(obj.content) + "</label></td></tr>";
                                    }
                                }
                                html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">标准答案：" + pares2(sub.answertt) + "</label></td></tr>";
                                html += "<tr><td><label style='color: #3c8dbc;float: left; '>输入评分：</label>" +
                                    "<input type='text' class='form-control' placeholder='评分' value='" + sub.pingfun + "' id='" + sub.answerId + "' style='width: 50px;'/></td></tr> </tbody></table>";
                                vm.location(sub.answerId);
                            }
                        }
                    } else if (subject.indexOf("多") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td> ";
                            var jungle = jungles[sub.subjectId];
                            var length2 = jungle.length;
                            if (length2 > 0) {
                                var j;
                                for (j = 0; j < length2; j++) {
                                    var obj = jungle[j];
                                    if (sub.answers.indexOf(obj.name) != -1) {
                                        html += "<tr><td><input  name='" + sub.subjectId + "' type=\"checkbox\" checked><label>" + obj.name + "   " + delHtmlTag(obj.content) + "</label></td></tr>";
                                    } else {
                                        html += "<tr><td><input  name='" + sub.subjectId + "' type=\"checkbox\"><label>" + obj.name + "   " + delHtmlTag(obj.content) + "</label></td></tr>";
                                    }
                                }
                            }
                            html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">标准答案：" + pares2(sub.answertt) + "</label></td></tr>";
                            html += "<tr><td><label style='color: #3c8dbc;float: left;'>输入评分：</label>" +
                                "<input type='text' class='form-control' value='" + sub.pingfun + "' placeholder='评分' id='" + sub.answerId + "' style='width: 50px;'/></td></tr> </tbody></table>";
                            vm.location(sub.answerId);
                        }
                    } else if (subject.indexOf("填空") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td> ";
                            html += "<tr><td><input type=\"text\" class=\"form-control\"  placeholder=\"请输入答案，不同部分答案用“/”分割\" value='" + sub.answers + "'/></td></tr>";
                            html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">标准答案：" + pares2(sub.answertt) + "</label></td></tr>";
                            html += "<tr><td><label style='color: #3c8dbc;float: left;'>输入评分：</label>" +
                                "<input type='text' class='form-control' placeholder='评分' value='" + sub.pingfun + "' id='" + sub.answerId + "' style='width: 50px;'/></td></tr> </tbody></table>";
                        }
                    } else if (subject.indexOf("判断") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td> ";
                            if (sub.answers == "正确") {
                                html += "<tr><td><input name='" + sub.subjectId + "'  type=\"radio\" checked><label>正确</label></td> </tr>";
                                html += "<tr><td><input name='" + sub.subjectId + "'  type=\"radio\"><label>错误</label></td> </tr>";
                            } else if (sub.answers == "错误") {
                                html += "<tr><td><input name='" + sub.subjectId + "'  type=\"radio\"><label>正确</label></td> </tr>";
                                html += "<tr><td><input name='" + sub.subjectId + "'  type=\"radio\" checked><label>错误</label></td> </tr>";
                            } else {
                                html += "<tr><td><input name='" + sub.subjectId + "'  type=\"radio\"><label>正确</label></td> </tr>";
                                html += "<tr><td><input name='" + sub.subjectId + "'  type=\"radio\"><label>错误</label></td> </tr>";
                            }
                            html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">标准答案：" + pares2(sub.answert) + "</label></td></tr>";
                            html += "<tr><td><label style='color: #3c8dbc;float: left;'>输入评分：</label>" +
                                "<input type='text' class='form-control' value='" + sub.pingfun + "' placeholder='评分' id='" + sub.answerId + "' style='width: 50px;'/></td></tr> </tbody></table>";
                            vm.location(sub.answerId);
                        }
                    } else if (subject.indexOf("打字") != -1 || subject.indexOf("问答") != -1 || subject.indexOf("作文") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td> ";
                            html += "<tr><td><input type=\"text\" class=\"form-control\" placeholder=\"请输入答案\"  value='" + sub.answers + "' style=\"height: 200px; width: 80%;\"></td></td> </tr>";
                            html += "<tr><td><label style='color: #3c8dbc;float: left;'>输入评分：</label>" +
                                "<input type='text' class='form-control' value='" + sub.pingfun + "' placeholder='评分' id='" + sub.answerId + "' style='width: 50px;'/></td></tr> </tbody></table>";
                            vm.location(sub.answerId);
                        }
                    } else if (subject.indexOf("操作") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td> ";
                            html += "<tr><td><span class=\"btn-upload\" style=\"margin-right: 20px;\"><a class=\"btn btn-primary\" href=\"" +
                                sub.file + "\">下载题目</a></span></td></tr>";
                            html += "<tr><td><input type=\"text\" class=\"form-control\" placeholder=\"请输入答案\"  value='" + sub.answers + "' style=\"height: 200px; width: 80%;\"></td></td> </tr>";
                            html += "<tr><td><label style='color: #3c8dbc;float: left;'>输入评分：</label>" +
                                "<input type='text' class='form-control' value='" + sub.pingfun + "' placeholder='评分' id='" + sub.answerId + "' style='width: 50px;'/></td></tr> </tbody></table>";
                            vm.location(sub.answerId);
                        }
                    }

                }
                html += "</div>";
            }
            $(".padding15").html(html);
        },
        pinjie2: function (subjects, test, size, counts, jungles, price) {
            $(".padding15").empty();

            var html = " <div class=\"test_paper_top\" ><div class=\"paper_title\">" + test.name + " </div> <div class=\"paper_right\">得分：<label>"
                + price + "</label>分<label></div></div>";

            if (subjects != null) {
                html += "<div class=\"write_in\">";
                var order = ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十"];
                var num = 0;
                for (var subject in subjects) {
                    var i;
                    var subject2 = subjects[subject];
                    var length = subject2.length;
                    html += " <table > <tbody><tr> <td  class=\"trmenu\">" + order[num++] + "." + subject + "（共" + length + "题,共" + counts[subject] + "分）</td> </tr>";
                    if (subject.indexOf("单") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td></tr> ";
                            var jungle = jungles[sub.subjectId];
                            var length2 = jungle.length;
                            if (length2 > 0) {
                                var j;
                                for (j = 0; j < length2; j++) {
                                    var obj = jungle[j];
                                    if (obj.name == sub.answers) {
                                        html += "<tr><td><input  name='" + sub.subjectId + "' type=\"radio\"    disabled='disabled' checked><label>" + obj.name + "   " + delHtmlTag(obj.content) + "</label></td></tr>";
                                    } else {
                                        html += "<tr><td><input  name='" + sub.subjectId + "' type=\"radio\"   disabled='disabled'><label>" + obj.name + "   " + delHtmlTag(obj.content) + "</label></td></tr>";
                                    }
                                }
                                html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">标准答案：" + pares2(sub.answertt) + "</label></td></tr>";
                            }
                            html += "<tr><td><label style='color: #3c8dbc;float: left; '>输入评分：</label>" +
                                "<input type='text' class='form-control' placeholder='评分' value='" + sub.pingfun + "' id='" + sub.answerId + "' style='width: 50px;'/></td></tr>";
                            vm.location(sub.answerId);
                            if (i == length - 1) {
                                html += " </tbody></table>";
                            }
                        }
                    } else if (subject.indexOf("多") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td> ";
                            var jungle = jungles[sub.subjectId];
                            var length2 = jungle.length;
                            if (length2 > 0) {
                                var j;
                                for (j = 0; j < length2; j++) {
                                    var obj = jungle[j];
                                    if (sub.answers.indexOf(obj.name) != -1) {
                                        html += "<tr><td><input  name='" + sub.subjectId + "' type=\"checkbox\"  disabled='disabled' checked><label>" + obj.name + "   " + delHtmlTag(obj.content) + "</label></td></tr>";
                                    } else {
                                        html += "<tr><td><input  name='" + sub.subjectId + "' type=\"checkbox\" disabled='disabled'><label>" + obj.name + "   " + delHtmlTag(obj.content) + "</label></td></tr>";
                                    }
                                }
                            }
                            html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">标准答案：" + pares2(sub.answertt) + "</label></td></tr>";
                            html += "<tr><td><label style='color: #3c8dbc;float: left; '>输入评分：</label>" +
                                "<input type='text' class='form-control' placeholder='评分' value='" + sub.pingfun + "' id='" + sub.answerId + "' style='width: 50px;'/></td></tr>";
                            vm.location(sub.answerId);
                            if (i == length - 1) {
                                html += " </tbody></table>";
                            }
                        }
                    } else if (subject.indexOf("填空") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td> ";
                            html += "<tr><td><input type=\"text\" class=\"form-control\"  readonly='readonly' placeholder=\"请输入答案，不同部分答案用“/”分割\" value='" + sub.answers + "'/></td></tr>";
                            html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">标准答案：" + sub.answer + "</label></td></tr>";
                            html += "<tr><td><label style='color: #3c8dbc;float: left; '>输入评分：</label>" +
                                "<input type='text' class='form-control' placeholder='评分' value='" + sub.pingfun + "' id='" + sub.answerId + "' style='width: 50px;'/></td></tr>";
                            vm.location(sub.answerId);
                            if (i == length - 1) {
                                html += " </tbody></table>";
                            }
                        }
                    } else if (subject.indexOf("判断") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td> ";
                            if (sub.answers == "正确") {
                                html += "<tr><td><input name='" + sub.subjectId + "'  type=\"radio\"    disabled='disabled' checked><label>正确</label></td> </tr>";
                                html += "<tr><td><input name='" + sub.subjectId + "' type=\"radio\"    disabled='disabled' ><label>错误</label></td> </tr>";
                            } else if (sub.answers == "错误") {
                                html += "<tr><td><input name='" + sub.subjectId + "' type=\"radio\"    disabled='disabled'><label>正确</label></td> </tr>";
                                html += "<tr><td><input name='" + sub.subjectId + "' type=\"radio\"    disabled='disabled' checked><label>错误</label></td> </tr>";
                            } else {
                                html += "<tr><td><input name='" + sub.subjectId + "'  type=\"radio\"    disabled='disabled' ><label>正确</label></td> </tr>";
                                html += "<tr><td><input name='" + sub.subjectId + "'  type=\"radio\"    disabled='disabled' ><label>错误</label></td> </tr>";
                            }
                            html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">标准答案：" + pares2(sub.answert) + "</label></td></tr> ";
                            html += "<tr><td><label style='color: #3c8dbc;float: left; '>输入评分：</label>" +
                                "<input type='text' class='form-control' placeholder='评分' value='" + sub.pingfun + "' id='" + sub.answerId + "' style='width: 50px;'/></td></tr>";
                            vm.location(sub.answerId);
                            if (i == length - 1) {
                                html += " </tbody></table>";
                            }
                        }
                    } else if (subject.indexOf("打字") != -1 || subject.indexOf("问答") != -1 || subject.indexOf("作文") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td> ";
                            html += "<tr><td><input type=\"text\" class=\"form-control\" placeholder=\"请输入答案\"  readonly='readonly' value='" + sub.answers + "' style=\"height: 200px; width: 80%;\"></td></td> </tr>";
                            html += "<tr><td><label style='color: #3c8dbc;float: left; '>输入评分：</label>" +
                                "<input type='text' class='form-control' placeholder='评分' value='" + sub.pingfun + "' id='" + sub.answerId + "' style='width: 50px;'/></td></tr>";
                            vm.location(sub.answerId);
                            if (i == length - 1) {
                                html += " </tbody></table>";
                            }
                        }
                    } else if (subject.indexOf("操作") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td> ";
                            html += "<tr><td><span class=\"btn-upload\" style=\"margin-right: 20px;\"><a class=\"btn btn-primary\" href=\"" +
                                sub.file + "\">下载题目</a></span></td></tr>";
                            html += "<tr><td><input type=\"text\" class=\"form-control\" placeholder=\"请输入答案\"   readonly='readonly'  value='" + sub.answers + "' style=\"height: 200px; width: 80%;\"></td></td> </tr>";
                            html += "<tr><td><label style='color: #3c8dbc;float: left; '>输入评分：</label>" +
                                "<input type='text' class='form-control' placeholder='评分' value='" + sub.pingfun + "' id='" + sub.answerId + "' style='width: 50px;'/></td></tr>";
                            vm.location(sub.answerId);
                            if (i == length - 1) {
                                html += " </tbody></table>";
                            }
                        }
                    }

                }
                html += "</div>";
            }
            $(".padding15").html(html);
        },
        tijiao: function () {
            var answerEntities = new Array();
            var answer = window.localStorage.getItem("answer").split(',');
            var mytestId = window.localStorage.getItem("mytestId");
            var testId = window.localStorage.getItem("testId");
            for (var i = 0; i < answer.length; i++) {
                var content = $("#" + answer[i]).val();
                if (content == undefined || content == "") {
                    continue;
                }
                var sysAnswerEntity = {
                    "answerId": answer[i],
                    "price": content
                };

                answerEntities.push(sysAnswerEntity);
            }

            var sysMytest = {
                "answerEntities": answerEntities,
                "mytestId": mytestId,
                "testId": testId
            };
            $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function () {
                var url = "sys/sysmytest/save";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(sysMytest),
                    success: function (r) {
                        if (r.code === 0) {
                            layer.msg("提交成功", {icon: 1});
                            vm.reload1();
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        } else {
                            layer.alert(r.msg);
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        }
                    }
                });
            });
        },
        saveOrUpdate: function (event) {
            $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function () {
                var url = vm.sysMytest.mytestId == null ? "sys/sysmytest/save" : "sys/sysmytest/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.sysMytest),
                    success: function (r) {
                        if (r.code === 0) {
                            layer.msg("操作成功", {icon: 1});
                            vm.reload();
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        } else {
                            layer.alert(r.msg);
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        }
                    }
                });
            });
        },
        del: function (event) {
            var mytestIds = getSelectedRows();
            if (mytestIds == null) {
                return;
            }
            var lock = false;
            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                if (!lock) {
                    lock = true;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "sys/sysmytest/delete",
                        contentType: "application/json",
                        data: JSON.stringify(mytestIds),
                        success: function (r) {
                            if (r.code == 0) {
                                layer.msg("操作成功", {icon: 1});
                                $("#jqGrid2").trigger("reloadGrid");
                            } else {
                                layer.alert(r.msg);
                            }
                        }
                    });
                }
            }, function () {
            });
        },
        del1: function (event) {
            var mytestIds = getSelectedRows();
            if (mytestIds == null) {
                return;
            }
            var lock = false;
            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                if (!lock) {
                    lock = true;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "sys/sysmytest/delete1",
                        contentType: "application/json",
                        data: JSON.stringify(mytestIds),
                        success: function (r) {
                            if (r.code == 0) {
                                layer.msg("操作成功", {icon: 1});
                                $("#jqGrid").trigger("reloadGrid");
                            } else {
                                layer.alert(r.msg);
                            }
                        }
                    });
                }
            }, function () {
            });
        },
        getInfo: function (mytestId) {
            vm.info2(mytestId);
        },
        reload: function (event) {
            vm.showList = true;
            vm.showList2 = false;
            vm.clear();
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    "testName": vm.q.testName
                },
                page: page
            }).trigger("reloadGrid");
            setTimeout(function () {
                $("#show").show();
            }, 1);
        },
        reload1: function (event) {
            vm.showList = true;
            vm.showList2 = true;
            vm.clear();
            var page = $("#jqGrid2").jqGrid('getGridParam', 'page');
            $("#jqGrid2").jqGrid('setGridParam', {
                postData: {
                    "deptId": vm.q.deptId,
                    "userName": vm.q.userName
                },
                page: page
            }).trigger("reloadGrid");
            vm.getDept();
            setTimeout(function () {
                $("#show").hide();
                $("#ss").hide();
            }, 1);
        }
    }
});