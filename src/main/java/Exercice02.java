import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;


public class Exercice02 {
	
	private static final String MONGO_SERVER_IP   = "localhost";
	private static final int    MONGO_SERVER_PORT = 32768;
	private static final String DATABASE_NAME     = "test";
	private static final String COLLECTION_NAME   = "CITY";
	
	private MongoClient client = null;
	private MongoDatabase db = null;
	private MongoCollection<Document> collection = null;
	
	
	public void initialSetup() {
		client = new MongoClient(MONGO_SERVER_IP, MONGO_SERVER_PORT);
		db = client.getDatabase(DATABASE_NAME);
		collection = db.getCollection(COLLECTION_NAME);
	}
	
	
	public void printOutput(FindIterable<Document> output) {
		for (Document doc : output) {
			System.out.println(doc.toJson());
		}
	}
	
	
	public void run() {
		initialSetup();
		
	}
	
	
	private void crud() {
		System.out.println("------------- CRUD -------------");
		
		System.out.println("[CREATE]");
		Document user = new Document("name", "John Doe").append("email", "johndoe@example.com");
		collection.insertOne(user);
		FindIterable<Document> mongoIter = collection.find(eq("name", "John Doe"));
		printOutput(mongoIter);
		
		System.out.println("\n[READ]");
		mongoIter = collection.find();
		printOutput(mongoIter);
		
		System.out.println("\n[UPDATE]");
		collection.updateOne(eq("name", "John Doe"), set("email", "wow@thatsanewemail.com"));
		printOutput(collection.find(eq("name", "John Doe")));
		
		System.out.println("\n[DELETE]");
		collection.deleteMany(eq("name", "John Doe"));
		printOutput(collection.find(eq("name", "John Doe"))); // should return nothing
	}
	
	
	public static void main(String... args) {
		Exercice02 exercice02 = new Exercice02();
		exercice02.run();
	}
	
}
