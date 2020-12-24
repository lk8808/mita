$(function(){
    $("#datagrid").datagrid({
    	title:"数据列表",
        url:'/position/queryPositions',
        loader: datagrid_loader,
        pagination:true,
        pageSize:10,
        pageNumber:1,
        toolbar:'#toolbar',
        loadMsg:'加载中...',
        fit:true,
        columns:[[
            {field:'id', checkbox:true},
            {field:'posno', title: '岗位编号'},
            {field:'posname', title: '岗位名称'},
            {field:'remark', title: '备注'}
        ]]
    });
});

function query() {
//    $('#datagrid').datagrid('load',{
//    	appcode: $('#appcode_q').val(),
//        appname: $('#appname_q').val()
//    });
}

function save(){
    $.messager.progress({
        title: '请稍后',
        msg: '正在保存...',
        text: '正在保存...'
    });
    $("#dataForm").form('submit', {
        url: '/position/save',
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
                reload();
            } else {
                $.messager.alert("消息", "保存失败");
            }
        }
    });
}