$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/sysanswer/cuotis',
        datatype: "json",
        colModel: [
            {label: 'id', sortable: false,name: 'subjectId', index: 'subject_id', width: 80, key: true},
            {label: '试题名称', sortable: false,name: 'name3', index: 'name3', width: 250},
            {label: '题型名称', sortable: false,name: 'name4', index: 'name4', width: 250},
            {label: '出错次数', sortable: false,name: 'num', index: 'num', width: 100}
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


var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        sysTest: {},
        q: {
            topicName: null,
            subjectName: null
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        del: function () {
            var subjectIds = getSelectedRows();
            if (subjectIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/sysanswer/delete3",
                    contentType: "application/json",
                    data: JSON.stringify(subjectIds),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function () {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        info: function () {
            var subjectId = getSelectedRow();
            if (subjectId == null) {
                return;
            }

            $.get(baseURL + "sys/sysanswer/cuotiInfo/" + subjectId, function (r) {
                if (r.code != 0) {
                    alert(r.msg);
                    return;
                } else {
                    vm.showList = false;
                    vm.pinjie(r.subject);
                }
            });
        },
        fanhui: function () {
            vm.showList = true;
            vm.reload();
        },
        pinjie: function (subjects) {
            $(".padding15").empty();

            var html = " <div class=\"test_paper_top\" ><div class=\"paper_title\">错题展示 </div>";

            if (subjects != null) {
                html += "<div class=\"write_in\">";
                var subject = subjects.topicName;
                if (subject.indexOf("单") != -1) {
                    var sub = subjects;
                    html += " <table  class='xx'><tbody><tr> <td  class=\"trmenu\">" + subject + "</td> </tr>";
                    html += "<tr><td>" + delHtmlTag(sub.content) + "</td></tr> ";
                    var jungle = sub.jugleList;
                    var length2 = jungle.length;
                    if (length2 > 0) {
                        var j;
                        for (j = 0; j < length2; j++) {
                            var obj = jungle[j];
                            // if (obj.name == sub.answers) {
                            //     html += "<tr><td><input  name='" + sub.subjectId + "' type=\"radio\"    disabled='disabled' checked><label>" + obj.name + "   " + delHtmlTag(obj.content) + "</label></td></tr>";
                            // } else {
                            // }
                            html += "<tr><td><input  name='" + sub.subjectId + "' type=\"radio\"   disabled='disabled'><label>" + obj.name + "   " + delHtmlTag(obj.content) + "</label></td></tr>";
                        }
                        html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">标准答案：" + pares2(sub.answertt) + "</label></td></tr>";
                        html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">解析：" + pares2(sub.parse) + "</label></td></tr>";
                    }
                    html += "</tbody></table>";
                } else if (subject.indexOf("多") != -1) {
                    var sub = subjects;
                    html += " <table  class='xx'><tbody><tr> <td  class=\"trmenu\">" + subject + "</td> </tr>";
                    html += "<tr><td>" + delHtmlTag(sub.content) + "</td></tr> ";
                    var jungle = sub.jugleList;
                    var length2 = jungle.length;
                    if (length2 > 0) {
                        var j;
                        for (j = 0; j < length2; j++) {
                            var obj = jungle[j];
                            // if (sub.answers.indexOf(obj.name) != -1) {
                            //     html += "<tr><td><input  name='" + sub.subjectId + "' type=\"checkbox\"  disabled='disabled' checked><label>" + obj.name + "   " + delHtmlTag(obj.content) + "</label></td></tr>";
                            // } else {
                            // }
                            html += "<tr><td><input  name='" + sub.subjectId + "' type=\"checkbox\" disabled='disabled'><label>" + obj.name + "   " + delHtmlTag(obj.content) + "</label></td></tr>";
                        }
                    }
                    html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">标准答案：" + pares2(sub.answertt) + "</label></td></tr>";
                    html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">解析：" + pares2(sub.parse) + "</label></td></tr>";
                    html += "</tbody></table>";
                } else if (subject.indexOf("填空") != -1) {
                    var sub = subjects;
                    html += " <table  class='xx'><tbody><tr> <td  class=\"trmenu\">" + subject + "</td> </tr>";
                    html += "<tr><td>" + delHtmlTag(sub.content) + "</td></tr> ";
                    html += "<tr><td><input type=\"text\" class=\"form-control\"  readonly='readonly'  /></td></tr>";
                    html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">标准答案：" + pares2(sub.answer) + "</label></td></tr>";
                    html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">解析：" + pares2(sub.parse) + "</label></td></tr>";
                    html += "</tbody></table>";
                } else if (subject.indexOf("判断") != -1) {
                    var sub = subjects;
                    html += " <table  class='xx'><tbody><tr> <td  class=\"trmenu\">" + subject + "</td> </tr>";
                    html += "<tr><td>" + delHtmlTag(sub.content) + "</td></tr> ";
                    // if (sub.answers == "正确") {
                    //     html += "<tr><td><input name='" + sub.subjectId + "'  type=\"radio\"    disabled='disabled' checked><label>正确</label></td> </tr>";
                    //     html += "<tr><td><input name='" + sub.subjectId + "'  type=\"radio\"    disabled='disabled' ><label>错误</label></td> </tr>";
                    // } else if (sub.answers == "错误") {
                    //     html += "<tr><td><input name='" + sub.subjectId + "'  type=\"radio\"    disabled='disabled'><label>正确</label></td> </tr>";
                    //     html += "<tr><td><input name='" + sub.subjectId + "'  type=\"radio\"    disabled='disabled' checked><label>错误</label></td> </tr>";
                    // } else {
                    // }
                    html += "<tr><td><input name='" + sub.subjectId + "'  type=\"radio\"    disabled='disabled' ><label>正确</label></td> </tr>";
                    html += "<tr><td><input name='" + sub.subjectId + "'  type=\"radio\"    disabled='disabled' ><label>错误</label></td> </tr>";
                    html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">标准答案：" + pares2(sub.answert) + "</label></td></tr> ";
                    html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">解析：" + pares2(sub.parse) + "</label></td></tr>";
                    html += "</tbody></table>";
                } else if (subject.indexOf("打字") != -1 || subject.indexOf("问答") != -1 || subject.indexOf("作文") != -1) {
                    var sub = subjects;
                    html += " <table  class='xx'><tbody><tr> <td  class=\"trmenu\">" + subject + "</td> </tr>";
                    html += "<tr><td>" + delHtmlTag(sub.content) + "</td></tr> ";
                    html += "<tr><td><input type=\"text\" class=\"form-control\"   readonly='readonly'  style=\"height: 200px; width: 80%;\"></td></td> </tr>";
                    html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">解析：" + pares2(sub.parse) + "</label></td></tr>";
                    html += "</tbody></table>";
                } else if (subject.indexOf("操作") != -1) {
                    var sub = subjects;
                    html += " <table class='xx'><tbody><tr> <td  class=\"trmenu\">" + subject + "</td> </tr>";
                    html += "<tr><td>" + delHtmlTag(sub.content) + "</td></tr> ";
                    html += "<tr><td><span class=\"btn-upload\" style=\"margin-right: 20px;\"><a class=\"btn btn-primary\" href=\"" +
                        sub.file + "\">下载题目</a></span></td></tr>";
                    html += "<tr><td><input type=\"text\" class=\"form-control\"   readonly='readonly'   style=\"height: 200px; width: 80%;\"></td></td> </tr>";
                    html += "<tr><td><label class=\"answer\" style=\"color: #3c8dbc;\">解析：" + pares2(sub.parse) + "</label></td></tr>";
                    html += "</tbody></table>";
                }
                html += "</div>";
            }
            $(".padding15").html(html);
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    "subjectName": vm.q.subjectName,
                    "topicName": vm.q.topicName
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});