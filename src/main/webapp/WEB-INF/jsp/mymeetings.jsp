<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CoolMeeting会议管理系统</title>
    <link rel="stylesheet" href="../../../resource/styles/common.css"/>
    <style type="text/css">
        .page-content{
            width:830px;
            position: absolute;
            left: 180px;
            top:10px;
    </style>
</head>
<body>
<jsp:include page="common/top.jsp"/>
<div class="page-body">
    <jsp:include page="common/leftMenu.jsp"/>
    <div class="page-content">
        <div class="content-nav">
            个人中心 > 我的会议
        </div>
        <table class="listtable">
            <caption>我将参加的会议：</caption>
            <tr class="listheader">
                <th>会议名称</th>
                <th>会议室名称</th>
                <th>会议开始时间</th>
                <th>会议结束时间</th>
                <th>会议预定时间</th>
                <th>预定者</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${mrs}" var="mr">
                <tr>
                    <td>${mr.meetingname}</td>
                    <td>${mr.roomname}</td>
                    <td><fmt:formatDate value="${mr.starttime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate> </td>
                    <td><fmt:formatDate value="${mr.endtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate> </td>
                    <td><fmt:formatDate value="${mr.reservationtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate> </td>
                    <td>${mr.empname}</td>
                    <td>
                        <a class="clickbutton" href="/coolmeetting/meetingdetails?meetingid=${mr.meetingid}">查看详情</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<jsp:include page="common/bottom.jsp"/>
</body>
</html>
