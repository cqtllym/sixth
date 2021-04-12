package Database;

/** 
* @author 罗艺铭 
*/
import org.bson.Document;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class keyboard {
	static MongoClient mongoClient = null;
	static MongoDatabase mgdb = null;
	static MongoCollection<Document> doc_k;
	public keyboard() {
		try {
			//链接到MongoDB服务器
			mongoClient  = new MongoClient("127.0.0.1",27017);
			//链接到MongoDB数据库
			mgdb = mongoClient.getDatabase("python");
			
			System.out.println("---数据库连接成功---");
			System.out.println("当前的数据库是 : " + mgdb.getName());
			
			doc_k =mgdb.getCollection("keyboard2");
			
		}catch(Exception e) {
			System.out.println(e.getClass().getName()+":"+e.getMessage());
		}
	}
	
	public void q() {
		FindIterable<Document> iter1=doc_k.find(new Document("price",new Document("$lt",1000)));
		iter1.forEach(new Block<Document>() {
			@Override
			public void apply(Document _doc) {
				System.out.println(_doc);
			}
		});
	}
}
	