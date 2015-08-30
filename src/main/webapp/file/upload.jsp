<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
	<div id="content" align="center">
		<form action="${ctx}/file/upload.do" method="post" enctype="multipart/form-data">
		<h2 align="center">文件上传页面</h2><br>
		<table border="1" style="border-collapse: collapse"
			bordercolor="green" align="center">
			<tr>
				<td>上传文件名：</td>
				<td><input type="file" name="filename"></td>
			</tr>
			<tr>
				<td>上传文件名1：</td>
				<td><input type="file" name="filename1"></td>
			</tr>
			<tr>
				<td>上传文件名s：</td>
				<td><input type="file" name="filename2"></td>
			</tr>
			<tr>
				<td>存储位置：</td>
				<td><select name="location">
				<option value="1" selected="selected">数据库</option>
				<option value="0">磁盘</option>
				</select></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">提交</button>
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>