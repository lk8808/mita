/**
 * 公共js
 */

/**
 * ajax全局处理
 * 	当session过期时跳转至登陆页面
 */
$(function(){
    $.ajaxSettings.complete = function(xhr, status){
    	var retData = xhr.responseText;
    	if (retData) {
    		retData = eval('(' + retData + ')');
        	if (retData.rtsts && retData.rtsts.code) {
        		if (retData.rtsts.code == '000009') {
        			if (window.parent) {
        				window.parent.location.href = '/view/login.html';
        				return;
        			} 
        			window.location.href = '/view/login.html';
        		}
        	}
    	}
    }
});

/**
 * 自定义datagrid动态改变editor的方法
 */
$.extend($.fn.datagrid.methods, {
	addEditor : function(jq, param) {
		if (param instanceof Array) {
			$.each(param, function(index, item) {
				var e = $(jq).datagrid('getColumnOption', item.field);
				e.editor = item.editor; 
			});
		} else {
			var e = $(jq).datagrid('getColumnOption', param.field);
			e.editor = param.editor;
		}
	},
	removeEditor : function(jq, param) {
		if (param instanceof Array) {
			$.each(param, function(index, item) {
				var e = $(jq).datagrid('getColumnOption', item);
				e.editor = {};
			});
		} else {
			var e = $(jq).datagrid('getColumnOption', param);
			e.editor = {};
		}
	}
});

/**
 * 自定义校验规则
 */
$.extend($.fn.validatebox.defaults.rules, {   
	//唯一校验
    unique: {   
        validator: function(value, param){   
        	var id = $("#id").val() || 0;
        	var params = {};
        	$.each(param, function(index, tmp){
        		params[tmp] = $('#' + tmp).val() || $("input[name='" + tmp + "']").val();
        	});
        	params.id = id;
        	var flag = false;
        	$.ajax({
        		url: _UNIQUE_URL,
        		async: false,
                type: "POST",
                data: params,
                success: function(retData) {
                    flag = retData
                },
                error: function (xhr, rtmsg, e) { 
                }
        	});
            return flag;   
        },   
        message: '当前字段值已存在'  
    },   
	//身份证校验
	idcard: {
		validator: function(value, param){   
        	var result = verify_idcard(value);
        	if (result && result.rtcode == 1) {
        		return true;
        	} 
        	return false;
        },   
        message: '身份证非法'  
	},
	//验证手机号  
    telephone: { 
        validator: function(value, param){
         return /^1[3-8]+\d{9}$/.test(value);
        },   
        message: '请输入正确的手机号码'  
    },
    phone:{ //既验证手机号，又验证座机号
      validator: function(value, param){
          return /(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\d3)|(\d{3}\-))?(1[358]\d{9})$)/.test(value);
         },   
         message: '请输入正确的电话号码'
    }
});

/**
 * 重写datebox的parser
 */
$.fn.datebox.defaults.parser = function(s){
	var t = Date.parse(s);
	if (!isNaN(t)){
		return new Date(t);
	} else {
		return new Date();
	}
}


/**
 * 数据字典初始化
 */
$(function() {
	$(".dict").combobox({
		loader: dict_loader,
		valueField: 'dictid',
		textField: 'dictname'
	});
});

/**
 * 数据字典查询加载器
 * @param params
 * @param success
 * @param error
 */
function dict_loader(params, success, error) {
	var that = $(this);  
    var opts = that.combobox("options");  
    var dicttypeid = opts.dicttypeid;
    $.ajax({  
        url: '/dict/queryDictsByDicttypeid',  
        type: "POST",  
        data: {dicttypeid: dicttypeid}, 
        success: function(retData) {  
            success(retData);
        },  
        error: function(xhr, rtmsg, e) { 
        } 
    });  
}

/**
 * 数据字典转换
 * 使用方法：
 * 	1、在datagrid的columns子元素属性中添加 dicttypeid 属性
 * 	2、设置columns子元素属性的formatter 为 dict_formatter
 */
function dict_formatter(value, rowData, rowIndex) {
	var that = this;
	if (that.dicttypeid) {
		$.ajax({  
	        url: '/dict/getDict',  
	        type: "POST",  
	        async: false,
	        data: {
	        	dicttypeid: that.dicttypeid,
	        	dictid: value
	        }, 
	        success: function(retData) {  
	            value = retData.dictname;
	        }
	    }); 
	} 
	return value;
}

/**
 * 引用实体id转换
 * 描述：用于引用实体的展示
 * 使用方法：
 * 	1、在datagrid的columns子元素属性中添加 
 * 	   url(用于查询引用实体)、refname(引用实体的展示属性)、params(查询参数，非必须项，如果有除id额外的参数时使用)
 * 	2、设置columns子元素属性的formatter 为 datagrid_ref_formatter
 */
function datagrid_ref_formatter(value, rowData, rowIndex) {
	var that = this;
	var url = that.url;
	var refname = that.refname;
	var params = that.params || {};
	params.id = value || 0;
	$.ajax({  
        url: url,  
        type: "POST",  
        async: false,
        data: params, 
        success: function(retData) {  
            value = retData[refname];
        }
    }); 
	return value;
}


