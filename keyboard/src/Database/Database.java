package Database;

import java.util.*;

/** 
* @author 罗艺铭
* @date 创建时间：2021年4月14日 下午8:07:17 
*/
public class Database {
	public static web_6 w;
	public Database(){
		 w = new web_6();
	}
	public TreeMap hash(int n, double per) {
		TreeMap tm = w.mongo_hash(n);//取出n项集
		//对n项集进行数据处理，清除掉低于per%的数据，打印出来
//		Set keyset = tm.keySet();
//		
//		//TreeMap的内部是有序的，它默认的排序策略是根据key来进行排序，将得到的hm的<key,value>,以<value,key>形式写入，自动排序输出
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
		//run.hash(n,per)查询n项集中，支持度大于per%的
		Database d  = new Database();
//		d.hash(4, 10);
//		
		//w.select(p[8]查询8选项卡，顺序包含：品牌，游戏性能，背光效果，数字键盘，适用场景，轴体类型，连接方式，颜色 ,
		//返回所有属性和取出商品名，价格，总评价数，好评率，url
		//未经过处理
		String t[] = {"未选择","发烧级","未选择","有数字键盘","游戏","黑轴","未选择","黑色"}; 
		String jsons[] = d.select(t, "价格降序排列");
		for(String j:jsons) {
			System.out.println(j);
		}
//		
	}
	

	

}
