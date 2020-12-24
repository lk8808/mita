var userInfo = {};
		var firstappid ;
		var headleftwidth=($("#appnavpanel").prev()).width();
		var headrightwidth=($("#appnavpanel").next()).width();
		//设置应用导航上级DIV宽度
		var appnum = Math.max(parseInt(($(window).width()-headleftwidth-headrightwidth-75) /100)-2,1) ;
		//选中的应用id
		var selectappindexid = -2;
					
	    $(function(){
	    	//加载用户信息
	    	$.ajax({
                url: "/base/getSession",
                type: "POST",
                success: function(retData) {
                	if (retData.rtsts && retData.rtsts.code=="000000") {
                		userInfo.empname=retData.rtdata.userObject.employee.empname ;
                        $('#username').text(userInfo.empname);
                        //获取应用数据
                        initAppData();
                	}
                },
                error: function (xhr, rtmsg, e) { 
                }
            });
	            
	        //tabs绑定右键菜单
			initTabsMenu();
			//初始化首页
			initHomePage();
			
	    });
	    
	    function initHomePage(){
// 	    	var content = '<iframe frameborder="0" src="' + menuurl + '"style="width:100%;height:100%;display:block;"></iframe>';
// 	    	content:"<iframe src=\'./portal/menu/menu_mgr.html\'  frameborder=\'0\' style=\'width:100%;height:100%;\'></iframe>"
	    	$('#homepanel').panel({
	    		content:"<iframe src='home.html' scrolling='yes' frameborder='0' style='width:100%;height:100%;'></iframe>"
	    	});
	    }
	    
	    //获取应用数据
	    function initAppData(){
	    	$.ajax({
				url: "/application/queryAllAuthList",
				type: "POST",
				success: function(retData)
				{
					headleftwidth=($("#appnavpanel").prev()).width();
					headrightwidth=($("#appnavpanel").next()).width();
					//设置应用导航上级DIV宽度
					appnum = Math.max(parseInt(($(window).width()-headleftwidth-headrightwidth-75) /100)-2,1) ;
					if(retData.length>0){
						var appshtml="";
						$.each(retData, function(index, obj) {
							if(index==0){
								//初始化菜单
								//设置第一个应用为首个应用
								firstappid=obj.id;
								//获取首个应用的菜单数据
								//getMenuData(firstappid);
			    				initMenu(firstappid);
							}
							if(index<appnum){
								var imgurl=obj.bigicon||'';
								if(imgurl=='')
								{
								    imgurl="/img/main/application.png";
								}
								else
								{
								    imgurl=imageUrlUtil(imgurl);
								}
								appshtml+='<li appid="'+obj.id+'" indexid="'+index+'" active="false" onmouseover="appAddHighLight(this);"  onmouseout="appRemoveHighLight(this);" onclick="selectedAppClick(this);"> <img src="'+imgurl+'"/><span>'+obj.appname+'</span></li>';
							}
						});
						
						if(retData.length>=appnum){
							appshtml+='<li appid="'+0+'" indexid="-1" active="false" onmouseover="appAddHighLight(this);"  onmouseout="appRemoveHighLight(this);" onclick="selectedAppClick(this);"> <img src="/img/main/application.png"/><span>其他应用</span></li>';
						}
						
						$("#appnavcontent").html(appshtml);
						
						appnavPanelReset();
						
						$('#allappwin').window({
						    width:(appnum+1)*100+30,
// 							width:330,
						    height:200,
						    left:headleftwidth+30-2,
						    top:60,
						    modal:false,
						    noheader:true,
						    closed:true,
						    draggable:true
						});
						
						document.getElementById("allappwin").addEventListener("mouseleave", function () {
							$('#allappwin').window('close');
						},false);
						
						var appshtml="";
						$.each(retData, function(index, obj) {
							if(index>=appnum){
								var imgurl=obj.bigicon||'';
								if(imgurl=='')
								{
								    imgurl="/img/main/application.png";
								}
								else
								{
								    imgurl=imageUrlUtil(imgurl);
								}
								appshtml+='<li appid="'+obj.id+'" indexid="'+index+'" active="false" onmouseover="appAddHighLight(this);"  onmouseout="appRemoveHighLight(this);" onclick="selectedAppClick(this);" > <img src="'+imgurl+'"/><span>'+obj.appname+'</span></li>';
							}
						});
						$('#allappcontent').html(appshtml);
						
						var allapps=$("#appnavpanel ul li");
						for(var i=0,l=allapps.length;i<l;i++)
						{
						    //如果应用为首个应用
							if(allapps[i].getAttribute("indexid")==0)
							{
							    //设置应用为激活样式
								$(allapps[i]).addClass("appMenuActive");	
								$(allapps[i]).attr("active",true);
							}
						}
						
					}
				}
			});
	    }
	    
	    /*
		 * 图标URL处理函数
		 */
		function imageUrlUtil(url)
		{
		    var murl=url.replace(/\\/g,"/");
		    if(murl.indexOf("http")!=0)
		    {
	    		if(murl.indexOf("/")==0)
	    		{
	        		if(murl.indexOf(nui.context)!=0)
	        		{
	        			murl = contextPath + murl;
	        		}
	    		}
	    		else
	    		{
	    			murl = contextPath +"/"+ murl;
	    		}
	    	}
	    	return murl;
		}
	
		/*
		 * 设置导航栏样式
		 */
		function appnavPanelReset()
		{
			$(".navleftarrow").css("visibility","visible");
			$(".navrightarrow").css("visibility","visible");
			 //获取所有应用列表
			var allapplis=$("#appnavcontent li");
	        //获取logo、名字所在div宽度
			var headleftwidth=($("#appnavpanel").prev()).width();
			var headrightwidth=($("#appnavpanel").next()).width();
			//设置应用导航上级DIV宽度
			$("#appnavcontent").parent().css("width",$(window).width()-headleftwidth-headrightwidth-75);
			//设置左边箭头所在div向右移动距离
			$("#appnavpanel").css("left",(headleftwidth+5));
			//获取滚动面板宽度
			var panelwidth=$("#appnavcontent").parent().width();  
			//获取所有应用的左右空白之和 
			// 每个应用初始化加宽10 
			__appnavwidth=0;
			for(var i=0,l=allapplis.length;i<l;i++)
			{
				var marginl=parseInt($(allapplis[i]).css("marginLeft"));
				var marginr=parseInt($(allapplis[i]).css("marginRight"));
				marginl=(isNaN(marginl)?0:marginl)+(isNaN(marginr)?0:marginr);
				__appnavwidth=__appnavwidth+(allapplis[i].offsetWidth+10)+marginl;
			}
			
			if(__appnavwidth<=panelwidth)
			{
				$("#appnavcontent").css("left",0);
				$(".navleftarrow").css("visibility","hidden");
				$(".navrightarrow").css("visibility","hidden");
			}
			var leftnum=parseInt($("#appnavcontent").css("left"));	
			if((__appnavwidth+leftnum<=panelwidth))
			{
				$(".navrightarrow").css("visibility","hidden");
			}
			
			if(leftnum>=0 )
			{
				$(".navleftarrow").css("visibility","hidden");
			}
		}
		
		//左箭头事件响应
		$(".navleftarrow").click(function()
		{
		    var contentpanel=$("#appnavcontent");
		  	var leftnum=parseInt(contentpanel.css("left"));
		  	var pwidth=contentpanel.parent().width();
		  	leftnum+=(pwidth*0.8);
		  	if(leftnum>=0)
		  	{
		  		leftnum=0;
		  		$(this).css("visibility","hidden");
		  	}
		  	$(".navrightarrow").css("visibility","visible");
		  	contentpanel.animate({left:leftnum},700);
		  	
		});
		
		//右箭头事件响应
		$(".navrightarrow").click(function()
		{
		  	var contentpanel=$("#appnavcontent");
		  	var leftnum=parseInt(contentpanel.css("left"));
		  	var pwidth=contentpanel.parent().width();
		  	leftnum-=(pwidth*0.8);
		  	if((leftnum+(0-pwidth))<=(0-__appnavwidth)){
		  		leftnum=-(__appnavwidth-(pwidth*0.7));
		  		$(this).css("visibility","hidden");
		  	}
		  	$(".navleftarrow").css("visibility","visible");
		  	contentpanel.animate({left:leftnum},700);
		});
		
		//添加app导航高亮
		function appAddHighLight(t){
			$(t).addClass("appHighLight");
			if($(t).attr("indexid")<appnum){
				if($(t).attr("appid")==0){
					$('#allappwin').window('open');
				}else{
					$('#allappwin').window('close');
				}
			}
		}
		
		//移除app导航高亮
		function appRemoveHighLight(t){
			$(t).removeClass("appHighLight");
			if($(t).attr("indexid")<appnum){
				if(selectappindexid!=-1&&selectappindexid<appnum){
					$('#allappwin').window('close');
				}
			}
		}
		
		//应用选择处理方法
		function selectedAppClick(appele){
			selectappindexid = $(appele).attr("indexid");
			$("#appnavpanel ul li").each(function(){
				if($(this).attr("appid")!=0){
					$(this).removeClass("appMenuActive");
			    	$(this).attr("active",false);
				}else{
					if($(appele).attr("indexid")<appnum){
						$(this).removeClass("appMenuActive");
			    		$(this).attr("active",false);
					}
				}
			});
			if(selectappindexid!=-1){
				$("#allappwin ul li").each(function(){
				    $(this).removeClass("appMenuActive");
				    $(this).attr("active",false);
				});
			}
			
			if(appele){
				$(appele).addClass("appMenuActive");	
				$(appele).attr("active",true);
				if(selectappindexid!=-1){
					initMenu($(appele).attr("appid"));
				}
			}
		    
			if(selectappindexid==-1){
				$('#allappwin').window('open');
			}else{
				$('#allappwin').window('close');
			}
			if(selectappindexid!=-1){
				setRecordlog($(appele).attr("appid"),"app");
			}
		}
		
		// 记录应用日志
		function setRecordlog(recordid,retype){
		   $.ajax({
				url: "/recordlog/addRecordlog",
				type: "POST",
				data: {recordid: recordid,recordtype:retype},
				success: function(text)
				{
				} 
			});
		}
	    
	    //初始化菜单
	    function initMenu(appid) {
	    	var panels = $("#RightAccordion").accordion("panels");
	    	for(var i = panels.length-1 ;i>=0;i--){
	    		var panel=panels[i];
	            $('#RightAccordion').accordion('remove',  panel.panel('options').title);
	    	}
	    	//初始化手风琴面板
            $("#RightAccordion").accordion({ 
                fillSpace:true,
                border:false,
                animate:true  ,
                fit:true,
                multiple:false
            });
	    	$.ajax({
                url: "/menu/queryAuthMenusLevel1ByAppid",
                type: "POST",
                data: {appid: appid},
                success: function (retData) {
                    //一级菜单使用手风琴
                    $.each(retData, function(index, obj) {
                        $('#RightAccordion').accordion('add', {
                            id: "menuPanel" + obj.id,
                            title: obj.menuname,
                            content: "<ul id='tree" + obj.id + "'></ul>",
                            heightStyle :'content',
                            selected: false
                        });
                        //后面多级菜单使用树结构
                        $.ajax({
                            url: "/menu/getMenuTree",
                            type: "POST",
                            data: {parentid: obj.id},
                            success: function(retData) {
                                $("#tree" + obj.id).tree({
                                    data: retData,
                                    animate: true,
                                    onClick : function(node){  
                                    	$("#tree" + obj.id).tree("toggle", node.target);
                                        if (node.attributes.menuurl) {
                                            //打开菜单
                                            openMenu(node.id,node.text, node.attributes.menuurl);
                                        }
                                    }
                                });
                                $(".tree-icon").remove();
                            },
                            error: function (xhr, rtmsg, e) { 
                            }
                        });
                    });
                },
                error: function (xhr, rtmsg, e) { 
                }
            });
	    }
	    //初始化tab右键菜单
	    function initTabsMenu() {
	    	//绑定右键菜单
	    	$("#menuTab").tabs({
	    		onContextMenu: function(e, title, index) {
	    			e.preventDefault();
	    			$('#rcmenu').menu('show', {  
	                    left: e.pageX,  
	                    top: e.pageY  
	                }); 
	    			$("#menuTab").tabs("select", index);
	    		}
	    	});
	    	//关闭全部
	    	$("#closeall").bind("click", function() {
	    		var tablist =$('#menuTab').tabs('tabs');
	    		for(var i=tablist.length-1;i>=1;i--){  
                    $('#menuTab').tabs('close',i);  
                }  
	    	});
	    	//关闭其他
            $("#closeother").bind("click", function() {
                var tablist =$('#menuTab').tabs('tabs');
                var tab = $('#menuTab').tabs('getSelected');  
                var index = $('#menuTab').tabs('getTabIndex',tab); 
                for(var i=tablist.length-1;i>index;i--){  
                    $('#menuTab').tabs('close',i);  
                } 
                var num = index-1;                  
                if(num < 1){                    
                	return;                 
                } else {                      
                    for(var i=num;i>=1;i--) {                        
                    	$('#menuTab').tabs('close',i);                     
                    }                                
                }  
            });
            //关闭当前
            $("#close").bind("click", function() {
                var tablist =$('#menuTab').tabs('tabs');
                var tab = $('#menuTab').tabs('getSelected');  
                var index = $('#menuTab').tabs('getTabIndex',tab); 
                if(index>0){
                	$('#menuTab').tabs('close',index);  
                }
            });
	    }
	    
	    //打开菜单
	    function openMenu(menuid,title, menuurl) {
	    	if($('#menu'+menuid).length>0){
	    		if ($("#menuTab").tabs('exists', $("#menuTab").tabs('getTabIndex',$("#menu"+menuid)))) {
		    		$('#menuTab').tabs('select', $("#menuTab").tabs('getTabIndex',$("#menu"+menuid)));
		    		return;
		    	} 
	    	}
	    	
	    	var content = '<iframe frameborder="0" src="' + menuurl + '"style="width:100%;height:100%;display:block;"></iframe>';
	    	$("#menuTab").tabs('add', {
	    		id:'menu'+menuid ,
	    		title: title,
	    		content: content,
	    		closable: true
	    	});
	    	setRecordlog(menuid,"menu");
	    }
		
		//注销用户
	    function logout() {
	    	$.messager.confirm('注销确认','你是否注销用户？',function (r) {
	    		if(r){
	    			$.ajax({
		                url: "/login/logout",
		                type: "POST",
		                success: function(retData) {
		                	window.location.href = "/view/login.html";
		                },
		                error: function (xhr, rtmsg, e) { 
		                	$.messager.alert("消息", "注销失败");
		                }
		            });
	    		}
        	})
	    }
	    
	    
var isFullScreen = false;

var App = function () {
    var setFullScreen = function () {
        var docEle = document.documentElement;
        if (docEle.requestFullscreen) {
            //W3C
            docEle.requestFullscreen();
        } else if (docEle.mozRequestFullScreen) {
            //FireFox
            docEle.mozRequestFullScreen();
        } else if (docEle.webkitRequestFullScreen) {
            //Chrome等
            docEle.webkitRequestFullScreen();
        } else if (docEle.msRequestFullscreen) {
            //IE11
            docEle.msRequestFullscreen();
        } else {
            $.iMessager.alert('温馨提示', '该浏览器不支持全屏', 'messager-warning');
        }
    };

    //退出全屏 判断浏览器种类
    var exitFullScreen = function () {
        // 判断各种浏览器，找到正确的方法
        var exitMethod = document.exitFullscreen || //W3C
            document.mozCancelFullScreen ||    //Chrome等
            document.webkitExitFullscreen || //FireFox
            document.msExitFullscreen; //IE11
        if (exitMethod) {
            exitMethod.call(document);
        }
        else if (typeof window.ActiveXObject !== "undefined") {//for Internet Explorer
            var wscript = new ActiveXObject("WScript.Shell");
            if (wscript !== null) {
                wscript.SendKeys("{F11}");
            }
        }
    };

    return {
        init: function () {

        },
        handleFullScreen: function () {
            if (isFullScreen) {
                exitFullScreen();
                isFullScreen = false;
            } else {
                setFullScreen();
                isFullScreen = true;
            }
        }
    };
};

	    //窗口改变事件响应
		window.onresize = function(){
			appnavPanelReset();
		};
		
		//回到主页	
		function gohomePanel(){
			 $('#menuTab').tabs('select', 0);
		}
		
		//刷新本页
		function refreshPanel(){
			var refresh_tab = $('#menuTab').tabs('getSelected');
            var refresh_iframe = refresh_tab.find('iframe')[0];
            refresh_iframe.contentWindow.location.href = refresh_iframe.src;
		}
		
		//关闭本页
		function removePanel(){
			var index = $('#menuTab').tabs('getTabIndex', $('#menuTab').tabs('getSelected'));
            var tab = $('#menuTab').tabs('getTab', index);
            if (tab.panel('options').closable) {
                $('#menuTab').tabs('close', index);
            }
		}
		
		/**
		 * 证件号改变后回写生日和性别
		 * @param newValue
		 * @param oldValue
		 */
		function idnumChange(newValue,oldValue) {
			var gender = getGenderByIdnum(newValue);
		    var birthday = getBirthdayByIdnum(newValue);
		    $("#birthday").datebox('setValue', birthday);
		    $("#gender").combobox('setValue', gender);
		}
		
		//屏幕最大
		function screenExpand(){
			App().handleFullScreen();	
		}
		
		//查看信息
		function openShowUserInfoWin(){
			//获取用户信息
			$.ajax({
                url: "/employee/getMyInfo",
                type: "POST",
                success: function(retData) {
					$dataForm = $("#userInfoSetDataForm");
				    $dataForm.form('load',retData);
				    $("#img").attr("src", "data:image/jpeg;base64," + retData.photo);
                	$('#modifyUserInfoWin').window('open');
                	$('#modifyUserInfoWin').dialog('setTitle', '个人信息');
                	$('#modifyUserInfoSavebtn').hide();
                	
                },
                error: function (xhr, rtmsg, e) { 
                
                }
            });
		}
		
		//编辑用户信息
		function openModifyUserInfoWin(){
			//获取用户信息
			$.ajax({
                url: "/employee/getMyInfo",
                type: "POST",
                success: function(retData) {
					$dataForm = $("#userInfoSetDataForm");
				    $dataForm.form('load',retData);
				    $("#img").attr("src", "data:image/jpeg;base64," + retData.photo);
                	$('#modifyUserInfoWin').window('open');
                	$('#modifyUserInfoWin').dialog('setTitle', '修改个人信息');
                	$('#modifyUserInfoSavebtn').show();
// 					$('#empno').textbox({disabled:true});
// 					$('#empname').textbox({disabled:true});
                },
                error: function (xhr, rtmsg, e) { 
                
                }
            });
			
		}
		//保存用户信息
		function saveUserInfo(){
			$.messager.progress({
		        title: '请稍后',
		        msg: '正在保存...',
		        text: '正在保存...'
		    });
		    $("#userInfoSetDataForm").form('submit', {
		        url: '/employee/save',
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
		                $('#modifyUserInfoWin').window('close');
		            } else {
		                $.messager.alert("消息", "保存失败");
		            }
		        },
		        error: function(xhr, rtmsg, e) {
		        	$.messager.alert("消息", "程序异常");
		        }
		    });
		}
		
		
		//修改密码
		function openModifyPwdWin(){
			$('#modifyPwdWin').window('open');
// 			alert($(":input[name='currentUserInfo']").length);
// 			var currentUserInfo =$(":input[name='currentUserInfo']")[0] ;
			$('#modifyPwdDataForm').form('reset');
			var currentUserInfo =$('#currentUserInfo');
			currentUserInfo.textbox('setValue',userInfo.empname);
// 			alert(userInfo.empname);
// 			$('#currentUserInfo').textbox('setValue',userInfo.empname);
// 			currentUserInfo
		}
		
		//保存密码
		function saveModifyPwd(){
			$.messager.progress({
		        title: '请稍后',
		        msg: '正在保存...',
		        text: '正在保存...'
		    });
		    var param = {};
		    param.oldpwd = $('#oldpwd').textbox('getValue');;
			param.pwd = $('#pwd').textbox('getValue');
			param.pwdcomfim = $('#pwdcomfim').textbox('getValue');
			
		    $("#modifyPwdDataForm").form('submit', {
		        url: '/user/modifyPwd',
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
		                $('#modifyPwdWin').window('close');
		            } else {
		                $.messager.alert("消息", retData.rtsts.msg);
		            }
		        },
		        error: function(xhr, rtmsg, e) {
		        	$.messager.alert("消息", "程序异常");
		        }
		    });
			
		}
		
		var editRow = undefined;
		//首页设置
		function openHomeSetWin(){
			$('#homeSetWin').window('open');	
			editRow = undefined;
			$("#homesetgrid").datagrid({
				url: '/homecfg/queryMyhomecfg',
				singleSelect: true,
		        loadMsg:'加载中...',
		        columns:[[
		            {field:'menuid', title: '功能id', editor:{type:'textbox'}, hidden:true},
		            {field:'menuname', title: '功能名称', width: 150, 
		   			 editor: {
		   				 type:'combobox', 
		   				 options:{
		   					 panelHeight: 'auto',
		   					 url: '/menu/queryAuthHomeMenus',
		   					 method:'post',
		   				     valueField: 'menuname',
		   					 textField: 'menuname',
		   					 onSelect: function(record) {
		   						 var editor = $("#homesetgrid").datagrid('getEditor', {index:editRow, field:'menuid'});
		   						 editor.target.textbox('setValue', record.id);	
//		   						 initLnkname(record.dictid);
		   					 }
		   				 }
		   			}},
		            {field:'widthcell', title: '宽（占几分之一）', editor:{type:'numberbox',required: true}},
		            {field:'heightcell', title: '高（单位高）', editor:{type:'numberbox',required: true}},
		            {field:'sortno', title: '排序', editor:{type:'numberbox'}},
					{field:'opt', title: '操作', align:'center', width: 100,
						formatter: function(value, row, index) {
							var delBtn = "<a href='javascript:void(0)' onclick='deleteHomesetRow(" + index + ")' class='delcls' >删除</a>";
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
		                    $("#homesetgrid").datagrid('endEdit', editRow);
		                }
		                if (editRow == undefined) {
		                	var footerrows = $("#homesetgrid").datagrid('getRows');
		                	var sortno = 0;
		                	if(footerrows!=undefined && footerrows.length>0&&footerrows[footerrows.length-1]['sortno']!=undefined) {
		                		sortno=Number(footerrows[footerrows.length-1]['sortno'])+1;
		                	}
		                    $("#homesetgrid").datagrid('appendRow', {widthcell:2,heightcell:4,sortno:sortno});
		                    var rows =  $("#homesetgrid").datagrid('getRows');
		                    editRow = rows.length - 1;
		                    $("#homesetgrid").datagrid('beginEdit', editRow); 
		                }
		                $('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
		            }
		        }],
				onAfterEdit:function(index,row){
					editRow = undefined;
					$('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
				},
				onEndEdit: function(index,row){
//					if (lnkname != undefined) {
//						$("#homesetgrid").datagrid('getRows')[index]['lnkname'] = lnkname;
//						lnkname = undefined;
//					}
				},
				onClickRow:function(rowIndex,rowData){
		            if (editRow != undefined) {
		                $("#homesetgrid").datagrid('endEdit', editRow);
		            }  
		        },
		        onDblClickRow:function (rowIndex, rowData) {
		        	if (editRow != undefined) {
		                $("#homesetgrid").datagrid('endEdit', editRow);
		            }
		            if (editRow == undefined) {
		                $("#homesetgrid").datagrid('beginEdit', rowIndex);
		                editRow = rowIndex;
		            }
		        }
			});
		}
		
		/**
		 * 删除一行
		 */
		function deleteHomesetRow(index) {
//			if (editRow == index) {
				editRow = undefined;
//			}
			$("#homesetgrid").datagrid("deleteRow", index);
			$("#homesetgrid").datagrid("loadData", $("#homesetgrid").datagrid("getRows"));
		}
		
		/**
		 * 保存首页配置
		 */
		function saveHomeSet() {
			if (editRow != undefined) {
		        $("#homesetgrid").datagrid('endEdit', editRow);
		    }
			var params = {};
			var homesetcfgs = $("#homesetgrid").datagrid('getRows');
			for(var i = 0 ;i<homesetcfgs.length;i++){
				var row = homesetcfgs[i];
				if(row['menuname']==undefined ||row['menuname']=='' ||row['widthcell']==undefined ||row['heightcell']==undefined ||row['sortno']==undefined){
					$.messager.alert("消息", "请校验第"+Number(i+1)+"行数据！");
					return;
				}
			}
			//把所有number类型的属性转成string
			homesetcfgs = toStringValue(homesetcfgs);
			params.homesetcfgs = homesetcfgs;
			$.messager.progress({
		        title: '请稍后',
		        msg: '正在保存...',
		        text: '正在保存...'
		    });
			$.ajax({
		        url: "/homecfg/saveHomecfg",
		        type: "POST",
		        contentType: 'application/json;charset=utf-8',
		        dataType : "json",  
		        data: JSON.stringify(params), 
		        success: function(retData) {
		        	$.messager.progress('close'); 
		        	if (retData.rtsts && retData.rtsts.code == '000000') {
		        		$.messager.alert("消息", "保存成功");
		        		$('#homeSetWin').dialog('close');
		                var home_iframe = $('#homepanel').find('iframe')[0];
		                home_iframe.contentWindow.location.href = home_iframe.src;
		        	} else {
		        		$.messager.alert("消息", "保存失败");
		        	}
		        },
		        error: function (xhr, rtmsg, e) { 
		        	$.messager.progress('close'); 
		        }
		    });
		}
		
		
		