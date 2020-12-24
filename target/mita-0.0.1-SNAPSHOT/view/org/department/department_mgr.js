

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
                	var parentid = node.id;
                	$('#datagrid').datagrid('load',{
                    	parentid: parentid
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
    	title:"数据列表",
        url:'/department/queryAllDepsByParentid',
        loader: datagrid_loader,
        pagination:true,
        pageSize:10,
        pageNumber:1,
        toolbar:'#toolbar',
        loadMsg:'加载中...',
        fit:true,
        columns:[[
            {field:'id', checkbox:true},
            {field:'sortno', title: '序号', sortable: "true"},
            {field:'depno', title: '部门编号'},
            {field:'depname', title: '部门名称'},
            {field:'parentid', title: '上级部门', url: '/department/get', refname: 'depname', formatter: datagrid_ref_formatter},
            {field:'principalid', title: '部门负责人', url: '/employee/get', refname: 'empname', formatter: datagrid_ref_formatter}
        ]]
    });
}

/**
 * 查询
 */
function query() {
	var node = $("#tree").tree("getSelected");
	var parentid = node.attributes.id;
    $('#datagrid').datagrid('load',{
    	parentid: parentid
    });
}

/**
 * 打开新增面板
 */
function add() {
 	$editTab = $("#" + _ADD_PARAM[0]);
 	var node = $("#tree").tree("getSelected");
 	if (!node) {
 		$.messager.alert("提示", "请选中上级部门!");
 		return;
 	}
	$dataForm = $("#" + _ADD_PARAM[2]);
	$dataForm.form("clear");
	$dataForm.url = _ADD_PARAM[3];	
 	$("#parentid").combotree('setValue', node.id);
	$editTab.dialog("open").dialog('setTitle', _ADD_PARAM[1]);	
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
        url: '/department/save',
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
        }
    });
}

/**
 * 重新加载数据
 * @param $datagrid
 */
function reload() {
	loadTree();
	loadList();
}

/**
 * 新增/编辑面板打开事件
 */
function editTabOpen() {
	//设置部门负责人选择查询参数
	$("#principalid").combogrid("grid").datagrid("load", {
		depid: (Number)($("#id").val()) || 0
	});
	//设置已选岗位
	$.ajax({
        url: "/depposlnk/queryByDepid",
        type: "POST",
        data: {depid: (Number)($("#id").val()) || 0},
        success: function(retData) {
        	var posids = [];
            $.each(retData, function(index, obj){
            	posids.push(obj.positionid); 	
            });
            $("#posids").combogrid("setValue", posids);
        },
        error: function (xhr, rtmsg, e) { 
        }
    });
}




