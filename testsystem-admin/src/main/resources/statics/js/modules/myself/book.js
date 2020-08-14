$(function () {
    vm.getCourse2();
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/sysbook/list',
        datatype: "json",
        colModel: [
            { label: 'id', sortable: false,name: 'bookId', index: 'book_id', width: 80, key: true },
            {label: '书籍名称',sortable: false, name: 'name', index: 'name', width: 180},
            {label: '所属科目',sortable: false, name: 'courseName', index: 'courseName', width: 380},
            {label: '浏览次数',sortable: false, name: 'num', index: 'num', width: 100},
            {label: '创建时间', sortable: false,name: 'createTime', index: 'create_time', width: 320}
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


var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            courseId: null,
            courseName: null,
            name: null
        },
        showList: true,
        title: null,
        sysBook: {courseId: null, courseName: null}
    },
    methods: {
        getNewsInfo: function () {
            var newId = getSelectedRow();
            if (newId == null) {
                return;
            }
            $.get(baseURL + "sys/sysbook/info/" + newId, function (r) {
                vm.sysbook = r.sysBook;
                vm.showList = false;
                vm.title = "书籍详情";

                $("#title").html(vm.sysbook.name);
                $("#content").html(vm.sysbook.body);
            });
        },
        query: function () {
            vm.reload();
        },
        getCourse2: function () {
            //加载部门树
            $.get(baseURL + "sys/syssubject/select", function (r) {
                ztree = $.fn.zTree.init($("#courseTree"), setting, r.courseList);
                var node = ztree.getNodeByParam("courseId", vm.q.courseId);
                if (node != null) {
                    ztree2.selectNode(node);
                    vm.q.courseName = node.name;
                }
            })
        },
        courseTree2: function () {
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
                    vm.q.courseId = node[0].courseId;
                    vm.q.courseName = node[0].name;

                    layer.close(index);
                }
            });
        },
        getInfo: function (bookId) {
            $.get(baseURL + "sys/sysbook/info/" + bookId, function (r) {
                vm.sysBook = r.sysBook;
                vm.getCourse();
                $("#courseName").val(vm.sysBook.courseName);
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    "courseId": vm.q.courseId,
                    "name": vm.q.name
                },
                page: page
            }).trigger("reloadGrid");
            vm.getCourse2();
        }
    }
});