$(function () {
    vm.getDept2();
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/user/list?type=2',
        datatype: "json",
        colModel: [
            {label: 'id', sortable: false, name: 'userId', index: 'user_id', width: 80, key: true},
            {label: '所属部门', name: 'deptName', sortable: false, width: 425},
            {label: '用户名', sortable: false, name: 'username', width: 125},
            {label: '密码', sortable: false, name: 'passw', width: 125},
            {label: '姓名', sortable: false, name: 'name', width: 130},
            {label: '性别', sortable: false, name: 'sex', width: 80},
            {label: '证件类型', sortable: false, name: 'idtype', width: 200},
            {label: '证件号码', sortable: false, name: 'idcard', width: 200},
            {label: '手机号', sortable: false, name: 'mobile', width: 200},
            {label: '邮箱', sortable: false, name: 'email', width: 120},
            {label: '创建时间', name: 'createTime', index: "create_time", sortable: false, width: 225}
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
var setting = {
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
    }
};
var ztree;

$(".file").on("change", "input[type='file']", function () {
    var filePath = $(this).val();
    if (filePath.indexOf("jpg") != -1 || filePath.indexOf("png") != -1) {
        $(".fileerrorTip").html("").hide();
        var arr = filePath.split('\\');
        var fileName = arr[arr.length - 1];
        $(".showFileName").html(fileName);
    } else {
        $(".showFileName").html("");
        $(".fileerrorTip").html("您未上传文件，或者您上传文件类型有误！").show();
        return false
    }
});


var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            username: null,
            deptName: null,
            deptId: null
        },
        showList: true,
        showList2: true,
        showList3: true,
        title: null,
        roleList: {},
        user: {
            status: 1,
            deptId: null,
            deptName: null,
            roleIdList: []
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.showList2 = true;
            vm.showList3 = true;
            vm.title = "新增";
            vm.roleList = {};
            vm.user = {deptName: null, deptId: null, status: 1, roleIdList: []};
            $("#deptName").val("所属部门");
            $("#deptName2").val("所属部门");
            $("#deptName3").val("所属部门");

            //获取角色信息
            this.getRoleList();

            vm.getDept();
        },
        adds: function () {
            vm.showList2 = false;
            vm.showList = true;
            vm.showList3 = true;
            $("#show").hide();
            vm.title = "新增";
            vm.roleList = {};
            vm.user = {deptName: null, deptId: null, status: 1, roleIdList: []};

            //获取角色信息
            this.getRoleList();

            vm.getDept();
        },
        input: function () {
            vm.showList3 = false;
            vm.showList = true;
            vm.showList2 = true;
            $("#show").hide();
            vm.title = "新增";
            vm.roleList = {};
            vm.user = {deptName: null, deptId: null, status: 1, roleIdList: []};

            //获取角色信息
            this.getRoleList();

            vm.getDept();
        },
        output: function () {
            var userIds = getSelectedRows();
            if (userIds == null) {
                return;
            }
            location.href = baseURL + "sys/user/outport?userIds=" + userIds;
        },
        getDept: function () {
            //加载部门树
            $.get(baseURL + "sys/dept/list", function (r) {
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.user.deptId);
                if (node != null) {
                    ztree.selectNode(node);

                    vm.user.deptName = node.name;
                }
            })
        },
        getDept2: function () {
            //加载部门树
            $.get(baseURL + "sys/dept/list", function (r) {
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.q.deptId);
                if (node != null) {
                    ztree.selectNode(node);

                    vm.q.deptName = node.name;
                }
            })
        },
        update: function () {
            var userId = getSelectedRow();
            if (userId == null) {
                return;
            }

            vm.showList = false;
            vm.showList2 = true;
            vm.showList3 = true;
            vm.title = "修改";

            vm.getUser(userId);
            //获取角色信息
            this.getRoleList();
        },
        permissions: function () {
            var userId = getSelectedRow();
            if (userId == null) {
                return;
            }

            window.location.href = baseURL + "sys/permissions/index/" + userId;
        },
        del: function () {
            var userIds = getSelectedRows();
            if (userIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/user/delete",
                    contentType: "application/json",
                    data: JSON.stringify(userIds),
                    success: function (r) {
                        if (r.code == 0) {
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
        saveOrUpdate: function () {
            var url = vm.user.userId == null ? "sys/user/save" : "sys/user/update";
            vm.user.type = 2;
            vm.user.passw = vm.user.password;
            vm.user.status=1;
            if (vm.user.username == undefined || vm.user.username == null || vm.user.username == "") {
                alert("用户名不能为空!");
                return;
            }
            if (vm.user.password == undefined || vm.user.password == null || vm.user.password == "") {
                alert("密码不能为空!");
                return;
            }
            if (vm.user.name == undefined || vm.user.name == null || vm.user.name == "") {
                alert("昵称不能为空!");
                return;
            }
            if (vm.user.roleIdList == undefined || vm.user.roleIdList == null || vm.user.roleIdList.length == 0) {
                alert("角色必须选择!");
                return;
            }
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.user),
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
        saveOrUpdate2: function () {
            var url = vm.user.userId == null ? "sys/user/saves" : "sys/user/update";
            vm.user.type = 2;
            if (vm.user.username == undefined || vm.user.username == null || vm.user.username == "") {
                alert("用户名不能为空!");
                return;
            }
            if (vm.user.password == undefined || vm.user.password == null || vm.user.password == "") {
                alert("密码不能为空!");
                return;
            }
            if (vm.user.name == undefined || vm.user.name == null || vm.user.name == "") {
                alert("昵称不能为空!");
                return;
            }
            vm.user.status=1;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.user),
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
        saveOrUpdate3: function () {
            var fileObj = document.getElementById("sc").files[0];
            var formFile = new FormData();
            formFile.append("action", "UploadVMKImagePath");
            formFile.append("file", fileObj); //加入文件对象
            $.ajax({
                url: baseURL + "public/importUsers",
                dataType: 'json',
                type: 'POST',
                async: false,
                data: formFile,
                processData: false, // 使数据不做处理
                contentType: false, // 不要设置Content-Type请求头
                success: function (data) {
                    if (data.msg == 'success') {
                        var url = vm.user.userId == null ? "sys/user/import" : "sys/user/update";
                        vm.user.type = 2;
                        vm.user.password = data.key;
                        $.ajax({
                            type: "POST",
                            url: baseURL + url,
                            contentType: "application/json",
                            data: JSON.stringify(vm.user),
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
                    }

                },
                error: function (response) {
                    alert("文件上传失败,请重新上传");
                }
            });

        },
        getUser: function (userId) {
            $.get(baseURL + "sys/user/info/" + userId, function (r) {
                vm.user = r.user;
                vm.user.password = vm.user.passw;
                $("#deptName").val(vm.user.deptName);
                $("#deptName2").val(vm.user.deptName);
                $("#deptName3").val(vm.user.deptName);

                vm.getDept();
            });
        },
        getRoleList: function () {
            $.get(baseURL + "sys/role/select", function (r) {
                vm.roleList = r.list;
            });
        },
        deptTree: function () {
            layer.open({
                type: 1,
                offset: '0px',
                skin: 'layui-layer-molv',
                title: "选择部门",
                area: ['280px', '400px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.user.deptId = node[0].deptId;
                    vm.user.deptName = node[0].name;

                    layer.close(index);
                }
            });
        },
        deptTree2: function () {
            layer.open({
                type: 1,
                offset: '0px',
                skin: 'layui-layer-molv',
                title: "选择部门",
                area: ['280px', '400px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.q.deptId = node[0].deptId;
                    vm.q.deptName = node[0].name;
                    $("#deptName").val(node[0].name);
                    $("#deptName2").val(node[0].name);
                    $("#deptName3").val(node[0].name);
                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            vm.showList2 = true;
            vm.showList3 = true;
            $("#show").show();
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    'username': vm.q.username,
                    "type": 2,
                    "deptId": vm.q.deptId
                },
                page: page
            }).trigger("reloadGrid");
            vm.getDept2();
        }
    }
});