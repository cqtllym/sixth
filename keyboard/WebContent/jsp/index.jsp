<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="Database.Database" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>爬取数据展示与分析</title>
<link rel="stylesheet" type="text/css" href="../css/index.css">
    <script src="index.js"></script>
</head>
<body>

    <div class="top">
        <div class="top_content">
            <img src="../png/校名.png" width="217px" height="69x" id="nameOfSchool">
            <ul >
                <li><a style="color: white" href="#" onclick="frame1_visible()">首页</a> </li>
                <li><a style="color: white" href="#" onclick="frame2_visible()">数据展示</a></li>
                <li><a style="color: white" href="#" onclick="frame3_visible()">数据分析</a></li>
            </ul>
        </div>
    </div>
    <div class="body">
        <div class="body_left">
            <div class="body_menu">
                <ul>
                    <li><a href="#" onclick="frame1_visible()">实验简介</a> </li>
                    <li><a href="#" onclick="frame2_visible()">数据展示</a> </li>
                    <li><a href="#" onclick="frame3_visible()">数据分析</a> </li>
                </ul>
            </div>
        </div>
        <div class="body_right">
            <div class="body_resources">
                <iframe id="iframe_1" src="frame_实验简介.jsp"></iframe>
                <iframe id="iframe_2" src="frame_数据展示.jsp"></iframe>
                <iframe id="iframe_3" src="frame_数据分析.jsp"></iframe>
            </div>
        </div>
    </div>
    <div class="footing">
        <br/>
        made by:   罗艺铭 201800301290<br/>
        山东大学软件学院软件工程专业2018级3班
    </div>
</body>
</html>