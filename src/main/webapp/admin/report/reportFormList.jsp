<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="pageContent">
	<form method="post" action="${ctx}/admin/reportForm/reportFormDownLoad.do">
		<div class="pageFormContent" layoutH="56">
		       <p style="width:800px;">
		         <label>报表名称：</label>
		         <select name="reportType" id="reportType">
			         <option value="0" selected="selected">YPOS订单执行报表</option>
			         <option value="1" >YPOS点单执行报表</option>
			         <option value="2" >YPOS销货执行报表</option>
			      </select>
		       </p>
			   <p style="width:800px;"> 
			      <label>日期:</label>
			      <input id="startDate" class="date" readonly="readonly" name="startDate" value=""/> - <input id="endDate" class="date" readonly="readonly" name="endDate" value=""/>
			   </p>
			   <p style="width:600px;margin-left:383px;"> 
			      <input type="button"  style="width:60px;height:30px" name="download" value="下载" onclick="this.form.submit();"/>
			   </p>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
					   <div class="buttonContent">
					     <button type="button" class="close">关闭</button>
					   </div>
					   
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
