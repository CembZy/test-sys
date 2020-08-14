$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/sysanswer/list',
        datatype: "json",
        colModel: [			
			{ label: 'answerId', sortable: false,name: 'answerId', index: 'answer_id', width: 80, key: true },
			{ label: '答题人id', sortable: false,name: 'userId', index: 'user_id', width: 380 },
			{ label: '试卷id', sortable: false,name: 'testId', index: 'test_id', width: 280 },
			{ label: '试题id', sortable: false,name: 'subjectId', index: 'subject_id', width: 280 },
			{ label: '答案内容', sortable: false,name: 'content', index: 'content', width: 280 },
			{ label: '创建时间', sortable: false,name: 'createTime', index: 'create_time', width: 280 }
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
    setStyWidth();
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		sysAnswer: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysAnswer = {};
		},
		update: function (event) {
			var answerId = getSelectedRow();
			if(answerId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(answerId)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.sysAnswer.answerId == null ? "sys/sysanswer/save" : "sys/sysanswer/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.sysAnswer),
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
			var answerIds = getSelectedRows();
			if(answerIds == null){
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
                        url: baseURL + "sys/sysanswer/delete",
                        contentType: "application/json",
                        data: JSON.stringify(answerIds),
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
		getInfo: function(answerId){
			$.get(baseURL + "sys/sysanswer/info/"+answerId, function(r){
                vm.sysAnswer = r.sysAnswer;
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