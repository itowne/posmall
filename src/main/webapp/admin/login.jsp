<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新大陆"易收银"YPOS订单平台</title>
<link href="../resources/themes/css/login.css" rel="stylesheet" type="text/css" />
<link href="../resources/themes/css/index.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/resources/js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/dwz.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery.validate.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/rsa/jsbn.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/rsa/prng4.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/rsa/rng.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/rsa/rsa.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/rsa/password.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		var err = "${errMsg}";
		if(err != null && err.trim() != "") {
			alert(err);
		}
		
		var href = window.location.href,hrefArr,
		loginUrl = "${ctx}/admin/login.do",
		regExp = "^(https|http)://"+"([^\/\:]+)"+"(:[0-9]+)?",
		re = new RegExp(regExp,"gi");
		href = href.indexOf("?") < 0 ? href : href.substring(0,href.indexOf("?")+1);
		if ( href.substring(href.match(re)[0].length) != loginUrl ){
			window.location.href = href.match(re) + loginUrl;
		}
	});
	function beforeSubmit() {
		/* var $j_username = $("#j_username");
		if(!$j_username.val()){
			alert("请输入用户名！");
			$j_username.focus();
			return false;
		} */
		if(!$("#adminloginForm").valid()) {
			return false;
		}
		$("#password").val(passwordEncrypt($("#password_").val()));
		return true;
	}
	function refreshValidCode(e){
		var t = new Date();
		var srcOld = $(e).attr("src");			
		var srcNew = srcOld.substring(0,srcOld.lastIndexOf("=")+1) + t.getTime();
		$(e).attr("src" ,srcNew);
	}
</script>
</head>

<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<a href="#"> <img
					src="../resources/themes/default/images/logo.png"></img> <span
					class="title">新大陆"易收银"YPOS订单平台</span> <span class="link">www.yposmall.com</span>
				</a>
			</h1>
			<div class="login_headerContent">
				<h2 class="login_title">
					<img src="../resources/themes/default/images/login_title.png" /> 
					<span class="head_title">YPOS订单平台</span>
				</h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form id="adminloginForm" action="${ctx}/admin/authenticate.do" method="post"
					onsubmit="return beforeSubmit();">
					<p
						style="color: red; font-size: 14px; text-align: center; margin-top: 0;">
						${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}</p>
					<p>
						<label>登录名：</label> <input type="text" name="loginName"
							id="loginName" class="login_input required" style="width: 150px" />
					</p>
					<p>
						<label>密码：</label> <input type="password" id="password_"
							class="login_input required" style="width: 150px" />
					</p>
					<p>
						<label>验证码：</label> <input name="validCode" class="code required"
							type="text" size="5" maxlength="4" />
						<%
							long random = System.currentTimeMillis();
						%>
						<a href="javascript:void(0)" title="刷新验证码"><img src="${ctx}/kaptcha.jpg?d=<%=random%>" alt=""
							width="75" height="24" onclick="refreshValidCode(this)"/></a>
					</p>
					<div class="login_bar">
						<input class="sub" type="submit" value="登录" /> <input
							type="hidden" name="password" id="password" />
					</div>
				</form>
			</div>
			<div class="login_banner">

				<img src="../resources/themes/default/images/login_banner.jpg" />
			</div>
			<div class="login_main">
			
			</div>
		</div>
		<div id="login_footer">Copyright &copy; 2014 福建新大陆支付技术有限公司 Inc.
			All Rights Reserved.</div>
	</div>
</body>
</html>