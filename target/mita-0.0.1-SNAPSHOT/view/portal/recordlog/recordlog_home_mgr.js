
$(function(){
    $("#datagrid").datagrid({
    	title:"常用功能",
        url:'/recordlog/queryRecordlogFetch10',
        loader: datagrid_loader,
        pagination:false,
        loadMsg:'加载中...',
        fit:true,
        singleSelect:true,
        rownumbers:true,
        tools: '#tt',
        columns:[[
        	
            {field:'recordname', title: '菜单名称',formatter: function(value, row, index) {
        		return '<a href="#" onclick="openmenu('+row.recordid+',\''+row.recordname+'\',\''+row.menuurl+'\')">'+value+'</a>';
        	}},
            {field:'recount', title: '点击次数',align:'right'},
            {field:'modifytime', title: '最后点击时间',formatter: function(value, row, index) {
	        		return formatDate(value, 'yyyy-MM-dd hh:mm:ss');
	        	}
            }
        ]]
    });
});

function query() {
    $('#datagrid').datagrid('load',{
    	recordname:''
    });
}

function openmenu(menuid,menuname,menuurl) {
	window.parent.parent.openMenu(menuid,menuname,menuurl);
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  decodeURI(r[2]); return null;
}

function menumore() {
	window.parent.menumore(getQueryString("menuid"));
}



