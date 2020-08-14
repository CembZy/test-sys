$(function () {
    $("#select").hide();
    $("#selectnum").hide();
    $("#answert").hide();
    $("#answertt").hide();
    $("#file").hide();
    $("#tofile").hide();

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
    var arr = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'];

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
                courseName: null,
                courseId: null,
                topicName: null
            },
            showList: true,
            showList2: true,
            showList3: true,
            title: null,
            sysSubject: {
                courseName: null,
                courseId: 0,
                courseIdList: [],
                answertt: ""
            },
            courseList: {},
            items: null,
            items2: [{text: '易', value: '易'}, {text: '较易', value: '较易'}, {text: '中等', value: '中等'}, {
                text: '较难',
                value: '较难'
            }
                , {text: '难', value: '难'}],
            items3: [{text: '2', value: 2}, {text: '3', value: 3}, {text: '4', value: 4}, {text: '5', value: 5}
                , {text: '6', value: 6}],
            nums1: null,
            selected: null,
            selected2: '易',
            selected3: 2
        },
        methods: {
            getSelected: function (value) {
                var is = vm.items;
                var item;
                for (item = 0; item < is.length; item++) {
                    if (value == is[item].value) {
                        return is[item].text2;
                    }
                }
            },
            changeCatalog: function () {
                var text2 = vm.getSelected(vm.sysSubject.topicId);
                if (text2 == undefined || text2 == null) {
                    return;
                }
                var text = text2.split("-")[1];
                if (text != undefined && (text.indexOf("单") != -1 || text.indexOf("多") != -1)) {
                    var num = vm.sysSubject.num;
                    vm.dymic(num, "未编辑");
                    $("#answertt").show();
                    $("#selectnum").show();
                    $("#answer").hide();
                    $("#answert").hide();
                    $("#file").hide();
                    $("#tofile").hide();
                } else if (text != undefined && text.indexOf("判断") != -1) {
                    $("#answert").show();
                    $("#answer").hide();
                    $("#file").hide();
                    $("#select").hide();
                    $("#selectnum").hide();
                    $("#answertt").hide();
                    $("#tofile").hide();
                } else if (text != undefined && text.indexOf("操作") != -1) {
                    $("#file").show();
                    var fileUrl = vm.sysSubject.file;
                    if (fileUrl != undefined && fileUrl != "") {
                        $("#tofile").show();
                        $("#fileUrl").attr("href", fileUrl);
                    } else {
                        $("#tofile").hide();
                    }
                    $("#select").hide();
                    $("#answertt").hide();
                    $("#answert").hide();
                    $("#answer").hide();
                    $("#selectnum").hide();
                } else {
                    $("#select").hide();
                    $("#answertt").hide();
                    $("#answert").hide();
                    $("#file").hide();
                    $("#answer").show();
                    $("#selectnum").hide();
                    $("#tofile").hide();
                }
            },
            dymic: function (num, str) {
                var i;
                vm.nums1 = null;
                vm.nums1 = new Array();
                for (i = 0; i < num; i++) {
                    var old = {text: null, value: null};
                    old.text = arr[i];
                    old.value = str;
                    old.text3 = false;
                    vm.nums1.push(old);
                }
                $("#select").show();
            },
            dymic2: function (num, str) {
                var arrt = vm.nums1;
                vm.nums1 = null;
                if (arrt != null) {
                    arrt[num].value = str;
                    vm.nums1 = arrt;
                }
            },
            dymic3: function (num, str) {
                var arrt = vm.nums1;
                vm.nums1 = null;
                if (arrt != null) {
                    arrt[num].text3 = str;
                    vm.nums1 = arrt;
                }
            },
            changeNum: function (num) {
                var num = vm.sysSubject.num;
                vm.dymic(num, "未编辑");
            },
            change4: function () {
                var num = vm.nums1.length;
                for (var i = 0; i < num; i++) {
                    if ($("#" + vm.nums1[i].text + "checkbox").is(":checked")) {
                        vm.dymic3(i, true);
                    } else {
                        vm.dymic3(i, false);
                    }
                }
            },
            getCourse: function () {
                //加载部门树
                $.get(baseURL + "sys/syssubject/select", function (r) {
                    ztree = $.fn.zTree.init($("#courseTree"), setting, r.courseList);
                    var node = ztree.getNodeByParam("courseId", vm.sysSubject.courseId);
                    if (node != null) {
                        ztree.selectNode(node);

                        vm.sysSubject.courseName = node.name;
                    }
                })
            },
            getCourse2: function () {
                //加载部门树
                $.get(baseURL + "sys/syssubject/select", function (r) {
                    ztree = $.fn.zTree.init($("#courseTree"), setting, r.courseList);
                    var node = ztree.getNodeByParam("courseId", vm.q.courseId);
                    if (node != null) {
                        ztree.selectNode(node);
                        vm.q.courseName = node.name;
                    }
                })
            },
            query: function () {
                vm.reload();
            },
            add: function () {
                vm.showList = false;
                vm.showList2 = true;
                vm.showList3 = true;
                vm.title = "新增";
                vm.sysSubject = {
                    courseName: null,
                    courseId: 0,
                    difficulty: '易',
                    num: 2,
                    courseIdList: [],
                    answertt: ""
                };
                vm.nums1 = null;
                vm.getTopics();

                vm.getCourse();
            },
            update: function (event) {
                var subjectId = getSelectedRow();
                if (subjectId == null) {
                    return;
                }
                vm.showList = false;
                vm.showList2 = true;
                vm.showList3 = true;
                vm.title = "修改";
                vm.nums1 = null;
                vm.getTopics();
                vm.getInfo(subjectId)
            },
            input: function () {
                vm.title = "新增";
                vm.sysSubject = {
                    courseName: null,
                    courseId: 0,
                    difficulty: '易',
                    num: 2,
                    courseIdList: []
                };
                vm.nums1 = null;
                vm.showList3 = false;
                vm.showList = true;
                vm.showList2 = true;
                $("#show1").hide();
                vm.getCourse();
            },
            output: function () {
                var subjectIds = getSelectedRows();
                if (subjectIds == null) {
                    return;
                }
                location.href = baseURL + "sys/syssubject/output?subjectIds=" + subjectIds;
            },
            clear: function () {
                localStorage.removeItem("content");
                localStorage.removeItem("parse");
                localStorage.removeItem("answer");
                localStorage.removeItem("type");
                var num = vm.sysSubject.num;
                for (i = 0; i < num; i++) {
                    localStorage.removeItem(arr[i]);
                }
            },
            cleart: function () {
                vm.edit("content", "&nbsp;未编辑");
                vm.edit("answer", "&nbsp;未编辑");
                vm.edit("parse", "&nbsp;未编辑");
                var num = vm.sysSubject.num;
                for (i = 0; i < num; i++) {
                    vm.dymic2(i, "&nbsp;未编辑");
                }
            },
            saveOrUpdate: function () {
                var text2 = vm.getSelected(vm.sysSubject.topicId);
                if (text2 == undefined || text2 == null) {
                    return;
                }
                var text = text2.split("-")[1];
                if (text != undefined && !text.indexOf("操作") != -1) {
                    vm.saveOrUpdate3();
                    return;
                }
                var fileObj = document.getElementById("sc").files[0];
                var formFile = new FormData();
                formFile.append("action", "UploadVMKImagePath");
                formFile.append("file", fileObj); //加入文件对象
                $.ajax({
                    url: baseURL + "public/uploadFile",
                    dataType: 'json',
                    type: 'POST',
                    async: false,
                    data: formFile,
                    processData: false, // 使数据不做处理
                    contentType: false, // 不要设置Content-Type请求头
                    success: function (data) {
                        if (data.code === 0) {
                            vm.sysSubject.file = data.fileUrl;
                            vm.saveOrUpdate3();
                        } else {
                            alert("文件上传失败,请重新上传");
                        }
                    },
                    error: function (response) {
                        alert("文件上传失败,请重新上传");
                    }
                });
            },
            saveOrUpdate4: function () {
                var fileObj = document.getElementById("up").files[0];
                var formFile = new FormData();
                formFile.append("action", "UploadVMKImagePath");
                formFile.append("file", fileObj); //加入文件对象
                $.ajax({
                    url: baseURL + "public/importTests",
                    dataType: 'json',
                    type: 'POST',
                    async: false,
                    data: formFile,
                    processData: false, // 使数据不做处理
                    contentType: false, // 不要设置Content-Type请求头
                    success: function (data) {
                        if (data.msg == 'success') {
                            vm.sysSubject.uuid = data.key;
                            $('#btnSaveOrUpdate3').button('loading').delay(1000).queue(function () {
                                var url = "sys/syssubject/input";
                                $.ajax({
                                    type: "POST",
                                    url: baseURL + url,
                                    contentType: "application/json",
                                    data: JSON.stringify(vm.sysSubject),
                                    success: function (r) {
                                        if (r.code === 0) {
                                            layer.msg("操作成功", {icon: 1});
                                            vm.reload();
                                            $('#btnSaveOrUpdate3').button('reset');
                                            $('#btnSaveOrUpdate3').dequeue();
                                            vm.clear();
                                            vm.cleart();
                                        } else {
                                            layer.alert(r.msg);
                                            $('#btnSaveOrUpdate3').button('reset');
                                            $('#btnSaveOrUpdate3').dequeue();
                                        }
                                    }
                                });
                            });
                        }
                    },
                    error: function (response) {
                        alert("文件上传失败,请重新上传");
                    }
                });
            },
            saveOrUpdate3: function () {
                var text2 = vm.getSelected(vm.sysSubject.topicId);
                if (text2 == undefined || text2 == null) {
                    return;
                }
                var text = text2.split("-")[1];
                if (text.indexOf("单") != -1 || text.indexOf("多") != -1) {
                    var num = vm.sysSubject.num;
                    vm.sysSubject.jugleList = new Array();
                    var i;
                    for (i = 0; i < num; i++) {
                        var item = {
                            "content": localStorage.getItem(arr[i]),
                            name: arr[i]
                        };
                        vm.sysSubject.jugleList.push(item);
                    }
                    var length = vm.nums1.length;
                    vm.sysSubject.answertt = "";
                    for (var i = 0; i < length; i++) {
                        if (vm.nums1[i].text3) {
                            vm.sysSubject.answertt += arr[i];
                            if (i != length - 1) {
                                vm.sysSubject.answertt += ",";
                            }
                        }
                    }
                }

                vm.sysSubject.content = localStorage.getItem("content");
                vm.sysSubject.parse = localStorage.getItem("parse");
                vm.sysSubject.answer = localStorage.getItem("answer");
                $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function () {
                    var url = vm.sysSubject.subjectId == null ? "sys/syssubject/save" : "sys/syssubject/update";
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(vm.sysSubject),
                        success: function (r) {
                            if (r.code === 0) {
                                layer.msg("操作成功", {icon: 1});
                                vm.reload();
                                $('#btnSaveOrUpdate').button('reset');
                                $('#btnSaveOrUpdate').dequeue();
                                vm.clear();
                                vm.cleart();
                            } else {
                                layer.alert(r.msg);
                                $('#btnSaveOrUpdate').button('reset');
                                $('#btnSaveOrUpdate').dequeue();
                            }
                        }
                    });
                });
            },
            saveOrUpdate2: function (type) {
                vm.showList = true;
                vm.showList2 = false;
                vm.showList3 = true;

                localStorage.setItem("type", type);
                setTimeout(function () {
                    $('#show1').hide();
                }, 1);
                if ($("#" + type + "Text").html() == "已编辑") {
                    editorContent.txt.html(localStorage.getItem(type));
                }
            },
            save: function () {
                $('#btnSaveOrUpdate2').button('loading').delay(100).queue(function () {
                    localStorage.setItem(localStorage.getItem("type"), editorContent.txt.html());
                    $('#btnSaveOrUpdate2').button('reset');
                    $('#btnSaveOrUpdate2').dequeue();
                    vm.qx();
                });
            },
            edit: function (type, str) {
                if (type == "content") {
                    $("#contentText").html(str);
                } else if (type == "answer") {
                    $("#answerText").html(str);
                } else if (type == "parse") {
                    $("#parseText").html(str);
                }

            },
            index: function (str) {
                var j = -1;
                for (var i = 0; i < arr.length; i++) {
                    if (arr[i] == str) {
                        j = i;
                        break;
                    }
                }
                return j;
            },
            qx: function () {
                vm.showList = false;
                vm.showList2 = true;
                vm.showList3 = true;
                var type = localStorage.getItem("type");
                var content = localStorage.getItem(type);
                var index = vm.index(type);
                if (content != "" && content != "<p><br></p>" && content != undefined) {
                    if (index != -1) {
                        vm.dymic2(index, "已编辑");
                    } else {
                        vm.edit(type, "已编辑");
                    }
                } else {
                    if (index != -1) {
                        vm.dymic2(index, "未编辑");
                    } else {
                        vm.edit(type, "&nbsp;未编辑");
                    }
                }
                editorContent.txt.clear();
            },
            del: function (event) {
                var subjectIds = getSelectedRows();
                if (subjectIds == null) {
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
                            url: baseURL + "sys/syssubject/delete",
                            contentType: "application/json",
                            data: JSON.stringify(subjectIds),
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
            getTopics: function () {
                $.get(baseURL + "sys/syssubject/getTopics", function (r) {
                    if (r.topics != null) {
                        var topics = r.topics;
                        vm.items = new Array();
                        for (var key in topics) {
                            var old = {text: null, text2: null, value: null};
                            old.text = key.split("-")[0];
                            old.text2 = key;
                            old.value = topics[key];
                            vm.items.push(old);
                        }
                    }
                });
            },
            getInfo: function (subjectId) {
                $.get(baseURL + "sys/syssubject/info/" + subjectId, function (r) {
                    vm.sysSubject = r.sysSubject;
                    vm.getCourse();
                    vm.sysSubject.topicId = r.sysSubject.topicId;
                    vm.sysSubject.difficulty = r.sysSubject.difficulty;
                    vm.sysSubject.num = r.sysSubject.num;
                    var contents = vm.sysSubject.content;
                    if (contents != "" && contents != "<p><br></p>" && contents != undefined) {
                        vm.edit("content", "已编辑");
                        localStorage.setItem("content", contents);
                    } else {
                        vm.edit("content", "&nbsp;未编辑");
                    }
                    var parse = vm.sysSubject.parse;
                    if (parse != "" && parse != "<p><br></p>" && parse != undefined) {
                        vm.edit("parse", "已编辑");
                        localStorage.setItem("parse", parse);
                    } else {
                        vm.edit("parse", "&nbsp;未编辑");
                    }

                    var text2 = vm.getSelected(vm.sysSubject.topicId);
                    if (text2 == undefined || text2 == null) {
                        return;
                    }
                    var text = text2.split("-")[1];
                    if (text.indexOf("单") != -1 || text.indexOf("多") != -1) {
                        var num = vm.sysSubject.num;
                        vm.dymic(num, "未编辑");
                        $("#answertt").show();
                        $("#selectnum").show();
                        $("#answer").hide();
                        $("#answert").hide();
                        $("#file").hide();
                        $("#tofile").hide();

                        var answertt = vm.sysSubject.answertt;
                        var strs = answertt.split(",");
                        for (var i = 0; i < strs.length; i++) {
                            if (vm.index(strs[i]) != -1) {
                                vm.dymic3(vm.index(strs[i]), true);
                            }
                        }

                        var jugleList = r.sysSubject.jugleList;
                        var i;
                        for (i = 0; i < num; i++) {
                            var jugle = jugleList[i];
                            var content = jugle.content;
                            var type = jugle.name;
                            if (content != "" && content != "<p><br></p>" && content != undefined) {
                                localStorage.setItem(type, content);
                                vm.dymic2(i, "已编辑");
                            }
                        }
                    } else if (text.indexOf("判断") != -1) {
                        $("#answert").show();
                        $("#answer").hide();
                        $("#file").hide();
                        $("#select").hide();
                        $("#selectnum").hide();
                        $("#answertt").hide();
                        $("#tofile").hide();
                    } else if (text.indexOf("操作") != -1) {
                        $("#file").show();
                        $("#select").hide();
                        $("#answertt").hide();
                        $("#answert").hide();
                        $("#answer").hide();
                        $("#selectnum").hide();
                        var fileUrl = vm.sysSubject.file;
                        if (fileUrl != undefined && fileUrl != "") {
                            $("#tofile").show();
                            $("#fileUrl").attr("href", fileUrl);
                        } else {
                            $("#tofile").hide();
                        }
                    } else {
                        $("#select").hide();
                        $("#answertt").hide();
                        $("#answert").hide();
                        $("#file").hide();
                        $("#answer").show();
                        $("#selectnum").hide();
                        $("#tofile").hide();

                        var answer = vm.sysSubject.answer;
                        if (answer != "" && answer != "<p><br></p>" && answer != undefined) {
                            vm.edit("answer", "已编辑");
                            localStorage.setItem("answer", answer);
                        } else {
                            vm.edit("answer", "&nbsp;未编辑");
                        }
                    }

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
                        vm.sysSubject.courseId = node[0].courseId;
                        vm.sysSubject.courseName = node[0].name;

                        layer.close(index);
                    }
                });
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
            reload: function (event) {
                vm.showList = true;
                vm.showList2 = true;
                vm.showList3 = true;
                $('#show1').show();
                $("#select").hide();
                $("#answer").hide();
                $("#answert").hide();
                $("#answertt").hide();
                $("#selectnum").hide();
                $("#file").hide();
                $("#tofile").hide();
                vm.cleart();
                vm.clear();
                var page = $("#jqGrid").jqGrid('getGridParam', 'page');
                $("#jqGrid").jqGrid('setGridParam', {
                    postData: {
                        "courseId": vm.q.courseId,
                        "topicName": vm.q.topicName
                    },
                    page: page
                }).trigger("reloadGrid");
                vm.getCourse2();
            }
        }
    });

    vm.getCourse2();
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/syssubject/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'subjectId', sortable: false, index: 'subject_id', width: 80, key: true},
            {label: '科目名称', name: 'courseName', sortable: false, width: 180},
            {label: '知识点', name: 'courseNameT', sortable: false, width: 480},
            {label: '题目名称', name: 'name', sortable: false, width: 180},
            {label: '题型', name: 'topicName', sortable: false, width: 100},
            {label: '难度', name: 'difficulty', sortable: false, width: 80},
            {label: '选项数量', name: 'num', sortable: false, index: 'num', width: 100},
            {label: '分数', name: 'price', sortable: false, index: 'price', width: 80}
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
    /** 富文本**/

    var E = window.wangEditor;
    var editorContent = new E('#editor');
    editors(editorContent);

    function editors(editor) {
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
    };
});

