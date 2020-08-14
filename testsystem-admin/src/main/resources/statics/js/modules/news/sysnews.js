$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/sysnews/list',
        datatype: "json",
        colModel: [
            {label: 'id', sortable: false, name: 'newId', index: 'new_id', width: 80, key: true},
            {label: '新闻标题', sortable: false, name: 'title', index: 'title', width: 125},
            {label: '浏览量', sortable: false, name: 'num', index: 'num', width: 100},
            {label: '创建人', sortable: false, name: 'admin', index: 'admin', width: 125},
            {label: '创建时间', sortable: false, name: 'createTime', index: 'create_time', width: 325}
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
        q: {
            title: null
        },
        showList: true,
        showList2: true,
        title: null,
        sysNews: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        getNewsInfo: function () {
            var newId = getSelectedRow();
            if (newId == null) {
                return;
            }
            $.get(baseURL + "sys/sysnews/info/" + newId, function (r) {
                vm.sysNews = r.sysNews;
                vm.showList2 = false;
                vm.showList = true;
                $("#show1").hide();
                vm.title = "新闻详情";

                $("#title").html(vm.sysNews.title);
                $("#content").html(vm.sysNews.body);
            });
        },
        add: function () {
            vm.showList = false;
            vm.showList2 = true;
            vm.title = "新增";
            vm.sysNews = {};
            editor.txt.html("");
            vm.getDataTree();
        },
        update: function (event) {
            var newId = getSelectedRow();
            if (newId == null) {
                return;
            }
            vm.showList = false;
            vm.showList2 = true;
            vm.title = "修改";
            vm.getDataTree();
            sleep(1000);
            vm.getInfo(newId);
        },
        saveOrUpdate: function (event) {
            $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function () {
                //获取选择的数据
                var nodes = data_ztree.getCheckedNodes(true);
                var deptIdList = new Array();
                for (var i = 0; i < nodes.length; i++) {
                    deptIdList.push(nodes[i].deptId);
                }
                vm.sysNews.deptIdList = deptIdList;

                vm.sysNews.body = editor.txt.html();
                var url = vm.sysNews.newId == null ? "sys/sysnews/save" : "sys/sysnews/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json;charset=utf-8",
                    data: JSON.stringify(vm.sysNews),
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
        getRole: function (deptIdList) {
            for (var i = 0; i < deptIdList.length; i++) {
                var node = data_ztree.getNodeByParam("deptId", deptIdList[i]);
                data_ztree.checkNode(node, true, false);
            }
        },
        del: function (event) {
            var newIds = getSelectedRows();
            if (newIds == null) {
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
                        url: baseURL + "sys/sysnews/delete",
                        contentType: "application/json",
                        data: JSON.stringify(newIds),
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
        getDataTree: function (roleId) {
            //加载菜单树
            $.get(baseURL + "sys/dept/list", function (r) {
                data_ztree = $.fn.zTree.init($("#dataTree"), data_setting, r);
                //展开所有节点
                data_ztree.expandAll(true);
            });
        },
        getInfo: function (newId) {
            $.get(baseURL + "sys/sysnews/info/" + newId, function (r) {
                vm.sysNews = r.sysNews;
                editor.txt.html(r.sysNews.body);

                vm.getRole(vm.sysNews.deptIdList);
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.showList2 = true;
            $("#show1").show();
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    "title": vm.q.title
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});


/** 富文本**/

var E = window.wangEditor;
var editor = new E('#editor');
// 自定义菜单配置
editor.customConfig.menus = [
    'head',  // 标题
    'bold',  // 粗体
    'fontSize',  // 字号
    'fontName',  // 字体
    'italic',  // 斜体
    'underline',  // 下划线
    'strikeThrough',  // 删除线
    'foreColor',  // 文字颜色
    'backColor',  // 背景颜色
    'justify',  // 对齐方式
    'emoticon',  // 表情
    'image',  // 插入图片
    'table',  // 表格
    'head', // 标题
    'link', // 插入链接
    'list', // 列表
    'quote', // 引用
    'code', // 插入代码
    'undo', // 撤销
    'redo' // 重复]
];


// 隐藏“网络图片”tab
editor.customConfig.showLinkImg = false;

editor.customConfig.uploadImgServer = baseURL + 'public/uploadFile';
editor.customConfig.uploadImgHeaders = {
    token: localStorage.getItem("token")  // 属性值会自动进行 encode ，此处无需 encode
};
editor.customConfig.uploadFileName = 'upfile';
editor.customConfig.uploadImgHooks = {
    before: function (xhr, editor, files) {
        // 图片上传之前触
    },
    success: function (xhr, editor, result) {
        if (!result.msg == "success") {
            layer.alert("图片上传失败");
        }
    },
    customInsert: function (insertImg, result, editor) {
        // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果
        var url = result.fileUrl;
        insertImg(url);
    },
    fail: function (xhr, editor, result) {
        // 图片上传并返回结果，但图片插入错误时触发
        alert('插入图片出错')
    },
    error: function (xhr, editor) {
        // 图片上传出错时触发
        alert('上传图片出错');
    },
    timeout: function (xhr, editor) {
        // 图片上传超时时触发
        alert('请求超时');
    }
};
editor.create();

