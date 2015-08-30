<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
	<div id="content" align="center">
		<form action="${ctx}/file/download.do" method="post">
		<h2 align="center">文件下载页面</h2><br>
			文件ID: <input name="fileid"></input>
			<button type="submit">提交</button>
		</form>
	</div>
</body>
</html>