<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="../css/frame_数据分析.css">
    
    <%@ page import="Database.Database" %>
    <%@ page import="java.util.*"%>
    
    <script src="../js/jquery-3.4.1.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">
    function con() {
        var n = document.getElementById("select").value;
        var per = document.getElementById("per").value;
        if(per=="") {
            alert("输入不能为空");
        }
        else if(per < 0||per > 100){
        	alert("输入错误，per取[0,100)中任意实数，推荐取20");
        }
        else if(per >= 0&& per <= 100){
        	$.ajax({
                url:"../Analysis" , // 请求路径
                type:"GET" , //请求方式
                //请求参数
                async:false,
                cache: false,
                data:{"n":n,"per":per},
                dataType:"html",//设置接受到的响应数据的格式
                //content:"indext.jsp",
                success:function (data) {
                	$("#cm").html();
               		$("#cm").html(data);
                },//响应成功后的回调函数
                error:function () {
                    alert("出错啦...");
                },//表示如果请求响应出现错误，会执行的回调函数
            });
            
        }
        else{
        	alert("错误输入，请输入数字");
        }
    }
    </script>
</head>
<body>
	<h3 id="ces">数据分析</h3>
    <div>
        将实验一中爬取的每个物品的商品介绍数据，取其中最具代表性的8个进行数据分析，分析的数据为：
    <br/>
        品牌，游戏性能，背光效果，数字键盘，适用场景，轴体类型，连接方式，颜色
    <br/><br/>
        输入两个数据：分析数据项数（n项集）；对应结果中最小支持度阈值per%
    <br/>  
		    项数(n):
            <select id=select style="width:120px;" name="n">  
	            <option value="1">1</option>  
	            <option value="2">2</option>  
	            <option value="3">3</option>  
	            <option value="4">4</option>  
	            <option value="5">5</option>  
	            <option value="6">6</option>  
	            <option value="7">7</option>  
	            <option value="8">8</option>  
            </select>  
		    阈值(per):
		    <input type="text" id="per" name="per">
		    <input id="conbt" type="submit" onclick="con();" value="确认"></input>
		    <br>
    </div>
    <div id='cm'></div>
    
</body>
</html>