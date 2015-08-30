<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<h2 class="contentTitle">初始化数据库</h2>
<div style="padding:0 10px;">

    <div class="tabs">
        <div class="tabsContent" layoutH="100">
            <div>
                <a class="button" href="javascript:;" onclick="dateBase('create');">
                    <span>导入sql文件 create.sql</span>
                </a>
                <br/><br/><br/>
                <a class="button" href="javascript:;" onclick="dateBase('init_t_auth');">
                    <span>导入sql文件 t_auth.sql</span>
                </a>
                <br/><br/><br/>
                <a class="button" href="javascript:;" onclick="dateBase('init_t_dict');">
                    <span>导入sql文件 t_dict.sql</span>
                </a>
                <br/><br/><br/>
                <a class="button" href="javascript:;" onclick="dateBase('initdata');">
                    <span>导入sql文件 initdata.sql</span>
                </a>
                <br/><br/><br/>
                <a class="button" href="javascript:;" onclick="dateBase('*');">
                    <span>导入全部sql文件</span>
                </a>
                <br/><br/><br/>
            </div>
        </div>

    </div>
</div>
<script type="text/javascript">
    function dateBase(sqlName) {
        $.ajax({
            type: "POST",
            url: "${ctx}/sys/date/initDate.do",
            data: "sqlName" + sqlName,
            success: function (data) {
                if (null == data || "" == data || data.length < 1) {
                    alert("未知原因，操作失败，请重试！");
                    return;
                }
                if ("0" == data.substr(0, 1)) {
                    alertMsg.info('操作成功！')
                    return;
                }
            }
        });
    }
</script>