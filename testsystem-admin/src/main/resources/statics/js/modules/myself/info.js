$(function () {
    vm.getUser3();
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
var userId;
var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: false,
        title: "信息维护",
        user: {
            deptId: 0,
            deptName: null
        }
    },
    methods: {
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
                var node = ztree.getNodeByParam("deptId", vm.user.deptId);
                if (node != null) {
                    ztree.selectNode(node);
                    vm.user.deptName = node.name;
                }
            })
        },
        update: function () {
            vm.getUser3();
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
        getUser3: function () {
            $.get(baseURL + "sys/user/info2", function (r) {
                vm.user = r.user;
                if (vm.user.type == 1) {
                    vm.showList = false;
                    $("#deptName").val(vm.user.deptName);
                } else {
                    vm.showList = true;
                    $("#deptName2").val(vm.user.deptName);
                }
                vm.user.password = null;
                vm.user.password = vm.user.passw;
                userId = vm.user.userId;
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

                    $("#deptName").val(node[0].name);
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
                    vm.user.deptId = node[0].deptId;
                    vm.user.deptName = node[0].name;

                    $("#deptName2").val(node[0].name);
                    layer.close(index);
                }
            });
        },
        save: function (type) {
            vm.user.type = type;
            vm.user.userId = userId;
            $.ajax({
                type: "POST",
                url: baseURL + "sys/user/update2",
                contentType: "application/json",
                data: JSON.stringify(vm.user),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.getUser3();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        saveOrUpdate2: function () {
            vm.save(2);
        },
        saveOrUpdate1: function () {
            vm.save(1);
        }
    }
});