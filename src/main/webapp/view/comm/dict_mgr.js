$(function(){
    $("#datagrid").datagrid({
    	title:"数据列表",
        url:'/dict/queryDicttypesWithPage',
        loader: datagrid_loader,
        pagination:true,
        pageSize:10,
        pageNumber:1,
        toolbar:'#toolbar',
        loadMsg:'加载中...',
        fit:true,
        columns:[[
            {field:'id', checkbox:true},
            {field:'dicttypeid', title: '数据字典类型id'},
            {field:'dicttypename', title: '数据字典类型名称'}
        ]]
    });
});

function loadDicts() {
	$("#editgrid").datagrid({
		title:'数据字典明细',
		url:'/dict/queryDictsByDicttypeid',
		loadMsg:'加载中...',
		fit:true,
		columns:[[
            {field:'id', checkbox:true},
            {field:'dicttypeid', title: '数据字典类型id'},
            {field:'dicttypename', title: '数据字典类型名称'}
        ]]
	});
}

function query() {
	$('#datagrid').datagrid('load',{
	  	dicttypeid: $('#dicttypeid_q').val(),
	    dicttypename: $('#dicttypename_q').val()
	});
}

/**
 * 打开新增面板
 */
function add() {
 	$editTab = $("#" + _ADD_PARAM[0]);
	$dataForm = $("#" + _ADD_PARAM[2]);
	$dataForm.form("clear");
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
    loadDicts();
    $editTab.dialog("open").dialog('setTitle', _EDIT_PARAM[1]);
}

function save(){
    $.messager.progress({
        title: '请稍后',
        msg: '正在保存...',
        text: '正在保存...'
    });
    
}
