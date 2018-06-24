<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CoolMeeting会议管理系统</title>
    <link rel="stylesheet" href="../../../resource/styles/common.css"/>
    <style>
        .page-content{
            width:830px;
            position: absolute;
            left: 180px;
            top:80px;
        }

    </style>

</head>
<body>
<jsp:include page="common/top.jsp"/>
<div class="page-body">
    <jsp:include page="common/leftMenu.jsp"/>
    <div class="page-content">
        <div class="content-nav">
            会议预定 > 查看会议室
        </div>
        <table class="listtable">
            <caption>所有会议室:</caption>
            <tr class="listheader">
                <th>门牌编号</th>
                <th>会议室名称</th>
                <th>容纳人数</th>
                <th>当前状态</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${list}" var="mr">
                <tr>
                    <td>${mr.roomnum}</td>
                    <td>${mr.roomname}</td>
                    <td>${mr.capacity}</td>
                    <td>
                        <c:choose>
                            <c:when test="${mr.status==0}">启用</c:when>
                            <c:when test="${mr.status==1}">停用</c:when>
                        </c:choose>
                    </td>
                    <td>
                        <a class="clickbutton" href="/coolmeetting/roomdetails?roomid=${mr.roomid}">查看详情</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<jsp:include page="common/bottom.jsp"/>
</body>
</html>
