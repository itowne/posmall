<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新大陆"易收银"YPOS订单平台</title>

</head>
<body>
  <img id="img" src="" width="300" height="300"/>
<script type="text/javascript">

var url = document.location.href.split("?imgSrc=")[1]; 
document.getElementById('img').src = "${ctx}/file/download.do?fileid="+url;
</script>

</body>
</html>