$(function () {
    var vm = new Vue({
        el: '#rrapp',
        data: {
            showList: true,
            title: null,
            sysNews: {}
        },
        methods: {
            query: function () {
                vm.reload();
            },
            getInfo: function () {
                var newId = getSelectedRow();
                if (newId == null) {
                    return;
                }
                $.get(baseURL + "sys/sysnews/info/" + newId, function (r) {
                    vm.sysNews = r.sysNews;
                    vm.showList = false;
                    vm.title = "新闻详情";

                    $("#title").html(vm.sysNews.title);
                    $("#content").html(vm.sysNews.body);
                });
            },
            reload: function (event) {
                vm.showList = true;
                var page = $("#jqGrid").jqGrid('getGridParam', 'page');
                $("#jqGrid").jqGrid('setGridParam', {
                    page: page
                }).trigger("reloadGrid");
            }
        }
    });


    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/sysnews/list',
        datatype: "json",
        colModel: [
            {label: 'id', sortable: false,name: 'newId', index: 'new_id', width: 80, key: true },
            {label: '新闻标题', sortable: false,name: 'title', index: 'title', width: 200},
            {label: '浏览量',sortable: false, name: 'num', index: 'num', width: 100},
            {label: '创建人', sortable: false,name: 'admin', index: 'admin', width: 180},
            {label: '创建时间',sortable: false, name: 'createTime', index: 'create_time', width: 320}
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

