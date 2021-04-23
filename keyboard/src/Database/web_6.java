package Database;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;


/** 
* @author 罗艺铭 
*/
import org.bson.Document;


import com.mongodb.*;

import com.mongodb.client.*;


public class web_6{
	static MongoClient mongoClient = null;
	static MongoDatabase mgdb = null;
	static MongoCollection<Document> doc_k;
	static MongoCollection<Document> doc_hash[];
	
	static int count = 0;//所有文件总评价之和
	static String jsons[];//select返回数据
	public web_6() {
		try {
			//链接到MongoDB服务器
			mongoClient  = new MongoClient("127.0.0.1",27017);
			//链接到MongoDB数据库
			mgdb = mongoClient.getDatabase("python");
			
			System.out.println("---数据库连接成功---");
			System.out.println("当前的数据库是 : " + mgdb.getName());
			
			doc_k = mgdb.getCollection("keyboard");//keyboard集合中存储所有数据的详细信息
			System.out.println("doc_k数据集合是 : " + doc_k.getNamespace());
			
			MongoIterable<String> databaseNames = mgdb.listCollectionNames();//获取所有集合名称
			String hash_n[] = {"hash1","hash2","hash3","hash4","hash5","hash6","hash7","hash8"};//需要判断是否存在的集合
	
			doc_hash = new MongoCollection[hash_n.length];
			boolean b_hash[] = new boolean[hash_n.length];
			
			for(int i = 0; i < hash_n.length; i++) {
				b_hash[i] = false;
			}
			for (String name : databaseNames) {
				for(int i = 0; i < hash_n.length; i++) {
					if(name.equals(hash_n[i])) {//判断mongo的集合中是否存在hash_n[i]
						b_hash[i] = true;
						continue;
					}
				}
			}
			for(int i = 0; i < hash_n.length; i++) {
				if(!b_hash[i]) {//如果不存在hash_n[i]，创建
					mgdb.createCollection(hash_n[i]);
				}
				doc_hash[i] =  mgdb.getCollection(hash_n[i]);//打开doc_hash[i]
			}

		}catch(Exception e) {
			System.out.println(e.getClass().getName()+":"+e.getMessage());
		}
		
		q();//获取所有文件中的评价数
	}
	//--------------------------------------------------------------以下为准备工作
	
	//获取所有文件中的评价数
	private void q() {
		FindIterable<Document> iter1=doc_k.find(new Document());
		iter1.forEach(new Block<Document>() {
			@Override
			public void apply(Document _doc) {
				Document eval = (Document) _doc.get("eval");
				int all = 0;
				if(eval.get("全部评价")!=null) {
					all = (int) eval.get("全部评价");
				}
				count += all;
			}
		});
	}
	
	//通过给定数据名数组，返回对应数据的所有hash值
	private HashMap question(String type[]) {
		FindIterable<Document> iter1=doc_k.find(new Document()).sort(new BasicDBObject("eval.全部评价",-1)); //-1降序，1升序  .limit(10)取前10个
		final HashMap hm = new HashMap();
		iter1.forEach(new Block<Document>() {
			@Override
			public void apply(Document _doc) {
				Document list = (Document) _doc.get("list");
				Document eval = (Document) _doc.get("eval");
				String str[] = new String[type.length];
				String hmkey = "";
				
				for(int i = 0; i < type.length; i++) {
					str[i] = (String) list.get(type[i]);//获取对应的数据名称
					hmkey += str[i] + "-";
				}
						
				int all = 0;
				if(eval.get("全部评价")!=null) {
					all = (int) eval.get("全部评价");//获取该数据下的评价数量
				}
				
				if(hm.containsKey(hmkey)) {
					int a = (int) hm.get(hmkey) + all;//在对应的数据后添加数据量
					hm.put(hmkey,a);
				}else {
					hm.put(hmkey,all);
				}
			}
		});
		return hm;
	}
	
	//------------------------------------------------------------------------以下为客户端需要操作的内容，在run文件中操作(之后只需要使用的函数）
	//取出商品名，价格，总评价数，好评率，url
	public void q2() {
		FindIterable<Document> iter1=doc_k.find(new Document()).sort(new BasicDBObject("eval.全部评价",-1)); //-1降序，1升序

		iter1.forEach(new Block<Document>() {
			@Override
			public void apply(Document _doc) {
				Document list = (Document) _doc.get("list");
				Document eval = (Document) _doc.get("eval");
				
				System.out.print(list.get("商品名称")+"     ");
				System.out.print(_doc.get("price")+"\t");
				int all = 0, bad = 0, mid = 0;
				String percent = "0";
				if(eval.get("全部评价")!=null) {
					all = (int) eval.get("全部评价");
				}
				if(eval.get("差评")!= null) {
					bad = (int) eval.get("差评");
				}
				if(eval.get("中评")!=null) {
					mid = (int) eval.get("中评");
				}
				if(all != 0) {
					double per = (double)(all-bad-mid)/all;
					NumberFormat nf = NumberFormat.getPercentInstance(); 
					nf.setMinimumFractionDigits(2);//设置保留小数位 
					nf.setRoundingMode(RoundingMode.HALF_UP); //设置舍入模式 
					percent = nf.format(per); 
				}
				
				System.out.print(all+"\t");
				System.out.print(percent+"\t");
				System.out.print(_doc.get("url")+"\n");
			}
		});
		
	}
	
	
	//8选项卡查询，每一次选项卡进行选择时，查询一次，按全部评价降序排列
	public String[] select(String p[], String kind) {
		//p[8]，顺序包含：品牌，游戏性能，背光效果，数字键盘，适用场景，轴体类型，连接方式，颜色 
		String t[] = {"品牌","游戏性能","背光效果","数字键盘","适用场景","轴体类型","连接方式","颜色"};
		BasicDBObject cond1 = new BasicDBObject();
		BasicDBObject bdbo = new BasicDBObject();
		for(int i = 0; i < 8; i++) {
			if(!p[i].equals("未选择")) {
				cond1.put("list."+t[i], p[i]);   
			}
		}
		if(kind.equals("评价数量降序排列")) {
			bdbo =  new BasicDBObject("eval.全部评价",-1);
		}
		if(kind.equals("价格降序排列")) {
			bdbo =  new BasicDBObject("price",-1);
		}
		if(kind.equals("价格升序排列")) {
			bdbo =  new BasicDBObject("price",1);
		}
		FindIterable<Document> iter1=doc_k.find(cond1).sort(bdbo).projection(new BasicDBObject().append("_id", 0)); 
		iter1.forEach(new Block<Document>() {
			@Override
			public void apply(Document _doc) {
				if(jsons == null) { //若数组为空，定义数组的长度为1
					jsons = new String[1];
					jsons[0] = _doc.toJson();
				}else {
					//若数组不为空，把数组复制出一个新的，在原数组的基础上加1
					String[] copy = Arrays.copyOf(jsons, jsons.length+1);
					//把原先数组制空
					jsons = null;
					//把新数组给原先这个数组
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
	
	public TreeMap mongo_hash(int n) { //返回mongo中的n项集，以<value,key>的形式返回TreeMap
		TreeMap tm = new TreeMap<>(new Comparator<String>() {//降序排列
            public int compare(String o1, String o2) {
            	//升序，默认为升序
                //return o1.compareTo(o2);

                //降序
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
				
				//TreeMap的内部是有序的，它默认的排序策略是根据key来进行排序，将得到的hm的<key,value>,以<value,key>形式写入，自动排序输出
				for(Object o:keyset){
					String d = (String) hm.get(o);
					tm.put(d, o);
				}
			}
		});
		
		return tm;
	}
}

	