<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>CoolMeeting会议管理系统</title>
    <link rel="stylesheet" href="../../../resource/styles/common.css"/>

    <style type="text/css">

        .page-content{
            width:830px;
            position: absolute;
            left: 180px;
            top:80px;
        }
    </style>

</head>
<body>
<div class="page-header">
    <div class="header-banner">
        <img src="../../../images/header.png" alt="CoolMeeting"/>
    </div>
    <div class="header-title">
        欢迎访问Cool-Meeting会议管理系统
    </div>
    <div class="header-quicklink">
        欢迎您，<strong><c:if test="${loginUser!=null}">,${loginUser.employeename}</c:if></strong>
    </div>
</div>
<div class="page-body">
    <jsp:include page="common/leftMenu.jsp"/>
    <div class="page-content">
        <div class="content-nav">
            修改密码
        </div>
        <form method="post" action="/changepassword?dostatus=1">
            <fieldset>
                <legend>修改密码信息</legend>
                <table class="formtable" style="width:50%">
                    <c:if test="${error!=null}">
                        <tr>
                            <td colspan="2">${error}</td>
                        </tr>
                    </c:if>
                    <tr>
                        <td>原密码:</td>
                        <td>
                            <input id="origin" name="origin" type="password" />
                        </td>
                    </tr>
                    <tr>
                        <td>新密码:</td>
                        <td>
                            <input id="new" name="new" type="password" />
                        </td>
                    </tr>
                    <tr>
                        <td>确认新密码：</td>
                        <td>
                            <input id="confirm" name="comfirm" type="password"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="command">
                            <input type="submit" value="确认修改" class="clickbutton"/>
                            <input type="button" value="返回" class="clickbutton" onclick="window.history.back();"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </form>
    </div>
</div>
<jsp:include page="common/bottom.jsp"/>
</body>
</html>
