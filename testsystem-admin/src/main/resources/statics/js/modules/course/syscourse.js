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
        showList: true,
        title: null,
        sysCourse: {
            parentName: null,
            courseId: 0,
            orderNum: 0
        }
    },
    methods: {
        getCourse: function () {
            //加载部门树
            $.get(baseURL + "sys/syscourse/select", function (r) {
                ztree = $.fn.zTree.init($("#courseTree"), setting, r.courseList);
                var node = ztree.getNodeByParam("courseId", vm.sysCourse.parentId);
                if (node != null) {
                    ztree.selectNode(node);
                    vm.sysCourse.parentName = node.name;
                }
            })
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.sysCourse = {
                parentName: null, parentId: 0, orderNum: 0
            };
            vm.getCourse();
        },
        update: function (event) {
            var courseId = getCourseId();
            if (courseId == null) {
                return;
            }

            $.get(baseURL + "sys/syscourse/info/" + courseId, function (r) {
                vm.showList = false;
                vm.title = "修改";
                vm.sysCourse = r.sysCourse;

                vm.getCourse();
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.sysCourse.courseId == null ? "sys/syscourse/save" : "sys/syscourse/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.sysCourse),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var courseIds = getCourseId2();
            if (courseIds == null) {
                return;
            }
            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/syscourse/delete",
                    contentType: "application/json",
                    data: JSON.stringify(courseIds),
                    success: function (r) {
                        if (r.code === 0) {
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
                    vm.sysCourse.parentId = node[0].courseId;
                    vm.sysCourse.parentName = node[0].name;

                    layer.close(index);
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            Course.table.refresh();
        }
    }
});


var Course = {
    id: "courseTable",
    table: null,
    layerIndex: -1
};


/**
 * 初始化表格的列
 */
Course.initColumn = function () {
    var columns = [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'courseId', visible: false, align: 'center', valign: 'middle', width: '80px', key: true},
        {title: '科目名称', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '上级科目', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '创建人', field: 'admin', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true, width: '180px'}]
    return columns;
};


function getCourseId() {
    var selected = $('#courseTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        return selected[0].id;
    }
}

function getCourseId2() {
    var selected = $('#courseTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        var ids = new Array();
        for (var i = 0; i < selected.length; i++) {
            ids[i] = selected[i].id;
        }
        return ids;
    }
}

$(function () {
    $.get(baseURL + "sys/syscourse/info", function (r) {
        var colunms = Course.initColumn();
        var table = new TreeTable(Course.id, baseURL + "sys/syscourse/list", colunms);
        table.setRootCodeValue(r.courseId);
        table.setExpandColumn(2);
        table.setIdField("courseId");
        table.setCodeField("courseId");
        table.setParentCodeField("parentId");
        table.setExpandAll(false);
        table.init();
        Course.table = table;
    });
});
