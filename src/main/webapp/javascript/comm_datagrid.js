/**
 * datagrid基本增删改查js
 * 调用时，需要在次js前面声明如下变量
 * 
 * var _DATAGRID_ID = ''
 * var _ADD_PARAM = [tabId, title, formid, addUrl]
 * var _EDIT_PARAM = [tabId, title, formid, editUrl]
 * var _DEL_PARAM = [delUrl, paramname]
 * 
 */

/**
 * datagrid 分页查询加载器
 */
function datagrid_loader(params, success, error) {
	var that = $(this);  
    var opts = that.datagrid("options");  
    if (!opts.url) {  
        return false;  
    }  
    $.ajax({  
        url : opts.url,  
        type : "POST",  
        contentType: 'application/json;charset=utf-8',
        dataType : "json",  
        data: JSON.stringify(params), 
        success: function (retData) {  
            success(retData.rtdata);  
        },  
        error : function () {  
            error.apply(this, arguments);  
        }  
    });   
}


/**
 * 新增
 * @param url
 * @param $editTab
 * @param $dataForm
 */
function add() {
 	$editTab = $("#" + _ADD_PARAM[0]);
	$dataForm = $("#" + _ADD_PARAM[2]);
	$dataForm.form("clear");
	$dataForm.url = _ADD_PARAM[3];
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
    $dataForm.url = _EDIT_PARAM[3];  
    $editTab.dialog("open").dialog('setTitle', _EDIT_PARAM[1]);
}
/**
 * 此方法通常只用主键id作为参数，可以删除多条，传参名称可自定义，一般建议使用ids
 */
function del() {
	var $datagrid = $("#" + _DATAGRID_ID);
    var rows = $datagrid.datagrid('getSelections');
    if (rows.length < 1) {
    	$.messager.alert("提醒", "请选中至少一条记录");
        return;
    }
    $.messager.confirm("提醒", "确认删除？", function(r) {
    	if (!r) {
    		return false;
    	}
    	$.messager.progress({
    		title: '请稍后',
    		text: '正在删除...'
    	});
        var tmp = '';
        $.each(rows, function(index, row) {
        	if (index == 0) {
        		tmp = row.id + '';
        	} else {
        		tmp = tmp + ',' + row.id;
        	}
        });
        var params = {};
        $(params).attr(_DEL_PARAM[1], tmp);
        $.ajax({
        	url: _DEL_PARAM[0],  
            type: "POST",  
            data: params, 
            success: function (retData) {  
            	$.messager.progress('close');
            	if (retData.rtsts.code == "000000") {
    	        	$.messager.alert("消息", "删除成功");
    	        	reload();
    	        } else {
    	        	$.messager.alert("消息", "删除成功");
    	        }
            },  
            error : function () {  
                error.apply(this, arguments);  
            }  
        });
    });
}

/**
 * 重新加载数据
 * @param $datagrid
 */
function reload() {
	var $datagrid = $("#" + _DATAGRID_ID);
	$datagrid.datagrid('load');
}