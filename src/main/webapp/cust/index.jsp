<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

<script src="../resources/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="../resources/js/dwz.util.number.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	DWZ.init("../resources/dwz.frag.xml", {
		//loginUrl:"login_dialog.html", loginTitle:"登录",	// 弹出登录对话框
		//loginUrl:"login.html",	// 跳到登录页面
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
				<a class="logo" href="#">标志</a> <span class="title">新大陆"易收银"YPOS订单平台</span>
				<ul class="nav">
					<li>
						<%
							org.ohuyo.rapid.base.TcustSession tcustSession = (org.ohuyo.rapid.base.TcustSession) org.ohuyo.rapid.base.servlet.AppSessionFilter.getAppSession();
							com.newland.posmall.bean.customer.TcustReg tcustReg = tcustSession.getTcustReg();
							String name = "";
							if(tcustReg!=null){
								name = tcustSession.getTcustReg().getName();
							}else{
								name = tcustSession.getTcust().getLoginName();
							}
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
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse">
					<h2>客户主菜单 ${session.menuNode.subMenus.value}</h2>
					<div>收缩</div>
				</div>

				<div class="accordion" fillSpace="sidebar">
					<div class="accordionContent">
						<posmall:menu />
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span
										class="home_icon">帮助信息</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div>
					<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">帮助信息</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<!-- <div class="accountInfo"></div> -->
						<div class="pageFormContent helpinfo" layoutH="80">
							<fieldset>
								<legend>帮助信息</legend>
								<ul>
									<li style="margin: 5px 2px;"><strong
										style="font-size: 15px">系统基本流程介绍：</strong></li>
									<li>注册 → 登录 → 公司注册信息管理 → 我要预订 → 我要付款 → 我要点单 → 我要付款 → 我要发货</li>
									<li style="margin: 5px 2px;"><strong
										style="font-size: 15px">功能介绍：</strong></li>
									<li>1. 我要预约：
										<ul>
											<li>（1）可以根据需要预约订单，选择产品及其数量；</li>
											<li>（2）可以查看已签署的协议内容与详细情况；</li>
											<li>（3）可以撤销未开始付款的协议。</li>
										</ul>
									</li>
									<li>2. 我要点单：
										<ul>
											<li>（1）可以根据需要进行点单，可以选择订单的产品、时间和数量；</li>
											<li>（2）可以查看已生成的订单详细情况；</li>
											<li>（3）可以撤销未开始付款的订单。</li>
										</ul>
									</li>
									<li>3. 我要付款：
										<ul>
											<li>（1）可以上传付款凭证；</li>
											<li>（2）可以查看已有的付款凭证信息。</li>
										</ul>
									</li>
									<li>4. 我要发货：
										<ul>
											<li>（1）可以选择订单物流配送的产品数量、地址、物流公司以及发货时间；</li>
											<li>（2）可以查看已制定的物流单详细情况；</li>
											<li>（3）可以撤销未审核的物流单。</li>
										</ul>
									</li>
									<li>5. 公司注册信息管理：用于填写公司的详细信息和联系信息。</li>
									<li>6. 密码修改：用于修改账号密码。</li>
									<li style="margin: 5px 2px 1px 2px;">
										<strong style="font-size: 15px">更多详细信息与疑问请咨询</strong>&nbsp;&nbsp;&nbsp;&nbsp;
										<strong style="font-size: 15px">联系人：谢清</strong>&nbsp;&nbsp;&nbsp;&nbsp;
										<strong style="font-size: 15px">联系电话：13600880856</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<%-- 暂时注释，待解决完apache配置问题再开放下载功能
										<a  href="${ctx}/cust/info/downOperationManual.do" style="color: blue;text-decoration:underline; line-height: 16px; font-size: 15px">单击此处下载客户操作手册</a>
										 --%>
									</li>
								</ul>
							</fieldset>
						</div>

					</div>

				</div>
			</div>
		</div>
	</div>
	<div id="footer">Copyright &copy; 2014 福建新大陆支付技术有限公司 Inc.All Rights Reserved.</div>
</body>
</html>