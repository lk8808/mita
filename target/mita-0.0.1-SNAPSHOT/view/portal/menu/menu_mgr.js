

$(function() {
	loadTree();
	loadList();
})

/**
 * 加载菜单树
 */
function loadTree() {
    $.ajax({
        url: "/menu/getAppMenuTree",
        type: "POST",
        success: function(retData) {
            $("#tree").tree({
                data: retData,
                animate: true,
                onClick: function(node){  	
                	var parentid = node.attributes.id;
                	var type = node.attributes.type;
                	$('#datagrid').datagrid('load',{
                    	parentid: parentid,
                    	type: type
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
        url:'/menu/queryMenus4Tree',
        loader: datagrid_loader,
        pagination:true,
        pageSize:10,
        pageNumber:1,
        toolbar:'#toolbar',
        loadMsg:'加载中...',
        fit:true,
        columns:[[
            {field:'id', checkbox:true},
            {field:'sortno', title: '序号'},
            {field:'menuno', title: '菜单编号'},
            {field:'menuname', title: '菜单名称'},
            {field:'menutype', title: '菜单类型', formatter: dict_formatter, dicttypeid:'portal_menutype'},
            {field:'menuurl', title: '菜单url'}
        ]]
    });
}

/**
 * 查询
 */
function query() {
	var node = $("#tree").tree("getSelected");
	var parentid = node.attributes.id;
	var type = node.attributes.type;
    $('#datagrid').datagrid('load',{
    	parentid: parentid,
    	type: type
    });
}

/**
 * 打开新增面板
 */
function add() {
 	$editTab = $("#" + _ADD_PARAM[0]);
 	var node = $("#tree").tree("getSelected");
 	if (!node) {
 		$.messager.alert("提示", "请选中上级菜单或应用!");
 		return;
 	}
	$dataForm = $("#" + _ADD_PARAM[2]);
	$dataForm.form("clear");
	$dataForm.url = _ADD_PARAM[3];	
	if (node.attributes.type == "app") {
 		$("#appid").combogrid('setValue', node.id);
 	} else {
 		$("#parentid").combogrid('setValue', node.id);
 	}
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
        url: '/menu/save',
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

