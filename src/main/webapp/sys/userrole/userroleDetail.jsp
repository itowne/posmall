<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="pageContent">
	<form id="userroleDetailForm" method="post" action="" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>角色名称：</label>
				<input name="name" type="text" size="30" value="${role.name}" readonly="readonly"/>
			</p>
			<p>
				<label>备注：</label>
				<input name="memo" type="text" size="30" value="${role.memo}" readonly="readonly"/>
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
$(":checkbox").attr("disabled","disabled");
<c:forEach var="authCode" items="${authCodeList}">
	var an = '${authCode}';
	$("#userroleDetailForm input[value='"+an+"']").attr("checked","checked");
</c:forEach>
</script>
