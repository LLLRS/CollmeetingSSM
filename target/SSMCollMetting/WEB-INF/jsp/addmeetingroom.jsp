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
            会议预定 > 添加会议室
        </div>
        <form action="/coolmeetting/addmr" method="post">
            <fieldset>
                <legend>会议室信息</legend>
                <table class="formtable">
                    <tr>
                        <td>门牌号:</td>
                        <td>
                            <input id="roomnumber" name="roomnum" type="text" placeholder="例如：201" maxlength="10"/>
                        </td>
                    </tr>
                    <tr>
                        <td>会议室名称:</td>
                        <td>
                            <input id="capacity" name="roomname" type="text" placeholder="例如：第一会议室" maxlength="20"/>
                        </td>
                    </tr>
                    <tr>
                        <td>最多容纳人数：</td>
                        <td>
                            <input id="roomcapacity" name="capacity" type="text" placeholder="填写一个正整数"/>
                        </td>
                    </tr>
                    <tr>
                        <td>当前状态：</td>
                        <td>
                            <input type="radio" id="statuson" name="status" checked="checked" value="0"/>启用
                            <input type="radio" id="statusoff" name="status" value="1"/>停用
                        </td>
                    </tr>
                    <tr>
                        <td>备注：</td>
                        <td>
                            <textarea id="description" name="description" maxlength="200" rows="5" cols="60"
                                      placeholder="200字以内的文字描述"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="command">
                            <input type="hidden" name="addstatus" value="1">
                            <input type="submit" value="添加" class="clickbutton"/>
                            <input type="reset" value="重置" class="clickbutton"/>
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
