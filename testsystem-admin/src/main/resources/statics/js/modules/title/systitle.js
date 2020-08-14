$(function () {
    vm.getDept2();
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/systitle/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'titleId',sortable: false, index: 'title_id', width: 80, key: true },
            {label: '所属试卷', name: 'testName', sortable: false,width: 180},
            {label: '所属题库', name: 'subjectName',sortable: false, width: 450},
            {label: '顺序', name: 'orders', sortable: false,width: 100},
            {label: '创建人', name: 'admin',sortable: false, width: 150},
            {label: '创建时间',sortable: false, name: 'createTime', width: 300}
        ],
        viewrecords: true,
        height: 330,
        rowNum: 9,
        rowList: [9, 27, 54, 81],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
    setStyWidth();
});

var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "courseId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    }
};
var ztree;


var setting2 = {
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
var ztree2;
var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            deptName: null,
            deptId: 0,
            name: null
        },
        showList: true,
        title: null,
        sysTitle: {
            courseId: null, courseName: null,
            deptId: 0,
            deptName: null
        },
        types: [{value: "考试", text: "考试"}, {text: "作业", value: "作业"}],
        tests: null,
        courses: null,
        topics: null,
        subjects: null
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.sysTitle = {
                courseId: 0,
                courseName: null,
                deptId: 0,
                deptName: null
            };

            vm.getDept();
            vm.getCourse();
            vm.getCoursesInfo();
        },
        update: function (event) {
            var titleId = getSelectedRow();
            if (titleId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getDept();
            vm.getCourse();
            vm.getInfo(titleId)
        },
        getDept: function () {
            //加载部门树
            $.get(baseURL + "sys/dept/list", function (r) {
                ztree2 = $.fn.zTree.init($("#deptTree"), setting2, r);
                var node = ztree2.getNodeByParam("deptId", vm.sysTitle.deptId);
                if (node != null) {
                    ztree2.selectNode(node);
                    vm.sysTitle.deptName = node.name;
                }
            })
        },
        getDept2: function () {
            //加载部门树
            $.get(baseURL + "sys/dept/list", function (r) {
                ztree2 = $.fn.zTree.init($("#deptTree"), setting2, r);
                var node = ztree2.getNodeByParam("deptId", vm.q.deptId);
                if (node != null) {
                    ztree2.selectNode(node);
                    vm.q.deptName = node.name;
                }
            })
        },
        getCourse: function () {
            //加载部门树
            $.get(baseURL + "sys/syssubject/select", function (r) {
                ztree = $.fn.zTree.init($("#courseTree"), setting, r.courseList);
                var node = ztree.getNodeByParam("courseId", vm.sysTitle.courseId);
                if (node != null) {
                    ztree.selectNode(node);
                    vm.sysTitle.courseName = node.name;
                }
            })
        },
        courseTree: function () {
            layer.open({
                type: 1,
                offset: '0px',
                skin: 'layui-layer-molv',
                title: "选择科目",
                area: ['280px', '400px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#courseLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.sysTitle.courseId = node[0].courseId;
                    vm.sysTitle.courseName = node[0].name;

                    vm.changeCourseName(node[0].courseId);

                    $("#courseName").val(node[0].name);
                    layer.close(index);
                }
            });
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
                    var node = ztree2.getSelectedNodes();
                    //选择上级部门
                    vm.sysTitle.deptId = node[0].deptId;
                    vm.sysTitle.deptName = node[0].name;
                    $("#deptName").val(vm.sysTitle.deptName);

                    layer.close(index);
                }
            });
        },
        deptTree2: function () {
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
                    var node = ztree2.getSelectedNodes();
                    //选择上级部门
                    vm.q.deptId = node[0].deptId;
                    vm.q.deptName = node[0].name;

                    layer.close(index);
                }
            });
        },
        changeTypes: function () {
            vm.getTypeInfo(vm.sysTitle.type, vm.sysTitle.deptId);
        },
        getTypeInfo: function (type, deptId) {
            $.get(baseURL + "sys/systitle/type/" + type + "/" + deptId, function (r) {
                if (r.sysTests != null) {
                    var tests = r.sysTests;
                    vm.tests = new Array();
                    for (var key in tests) {
                        var old = {text: null, value: null};
                        old.text = key;
                        old.value = tests[key];
                        vm.tests.push(old);
                    }
                }
            });
        },
        changeCourseName: function (courseId) {
            vm.getTopicNameInfo(courseId);
        },
        changeTopicName: function () {
            vm.getSubjectsInfo(vm.sysTitle.courseId, vm.sysTitle.topicId);
        },
        getCoursesInfo: function () {
            $.get(baseURL + "sys/systitle/courses", function (r) {
                if (r.courses != null) {
                    vm.courses = new Array();
                    var i;
                    var courses = r.courses;
                    for (i = 0; i < courses.length; i++) {
                        for (var key in courses[i]) {
                            var old = {text: null, value: null};
                            old.text = key;
                            old.value = courses[i][key];
                            vm.courses.push(old);
                        }
                    }
                }
            });
        },
        getTopicNameInfo: function (id) {
            $.get(baseURL + "sys/systitle/topic/" + id, function (r) {
                if (r.topics != null) {
                    var tests = r.topics;
                    vm.topics = new Array();
                    for (var key in tests) {
                        var old = {text: null, value: null};
                        old.text = key;
                        old.value = tests[key];
                        vm.topics.push(old);
                    }
                }
            });
        },
        getSubjectsInfo: function (topicId, courseId) {
            $.get(baseURL + "sys/systitle/subject/" + topicId + "/" + courseId, function (r) {
                if (r.subjects != null) {
                    var tests = r.subjects;
                    vm.subjects = new Array();
                    for (var key in tests) {
                        var old = {text: null, value: null};
                        old.text = key;
                        old.value = tests[key];
                        vm.subjects.push(old);
                    }
                }
            });
        },
        saveOrUpdate: function (event) {
            $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function () {
                var url = vm.sysTitle.titleId == null ? "sys/systitle/save" : "sys/systitle/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.sysTitle),
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
            var titleIds = getSelectedRows();
            if (titleIds == null) {
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
                        url: baseURL + "sys/systitle/delete",
                        contentType: "application/json",
                        data: JSON.stringify(titleIds),
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
            });
        },
        getInfo: function (titleId) {
            $.get(baseURL + "sys/systitle/info/" + titleId, function (r) {
                vm.sysTitle = r.sysTitle;
                $("#deptName").val(vm.sysTitle.deptName);
                $("#courseName").val(vm.sysTitle.courseName);

                if (vm.sysTitle.courses != null) {
                    vm.courses = new Array();
                    var courses = vm.sysTitle.courses;
                    var i;
                    for (i = 0; i < courses.length; i++) {
                        for (var key in courses[i]) {
                            var old = {text: null, value: null};
                            old.text = key;
                            old.value = courses[i][key];
                            vm.courses.push(old);
                        }
                    }
                }

                if (vm.sysTitle.topics != null) {
                    var tests = vm.sysTitle.topics;
                    vm.topics = new Array();
                    for (var key in tests) {
                        var old = {text: null, value: null};
                        old.text = key;
                        old.value = tests[key];
                        vm.topics.push(old);
                    }
                }


                if (vm.sysTitle.subjects != null) {
                    var tests = vm.sysTitle.subjects;
                    vm.subjects = new Array();
                    for (var key in tests) {
                        var old = {text: null, value: null};
                        old.text = key;
                        old.value = tests[key];
                        vm.subjects.push(old);
                    }
                }

                if (vm.sysTitle.tests != null) {
                    var tests = vm.sysTitle.tests;
                    vm.tests = new Array();
                    for (var key in tests) {
                        var old = {text: null, value: null};
                        old.text = key;
                        old.value = tests[key];
                        vm.tests.push(old);
                    }
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    "deptId": vm.q.deptId,
                    "name": vm.q.name
                },
                page: page
            }).trigger("reloadGrid");
            vm.getDept2();
        }
    }
});