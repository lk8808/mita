var _GLOBAL_COOKIES = document.cookie;
var _DFE_COOKIE_CFG = {
	expires:function(days){
		var date=new Date(); 
		date.setTime(date.getTime()+(days||10)*24*3600*1000); 
		return date.toGMTString();
	},///默认设置10天过期
	path:'/',
	domain:'',
	secure:'true'
};
/////获取某个key的cookie值
function getCookie(key)
{
	var strCookie = _GLOBAL_COOKIES||document.cookie;
	var arrCookie = strCookie.split(";"); 
	for(var i=0;i<arrCookie.length;i++)
	{ 
		var arr=arrCookie[i].split("="); 
		var cookieKey = arr[0].replace(/^(\s|\u00A0)+/,'').replace(/(\s|\u00A0)+$/,'');//去除空格
		if(cookieKey==key)
		{
			return arr[1]; 
		}
	} 
	return ""; 
}
/////设置某个key的cookie值及其相关属性
function setCookie(key,value,options)
{
	var cookieStr = key+"="+escape(value); 
	if(options)
	{
		cookieStr += (options.expires||_DFE_COOKIE_CFG.expires(10)||'')?(';expires='+(options.expires||_DFE_COOKIE_CFG.expires(10)||'')):('')
				   + (options.path||_DFE_COOKIE_CFG.path||'')?(';path='   +(options.path||_DFE_COOKIE_CFG.path||'')):('')
				   + (options.domain||_DFE_COOKIE_CFG.domain||'')?(';domain=' +(options.domain||_DFE_COOKIE_CFG.domain||'')):('')
				   + (options.secure||_DFE_COOKIE_CFG.secure||'')?(';secure=' +(options.secure||_DFE_COOKIE_CFG.secure||'')):('');
	}
	else{
		cookieStr += (_DFE_COOKIE_CFG.expires(10)||'')?(';expires='+(_DFE_COOKIE_CFG.expires(10)||'')):('')
				   + (_DFE_COOKIE_CFG.path||'')?(';path='   +(_DFE_COOKIE_CFG.path||'')):('')
				   + (_DFE_COOKIE_CFG.domain||'')?(';domain=' +(_DFE_COOKIE_CFG.domain||'')):('')
				   + (_DFE_COOKIE_CFG.secure||'')?(';secure=' +(_DFE_COOKIE_CFG.secure||'')):('');
	}	
	document.cookie=cookieStr; 
}
////删除某个key的cookie值
function delCookie(key)
{
	var date=new Date(); 
	date.setTime(date.getTime()-10000); 
	document.cookie=key+"=;expires="+date.toGMTString(); 
}