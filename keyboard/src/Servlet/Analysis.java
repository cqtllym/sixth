package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import Database.Database;

@WebServlet("/Analysis")
public class Analysis extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public Analysis() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		
		int n =Integer.parseInt(request.getParameter("n"));
		double per = Double.parseDouble(request.getParameter("per"));
		Database db = new Database();
		TreeMap tm = db.hash(n, per);
		Set keyset = tm.keySet();
		String send = "<table border='1' width='600px'>"
				+ "<thead>"
				+ "<tr align='center'>"
				+ "<th>���</th>"
				+ "<th>���</th>"
				+ "<th>֧�ֶ�(%)</th>"
				+ "</tr>"
				+ "</thead>"
				+ "<tbody>";
		int count = 1;
		//TreeMap���ڲ�������ģ���Ĭ�ϵ���������Ǹ���key���������򣬽��õ���hm��<key,value>,��<value,key>��ʽд�룬�Զ��������
		for(Object o:keyset){
			if(Double.parseDouble(o.toString()) > per) {
				String get_name = (String) tm.get(o);
				String show = get_name.replace("[", "").replace("]", "").replace("'", "").replace("\"", "");
				int index = show.lastIndexOf("-");
				if(index != -1) {
					show = show.substring(0, index);
				}
				send+="<tr align='center'>"
						+ "<td>"+count+"</td>"
						+ "<td>"+show+"</td>"
						+ "<td>"+o+"</td>"
								+ "</tr>";
				count++;
			}
			
		}
		send+="</tbody>"
				+ "</table>";
		PrintWriter out = response.getWriter();
		out.print(send);
		out.close();
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}
}
