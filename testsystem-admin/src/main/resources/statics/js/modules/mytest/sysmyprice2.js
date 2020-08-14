$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/sysmytest/list2',
        datatype: "json",
        colModel: [
            {label: 'id', sortable: false,name: 'mytestId', index: 'mytest_id', width: 80, key: true},
            {label: '作业名称', sortable: false,name: 'testName', index: 'test_id', width: 200},
            {label: '得分', sortable: false,name: 'price', index: 'price', width: 100},
            {
                label: '是否通过', sortable: false,name: 'inprice', width: 100, formatter: function (value, options, row) {
                    return value === "未通过" ?
                        '<span class="label label-danger">未通过</span>' :
                        '<span class="label label-success">已通过</span>';
                }
            },
            {label: '提交时间', sortable: false,name: 'date', index: 'date', width: 200}
        ],
        postData: {
            "type": "作业"
        },
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

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        items: [{text: "已通过", value: "已通过"}, {text: "未通过", value: "未通过"}],
        q: {
            courseId: null,
            courseName: null,
            testName: null
        },
        sysMytest: {
            courseId: 0,
            courseName: null
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        update: function (event) {
            var mytestId = getSelectedRow();
            if (mytestId == null) {
                return;
            }
            vm.title = "查看评卷";

            vm.getInfo(mytestId);
        },
        info2: function (mytestId) {
            $.get(baseURL + "sys/sysmytest/titles2/" + mytestId, function (r) {
                if (r.code != 0) {
                    alert(r.msg);
                } else {
                    vm.showList = false;
                    window.localStorage.setItem("mytestId", mytestId);
                    window.localStorage.setItem("testId", r.test.testId);
                    vm.pinjie2(r.subjects, r.test, r.size, r.counts, r.jungles, r.price);
                }
            });
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
                                html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">标准答案：" + pares2(obj.content) + "</label></td></tr>";
                                html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">解析：" + pares2(sub.parse) + "</label></td></tr>";
                            }
                            html += "<tr><td><label style='color: #3c8dbc;float: left; '>得分：</label>" +
                                "<input type='text' class='form-control'   readonly='readonly' placeholder='评分' value='" + sub.pingfun + "' id='" + sub.answerId + "' style='width: 50px;'/></td></tr>";
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
                            html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">标准答案：" + pares2(obj.content) + "</label></td></tr>";
                            html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">解析：" + pares2(sub.parse) + "</label></td></tr>";
                            html += "<tr><td><label style='color: #3c8dbc;float: left; '>得分：</label>" +
                                "<input type='text' class='form-control'   readonly='readonly' placeholder='评分' value='" + sub.pingfun + "' id='" + sub.answerId + "' style='width: 50px;'/></td></tr>";
                            if (i == length - 1) {
                                html += " </tbody></table>";
                            }
                        }
                    } else if (subject.indexOf("填空") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td> ";
                            html += "<tr><td><input type=\"text\" class=\"form-control\"  readonly='readonly'  value='" + sub.answers + "'/></td></tr>";
                            html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">标准答案：" + pares2(sub.answer) + "</label></td></tr>";
                            html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">解析：" + pares2(sub.parse) + "</label></td></tr>";
                            html += "<tr><td><label style='color: #3c8dbc;float: left; '>得分：</label>" +
                                "<input type='text' class='form-control'   readonly='readonly' placeholder='评分' value='" + sub.pingfun + "' id='" + sub.answerId + "' style='width: 50px;'/></td></tr>";
                            if (i == length - 1) {
                                html += " </tbody></table>";
                            }
                        }
                    } else if (subject.indexOf("判断") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td> ";
                            if (sub.answers == "正确") {
                                html += "<tr><td><input name='" + sub.subjectId + "' type=\"radio\"    disabled='disabled' checked><label>正确</label></td> </tr>";
                                html += "<tr><td><input name='" + sub.subjectId + "' type=\"radio\"    disabled='disabled' ><label>错误</label></td> </tr>";
                            } else if (sub.answers == "错误") {
                                html += "<tr><td><input name='" + sub.subjectId + "' type=\"radio\"    disabled='disabled'><label>正确</label></td> </tr>";
                                html += "<tr><td><input name='" + sub.subjectId + "' type=\"radio\"    disabled='disabled' checked><label>错误</label></td> </tr>";
                            } else {
                                html += "<tr><td><input name='" + sub.subjectId + "' type=\"radio\"    disabled='disabled' ><label>正确</label></td> </tr>";
                                html += "<tr><td><input name='" + sub.subjectId + "' type=\"radio\"    disabled='disabled' ><label>错误</label></td> </tr>";
                            }
                            html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">标准答案：" + pares2(sub.answert) + "</label></td></tr> ";
                            html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">解析：" + pares2(sub.parse) + "</label></td></tr>";
                            html += "<tr><td><label style='color: #3c8dbc;float: left; '>得分：</label>" +
                                "<input type='text' class='form-control'   readonly='readonly' placeholder='评分' value='" + sub.pingfun + "' id='" + sub.answerId + "' style='width: 50px;'/></td></tr>";
                            if (i == length - 1) {
                                html += " </tbody></table>";
                            }
                        }
                    } else if (subject.indexOf("打字") != -1 || subject.indexOf("问答") != -1 || subject.indexOf("作文") != -1) {
                        for (i = 0; i < length; i++) {
                            var sub = subject2[i];
                            html += "<tr><td>" + (i + 1) + "." + delHtmlTag(sub.content) + "（" + sub.price + "分）</td> ";
                            html += "<tr><td><input type=\"text\" class=\"form-control\"   readonly='readonly' value='" + sub.answers + "' style=\"height: 200px; width: 80%;\"></td></td> </tr>";
                            html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">解析：" + pares2(sub.parse) + "</label></td></tr>";
                            html += "<tr><td><label style='color: #3c8dbc;float: left; '>得分：</label>" +
                                "<input type='text' class='form-control'   readonly='readonly' placeholder='评分' value='" + sub.pingfun + "' id='" + sub.answerId + "' style='width: 50px;'/></td></tr>";
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
                            html += "<tr><td><input type=\"text\" class=\"form-control\"   readonly='readonly'  value='" + sub.answers + "' style=\"height: 200px; width: 80%;\"></td></td> </tr>";
                            html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">解析：" + pares2(sub.parse) + "</label></td></tr>";
                            html += "<tr><td><label style='color: #3c8dbc;float: left; '>得分：</label>" +
                                "<input type='text' class='form-control'   readonly='readonly' placeholder='评分' value='" + sub.pingfun + "' id='" + sub.answerId + "' style='width: 50px;'/></td></tr>";
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
        getInfo: function (mytestId) {
            vm.info2(mytestId);
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    "testName": vm.q.testName,
                    "type": "作业",
                    "inprice": vm.q.inprice
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});