
$(function(){
    $("#datagrid").datagrid({
    	title:"数据列表",
        url:'/recordlog/queryRecordlogByPage',
        loader: datagrid_loader,
        pagination:true,
        pageSize:10,
        pageNumber:1,
        toolbar:'#toolbar',
        loadMsg:'加载中...',
        fit:true,
        singleSelect:true,
        columns:[[
            {field:'recordname', title: '菜单名称',formatter: function(value, row, index) {
        		return '<a href="#" onclick="openmenu('+row.recordid+',\''+row.recordname+'\',\''+row.menuurl+'\')">'+value+'</a>';
        	}},
            {field:'recount', title: '点击次数',align:'right'},
            {field:'modifytime', title: '最后点击时间',formatter: function(value, row, index) {
	        		return formatDate(value, 'yyyy-MM-dd hh:mm:ss');
	        	}
            },
            {field:'ipaddress', title: '最后使用机器IP'}
        ]]
    });
});

function query() {
    $('#datagrid').datagrid('load',{
    	recordname: $('#recordname_q').val()
    });
}

function openmenu(menuid,menuname,menuurl) {
	window.parent.openMenu(menuid,menuname,menuurl);
}
