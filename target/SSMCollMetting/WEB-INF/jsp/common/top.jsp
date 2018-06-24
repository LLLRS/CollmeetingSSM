<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="page-header">
    <div class="header-banner">
        <img src="../../../resource/images/header.png" alt="CoolMeeting"/>
    </div>
    <div class="header-title">
        欢迎访问Cool-Meeting会议管理系统
    </div>
    <div class="header-quicklink">
        欢迎您<strong><c:if test="${loginUser.employeename!=null}">,${loginUser.employeename}</c:if></strong>
        <a href="/coolmeetting/logout">[退出]</a>
        <%--  <a href="/changepassword?dostatus=0">[修改密码]</a> --%>
    </div>
</div>
