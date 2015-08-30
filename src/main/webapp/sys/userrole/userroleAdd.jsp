<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="pageContent">
	<form method="post" action="${ctx}/sys/userrole/userroleToAdd.do" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>角色名称：</label>
				<input name="name" type="text" size="30"/>
			</p>
			<p>
				<label>备注：</label>
				<input name="memo" type="text" size="30"/>
			</p>
			<p>
				<label>菜单与权限设置：</label>
			</p>
			<div class="divider"></div>
			<table class="list" width="98%">
				<tr>
					<td width="23%"><input type="checkbox" id="checkAll" />全选</td>
					<td width="23%"></td>
					<td></td>
				</tr>
				<tr align="center">
					<td>【一级菜单】</td>
					<td>【二级菜单】</td>
					<td>【权限功能】</td>
				</tr>
				<c:forEach items="${mnColl}" var="mn" >
					<tr>
						<td colspan="3">
							<table style="border: 1px;width: 100%">
								<tr>
								    <c:choose>
								       <c:when test="${mn.auth.url=='#'}">
											<td width="23%">
												<input type="checkbox" name="auth" id="${mn.auth.code}" value="${mn.auth.code}" />
												${mn.auth.name}
											</td>
											<td colspan="2">
												<table style="border: 1px;width: 100%" id="${mn.auth.code}_TABLE">
													<c:forEach var="subM" items="${mn.subMenus}"  varStatus="st">
														<tr>
															<td width="30%" id="${mn.auth.code}_MENU">
																<input type="checkbox" name="auth" id="${subM.value.auth.code}" value="${subM.value.auth.code}" />${subM.value.auth.name}
															</td>
															<td id="${subM.value.auth.code}_AUTH_ALL">
																<c:forEach var="subAuth" items="${subM.value.subMenus}" >
																	<input type="checkbox" name="auth" value="${subAuth.value.auth.code}" />${subAuth.value.auth.name}
																</c:forEach>
															</td>
														</tr>
													</c:forEach>
												</table>
											</td>
										</c:when>
										<c:otherwise>
											<td width="23%">
											</td>
											<td colspan="2">
												<table style="border: 1px;width: 100%" id="${mn.auth.code}_TABLE">
													
														<tr>
															<td width="30%" id="${mn.auth.code}_MENU">
															<input type="checkbox" name="auth" id="${mn.auth.code}" value="${mn.auth.code}" />
																${mn.auth.name}
															</td>
															<td id="${mn.auth.code}_AUTH_ALL">
																<c:forEach var="subM" items="${mn.subMenus}"  varStatus="st">
																	<input type="checkbox" name="auth" value="${subM.value.auth.code}" />${subM.value.auth.name}
																</c:forEach>
															</td>
														</tr>
													
												</table>
											</td>
										</c:otherwise>
									
									</c:choose>
								</tr>
							</table>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">&nbsp;&nbsp;提&nbsp;&nbsp;&nbsp;&nbsp;交&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	$('#checkAll').click(function(){
		if($("#checkAll").attr("checked")=="checked"){
			$(":checkbox").attr("checked","checked");
			$(":checkbox").removeAttr("disabled");
		}else{
			$(":checkbox").removeAttr("checked");
			<c:forEach var="mn" items="${mnColl}">
				<c:forEach var="subM" items="${mn.subMenus}" >
						$("${subM.value.auth.code}_AUTH_ALL input[type='checkbox']").attr("disabled","disabled");
				</c:forEach>
			</c:forEach>
		}
	});
	<c:forEach var="mn" items="${mnColl}">
		<c:forEach var="subM" items="${mn.subMenus}" >
			//menu --- auth
			if($("#${subM.value.auth.code}").attr("checked")==undefined){
				$("#${subM.value.auth.code}_AUTH_ALL input[type='checkbox']").attr("disabled","disabled");
				$("#${subM.value.auth.code}_AUTH_ALL input[type='checkbox']").removeAttr("checked");
			}else{
				$("#${subM.value.auth.code}_AUTH_ALL input[type='checkbox']").removeAttr("disabled");
			}
			$("#${subM.value.auth.code}").click(function (){
				if($("#${subM.value.auth.code}").attr("checked")==undefined){
					$("#${subM.value.auth.code}_AUTH_ALL input[type='checkbox']").attr("disabled","disabled");
					$("#${subM.value.auth.code}_AUTH_ALL input[type='checkbox']").removeAttr("checked");
				}else{
					$("#${subM.value.auth.code}_AUTH_ALL input[type='checkbox']").removeAttr("disabled");
				}
			});
		
		</c:forEach>
		//menu --- all
		$("#${mn.auth.code}").click(function (){
			if($("#${mn.auth.code}").attr("checked")!="checked"){
				$("#${mn.auth.code}_TABLE input[type='checkbox']").removeAttr("checked");
				<c:forEach var="subM" items="${mn.subMenus}" >
					$("#${subM.value.auth.code}_AUTH_ALL input[type='checkbox']").attr("disabled","disabled");
					$("#${subM.value.auth.code}_AUTH_ALL input[type='checkbox']").removeAttr("checked");
				</c:forEach>
			}
		});
		//menu
		$("#${mn.auth.code}_TABLE input[name='auth']").click(function (){
			if($("#${mn.auth.code}_TABLE input[name='auth']:checked").length>0){
				$('#${mn.auth.code}').attr("checked","checked");
			}else{
				$('#${mn.auth.code}').removeAttr("checked");
			}
		});
	</c:forEach>

</script>