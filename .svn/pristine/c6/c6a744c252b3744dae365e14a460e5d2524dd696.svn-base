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
    	title:"数据列表",
        url:'/employee/queryAllEmpsByDepid',
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
            {field:'empno', title: '员工编号'},
            {field:'empname', title: '员工名称'},
            {field:'departmentid', title: '所在部门', url: '/department/get', refname: 'depname', formatter: datagrid_ref_formatter},
            {field:'gender', title: '性别', dicttypeid:'comm_gender' , formatter: dict_formatter},
            {field:'birthday', title: '出生日期', 
            	formatter: function(value, row, index) {
            		return formatDate(value, 'yyyy-MM-dd');
            	}
            },
            {field:'education', title: '学历', dicttypeid:'comm_education' , formatter: dict_formatter},
            {field:'telephone', title: '联系电话'}
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
 * 打开新增面板
 */
function add() {
 	$editTab = $("#" + _ADD_PARAM[0]);
 	var node = $("#tree").tree("getSelected");
 	if (!node || node.id == 0) {
 		$.messager.alert("提示", "请选中所属部门!");
 		return;
 	}
	$dataForm = $("#" + _ADD_PARAM[2]);
	$dataForm.form("clear");
	$dataForm.url = _ADD_PARAM[3];	
 	$("#departmentid").combotree('setValue', node.id);
	$editTab.dialog("open").dialog('setTitle', _ADD_PARAM[1]);	
}

/**
 * 编辑
 */
function edit() {
	var $datagrid = $("#" + _DATAGRID_ID);
	$editTab = $("#" + _EDIT_PARAM[0]);
	$dataForm = $("#" + _EDIT_PARAM[2]);
    var rows = $datagrid.datagrid('getSelections');
    var row = rows[0];
    if (rows.length != 1) {
        alert("请选中一条记录");
        return;
    }
    $dataForm.form('load',row);
    $("#img").attr("src", "data:image/jpeg;base64," + row.photo);
    $dataForm.url = _EDIT_PARAM[3];  
    $editTab.dialog("open").dialog('setTitle', _EDIT_PARAM[1]);
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
        url: '/employee/save',
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
 * 证件号改变后回写生日和性别
 * @param newValue
 * @param oldValue
 */
function idnumChange(newValue,oldValue) {
	var gender = getGenderByIdnum(newValue);
    var birthday = getBirthdayByIdnum(newValue);
    $("#birthday").datebox('setValue', birthday);
    $("#gender").combobox('setValue', gender);
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
	$("#depposlnkids").combogrid("grid").datagrid("load", {
		depid: (Number)($("#departmentid").val()) || 0
	});
}

function change_photo() {
	PreviewImage($("#file_upload")[0], 'img', 'imgdiv');
}
