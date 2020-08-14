$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/systopic/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'topicId', sortable: false, index: 'topic_id', width: 80, key: true},
            {label: '题型', name: 'type', sortable: false, index: 'type', width: 250},
            {label: '题型名称', name: 'name2', sortable: false, index: 'name2', width: 350},
            // {
            //     label: '状态', name: 'status', sortable: false, width: 100, formatter: function (value, options, row) {
            //         return value === 0 ?
            //             '<span class="label label-danger">禁用</span>' :
            //             '<span class="label label-success">正常</span>';
            //     }
            // },
            {label: '创建人', name: 'admin', index: 'admin', sortable: false, width: 250},
            {label: '创建时间', name: 'createTime', index: 'create_time', sortable: true, width: 350}
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
        items: [{text: '单选类', value: '单选类'}, {text: '多选类', value: '多选类'}, {text: '判断类', value: '判断类'}, {
            text: '填空类',
            value: '填空类'
        }
            , {text: '问答类', value: '问答类'}, {text: '作文类', value: '作文类'}, {text: '打字类', value: '打字类'}, {
                text: '操作类',
                value: '操作类'
            }],
        selected: '单类',
        showList: true,
        title: null,
        q: {
            type: null,
            name: null
        },
        sysTopic: {
            status: 1
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.sysTopic = {status: 1};
        },
        update: function (event) {
            var topicId = getSelectedRow();
            if (topicId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(topicId)
        },
        saveOrUpdate: function (event) {
            vm.sysTopic.name = vm.sysTopic.type;
            vm.sysTopic.status=1;
            $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function () {
                var url = vm.sysTopic.topicId == null ? "sys/systopic/save" : "sys/systopic/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.sysTopic),
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
            var topicIds = getSelectedRows();
            if (topicIds == null) {
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
                        url: baseURL + "sys/systopic/delete",
                        contentType: "application/json",
                        data: JSON.stringify(topicIds),
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
        getInfo: function (topicId) {
            $.get(baseURL + "sys/systopic/info/" + topicId, function (r) {
                vm.sysTopic = r.sysTopic;
                vm.selected = r.sysTopic.type;
                vm.sysTopic.name2 = r.sysTopic.name2;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData: {'type': vm.q.type, 'name': vm.q.name}
            }).trigger("reloadGrid");
        }
    }
});