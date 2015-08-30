<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新大陆"易收银"YPOS订单平台</title>

<link href="../resources/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="../resources/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="../resources/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="../resources/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="../resources/themes/css/index.css" rel="stylesheet" type="text/css" media="screen"/>
<!--[if IE]>
<link href="../resources/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<!--[if lte IE 9]>
<script src="../resources/js/speedup.js" type="text/javascript"></script>
<![endif]-->

<script src="../resources/js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="../resources/js/jquery.cookie.js" type="text/javascript"></script>
<script src="../resources/js/jquery.validate.js" type="text/javascript"></script>
<script src="../resources/js/jquery.validate_extend.js" type="text/javascript"></script>
<script src="../resources/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="../resources/xheditor/xheditor-1.2.1.min.js" type="text/javascript"></script>
<script src="../resources/xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
<script src="../resources/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>

<script src="../resources/js/dwz.min.js" type="text/javascript"></script>
<script src="../resources/js/dwz.util.number.js" type="text/javascript"></script>
<script src="../resources/js/dwz.regional.zh.js" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
	DWZ.init("../resources/dwz.frag.xml", {
		//loginUrl:"login_dialog.do", loginTitle:"登录",	// 弹出登录对话框
		//loginUrl:"login.do",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		keys: {statusCode:"statusCode", message:"message"}, //【可选】
		ui:{hideMode:'offsets'}, //【可选】hideMode:navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"themes"}); // themeBase 相对于index页面的主题base路径
		}
	});
});

</script>
</head>

<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="#">标志</a>
				<span class="title">新大陆"易收银"YPOS订单平台</span>
				<ul class="nav">
					<li>
					<%
						org.ohuyo.rapid.base.TuserSession tuserSession = (org.ohuyo.rapid.base.TuserSession) org.ohuyo.rapid.base.servlet.AppSessionFilter.getAppSession();
					    String name = tuserSession.getTuserSub().getName();
					%>	
					<font color="white">欢迎您，<%=name%></font>
					</li>
					<li><a href="logout.do">退出</a></li>
				</ul>
			</div>

			<!-- navMenu -->
			
		</div>
		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>admin主菜单</h2><div>收缩</div></div>

				<div class="accordion" fillSpace="sidebar">
					<div class="accordionContent">
						<posmall:menu/>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="accountInfo">
							
						</div>
						<div class="pageFormContent" layoutH="80" style="margin-right:230px">
							

						</div>
						
					</div>
					
				</div>
			</div>
		</div>

	</div>

	<div id="footer">Copyright &copy; 2014 福建新大陆支付技术有限公司 Inc.All Rights Reserved.</div>

</body>
</html>