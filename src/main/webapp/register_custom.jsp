<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>POS点单系统</title>
<link href="resources/themes/css/register.css" rel="stylesheet" type="text/css" />
<script src="resources/js/jquery-1.7.2.js" type="text/javascript"></script>

<!-- 密码加密js -->
<script src="${ctx}/resources/js/rsa/jsbn.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/rsa/prng4.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/rsa/rng.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/rsa/rsa.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/rsa/password.js" type="text/javascript"></script>
</head>
<body>
	<div class="body">
		<div id="register">
			<div id="register_header">
				<h2 class="register_title">
					用户注册
				</h2>
			</div>
			<div id="register_Content">
				<form class="register_form" method="post" action="${ctx}/register.do">
					<p>
						<label>用户名：</label>
						<input type="text" name="loginName" class="required" />
					</p>
					<p class="nowrap">
						<label>登录密码：</label>
						<input type="password" name="password" class="required" />
					</p>
					<p class="nowrap">
						<label>确认密码：</label>
						<input type="password" name="confirmPassword" class="required" />
					</p>
					<!-- <p id="teamAct">
						<label>公司名称：</label>
						<input type="text" name="teamAct.actName" class="required" />
					</p>
					<p id="registerDate" class="nowrap">
						<label>登录密码：</label>
						<input type="text" name="teamAct.actIntroduction" class="required" />
					</p>
					<p id="registerDate" class="nowrap">
						<label>确认密码：</label>
						<input type="text" name="teamAct.actIntroduction" class="required" />
					</p>
					<p id="proName">
						<label>公司地址：</label>
						<input type="text" name="teamAct.proName" class="required" />
					</p>
					<p id="registerDate" class="nowrap">
						<label>联系人：</label>
						<input type="text" name="teamAct.actIntroduction" class="required" />
					</p>
					<p id="registerDate" class="nowrap">
						<label>联系人电话：</label>
						<input type="text" name="teamAct.actIntroduction" class="required" />
					</p>
					<p id="registerDate" class="nowrap">
						<label>注册时间：</label>
						<input type="text" name="teamAct.actIntroduction" class="required" />
					</p>
					<p id="registerDate" class="nowrap">
						<label>注册资金：</label>
						<input type="text" name="teamAct.actIntroduction" class="required" />
					</p>
					<p id="registerDate" class="nowrap">
						<label>法人姓名：</label>
						<input type="text" name="teamAct.actIntroduction" class="required" />
					</p>
					<p id="registerDate" class="nowrap">
						<label>员工人数：</label>
						<input type="text" name="teamAct.actIntroduction" class="required" />
					</p>
					<p id="registerDate" class="nowrap">
						<label>公司类型：</label>
						<input type="text" name="teamAct.actIntroduction" class="required" />
					</p>
					<p id="registerDate" class="nowrap">
						<label>经营范围：</label>
						<input type="text" name="teamAct.actIntroduction" class="required" />
					</p>
					<p id="registerDate" class="nowrap">
						<label>营业执照：</label>
						<input type="file" name="teamAct.actIntroduction" class="required" />
					</p>
					<p id="registerDate" class="nowrap">
						<label>组织机构代码证：</label>
						<input type="file" name="teamAct.actIntroduction" class="required" />
					</p>
					<p id="registerDate" class="nowrap">
						<label>税务登记证：</label>
						<input type="file" name="teamAct.actIntroduction" class="required" />
					</p>
					<p id="registerDate" class="nowrap">
						<label style="vertical-align: top;">公司简介：</label>
						<textarea name="teamAct.actIntroduction"></textarea>
					</p> -->
					<div class="btn_bar">
						<input class="sub" type="button" value="注 册" onclick="return beforeSubmit();" style="margin-right:10px"/>
						<input class="sub" type="button" value="返 回" onclick="window.location.href='${ctx}/cust/login.do'"/>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		alert(<% out.println("yes");%>);
		function beforeSubmit() {
			var _login_name = $("input[name='loginName']").val();
			var _password = $("input[name='password']").val();
			var _confirm_password = $("input[name='confirmPassword']").val();
			if(null == _login_name || "" == _login_name.trim()) {
				alert("用户名不能为空！");
				return false;
			}
			if(null == _password || "" == _password.trim()) {
				alert("密码不能为空！");
				return false;
			}
			if(null == _confirm_password || "" == _confirm_password.trim()) {
				alert("确认密码不能为空！");
				return false;
			}
			if(_password != _confirm_password) {
				alert("输入的密码不一致！");
				return false;
			}
			
			//密码加密
			_password = passwordEncrypt(_password);
			$("input[name='password']").val(_password);
			$("input[name='confirmPassword']").val(_password);
			
			$.ajax({
				type : "POST",
				url : "${ctx}/validateReg.do",
				data : "loginName=" + _login_name + "&password=" + _password,
				success : function(data) {
					if(null == data || "" == data || data.length < 1) {
						$("input[name='password']").val("");
						$("input[name='confirmPassword']").val("");
						alert("未知原因，注册失败，请重试！");
						return;
					}
					if("0" != data.substr(0, 1)) {
						$("input[name='password']").val("");
						$("input[name='confirmPassword']").val("");
						alert(data.substr(2));
						return;
					}
					$(".register_form").submit();
				}
			});
		}
	</script>
</body>
</html>