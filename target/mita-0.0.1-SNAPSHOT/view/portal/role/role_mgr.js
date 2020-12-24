

$(function() {
	loadTree();
	loadList();
})

var isLoadAuth = false;

/**
 * 加载菜单树
 */
function loadTree() {
    $.ajax({
        url: "/role/getAppRoleTree",
        type: "POST",
        success: function(retData) {
            $("#tree").tree({
                data: retData.rtdata.tree,
                animate: true,
                onClick: function(node){  	
                	if (node.attributes.type == "app") {
                		$('#datagrid').datagrid('load',{
                        	appid: node.id,
                        });
                	}
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
        url:'/role/queryRoles4Tree',
        loader: datagrid_loader,
        pagination:true,
        pageSize:10,
        pageNumber:1,
        toolbar:'#toolbar',
        loadMsg:'加载中...',
        fit:true,
        columns:[[
            {field:'id', checkbox:true},
            {field:'roleno', title: '角色编号'},
            {field:'rolename', title: '角色名称'},
            {field:'isdefault', title: '是否默认角色', formatter: dict_formatter, dicttypeid:'comm_yesorno'}
        ]]
    });
}

/**
 * 查询
 */
function query() {
	var node = $("#tree").tree("getSelected");
	var appid = node.attributes.id;
    $('#datagrid').datagrid('load',{
    	appid: appid
    });
}

/**
 * 打开新增面板
 */
function add() {
 	$editTab = $("#" + _ADD_PARAM[0]);
 	var node = $("#tree").tree("getSelected");
 	if (!node || !node.attributes || node.attributes.type != 'app') {
 		$.messager.alert("提示", "请选中所属应用!");
 		return;
 	}
	$dataForm = $("#" + _ADD_PARAM[2]);
	$dataForm.form("clear");
	$dataForm.url = _ADD_PARAM[3];	
 	$("#appid").combogrid('setValue', node.id);
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
        url: '/role/save',
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
 * 加载权限配置
 */
var editRow = undefined;
var lnkname = undefined;
function loadAuth(roleid) {
	$("#roleid_auth").val(roleid);
	$("#authgrid").datagrid({
		url: '/roleauthcfg/queryRoleauthcfgsByRoleid',
		queryParams: {
			roleid: roleid
		},
		singleSelect: true,
        loadMsg:'加载中...',
        columns:[[
            {field:'authcfgid', title: '权限配置id', editor:{type:'textbox'}, hidden:true},
            {field:'cfgtype', title: '配置类型', editor:{type:'textbox'}, hidden:true},
            {field:'departmentid', title: '部门id', editor:{type:'textbox'}, hidden:true},
            {field:'positionid', title: '岗位id', editor:{type:'textbox'}, hidden:true},
			{field:'cfgtypedesc', title: '配置类型', width: 150, 
			 editor: {
				 type:'combobox', 
				 options:{
					 panelHeight: 'auto',
					 url: '/dict/queryDictsByDicttypeid?dicttypeid=portal_roleauthtype',
					 method:'post',
				     valueField: 'dictname',
					 textField: 'dictname',
					 onSelect: function(record) {
						 var editor = $("#authgrid").datagrid('getEditor', {index:editRow, field:'cfgtype'});
						 editor.target.textbox('setValue', record.dictid);	
						 initLnkname(record.dictid);
					 }
				 }
			}},
			{field:'lnkname', title: '配置名称', width: 200},
			{field:'opt', title: '操作', align:'center', width: 100,
				formatter: function(value, row, index) {
					var delBtn = "<a href='javascript:void(0)' onclick='deleteRow(" + index + ")' class='delcls' >删除</a>";
					return delBtn;
				}
			}
		]],
		onLoadSuccess:function(data){
			$('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
		},
		toolbar: [{
            text: '添加', iconCls: 'icon-add', handler: function () {
            	if (editRow != undefined) {
                    $("#authgrid").datagrid('endEdit', editRow);
                }
                if (editRow == undefined) {
                    $("#authgrid").datagrid('appendRow', {});
                    var rows =  $("#authgrid").datagrid('getRows');
                    editRow = rows.length - 1;
                    $("#authgrid").datagrid('beginEdit', editRow); 
                }
                $('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
            }
        }],
        onBeforeEdit:function(index,row){
        	initLnkname(row.cfgtype);
		},
		onAfterEdit:function(index,row){
			editRow = undefined;
			$('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
		},
		onEndEdit: function(index,row){
			if (lnkname != undefined) {
				$("#authgrid").datagrid('getRows')[index]['lnkname'] = lnkname;
				lnkname = undefined;
			}
		},
		onClickRow:function(rowIndex,rowData){
            if (editRow != undefined) {
                $("#authgrid").datagrid('endEdit', editRow);
            }  
        },
        onDblClickRow:function (rowIndex, rowData) {
        	if (editRow != undefined) {
                $("#authgrid").datagrid('endEdit', editRow);
            }
            if (editRow == undefined) {
                $("#authgrid").datagrid('beginEdit', rowIndex);
                editRow = rowIndex;
            }
        }
	});
	isLoadAuth = true;
}

function initLnkname(cfgtype) {
	switch(cfgtype) {
	case 'DEP':
		$("#authgrid").datagrid('addEditor',{
			field: 'lnkname',
			editor: {
				type: 'combotree',
				options: { 
					url: '/department/getDepTree',
					onSelect: function(node) {
						var editor = $("#authgrid").datagrid('getEditor', {index:editRow, field:'authcfgid'});
						editor.target.textbox('setValue', node.id);
						editor = $("#authgrid").datagrid('getEditor', {index:editRow, field:'departmentid'});
						editor.target.textbox('setValue', node.id);
						lnkname = node.text;	
					},
					onHidePanel: function() {
						$("#authgrid").datagrid("removeEditor", 'lnkname');
					}
				}
			}
		});
		break;
	case 'DEPPOS':
		$("#authgrid").datagrid('addEditor',{
			field: 'lnkname',
			editor: {
				type: 'combotree',
				options: { 
					url: '/depposlnk/getDepposTree',
					onBeforeSelect : function(node) {
						if (node.attributes.type != 'DEPPOS') {
							$(this).combotree('clear');
						}
					},
					onSelect: function(node) {
						var editor = $("#authgrid").datagrid('getEditor', {index:editRow, field:'authcfgid'});
						editor.target.textbox('setValue', node.id);
						editor = $("#authgrid").datagrid('getEditor', {index:editRow, field:'departmentid'});
						editor.target.textbox('setValue', node.attributes.departmentid);
						editor = $("#authgrid").datagrid('getEditor', {index:editRow, field:'positionid'});
						editor.target.textbox('setValue', node.attributes.positionid);
						lnkname = node.text;
					},
					onHidePanel: function() {
						$("#authgrid").datagrid("removeEditor", 'lnkname');
					}
				}
			}
		});
		break;
	case 'POS':
		$("#authgrid").datagrid('addEditor',{
			field: 'lnkname',
			editor: {
				type: 'combogrid',
				options: { 
					url: '/position/queryPositions',
					loader: datagrid_loader,
					panelWidth: 400,
                    pagination:true,
                    pageSize:5,
                    pageNumber:1,
                    pageList:[5,10,15],
                    idField:'id',
                    textField:'posname',
                    columns:[[
                       {field:'id',title:'id',checkbox:true},
                       {field:'posname',title:'岗位名称',width:100}
                    ]],
					onSelect: function(index, row) {
						console.log(row);
						var editor = $("#authgrid").datagrid('getEditor', {index:editRow, field:'authcfgid'});
						editor.target.textbox('setValue', row.id);
						editor = $("#authgrid").datagrid('getEditor', {index:editRow, field:'positionid'});
						editor.target.textbox('setValue', row.id);
						lnkname = row.posname;
					},
					onHidePanel: function() {
						$("#authgrid").datagrid("removeEditor", 'lnkname');
					}
				}
			}
		});
		break;
	default:
		break;
	}
}

/**
 * 打开权限配置面板
 */
function auth() {
	editRow = undefined;
	var rows = $("#datagrid").datagrid('getSelections');
	var row = rows[0];
    if (rows.length != 1) {
        alert("请选中一条记录");
        return;
    }
    $("#id_auth").val(row.id);
    //$("#roleno_auth").textbox("setValue", row.roleno);
    //$("#rolename_auth").textbox("setValue", row.rolename);
    if (isLoadAuth) {
    	$('#authgrid').datagrid('load',{
        	roleid: row.id
    	});
    } else {
    	loadAuth(row.id);
    }
    $("#authTab").dialog("open").dialog('setTitle', "权限配置");
}

/**
 * 删除一行
 */
function deleteRow(index) {
	if (editRow == index) {
		editRow = undefined;
	}
	$("#authgrid").datagrid("deleteRow", index);
	$("#authgrid").datagrid("loadData", $("#authgrid").datagrid("getRows"));
}

/**
 * 保存权限配置
 */
function saveAuth() {
	if (editRow != undefined) {
        $("#authgrid").datagrid('endEdit', editRow);
    }
	var params = {};
	var roleauthcfgs = $("#authgrid").datagrid('getRows');
	//把所有number类型的属性转成string
	roleauthcfgs = toStringValue(roleauthcfgs);
	params.roleid = parseInt($("#roleid_auth").val()) || 0;
	params.roleauthcfgs = roleauthcfgs;
	$.ajax({
        url: "/roleauthcfg/saveRoleauthcfgByRole",
        type: "POST",
        contentType: 'application/json;charset=utf-8',
        dataType : "json",  
        data: JSON.stringify(params), 
        success: function(retData) {
           if (retData.rtsts && retData.rtsts.code == '000000') {
        	   $.messager.alert("消息", "保存成功");
               $('#authTab').dialog('close');
           } else {
        	   $.messager.alert("消息", "保存失败");
           }
        },
        error: function (xhr, rtmsg, e) { 
        }
    });
}

