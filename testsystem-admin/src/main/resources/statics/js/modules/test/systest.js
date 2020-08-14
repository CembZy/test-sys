$(function () {

    $("#times").hide();
    $("#course").hide();

    vm.getDept2();
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/systest/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'testId', sortable: false, index: 'test_id', width: 80, key: true},
            {label: '名称', name: 'name', sortable: false, index: 'name', width: 150},
            {label: '类型', name: 'type', sortable: false, index: 'type', width: 80},
            {label: '出题方式', name: 'typet', sortable: false, index: 'typet', width: 120},
            {label: '试题总分', name: 'price', sortable: false, index: 'price', width: 120},
            {label: '通过分数', name: 'inprice', sortable: false, index: 'inprice', width: 120},
            {label: '答题时间', name: 'times', sortable: false, index: 'times', width: 120},
            {label: '开考时间', name: 'startTime', sortable: false, index: 'start_time', width: 280},
            {label: '结束时间', name: 'endTime', sortable: false, index: 'end_time', width: 280}
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


var ztrees = new Array();
var settings = new Array();

function create(num) {
    var setting3 = {
        data: {
            simpleData: {
                enable: true,
                idKey: "courseId" + num,
                pIdKey: "parentId" + num,
                rootPId: -1
            },
            key: {
                url: "nourl"
            }
        }
    };
    var ztree3;
    ztrees.push(ztree3);
    settings.push(setting3);
}

//数据树
var data_ztree;
var data_setting = {
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
    },
    check: {
        enable: true,
        nocheckInherit: true
    }
};


var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        showList2: true,
        title: null,
        testTypeList: {},
        admins: {},
        courses: null,
        courseList: {},
        sysTest: {
            deptId: null,
            deptName: null,
            testTypeList: [],
            adminsList: [],
            sysTestEntityVos: []
        },
        q: {
            name: null,
            deptName: null,
            deptId: null,
            type: null
        },
        infos: [],
        map: null,
        items2: [],
        timus: [],
        nums2: null,
        subjects: null,
        type: [{text: '考试', value: '考试'}, {text: '作业', value: '作业'}],
        typet: [{text: '随机组卷', value: '随机组卷'}, {text: '手工组卷', value: '手工组卷'}],
        testType: [{text: '允许多次参加考试', value: 1}, {text: '允许查看评卷结果', value: 2}]
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.showList2 = true;
            $("#show1").hide();
            vm.title = "新增";
            vm.admins = {};
            vm.map = null;
            vm.typet = [{text: '随机组卷', value: '随机组卷'}, {text: '手工组卷', value: '手工组卷'}];
            vm.sysTest = {
                adminsList: [], testTypeList: [], deptName: null, deptId: null, sysTestEntityVos: []
            };
            vm.timus = [];
            vm.items2 = [];
            vm.nums2 = null;
            $("#deptName").val("所属部门");

            ztrees = null;
            settings = null;
            ztrees = new Array();
            settings = new Array();

            // vm.getDept();
            vm.getDataTree();
            vm.getAdminsList();
        },
        update: function (event) {
            var testId = getSelectedRow();
            if (testId == null) {
                return;
            }
            vm.showList = false;
            vm.showList2 = true;
            $("#show1").hide();
            vm.title = "修改";
            vm.items2 = [];
            ztrees = null;
            settings = null;
            ztrees = new Array();
            settings = new Array();
            // vm.getDept();
            vm.getDataTree();
            // sleep(1000);
            vm.getInfo(testId);
            vm.getAdminsList();
        },
        addt: function () {
            var num = vm.nums2.length + 1;
            vm.nums2.push(num);
            create(num);
            vm.sysTest["courseId" + num] = 0;
            vm.sysTest["courseName" + num] = null;
            vm.sysTest["courseIdList" + num] = [];

            vm.sysTest["topicId" + num] = null;
            vm.sysTest["topicName" + num] = null;

            vm.getCourse(num - 1);
        },
        detelet: function () {
            var num = vm.nums2.length;
            if (num > 1) {
                $("#courseName" + num).val("所属科目");
                vm.nums2.removeByIndex(vm.nums2.length - 1);
                delete vm.sysTest["courseId" + num];
                delete vm.sysTest["courseName" + num];
                delete vm.sysTest["courseIdList" + num];
                delete vm.map["courseName" + num];
                delete vm.sysTest["topicId" + num];
                delete vm.sysTest["topicName" + num];

                var arr = vm.items2;
                vm.items2 = null;
                arr.removeByIndex(num - 1);
                vm.items2 = arr;
            } else {
                alert("不能再删除!");
            }
        },
        getDataTree: function (roleId) {
            //加载菜单树
            $.get(baseURL + "sys/dept/list", function (r) {
                data_ztree = $.fn.zTree.init($("#dataTree"), data_setting, r);
                //展开所有节点
                data_ztree.expandAll(true);
            });
        },
        getTopics: function (courseId, num) {
            $.get(baseURL + "sys/syssubject/subjects/" + courseId, function (r) {
                if (r.topics != null) {
                    var topics = r.topics;
                    var items = new Array();
                    var topicId = null;
                    for (var key in topics) {
                        var old = {text: null, value: null};
                        old.text = key;
                        old.value = topics[key];
                        if (topicId == null) {
                            topicId = topics[key];
                        }
                        items.push(old);
                    }
                    var arr = vm.items2;
                    vm.items2 = null;
                    if (num > 1 && arr.length < num) {
                        for (var i = 0; i < num - 1; i++) {
                            if (arr[i] == null || arr[i] == undefined) {
                                var old = {text: null, value: null};
                                var item2 = new Array();
                                item2.push(old);
                                arr[i] = item2;
                            }
                        }
                    }
                    if (arr[num - 1] != null && arr[num - 1] != undefined) {
                        arr[num - 1] = items;
                    } else {
                        arr.push(items);
                    }
                    vm.items2 = arr;


                    vm.getCount(courseId, topicId, num);
                }
            });
        },
        courseTree: function (num) {
            layer.open({
                type: 1,
                offset: '0px',
                skin: 'layui-layer-molv',
                title: "选择科目",
                area: ['280px', '400px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#courseLayer" + num),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztrees[num - 1].getSelectedNodes();
                    var courseId = node[0]["courseId" + num];
                    vm.sysTest["courseId" + num] = courseId;
                    var courseName = "courseName" + num;
                    vm.sysTest[courseName] = node[0].name;

                    if (vm.map != null) {
                        vm.map[courseName] = courseId;
                    } else {
                        vm.map = {};
                        vm.map[courseName] = courseId;
                    }

                    $("#courseName" + num).val(node[0].name);
                    vm.getTopics(vm.sysTest["courseId" + num], num);

                    layer.close(index);
                }
            });
        },
        getCourse: function (num) {
            //加载部门树
            $.get(baseURL + "sys/syssubject/select", function (r) {
                for (var i = 0; i < r.courseList.length; i++) {
                    r.courseList[i]["courseId" + (num + 1)] = r.courseList[i].courseId;
                    r.courseList[i]["parentId" + (num + 1)] = r.courseList[i].parentId;
                }
                ztrees[num] = $.fn.zTree.init($("#courseTree" + (num + 1)), settings[num], r.courseList);
                var b = "courseId" + (num + 1);
                var courseId = vm.sysTest["courseId" + (num + 1)];
                var node = ztrees[num].getNodeByParam(b, courseId);
                if (node != null && node != undefined) {
                    ztrees[num].selectNode(node);
                    vm.sysTest["courseName" + (num + 1)] = node.name;
                }
            })
        },
        change: function () {
            var checks = vm.sysTest.testTypeList;
            if (checks != undefined && checks != null) {
                var i;
                var num = 0;
                for (i = 0; i < checks.length; i++) {
                    var checked = checks[i];
                    if (checked == 4) {
                        num++;
                    }
                }
                if (num == 0) {
                    $("#times").hide();
                } else {
                    $("#times").show();
                }
            } else {
                $("#times").hide();
            }
        },
        changet: function () {
            var checks = vm.sysTest.typet;
            if (checks != undefined && checks != null) {
                if (checks == "随机组卷") {
                    if (vm.nums2 == null) {
                        vm.nums2 = new Array();
                        var num = 1;
                        vm.nums2.push(num);
                        create(num);
                        vm.sysTest["courseId" + num] = 0;
                        vm.sysTest["courseName" + num] = null;
                        vm.sysTest["courseIdList" + num] = [];
                        vm.sysTest["topicId" + num] = null;
                        vm.sysTest["topicName" + num] = null;

                        vm.getCourse(num - 1);
                    }
                    $("#course").show();
                } else {
                    $("#course").hide();
                }
            } else {
                $("#course").hide();
            }
        },
        getCount: function (courseId, topicId, num) {
            $.get(baseURL + "sys/systest/count/" + courseId + "/" + topicId, function (r) {
                if (r.code == 0) {
                    var arr = vm.timus;
                    vm.timus = null;
                    if (num > 1 && arr.length < num) {
                        for (var i = 0; i < num - 1; i++) {
                            if (arr[i] == null || arr[i] == undefined) {
                                arr[i] = 0;
                            }
                        }
                    }
                    if (arr[num - 1] != null && arr[num - 1] != undefined) {
                        arr[num - 1] = r.num;
                    } else {
                        arr.push(r.num);
                    }
                    vm.timus = arr;
                } else {
                    var arr = vm.timus;
                    vm.timus = null;
                    if (num > 1 && arr.length < num) {
                        for (var i = 0; i < num - 1; i++) {
                            if (arr[i] == null || arr[i] == undefined) {
                                arr[i] = 0;
                            }
                        }
                    }
                    if (arr[num - 1] != null && arr[num - 1] != undefined) {
                        arr[num - 1] = 0;
                    } else {
                        arr.push(0);
                    }
                    vm.timus = arr;
                }

            })
        },
        change2: function (num) {
            var topicId = $("#topicId" + num).val();
            var courseId = vm.sysTest["courseId" + num];
            if (num != null && num != undefined && topicId != null && topicId != undefined
                && courseId != null && courseId != undefined) {
                vm.getCount(courseId, topicId, num);
            } else {
                vm.getCount("", "", num);
            }
        },
        getDept: function () {
            //加载部门树
            $.get(baseURL + "sys/dept/list", function (r) {
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.sysTest.deptId);
                if (node != null) {
                    ztree.selectNode(node);
                    vm.sysTest.deptName = node.name;
                }
            })
        },
        getDept2: function () {
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
        getAdminsList: function () {
            $.get(baseURL + "sys/systest/select", function (r) {
                vm.admins = r.list;
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
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.sysTest.deptId = node[0].deptId;
                    vm.sysTest.deptName = node[0].name;
                    $("#deptName").val(vm.sysTest.deptName);

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
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.q.deptId = node[0].deptId;
                    vm.sysTest.deptName = node[0].name;
                    $("#deptName").val(vm.sysTest.deptName);

                    layer.close(index);
                }
            });
        },
        parse: function (str) {
            return new String(str).replace("T", " ");
        },
        reParse: function (str) {
            return new String(str).replace(" ", "T");
        },
        saveOrUpdate: function (event) {
            var adminsList = vm.sysTest.adminsList;
            if (adminsList == undefined || adminsList == null || adminsList == "") {
                alert("评卷人不能为空！");
                return;
            }

            var deptName = $("#deptName");
            if (deptName == undefined || deptName == null || deptName == "") {
                alert("参考部门不能为空！");
                return;
            }

            var type = vm.sysTest.type;
            if (type == undefined || type == null || type == "") {
                alert("试题类型不能为空！");
                return;
            }

            if (vm.sysTest.startTime == undefined || vm.sysTest.startTime == null || vm.sysTest.startTime == "") {
                alert("开始时间不能为空！");
                return;
            }
            if (vm.sysTest.endTime == undefined || vm.sysTest.endTime == null || vm.sysTest.endTime == "") {
                alert("结束时间不能为空！");
                return;
            }
            if (vm.sysTest.inprice == undefined || vm.sysTest.inprice == null || vm.sysTest.inprice == "") {
                alert("通过分数不能为空！");
                return;
            }
            if (vm.sysTest.price == undefined || vm.sysTest.price == null || vm.sysTest.price == "") {
                alert("试题总分不能为空！");
                return;
            }
            var checks = vm.sysTest.typet;
            if (checks != undefined && checks != null) {
                if (checks == "随机组卷") {
                    vm.sysTest.sysTestEntityVos = null;
                    vm.sysTest.sysTestEntityVos = new Array();
                    var length = vm.nums2.length;
                    localStorage.setItem("total", length);
                    for (var i = 0; i < length; i++) {
                        var num = $("#subject" + (i + 1)).val();
                        if (num > vm.timus[i]) {
                            // alert("第" + (i + 1) + "列题库数量超出范围！");
                            // return;
                        }
                        var topicId = $("#topicId" + (i + 1)).val();
                        var courseId = vm.sysTest["courseId" + (i + 1)];
                        var courseName = vm.sysTest["courseName" + (i + 1)];
                        if (num != "" && num != null && num != undefined && topicId != null && topicId != "" && topicId != undefined
                            && courseId != null && courseId != "" && courseId != undefined) {
                            var sysTestEntityVo = {
                                "num": num,
                                "topicId": topicId,
                                "courseName": courseName,
                                "courseId": courseId
                            };
                            vm.sysTest.sysTestEntityVos.push(sysTestEntityVo);
                        } else {
                            // alert("第" + (i + 1) + "列题库题目信息填写错误，不能有空白项！");
                            // return;
                        }

                        localStorage.setItem("cachea" + (i + 1), courseId);
                        localStorage.setItem("cachew" + (i + 1), courseName);
                        localStorage.setItem("cachee" + (i + 1), vm.sysTest["courseIdList" + (i + 1)]);
                        localStorage.setItem("cacher" + (i + 1), vm.map["courseName" + (i + 1)]);
                        localStorage.setItem("cachet" + (i + 1), topicId);
                        localStorage.setItem("cachey" + (i + 1), vm.sysTest["topicName" + (i + 1)]);

                        delete vm.sysTest["courseId" + (i + 1)];
                        delete vm.sysTest["courseName" + (i + 1)];
                        delete vm.sysTest["courseIdList" + (i + 1)];
                        delete vm.map["courseName" + (i + 1)];
                        delete vm.sysTest["topicId" + (i + 1)];
                        delete vm.sysTest["topicName" + (i + 1)];
                    }
                }
            } else {
                alert("出题方式不能为空！");
                return;
            }

            //获取选择的数据
            var nodes = data_ztree.getCheckedNodes(true);
            var deptIdList = new Array();
            for (var i = 0; i < nodes.length; i++) {
                deptIdList.push(nodes[i].deptId);
            }
            vm.sysTest.deptIdList = deptIdList;

            vm.sysTest.startTime = vm.parse(vm.sysTest.startTime);
            vm.sysTest.endTime = vm.parse(vm.sysTest.endTime);
            $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function () {
                var url = vm.sysTest.testId == null ? "sys/systest/save" : "sys/systest/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.sysTest),
                    success: function (r) {
                        if (r.code === 0) {
                            var total = localStorage.getItem("total");
                            for (var i = 0; i < total; i++) {
                                localStorage.removeItem("cachea" + (i + 1));
                                localStorage.removeItem("cachew" + (i + 1));
                                localStorage.removeItem("cachee" + (i + 1));
                                localStorage.removeItem("cacher" + (i + 1));
                                localStorage.removeItem("cachet" + (i + 1));
                                localStorage.removeItem("cachey" + (i + 1));
                            }
                            localStorage.removeItem("total");

                            layer.msg("操作成功", {icon: 1});
                            vm.reload();
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        } else {
                            var total = localStorage.getItem("total");
                            for (var i = 0; i < total; i++) {
                                vm.sysTest["courseId" + (i + 1)] = localStorage.getItem("cachea" + (i + 1));
                                vm.sysTest["courseName" + (i + 1)] = localStorage.getItem("cachew" + (i + 1));
                                vm.sysTest["courseIdList" + (i + 1)] = localStorage.getItem("cachee" + (i + 1));
                                vm.map["courseName" + (i + 1)] = localStorage.getItem("cacher" + (i + 1));
                                vm.sysTest["topicId" + (i + 1)] = localStorage.getItem("cachet" + (i + 1));
                                vm.sysTest["topicName" + (i + 1)] = localStorage.getItem("cachey" + (i + 1));
                            }

                            layer.alert(r.msg);
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        }
                    }
                });
            });
        },
        del: function (event) {
            var testIds = getSelectedRows();
            if (testIds == null) {
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
                        url: baseURL + "sys/systest/delete",
                        contentType: "application/json",
                        data: JSON.stringify(testIds),
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
        getInfo: function (testId) {
            $.get(baseURL + "sys/systest/info/" + testId, function (r) {
                vm.sysTest = r.sysTest;
                vm.sysTest.startTime = vm.reParse(vm.sysTest.startTime);
                vm.sysTest.endTime = vm.reParse(vm.sysTest.endTime);
                $("#deptName").val(vm.sysTest.deptName);

                if (vm.sysTest.courses != null) {
                    vm.courses = new Array();
                    var courses = vm.sysTest.courses;
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

                var tests = vm.sysTest.testTypeList;
                if (tests != null && tests != "") {
                    var i;
                    for (i = 0; i < tests.length; i++) {
                        if (tests[i] == 4) {
                            $("#times").show();
                            break;
                        }
                    }
                } else {
                    vm.testTypeList = new Array();
                }

                var checks = vm.sysTest.typet;
                vm.typet = null;
                vm.typet = new Array();
                var old = {text: null, value: null};
                old.text = checks;
                old.value = checks;
                vm.typet.push(old);
                if (checks == "随机组卷") {

                    var sysTestEntityVos = vm.sysTest.sysTestEntityVos;
                    vm.nums2 = new Array();
                    for (var i = 0; i < sysTestEntityVos.length; i++) {
                        var num = i + 1;
                        vm.nums2.push(num);
                        create(num);
                        vm.sysTest["courseId" + num] = 0;
                        vm.sysTest["courseName" + num] = null;
                        vm.sysTest["courseIdList" + num] = [];
                        vm.sysTest["topicId" + num] = null;
                        vm.sysTest["topicName" + num] = null;
                        vm.getCourse(num - 1);

                        var courseId = sysTestEntityVos[i].courseId;
                        vm.sysTest["courseId" + num] = courseId;
                        var courseName = "courseName" + num;
                        vm.sysTest[courseName] = sysTestEntityVos[i].courseName;


                        if (vm.map != null) {
                            vm.map[courseName] = courseId;
                        } else {
                            vm.map = {};
                            vm.map[courseName] = courseId;
                        }
                        vm.getTopics(courseId, num);

                    }
                    setTimeout(function () {
                        for (var i = 0; i < sysTestEntityVos.length; i++) {
                            $("#courseName" + (i + 1)).val(sysTestEntityVos[i].courseName);
                            $("#subject" + (i + 1)).val(sysTestEntityVos[i].num);
                            var topicId2 = sysTestEntityVos[i].topicId;
                            $("#topicId" + (i + 1)).val(topicId2);
                            vm.getCount(sysTestEntityVos[i].courseId, topicId2, i + 1);
                        }
                    }, 6000);

                    $("#course").show();
                } else {
                    $("#course").hide();
                }

                vm.getRole(vm.sysTest.deptIdList);
            });
        },
        getRole: function (deptIdList) {
            for (var i = 0; i < deptIdList.length; i++) {
                var node = data_ztree.getNodeByParam("deptId", deptIdList[i]);
                data_ztree.checkNode(node, true, false);
            }
        },
        reload: function (event) {
            vm.showList = true;
            vm.showList2 = true;
            $("#show1").show();

            $("#times").hide();
            $("#course").hide();
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    "name": vm.q.name,
                    "deptId": vm.q.deptId,
                    "type": vm.q.type
                },
                page: page
            }).trigger("reloadGrid");
            vm.getDept2();
        }
    }
});