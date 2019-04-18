
$(function() {
	loadTree();
	loadList();
})

/**
 * 加载树
 */
function loadTree() {
    $.ajax({
        url: "/department/getDepTree",
        type: "POST",
        success: function(retData) {
            $("#tree").tree({
                data: retData,
                animate: true,
                onClick: function(node){  	
                	var depid = node.id;
                	$('#datagrid').datagrid('load',{
                		depid: depid
                    });
                }
            });
        },
        error: function (xhr, rtmsg, e) { 
        }
    });
}

/**
 * 加载列表
 */
function loadList() {
	$("#datagrid").datagrid({
		url:'/user/queryAllUsersByDepid',
		loader: datagrid_loader,
		pagination:true,
		pageSize:10,
        pageNumber:1,
        toolbar:'#toolbar',
        loadMsg:'加载中...',
        fit:true,
        columns:[[
            {field:'id', checkbox:true},
            {field:'username', title: '用户名'},
            {field:'telephone', title: '联系电话'},
            {field:'email', title: '邮箱'},
            {field:'createtime', title: '创建时间',
            	formatter: function(value, row, index) {
            		return formatDate(value, 'yyyy-MM-dd hh:mm:ss');
            	}
            },
            {field:'creator', title: '创建人'}
        ]]
	});
}

/**
 * 查询
 */
function query() {
	var node = $("#tree").tree("getSelected");
	var depid = node.attributes.id;
    $('#datagrid').datagrid('load',{
    	depid: depid
    });
}

/**
 * 保存
 */
function save(){
    $.messager.progress({
        title: '请稍后',
        msg: '正在保存...',
        text: '正在保存...'
    });
    $("#dataForm").form('submit', {
        url: '/user/save',
        onSubmit: function(){
            var isValid = $(this).form('validate');
            if (!isValid){
                $.messager.progress('close');   
            }
            return isValid; 
        },
        success: function(retData){
            $.messager.progress('close'); 
            retData = eval('(' + retData + ')');
            if (retData.rtsts.code == "000000") {
                $.messager.alert("消息", "保存成功");
                $('#editTab').dialog('close');
                loadTree();
                reload();
            } else {
                $.messager.alert("消息", "保存失败");
            }
        },
        error: function(xhr, rtmsg, e) {
        	$.messager.alert("消息", "程序异常");
        }
    });
}

/**
 * 重置密码
 */
function resetPasswd() {
	var $datagrid = $("#datagrid");
    var rows = $datagrid.datagrid('getSelections');
    if (rows.length < 1) {
    	$.messager.alert("提醒", "请选中至少一条记录");
        return;
    }
	$.messager.progress({
        title: '请稍后',
        text: '正在重置...'
    });
	var ids = '';
    $.each(rows, function(index, row) {
     	if (index == 0) {
     		ids = row.id + '';
     	} else {
     		ids = ids + ',' + row.id;
     	}
    });
	$.ajax({
        url: "/user/resetPasswd",
        type: "POST",
        data: {ids: ids},
        success: function(retData) {
        	$.messager.progress('close'); 
            if (retData.rtsts.code == "000000") {
                $.messager.alert("消息", "重置成功");
            } else {
                $.messager.alert("消息", "重置失败");
            }
        },
        error: function (xhr, rtmsg, e) { 
        }
    });
}