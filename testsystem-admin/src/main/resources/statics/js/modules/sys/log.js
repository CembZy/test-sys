$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/log/list',
        datatype: "json",
        colModel: [			
			{ label: '用户名',sortable: false, name: 'username', width: 300 },
			{ label: '用户操作', sortable: false,name: 'operation', width: 300 },
			{ label: '请求方法', sortable: false,name: 'method', width: 300 },
			{ label: '请求参数', sortable: false,name: 'params', width: 200 },
            { label: '执行时长(毫秒)',sortable: false, name: 'time', width: 200 },
			{ label: 'IP地址', sortable: false,name: 'ip', width: 250 },
			{ label: '创建时间', sortable: false,name: 'createDate', width: 300 }
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
	el:'#rrapp',
	data:{
		q:{
			key: null
		},
	},
	methods: {
		query: function () {
			vm.reload();
		},
		reload: function (event) {
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'key': vm.q.key},
                page:page
            }).trigger("reloadGrid");
		}
	}
});