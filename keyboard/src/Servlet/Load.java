package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import Database.Txt;

@WebServlet("/Load")
public class Load extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public Load() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		
		Txt t = new Txt();
		HashMap hs[] = t.hash;
		String send ="";
		for(int i = 0; i < hs.length; i++) {
			Set keyset = hs[i].keySet();
			send += "<select id=\"select"+i+"\" style='width:120px;'>";
			send+="<option value=\"δѡ��\">δѡ��</option>";
			//TreeMap���ڲ�������ģ���Ĭ�ϵ���������Ǹ���key���������򣬽��õ���hm��<key,value>,��<value,key>��ʽд�룬�Զ��������
			for(Object o:keyset){
				String str = o.toString();
				if(!str.equals("null-")) {
					String str1 = str.replace("[", "").replace("'", "").replace("\"", "").replace("]", "").replace("-", "");
					send+="<option value=\""+str+"\">"+str1+"</option>";
				}
			}
			send+="</select>  ";
		}
		send += "<select id=\"kind\" style='width:120px;'>";
		send+="<option value=\"����������������\">����������������</option>";
		send+="<option value=\"�۸�������\">�۸�������</option>";
		send+="<option value=\"�۸���������\">�۸���������</option>";
		send+="</select>  ";
		PrintWriter out = response.getWriter();
		out.print(send);
		out.flush();
		out.close();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}
}
