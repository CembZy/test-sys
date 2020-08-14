$(function () {
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
    var vm = new Vue({
        el: '#rrapp',
        data: {
            q: {
                courseName: null,
                courseId: null,
                difficulty: null,
                topicName: null
            },
            showList: true,
            title: null,
            sysSubject: {
                courseName: null,
                courseId: 0
            }
        },
        methods: {
            getCourse: function () {
                //加载部门树
                $.get(baseURL + "sys/syssubject/select", function (r) {
                    ztree = $.fn.zTree.init($("#courseTree"), setting, r.courseList);
                    var node = ztree.getNodeByParam("courseId", vm.q.courseId);
                    if (node != undefined && node != null) {
                        ztree.selectNode(node);

                        vm.q.courseName = node.name;
                    }
                })
            },
            query: function () {
                vm.reload();
            },
            courseTree: function () {
                layer.open({
                    type: 1,
                    offset: '0px',
                    skin: 'layui-layer-molv',
                    title: "选择科目",
                    area: ['300px', '420px'],
                    shade: 0,
                    shadeClose: false,
                    content: jQuery("#courseLayer"),
                    btn: ['确定', '取消'],
                    btn1: function (index) {
                        var node = ztree.getSelectedNodes();
                        //选择上级部门
                        vm.q.courseId = node[0].courseId;
                        vm.q.courseName = node[0].name;

                        layer.close(index);
                    }
                });
            },
            reload: function (event) {
                vm.showList = true;
                var page = $("#jqGrid").jqGrid('getGridParam', 'page');
                $("#jqGrid").jqGrid('setGridParam', {
                    postData: {
                        "courseId": vm.q.courseId,
                        "difficulty": vm.q.difficulty,
                        "topicName": vm.q.topicName
                    },
                    page: page
                }).trigger("reloadGrid");
                vm.getCourse();
            }
        }
    });

    vm.getCourse();
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/syssubject/list2',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'subjectId',sortable: false, index: 'subject_id', width: 80, key: true },
            {label: '科目名称', name: 'courseName', sortable: false, width: 180},
            {label: '知识点', name: 'courseNameT', sortable: false, width: 480},
            {label: '名称', name: 'topicName', sortable: false, width: 100},
            {label: '难度', name: 'difficulty', sortable: false, width: 80},
            {label: '数量', name: 'num2', sortable: false,index: 'num2', width: 80}
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

