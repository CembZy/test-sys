$(function () {
    $("#show").show();
    vm.getCourse2();
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/sysbook/list',
        datatype: "json",
        colModel: [
            { label: 'id', sortable: false,name: 'bookId', index: 'book_id', width: 80, key: true },
            {label: '书籍名称', sortable: false,name: 'name', index: 'name', width: 180},
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
        showList2: true,
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
                vm.showList2 = false;
                vm.showList = true;
                $("#show").hide();
                vm.title = "书籍详情";

                $("#title").html(vm.sysbook.name);
                $("#content").html(vm.sysbook.body);
            });
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.showList2 = true;
            $("#show").hide();
            vm.title = "新增";
            vm.sysBook = {courseId: 0, courseName: null};

            vm.getCourse();
        },
        update: function (event) {
            var bookId = getSelectedRow();
            if (bookId == null) {
                return;
            }
            vm.showList = false;
            vm.showList2 = true;
            $("#show").hide();
            vm.title = "修改";

            vm.getInfo(bookId);
        },
        getCourse: function () {
            //加载部门树
            $.get(baseURL + "sys/syssubject/select", function (r) {
                ztree = $.fn.zTree.init($("#courseTree"), setting, r.courseList);
                var node = ztree.getNodeByParam("courseId", vm.sysBook.courseId);
                if (node != null) {
                    ztree.selectNode(node);
                    vm.sysBook.courseName = node.name;
                }
            })
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
                    vm.sysBook.courseId = node[0].courseId;
                    vm.sysBook.courseName = node[0].name;

                    $("#courseName").val(node[0].name);
                    layer.close(index);
                }
            });
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
        saveOrUpdate: function (event) {
            $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function () {
                var url = vm.sysBook.bookId == null ? "sys/sysbook/save" : "sys/sysbook/update";
                vm.sysBook.body = editor.txt.html();
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json;charset=utf-8",
                    data: JSON.stringify(vm.sysBook),
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
            var bookIds = getSelectedRows();
            if (bookIds == null) {
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
                        url: baseURL + "sys/sysbook/delete",
                        contentType: "application/json",
                        data: JSON.stringify(bookIds),
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
        getInfo: function (bookId) {
            $.get(baseURL + "sys/sysbook/info/" + bookId, function (r) {
                vm.sysBook = r.sysBook;

                editor.txt.html(vm.sysBook.body);

                vm.getCourse();
                $("#courseName").val(vm.sysBook.courseName);
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.showList2 = true;
            $("#show").show();
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