package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.Database;
import net.sf.json.JSONObject;

@WebServlet("/Show")
public class Show extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public Show() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		
		String str[] = request.getParameter("t").split("&");
		for(int i = 0; i < str.length; i ++) {
			String temp = str[i];
			int index = temp.lastIndexOf("-");
			if(index != -1) {
				str[i] = temp.substring(0, index);
			}
		}
		String kind = request.getParameter("kind");
		Database db = new Database();
		String jsons[] = db.select(str, kind);
		
		String send = "<table border='1' width='740px'>"
				+ "<thead>"
				+ "<tr align='center'>"
				+ "<th>序号</th>"
				+ "<th>商品信息</th>"
				+ "<th>价格</th>"
				+ "<th>评价信息</th>"
				+ "<th>原网页地址</th>"
				+ "</tr>"
				+ "</thead>"
				+ "<tbody>";
		int count = 1;
		//TreeMap的内部是有序的，它默认的排序策略是根据key来进行排序，将得到的hm的<key,value>,以<value,key>形式写入，自动排序输出
		for(String j:jsons){
			JSONObject jsonObj = JSONObject.fromObject(j);
			String url = "";
			double price = 0;
			String list = "";
			String eval = "";
			try {
				url = jsonObj.getString("url");
			}catch(Exception e) {}
			try {
				price = jsonObj.getDouble("price");
			}catch(Exception e) {}
			try {
				list = jsonObj.getString("list");
			}catch(Exception e) {}
			try {
				eval = jsonObj.getString("eval");
			}catch(Exception e) {}
			if(url.equals("")||price==0||list.equals("")||eval.equals("")) {
			}else {
				send+="<tr>"
						+ "<td>" + count + "</td>"
						+ "<td align='left'>" + list.replace("{", "").replace("}", "").replace("\"", "")
						.replace("'", "").replace("]", "").replace("[", "").replace(",", "<br/>") + "</td>"
						+ "<td>" + price + "</td>"
						+ "<td align='left'>" + eval.replace("{", "").replace("}", "").replace("\"", "")
						.replace("'", "").replace(",", "<br/>") + "</td>"
						+ "<td><button onclick=\"window.open('" + url + "')\">点击</button></td>"
						+ "</tr>";
				count++;
			}
		}
		send+="</tbody>"
				+ "</table>";
		PrintWriter out = response.getWriter();
		out.print(send);
		jsons.clone();
		out.close();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}
}
