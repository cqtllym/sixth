package Database;

import java.util.*;

/** 
* @author ������
* @date ����ʱ�䣺2021��4��14�� ����8:07:17 
*/
public class Database {
	public static web_6 w;
	public Database(){
		 w = new web_6();
	}
	public TreeMap hash(int n, double per) {
		TreeMap tm = w.mongo_hash(n);//ȡ��n�
		//��n��������ݴ������������per%�����ݣ���ӡ����
//		Set keyset = tm.keySet();
//		
//		//TreeMap���ڲ�������ģ���Ĭ�ϵ���������Ǹ���key���������򣬽��õ���hm��<key,value>,��<value,key>��ʽд�룬�Զ��������
//		for(Object o:keyset){
//			if(Double.parseDouble(o.toString()) > per) {
//				String name = (String) tm.get(o);
//				System.out.println(o+"\t"+name);
//			}
//		}
		return tm;
	}
	
	public String[] select(String t[], String kind) {
		String jsons[] = w.select(t, kind);
		System.out.println(jsons.length);
		return jsons;
	}
	
	public static void main(String args[]) throws ClassNotFoundException {
		//run.hash(n,per)��ѯn��У�֧�ֶȴ���per%��
		Database d  = new Database();
//		d.hash(4, 10);
//		
		//w.select(p[8]��ѯ8ѡ���˳�������Ʒ�ƣ���Ϸ���ܣ�����Ч�������ּ��̣����ó������������ͣ����ӷ�ʽ����ɫ ,
		//�����������Ժ�ȡ����Ʒ�����۸����������������ʣ�url
		//δ��������
		String t[] = {"δѡ��","���ռ�","δѡ��","�����ּ���","��Ϸ","����","δѡ��","��ɫ"}; 
		String jsons[] = d.select(t, "�۸�������");
		for(String j:jsons) {
			System.out.println(j);
		}
//		
	}
	

	

}
