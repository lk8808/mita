/**
 * js工具类
 */
  

/**
 *	格式化日期
 *	value:传入的日期
 *	pattern:格式
 */
function formatDate(value, pattern) {
	if (value == null || value == '') {  
        return '';  
    }  
    var dt;  
    if (value instanceof Date) {  
        dt = value;  
    } else {  
    	dt = new Date(value);  
        if (isNaN(dt)) {  
            value = value.replace(/\/Date\//, '$1'); //将长字符串的日期值转换成正常的JS日期格式  
            dt = new Date();  
            dt.setTime(value);  
        }   
    }  
    var o = {  
        "M+": dt.getMonth() + 1, //month   
        "d+": dt.getDate(),    //day   
        "h+": dt.getHours(),   //hour   
        "m+": dt.getMinutes(), //minute   
        "s+": dt.getSeconds(), //second   
        "q+": Math.floor((dt.getMonth() + 3) / 3),  //quarter   
        "S": dt.getMilliseconds() //millisecond   
    }  
    if (/(y+)/.test(pattern)) {
    	pattern = pattern.replace(RegExp.$1, (dt.getFullYear() + "").substr(4 - RegExp.$1.length));  
    }
    for (var k in o) if (new RegExp("(" + k + ")").test(pattern)) {
    	pattern = pattern.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
    }
    return pattern;  
}


/*
根据〖中华人民共和国国家标准 GB 11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
    排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
    地址码表示编码对象常住户口所在县(市、旗、区)的行政区划代码。
    出生日期码表示编码对象出生的年、月、日，其中年份用四位数字表示，年、月、日之间不用分隔符。
    顺序码表示同一地址码所标识的区域范围内，对同年、月、日出生的人员编定的顺序号。顺序码的奇数分给男性，偶数分给女性。
    校验码是根据前面十七位数字码，按照ISO 7064:1983.MOD 11-2校验码计算出来的检验码。

出生日期计算方法。
    15位的身份证编码首先把出生年扩展为4位，简单的就是增加一个19或18,这样就包含了所有1800-1999年出生的人;
    2000年后出生的肯定都是18位的了没有这个烦恼，至于1800年前出生的,那啥那时应该还没身份证号这个东东，⊙﹏⊙b汗...
下面是正则表达式:
 出生日期1800-2099  (18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])
 身份证正则表达式    /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i      
 15位校验规则 6位地址编码+6位出生日期+3位顺序号
 18位校验规则 6位地址编码+8位出生日期+3位顺序号+1位校验位

 校验位规则     公式:∑(ai×Wi)(mod 11)……………………………………(1)
                公式(1)中： 
                i----表示号码字符从由至左包括校验码在内的位置序号； 
                ai----表示第i位置上的号码字符值； 
                Wi----示第i位置上的加权因子，其数值依据公式Wi=2^(n-1）(mod 11)计算得出。
                i 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
                Wi 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 1

	返回：reslut(包含两个属性)
			rtcode: 状态码 （1：成功，0：失败）
			rtmsg: 失败原因
*/
function verify_idcard(code) { 
	var city={
    		11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
    		21:"辽宁",22:"吉林",23:"黑龙江 ",
    		31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",
    		41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",
    		50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",
    		61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",
    		71:"台湾",81:"香港",82:"澳门",91:"国外 "
    	};
	var result = {
		rtcode: 1,
		rtmsg: ""
	};
	//格式验证 
    if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
    	result.rtcode = 0;
    	result.rtmsg = "身份证号格式错误";  
    } else if(!city[code.substr(0,2)]){ //地区验证 
    	result.rtcode = 0;
    	result.rtmsg = "地址编码错误";
    } else{
        //18位身份证需要验证最后一位校验位
        if(code.length == 18){
            code = code.split('');
            //∑(ai×Wi)(mod 11)
            //加权因子
            var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
            //校验位
            var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
            var sum = 0;
            var ai = 0;
            var wi = 0;
            for (var i = 0; i < 17; i++)
            {
                ai = code[i];
                wi = factor[i];
                sum += ai * wi;
            }
            var last = parity[sum % 11];
            if(parity[sum % 11] != code[17]){
            	result.rtcode = 0;
            	result.rtmsg = "校验位错误";
            }
        }
    }
    return result;
}

/*
 * 描述：根据idnum（验证后）身份证号码获取对应的出生日期（yyyy-MM-dd）
 * 输入：
 *     idnum:身份证号码;
 * 输出：
 *    birthday：出生日期（yyyy-MM-dd）
 */
function getBirthdayByIdnum(idnum) {
    var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;  
    var arr_data = idnum.match(re_eighteen);  
    var year = arr_data[2];  
    var month = arr_data[3];  
    var day = arr_data[4];  
    var birthday = year+'-'+month+'-'+day;
    return birthday;
};

/**
 * 通过出生日期获取年龄
 * @param birthday
 * @returns
 */
function getAge(birthday) {   
    var r = birthday.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);     
    if(!r)
    	return -1;     
    var d = new Date(r[1], r[3]-1, r[4]);     
    if (d.getFullYear() == r[1] && (d.getMonth()+1) == r[3] && d.getDate() == r[4]) {   
        var Y = new Date().getFullYear();   
        return Y - r[1];   
    }   
    return -1;   
}  

/*
 * 描述：根据idnum（验证后）身份证号码获取对应的性别
 * 输入：
 *     idnum:身份证号码;
 * 输出：
 *    F/M：性别标志；（F:女 M：男）
 */
function getGenderByIdnum(idnum) {
    var sexchar =idnum.substring(16,17);
    if(sexchar%2==0) {
	   return 'F';
	    
	} else {
	   return 'M';    
	}
}

/**
 * 获取文本的宽度
 * @param text
 * @returns
 */
function textWidth(text){ 
    var sensor = $('<pre>'+ text +'</pre>').css({display: 'none'}); 
    $('body').append(sensor); 
    var width = sensor.width();
    sensor.remove(); 
    return width;
}

/**
 * 预览图片
 * @param fileObj
 * @param imgPreviewId
 * @param divPreviewId
 */
function PreviewImage(fileObj,imgPreviewId,divPreviewId){  
    var allowExtention=".jpg,.bmp,.gif,.png";//允许上传文件的后缀名document.getElementById("hfAllowPicSuffix").value;  
    var extention=fileObj.value.substring(fileObj.value.lastIndexOf(".")+1).toLowerCase();              
    var browserVersion= window.navigator.userAgent.toUpperCase();  
    if(allowExtention.indexOf(extention)>-1){   
        if(fileObj.files){//HTML5实现预览，兼容chrome、火狐7+等  
            if(window.FileReader){  
                var reader = new FileReader();   
                reader.onload = function(e){  
                    document.getElementById(imgPreviewId).setAttribute("src",e.target.result);  
                }    
                reader.readAsDataURL(fileObj.files[0]);  
            }else if(browserVersion.indexOf("SAFARI")>-1){  
                alert("不支持Safari6.0以下浏览器的图片预览!");  
            }  
        }else if (browserVersion.indexOf("MSIE")>-1){  
            if(browserVersion.indexOf("MSIE 6")>-1){//ie6  
                document.getElementById(imgPreviewId).setAttribute("src",fileObj.value);  
            }else{//ie[7-9]  
                fileObj.select();  
                if(browserVersion.indexOf("MSIE 9")>-1)  
                    fileObj.blur();//不加上document.selection.createRange().text在ie9会拒绝访问  
                var newPreview =document.getElementById(divPreviewId+"New");  
                if(newPreview==null){  
                    newPreview =document.createElement("div");  
                    newPreview.setAttribute("id",divPreviewId+"New");  
                    newPreview.style.width = document.getElementById(imgPreviewId).width+"px";  
                    newPreview.style.height = document.getElementById(imgPreviewId).height+"px";  
                    newPreview.style.border="solid 1px #d2e2e2";  
                }  
                newPreview.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + document.selection.createRange().text + "')";                              
                var tempDivPreview=document.getElementById(divPreviewId);  
                tempDivPreview.parentNode.insertBefore(newPreview,tempDivPreview);  
                tempDivPreview.style.display="none";                      
            }  
        }else if(browserVersion.indexOf("FIREFOX")>-1){//firefox  
            var firefoxVersion= parseFloat(browserVersion.toLowerCase().match(/firefox\/([\d.]+)/)[1]);  
            if(firefoxVersion<7){//firefox7以下版本  
                document.getElementById(imgPreviewId).setAttribute("src",fileObj.files[0].getAsDataURL());  
            }else{//firefox7.0+                      
                document.getElementById(imgPreviewId).setAttribute("src",window.URL.createObjectURL(fileObj.files[0]));  
            }  
        }else{  
            document.getElementById(imgPreviewId).setAttribute("src",fileObj.value);  
        }           
    }else{  
        alert("仅支持"+allowExtention+"为后缀名的文件!");  
        fileObj.value="";//清空选中文件  
        if(browserVersion.indexOf("MSIE")>-1){                          
            fileObj.select();  
            document.selection.clear();  
        }                  
        fileObj.outerHTML=fileObj.outerHTML;  
    }  
}

//将对象的数字值转为字符串
function toStringValue(obj) {
    if (obj instanceof Array) {
        var arr = [];
        for (var i = 0; i < obj.length; i++) {
            arr[i] = toStringValue(obj[i]);
        }
        return arr;
    } else if (typeof obj == 'object') {
        for (var p in obj) {
            obj[p] = toStringValue(obj[p]);
        }
    } else if (typeof obj == 'number') {
        obj = obj + '';
    }
    return obj;
}