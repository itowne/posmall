<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>注册成功</title>
		<link href="resources/themes/css/register.css" rel="stylesheet" type="text/css" />
		<script src="resources/js/jquery-1.7.2.js" type="text/javascript"></script>
		<script type="text/javascript">
		$(function(){
			var changeNum = setInterval("changeNum()",1000);
			setTimeout('changePage()',5000);
		})
		function changeNum(){
			var sec = $(".timing").text() - 1;
			$(".timing").text(sec);
		}
		function changePage(){
			clearInterval(changeNum);
			window.location.href = "${ctx}/cust/login.do";
		}
		</script>
	</head>
	<body>
	<div class="result">
		<img src="resources/themes/default/images/right.jpg"></img>
		<div class="result_msg">
			<p class="result_success">恭喜您，您已注册成功 !</p>
			<p class="mt">
				<span class="timing">5</span>秒后跳转登陆页。。。	
				<a href="${ctx}/cust/login.do">立即跳转</a>
			</p>
		</div>
	</div>
	</body>
</html>