package Database;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;


/** 
* @author ������ 
*/
import org.bson.Document;


import com.mongodb.*;

import com.mongodb.client.*;


public class web_6{
	static MongoClient mongoClient = null;
	static MongoDatabase mgdb = null;
	static MongoCollection<Document> doc_k;
	static MongoCollection<Document> doc_hash[];
	
	static int count = 0;//�����ļ�������֮��
	static String jsons[];//select��������
	public web_6() {
		try {
			//���ӵ�MongoDB������
			mongoClient  = new MongoClient("127.0.0.1",27017);
			//���ӵ�MongoDB���ݿ�
			mgdb = mongoClient.getDatabase("python");
			
			System.out.println("---���ݿ����ӳɹ�---");
			System.out.println("��ǰ�����ݿ��� : " + mgdb.getName());
			
			doc_k = mgdb.getCollection("keyboard");//keyboard�����д洢�������ݵ���ϸ��Ϣ
			System.out.println("doc_k���ݼ����� : " + doc_k.getNamespace());
			
			MongoIterable<String> databaseNames = mgdb.listCollectionNames();//��ȡ���м�������
			String hash_n[] = {"hash1","hash2","hash3","hash4","hash5","hash6","hash7","hash8"};//��Ҫ�ж��Ƿ���ڵļ���
	
			doc_hash = new MongoCollection[hash_n.length];
			boolean b_hash[] = new boolean[hash_n.length];
			
			for(int i = 0; i < hash_n.length; i++) {
				b_hash[i] = false;
			}
			for (String name : databaseNames) {
				for(int i = 0; i < hash_n.length; i++) {
					if(name.equals(hash_n[i])) {//�ж�mongo�ļ������Ƿ����hash_n[i]
						b_hash[i] = true;
						continue;
					}
				}
			}
			for(int i = 0; i < hash_n.length; i++) {
				if(!b_hash[i]) {//���������hash_n[i]������
					mgdb.createCollection(hash_n[i]);
				}
				doc_hash[i] =  mgdb.getCollection(hash_n[i]);//��doc_hash[i]
			}

		}catch(Exception e) {
			System.out.println(e.getClass().getName()+":"+e.getMessage());
		}
		
		q();//��ȡ�����ļ��е�������
	}
	//--------------------------------------------------------------����Ϊ׼������
	
	//��ȡ�����ļ��е�������
	private void q() {
		FindIterable<Document> iter1=doc_k.find(new Document());
		iter1.forEach(new Block<Document>() {
			@Override
			public void apply(Document _doc) {
				Document eval = (Document) _doc.get("eval");
				int all = 0;
				if(eval.get("ȫ������")!=null) {
					all = (int) eval.get("ȫ������");
				}
				count += all;
			}
		});
	}
	
	//ͨ���������������飬���ض�Ӧ���ݵ�����hashֵ
	private HashMap question(String type[]) {
		FindIterable<Document> iter1=doc_k.find(new Document()).sort(new BasicDBObject("eval.ȫ������",-1)); //-1����1����  .limit(10)ȡǰ10��
		final HashMap hm = new HashMap();
		iter1.forEach(new Block<Document>() {
			@Override
			public void apply(Document _doc) {
				Document list = (Document) _doc.get("list");
				Document eval = (Document) _doc.get("eval");
				String str[] = new String[type.length];
				String hmkey = "";
				
				for(int i = 0; i < type.length; i++) {
					str[i] = (String) list.get(type[i]);//��ȡ��Ӧ����������
					hmkey += str[i] + "-";
				}
						
				int all = 0;
				if(eval.get("ȫ������")!=null) {
					all = (int) eval.get("ȫ������");//��ȡ�������µ���������
				}
				
				if(hm.containsKey(hmkey)) {
					int a = (int) hm.get(hmkey) + all;//�ڶ�Ӧ�����ݺ����������
					hm.put(hmkey,a);
				}else {
					hm.put(hmkey,all);
				}
			}
		});
		return hm;
	}
	
	//------------------------------------------------------------------------����Ϊ�ͻ�����Ҫ���������ݣ���run�ļ��в���(֮��ֻ��Ҫʹ�õĺ�����
	//ȡ����Ʒ�����۸����������������ʣ�url
	public void q2() {
		FindIterable<Document> iter1=doc_k.find(new Document()).sort(new BasicDBObject("eval.ȫ������",-1)); //-1����1����

		iter1.forEach(new Block<Document>() {
			@Override
			public void apply(Document _doc) {
				Document list = (Document) _doc.get("list");
				Document eval = (Document) _doc.get("eval");
				
				System.out.print(list.get("��Ʒ����")+"     ");
				System.out.print(_doc.get("price")+"\t");
				int all = 0, bad = 0, mid = 0;
				String percent = "0";
				if(eval.get("ȫ������")!=null) {
					all = (int) eval.get("ȫ������");
				}
				if(eval.get("����")!= null) {
					bad = (int) eval.get("����");
				}
				if(eval.get("����")!=null) {
					mid = (int) eval.get("����");
				}
				if(all != 0) {
					double per = (double)(all-bad-mid)/all;
					NumberFormat nf = NumberFormat.getPercentInstance(); 
					nf.setMinimumFractionDigits(2);//���ñ���С��λ 
					nf.setRoundingMode(RoundingMode.HALF_UP); //��������ģʽ 
					percent = nf.format(per); 
				}
				
				System.out.print(all+"\t");
				System.out.print(percent+"\t");
				System.out.print(_doc.get("url")+"\n");
			}
		});
		
	}
	
	
	//8ѡ���ѯ��ÿһ��ѡ�����ѡ��ʱ����ѯһ�Σ���ȫ�����۽�������
	public String[] select(String p[], String kind) {
		//p[8]��˳�������Ʒ�ƣ���Ϸ���ܣ�����Ч�������ּ��̣����ó������������ͣ����ӷ�ʽ����ɫ 
		String t[] = {"Ʒ��","��Ϸ����","����Ч��","���ּ���","���ó���","��������","���ӷ�ʽ","��ɫ"};
		BasicDBObject cond1 = new BasicDBObject();
		BasicDBObject bdbo = new BasicDBObject();
		for(int i = 0; i < 8; i++) {
			if(!p[i].equals("δѡ��")) {
				cond1.put("list."+t[i], p[i]);   
			}
		}
		if(kind.equals("����������������")) {
			bdbo =  new BasicDBObject("eval.ȫ������",-1);
		}
		if(kind.equals("�۸�������")) {
			bdbo =  new BasicDBObject("price",-1);
		}
		if(kind.equals("�۸���������")) {
			bdbo =  new BasicDBObject("price",1);
		}
		FindIterable<Document> iter1=doc_k.find(cond1).sort(bdbo).projection(new BasicDBObject().append("_id", 0)); 
		iter1.forEach(new Block<Document>() {
			@Override
			public void apply(Document _doc) {
				if(jsons == null) { //������Ϊ�գ���������ĳ���Ϊ1
					jsons = new String[1];
					jsons[0] = _doc.toJson();
				}else {
					//�����鲻Ϊ�գ������鸴�Ƴ�һ���µģ���ԭ����Ļ����ϼ�1
					String[] copy = Arrays.copyOf(jsons, jsons.length+1);
					//��ԭ�������ƿ�
					jsons = null;
					//���������ԭ���������
					jsons = copy;
					jsons[jsons.length-1] = _doc.toJson();
				}
			}
		});
		if(jsons == null) {
			String fail[] = {"null"};
			return fail;
		}else {
			String success[] = jsons;
			jsons = null;
			return success;
		}
	}
	
	public TreeMap mongo_hash(int n) { //����mongo�е�n�����<value,key>����ʽ����TreeMap
		TreeMap tm = new TreeMap<>(new Comparator<String>() {//��������
            public int compare(String o1, String o2) {
            	//����Ĭ��Ϊ����
                //return o1.compareTo(o2);

                //����
            	return o2.compareTo(o1);
            }
		});
		
		FindIterable<Document> iter1=doc_hash[n-1].find(new Document()).projection(new BasicDBObject().append("_id", 0)); 
		iter1.forEach(new Block<Document>() {
			@Override
			public void apply(Document _doc) {
				Document list = (Document) _doc;
				String s = list.toJson();
				String s1 = s.replace("{", "");
				String s2 = s1.replace("}", "");
				String str[] = s2.split(",");
				HashMap hm = new HashMap();
				for(int i = 0; i < str.length; i++) {
					hm.put(str[i].split(":")[0],str[i].split(":")[1]);
				}
				Set keyset = hm.keySet();
				
				//TreeMap���ڲ�������ģ���Ĭ�ϵ���������Ǹ���key���������򣬽��õ���hm��<key,value>,��<value,key>��ʽд�룬�Զ��������
				for(Object o:keyset){
					String d = (String) hm.get(o);
					tm.put(d, o);
				}
			}
		});
		
		return tm;
	}
}

	