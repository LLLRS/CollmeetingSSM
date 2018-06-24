<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>Cool-Meeting会议管理系统</title>
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
    <div class="page-header">
      <div class="header-banner">
        <img src="../../../resource/images/header.png" alt="CoolMeeting"/>
      </div>
      <div class="header-title">
        欢迎访问Cool-Meeting会议管理系统
      </div>

      <div class="page-body">
        <jsp:include page="common/leftMenu.jsp"/>

        <div class="page-content">
          <div class="content-nav">
            &nbsp&nbsp  登录
          </div>

          <form action="/coolmeetting/login" method="post">
            <fieldset>
              <legend>登录信息</legend>
              <table class="formtable" style="width:50%">
                <c:if test="${error!=null}">
                  <tr>
                    <td colspan="2">${error}</td>
                  </tr>
                </c:if>
                <tr>
                  <td>账号名:</td>
                  <td>
                    <input id="accountname" name="username" type="text"/>
                  </td>
                </tr>
                <tr>
                  <td>密&nbsp&nbsp&nbsp码:</td>
                  <td>
                    <input id="new" type="password" name="password"/>
                  </td>
                </tr>
                <tr>
                  <td colspan="2" class="command">
                    <input type="submit" value="登录" class="clickbutton"/>
                    <input type="button" value="返回" class="clickbutton" onclick="window.history.back();"/>
                  </td>
                </tr>
              </table>
            </fieldset>
          </form>
        </div>
      </div>

      <jsp:include page="common/bottom.jsp"/>

    </div>


  </body>
</html>
