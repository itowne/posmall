    <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
        <%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <div class="pageContent">
        <form id="timerModifyForm" method="post" action="${ctx}/sys/timer/modify.do"
        class="pageForm required-validate"
        onsubmit="return validateCallback(this, dialogAjaxDone);">
        <input name="itask" type="hidden" value="${taskConf.itask}">

        <div class="pageFormContent" layoutH="68">
        <p style="width: 400px;">
        <label>任务名称：</label>
        <label name="taskName" style="width: 250px;">${taskConf.taskName}</label>
        </p>

        <p style="width: 400px;">
        <label>执行时间：</label>
        <label name="lastDate" style="width: 250px;"><fmt:formatDate pattern='yyyy-MM-dd' value='${taskConf.lastDate}'/></label>
        </p>

        <p style="width: 400px;">
        <label>更新时间：</label>
        <label name="updTime"  style="width: 250px;"><fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${taskConf.updTime}'/></label>
        </p>

        <p style="width: 400px;">
        <label>执行结果：</label>
        <label name="endStat"  style="width: 250px;">${taskConf.endStat}</label>
        </p>

        <p style="width: 500px;">
        <label>错误信息：</label>
        <label name="errMsg"  style="width: 360px;">${taskConf.errMsg}</label>
        </p>

        <p style="width: 400px;">
        <label>次数：</label>
        <label name="count"  style="width: 250px;">${taskConf.count}</label>
        </p>

        <p style="width: 400px;">
        <label>状态：</label>
        <select class="combox" name="enabledFlag" style="width:160px;">
        <c:if test="${taskConf.enabledFlag == 'TRUE'}">
        <option selected="selected" value="TRUE">
        &nbsp;&nbsp;&nbsp;TRUE&nbsp;&nbsp;&nbsp;</option>
        <option value="FALSE">
        &nbsp;&nbsp;&nbsp;FALSE&nbsp;&nbsp;&nbsp;</option>
        </c:if>
        <c:if test="${taskConf.enabledFlag == 'FALSE'}">
        <option value="TRUE">
        &nbsp;&nbsp;&nbsp;TRUE&nbsp;&nbsp;&nbsp;</option>
        <option selected="selected" value="FALSE">
        &nbsp;&nbsp;&nbsp;FALSE&nbsp;&nbsp;&nbsp;</option>
        </c:if>
        </select>
        </p>
        </div>
        <div class="formBar">
        <ul>
        <li>
        <div class="buttonActive">
        <div class="buttonContent">
        <button type="submit">&nbsp;&nbsp;提&nbsp;&nbsp;&nbsp;&nbsp;交&nbsp;&nbsp;</button>
        </div>
        </div>
        </li>
        <li>
        <div class="button">
        <div class="buttonContent">
        <button type="button" class="close">
        &nbsp;&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;&nbsp;</button>
        </div>
        </div>
        </li>
        </ul>
        </div>
        </form>
        </div>
