$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/sysjungle/list',
        datatype: "json",
        colModel: [			
			{ label: 'jungleId',sortable: false, name: 'jungleId', index: 'jungle_id', width: 150, key: true },
			{ label: '题目id', sortable: false,name: 'subjectId', index: 'subject_id', width: 380 },
			{ label: '内容', sortable: false,name: 'content', index: 'content', width: 380 },
			{ label: '选项', sortable: false,name: 'name', index: 'name', width: 380 },
			{ label: '创建人',sortable: false, name: 'admin', index: 'admin', width: 380 },
			{ label: '创建时间',sortable: false, name: 'createTime', index: 'create_time', width: 380 }
        ],
        height: 330,
        rowNum: 9,
        rowList: [9, 27, 54, 81],
        viewrecords: true,
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        shrinkToFit: false,
        autoScroll: true, //开启水平滚动条
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
             $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		sysJungle: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysJungle = {};
		},
		update: function (event) {
			var jungleId = getSelectedRow();
			if(jungleId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(jungleId)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.sysJungle.jungleId == null ? "sys/sysjungle/save" : "sys/sysjungle/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.sysJungle),
                    success: function(r){
                        if(r.code === 0){
                             layer.msg("操作成功", {icon: 1});
                             vm.reload();
                             $('#btnSaveOrUpdate').button('reset');
                             $('#btnSaveOrUpdate').dequeue();
                        }else{
                            layer.alert(r.msg);
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        }
                    }
                });
			});
		},
		del: function (event) {
			var jungleIds = getSelectedRows();
			if(jungleIds == null){
				return ;
			}
			var lock = false;
            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定','取消'] //按钮
            }, function(){
               if(!lock) {
                    lock = true;
		            $.ajax({
                        type: "POST",
                        url: baseURL + "sys/sysjungle/delete",
                        contentType: "application/json",
                        data: JSON.stringify(jungleIds),
                        success: function(r){
                            if(r.code == 0){
                                layer.msg("操作成功", {icon: 1});
                                $("#jqGrid").trigger("reloadGrid");
                            }else{
                                layer.alert(r.msg);
                            }
                        }
				    });
			    }
             }, function(){
             });
		},
		getInfo: function(jungleId){
			$.get(baseURL + "sys/sysjungle/info/"+jungleId, function(r){
                vm.sysJungle = r.sysJungle;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});