
$(function(){
    $("#datagrid").datagrid({
    	title:"数据列表",
        url:'/application/queryApplicationsByPage',
        loader: datagrid_loader,
        pagination:true,
        pageSize:10,
        pageNumber:1,
        toolbar:'#toolbar',
        loadMsg:'加载中...',
        fit:true,
        columns:[[
            {field:'id', checkbox:true},
            {field:'appcode', title: '应用编码'},
            {field:'appname', title: '应用名称'},
            {field:'apptype', title: '应用类型', formatter: dict_formatter, dicttypeid:'portal_apptype'},
            {field:'sortno', title: '顺序号'},
            {field:'publisher', title: '发布人'}
        ]]
    });
});

function query() {
    $('#datagrid').datagrid('load',{
    	appcode: $('#appcode_q').val(),
        appname: $('#appname_q').val()
    });
}

function save(){
    $.messager.progress({
        title: '请稍后',
        msg: '正在保存...',
        text: '正在保存...'
    });
    $("#dataForm").form('submit', {
        url: '/application/save',
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