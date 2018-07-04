<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="page-sidebar">
    <div class="sidebar-menugroup">
        <div class="sidebar-grouptitle">个人中心</div>
        <ul class="sidebar-menu">
            <li class="sidebar-menuitem"><a href="/coolmeetting/notifications">最新通知</a></li>
            <li class="sidebar-menuitem active"><a href="/coolmeetting/mybooking">我的预定</a></li>
            <li class="sidebar-menuitem"><a href="/coolmeetting/mymeeting">我的会议</a></li>
            <li class="sidebar-menuitem"><a href="/coolmeetting/changepassword?dostatus=0">修改密码</a></li>
        </ul>
    </div>
    <c:if test="${loginUser.role==1}">
        <div class="sidebar-menugroup">
            <div class="sidebar-grouptitle">人员管理</div>
            <ul class="sidebar-menu">
                <li class="sidebar-menuitem"><a href="/coolmeetting/departments">部门管理</a></li>
                <li class="sidebar-menuitem"><a href="/coolmeetting/reg">员工注册</a></li>
                <li class="sidebar-menuitem"><a href="/coolmeetting/approveaccount">注册审批</a></li>
                <li class="sidebar-menuitem"><a href="/coolmeetting/serachemp?">搜索员工</a></li>
            </ul>
        </div>
    </c:if>
    <div class="sidebar-menugroup">
        <div class="sidebar-grouptitle">会议预定</div>
        <ul class="sidebar-menu">
            <c:if test="${loginUser.role==1}">
                <li class="sidebar-menuitem"><a href="/coolmeetting/addmr?addstatus=0">添加会议室</a></li>
            </c:if>
            <li class="sidebar-menuitem"><a href="/coolmeetting/getallmr">查看会议室</a></li>
            <li class="sidebar-menuitem"><a href="/coolmeetting/bookmeeting">预定会议</a></li>
            <c:if test="${loginUser.role==1}">
                <li class="sidebar-menuitem"><a href="/coolmeetting/searchmeeting">搜索会议</a></li>
            </c:if>
        </ul>
    </div>
</div>
