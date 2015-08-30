<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.lang.*" %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新大陆"易收银"YPOS订单平台</title>
<script src="${ctx}/resources/js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/dwz.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery.validate.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/rsa/jsbn.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/rsa/prng4.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/rsa/rng.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/rsa/rsa.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/rsa/password.js" type="text/javascript"></script>
<link href="../resources/themes/css/login.css" rel="stylesheet" type="text/css" />
<link href="../resources/themes/css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(function(){
		var err = "${errMsg}";
		if(err != null && err.trim() != "") {
			alert(err);
		}
		
		var href = window.location.href,hrefArr,
		loginUrl = "${ctx}/cust/login.do",
		regExp = "^(https|http)://"+"([^\/\:]+)"+"(:[0-9]+)?",
		re = new RegExp(regExp,"gi");
		href = href.indexOf("?") < 0 ? href : href.substring(0,href.indexOf("?")+1);
		//if ( href.indexOf(loginUrl) < 0 ){
		if ( href.substring(href.match(re)[0].length) != loginUrl ){
			window.location.href = href.match(re) + loginUrl;
		}
		$.validator.addMethod( "confirmPassword",confirmPassword,"密码不一致" );
		
		// 帮助信息点击
        $("#helpInfo").click(function (event) {
            var e = window.event || event;
            if (e.stopPropagation) {
                e.stopPropagation();
            } else {
                e.cancelBubble = true;
            }
        });
        $(".help_info").click(function (event) {
            var e = window.event || event;
            if (e.stopPropagation) {
                e.stopPropagation();
            } else {
                e.cancelBubble = true;
            }
        });
		document.onclick = function (event) {
        	var e = window.event || event;
        	if (e.stopPropagation) {
                e.stopPropagation();
            } else {
                e.cancelBubble = true;
            }
            closeHelpInfo();
        }; 
	});
	function confirmPassword(){
		var _password = $("#password_register").val();
		var _confirm_password = $("#confirm_password_register").val();
		if(_password != _confirm_password && _password != "" && _confirm_password !="") {
			return false;
		}
		return true;
	}
	//验证码刷新
	function refreshValidCode(e){
		var t = new Date();
		var srcOld = $(e).attr("src");
		var srcNew = srcOld.substring(0,srcOld.lastIndexOf("=")+1) + t.getTime();
		$(e).attr("src",srcNew);
	}
	//切换注册页
	function toRegister(){
		$(".loginForm").hide();
		$(".head_title").hide();
		$(".registerForm").show();
		$(".head_title_register").show();
	}
	//切换登录页
	function toLogin(){
		//$(".loginForm").show();
		//$(".head_title").show();
		//$(".registerForm").hide();
		//$(".head_title_register").hide();
		window.location.reload();
	}
	//登录前操作
	function beforeSubmit() {
		/* var $j_username = $("#j_username");
		if(!$j_username.val()){
			alert("请输入用户名！");
			$j_username.focus();
			return false;
		} */
		if(!$("#custloginForm").valid()) {
			return false;
		}
		$("#password").val(passwordEncrypt($("#password_").val()));
		return true;
	}
	function beforeRegisterSubmit() {
		var _login_name = $("#name_register").val();
		_password = $("#password_register").val(),
		_password_register = $("#confirm_password_register").val();		
		//验证码
		var _validCode = $("#valid_code_register").val();
		//注册码
		var _custCode = $("#cust_code").val();
		if( !$(".register_form").valid() && 
				!(_password == _password_register && _login_name != "" && _password != "" && _validCode != "" && _custCode != "") ){
			return;
		}
		//密码加密
		_password = passwordEncrypt(_password);
		$("#password_register").val(_password);
		$("#confirm_password_register").val(_password);	
		
		
		$.ajax({
			type : "POST",
			url : "${ctx}/validateReg.do",
			data : "loginName=" + _login_name + "&password=" + _password + "&validCode=" + _validCode + "&custCode=" + _custCode,
			success : function(data) {
				if(null == data || "" == data || data.length < 1) {
					//$("#name_register").val("");
					$("#password_register").val("");
					$("#confirm_password_register").val("");
					$("#valid_code_register").val("");
					alert("未知原因，注册失败，请重试！");
					refreshValidCode(document.getElementById("valid_code_img_register"));
					return;
				}
				if("0" != data.substr(0, 1)) {
					//$("#name_register").val("");
					$("#password_register").val("");
					$("#confirm_password_register").val("");
					$("#valid_code_register").val("");
					alert(data.substr(2));
					refreshValidCode(document.getElementById("valid_code_img_register"));
					return;
				}
				$(".register_form").submit();
			}
		});	
	}
	// 帮助信息显示
	function showHelpInfo() {
		$(".help_info").css({"display": "block"});
	}
	// 帮助信息关闭
	function closeHelpInfo() {
		$(".help_info").css({"display": "none"});
	}
</script>
</head>
<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<a href="#">
					<img src="../resources/themes/default/images/logo.png" />
					<span class="title">新大陆"易收银"YPOS订单平台</span> 
					<span class="link">www.yposmall.com</span>
				</a>
			</h1>
			<div class="login_headerContent">
				<div class="navList" style="padding-right:54px;">
					<ul style="float: right">
						<li><a href="#" onclick="showHelpInfo();" id="helpInfo" style="font-size: 14px;font-weight: bold;">帮助</a></li>
					</ul>
				</div>
				<h2 class="login_title">
					<img src="../resources/themes/default/images/login_title.png"/>
					<span class="head_title">YPOS订单平台</span>
					<span class="head_title_register">客户注册</span>
				</h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form id="custloginForm" action="${ctx}/cust/authenticate.do" method="post" onsubmit="return beforeSubmit();">
					<p
						style="color: red; font-size: 14px; text-align: center; margin-top: 0;">
						${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}</p>
					<p>
						<label>登录名：</label> <input type="text" name="loginName"
							id="loginName" style="width: 150px" class="login_input required" />
					</p>
					<p>
						<label>密码：</label> <input type="password" style="width: 150px" id="password_" class="required" />
					</p>
					<p>
						<label>验证码：</label> <input name="validCode" class="code required" type="text"
							size="5" maxlength="4" />
						<%
							long random = System.currentTimeMillis();
						%>
						<a href="javascript:void(0)" title="刷新验证码"><img src="${ctx}/kaptcha.jpg?d=<%=random%>" alt=""
							width="75" height="24" onclick="return refreshValidCode(this)"/></a>
					</p>
					<div class="btn_bar">
						<input class="sub" type="submit" value="登录" style="margin-right: 5px;"/> 
						<input type="hidden" name="password" id="password" /> 
						<a href="javascript:void(0)" onclick="toRegister()">我要注册</a>
					</div>
				</form>
			</div>
			<div class="registerForm">
				<form class="register_form" action="${ctx}/register.do" method="post" >
					<p>
						<label>登录名：</label>
						<input type="text" id="name_register" style="width: 150px"  name="loginName" class="required" />
					</p>
					<p>
						<label>登录密码：</label> 
						<input type="password" id="password_register" style="width: 150px" name="password" class="required"  />
					</p>
					<p>
						<label>确认密码：</label>
						<input type="password" id="confirm_password_register" style="width: 150px" name="confirmPassword" class="required confirmPassword" />
					</p>
					<p>
						<label>注册码：</label>
						<input type="text" id="cust_code" name="custCode" style="width: 150px" class="required" />
					</p>
					<p>
						<label>验证码：</label> 
						<input name="validCode" id="valid_code_register" class="code required" type="text" size="5" maxlength="4" />
						<%
							long random2 = System.currentTimeMillis();
						%>
						<a href="javascript:void(0)" title="刷新验证码"><img src="${ctx}/kaptcha.jpg?d=<%=random2%>" alt=""
							width="75" height="24" onclick="return refreshValidCode(this)" id="valid_code_img_register"/></a>
					</p>
					<div class="btn_bar">
						<input class="sub" type="button" value="注册"  onclick="beforeRegisterSubmit()" style="margin-right: 5px;"/> 
						<input class="sub" type="button" value="返回" onclick="toLogin()"/> 
					</div>
				</form>
			</div>
			<div class="login_banner">
				<div class="help_info" style="display: none;">
					<p><strong>帮助信息</strong></p>
					<ul>
						<li>
							<strong>项目介绍</strong>
							<ul style="padding-top: 3px;">
								<li>本着支持客户的收单业务发展，通过自主开发的便捷有效的“新大陆“易收银”YPOS订单平台”，让客户享受高效便捷的预约订单、采购要货、付款、通知发货等一系列高品质服务。</li>
							</ul>
						</li>
						<li>
							<strong>操作介绍</strong>
							<ul style="padding-top: 3px;">
								<li>客户需要注册产生账号，账号用于登录该系统完成后续操作。（注册中所需要的注册码由签署合同时分发）</li>
							</ul>
						</li>
						<li style="padding-bottom: 1px;">
							<strong>更多详细信息与疑问请咨询</strong>&nbsp;&nbsp;&nbsp;&nbsp;
						</li>
						<li style="padding-top: 1px;"><strong>联系人：谢清</strong>&nbsp;&nbsp;&nbsp;&nbsp;
							<strong>联系电话：13600880856</strong></li>
					</ul>
					<input type="button" value=" 关  闭 " onclick="closeHelpInfo();" style="margin-left: 275px; margin-top: 10px;cursor: pointer;"/>
				</div>
				<img src="../resources/themes/default/images/login_banner.jpg" />
			</div>
			<div class="login_main">
			</div>
		</div>
		<div id="login_footer">Copyright &copy; 2014 福建新大陆支付技术有限公司 Inc.All Rights Reserved.</div>
	</div>
</body>
</html>