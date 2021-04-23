<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="../css/frame_数据展示.css">
    <script src="../js/jquery-3.4.1.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">
    function select() {
    	var n0 = document.getElementById("select0").value;
    	var n1 = document.getElementById("select1").value;
        var n2 = document.getElementById("select2").value;
        var n3 = document.getElementById("select3").value;
        var n4 = document.getElementById("select4").value;
        var n5 = document.getElementById("select5").value;
        var n6 = document.getElementById("select6").value;
        var n7 = document.getElementById("select7").value;
        var kind = document.getElementById("kind").value;
        var t=n0+"&"+n1+"&"+n2+"&"+n3+"&"+n4+"&"+n5+"&"+n6+"&"+n7;
        $.ajax({
               url:"../Show" , // 请求路径
               type:"GET" , //请求方式
               //请求参数
               async:false,
               cache: false,
               data:{"t":t,"kind":kind},
               dataType:"html",//设置接受到的响应数据的格式
               success:function (data) {
              		$("#sel").html();
              		$("#sel").html(data);
               },//响应成功后的回调函数
               error:function () {
                   alert("出错啦...");
               },//表示如果请求响应出现错误，会执行的回调函数
           });
    }
    function find(){
    	$.ajax({
            url:"../Load" , // 请求路径
            type:"GET" , //请求方式
            //请求参数
            async:false,
            cache: false,
            dataType:"html",//设置接受到的响应数据的格式
            success:function (data) {
           		$("#find").html(data);
            },//响应成功后的回调函数
            error:function () {
                alert("出错啦...");
            },//表示如果请求响应出现错误，会执行的回调函数
        });
    }
    </script>
</head>
<body onload="find()">
	<div>
        实验一中，我爬取了每个物品的数据如下：
        <ul>
	        <li>url</li>
	        <li>价格</li>
	        <li>信息列表（包含所有信息），其中选择八个数据进行数据分析，分别为：<br/>品牌，游戏性能，背光效果，数字键盘，适用场景，轴体类型，连接方式，颜色</li>
	        <li>评价列表</li>
        </ul>
     </div>
     <button onclick="select();">测试</button>
     <div id="find"></div>
     <div id="sel">
	
     </div>
</body>
</html>