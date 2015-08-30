<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.InputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
	p body{
		margin:0px;
	}
	.body{
		padding: 30px 50px;
		border: 5px solid #ccc;
		width: 500px;
		margin: 100px auto;
	}
	.body img{
		width:82px;
		height:93px;
		margin:20px 10px 0 0 ;
		vertical-align: top;
	}
	.result_msg{
		display: inline-block;
		width: 80%;
	}
	.errorContent{
		border-top:2px solid #ccc;
	}
	.wrap{
		word-break:break-all;
		padding:20px 20px;
		word-break:break-all;
	}
	.back{
		text-align: right;
	}
</style>
<div class="body">
	<img src="${ctx}/resources/themes/default/images/error.jpg"></img>
	<div class="result_msg">
		<h3>错误页</h3>
		<p class="wrap">${exception}</p>
		<p class="errorContent wrap">
			<%
				Exception e = (Exception) request.getAttribute("exception");
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				PrintWriter pw = new PrintWriter(bos);
				//e.printStackTrace(pw);
				if (e != null && e.getMessage() != null)
					pw.write(e.getMessage());
				pw.flush();
				out.write(bos.toString());
			%>
		</p>
		<!-- <p class="back">
			<input type="button" onclick="return window.history.back();" value="返回"/>
		</p> -->
	</div>
</div>

