<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%!public String getUrl(HttpServletRequest request) {
		String url = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath();
		if ("http".equals(request.getScheme())
				&& "80".equals(request.getServerPort())) {
			url = request.getScheme() + "://" + request.getServerName() + ":"
					+ request.getContextPath();
		}
		if ("https".equals(request.getScheme())
				&& "443".equals(request.getServerPort())) {
			url = request.getScheme() + "://" + request.getServerName() + ":"
					+ request.getContextPath();
		}
		return url;
	}%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>Welcome</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>
<script type="text/javascript">
	function go() {
		document.getElementById("indexCustLogin").click();
	}
</script>
<body onload="go();">
	<a id="indexCustLogin" href="<%=getUrl(request)%>/cust/login.do"> <font
		style="font-size: 20; font-weight: bold">客户入口</font>
	</a>
	<br />
	<br />
	<a href="<%=getUrl(request)%>/admin/login.do"><font
		style="font-size: 20; font-weight: bold">管理后台入口</font></a>
	<br />
	<br />
	<a href="<%=getUrl(request)%>/sys/login.do"><font
		style="font-size: 20; font-weight: bold">系统管理入口</font></a>
</body>
</html>
