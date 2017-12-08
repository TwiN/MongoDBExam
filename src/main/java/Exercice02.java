import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import onetomany.OneToManyCity;
import onetomany.OneToManyStudent;
import onetoone.OneToOneCity;
import onetoone.OneToOneStudent;
import org.bson.Document;


import java.util.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;


/**
 * Except for the CRUD, I'm not too clear on the instructions, but I will do what I think I should do.
 *
 * NOTE: The beans are just to better represent the situation, but really, I could have directly
 * written the values.
 *
 * Because MongoDB doesn't have foreign keys, you need to manually take care of it
 */
public class Exercice02 {
	
	private static final String MONGO_SERVER_IP   = "localhost";
	private static final int    MONGO_SERVER_PORT = 32768;
	private static final String DATABASE_NAME     = "test";
	
	private MongoClient client = null;
	private MongoDatabase db = null;
	private MongoCollection<Document> cityCollection = null;
	private MongoCollection<Document> studentCollection = null;
	private MongoCollection<Document> relationshipCollection = null;
	
	
	public void initialSetup() {
		client = new MongoClient(MONGO_SERVER_IP, MONGO_SERVER_PORT);
		db = client.getDatabase(DATABASE_NAME);
		cityCollection = db.getCollection("CITY");
		studentCollection = db.getCollection("STUDENTS");
		relationshipCollection = db.getCollection("RELATIONSHIPS");
		relationshipCollection.drop();
	}
	
	
	public void printOutput(FindIterable<Document> output) {
		for (Document doc : output) {
			System.out.println(doc.toJson());
		}
	}
	
	
	public void run() {
		initialSetup();
		crud();
		oneToOne();
		oneToMany();
		manyToMany();
	}
	
	
	private void crud() {
		System.out.println("------------- CRUD -------------");
		
		System.out.println("[CREATE]");
		Document user = new Document("name", "John Doe").append("email", "johndoe@example.com");
		cityCollection.insertOne(user);
		FindIterable<Document> mongoIter = cityCollection.find(eq("name", "John Doe"));
		printOutput(mongoIter);
		
		System.out.println("\n[READ]");
		mongoIter = cityCollection.find(eq("state", "NY"));
		printOutput(mongoIter);
		
		System.out.println("\n[UPDATE]");
		cityCollection.updateOne(eq("name", "John Doe"), set("email", "wow@thatsanewemail.com"));
		printOutput(cityCollection.find(eq("name", "John Doe")));
		
		System.out.println("\n[DELETE]");
		cityCollection.deleteMany(eq("name", "John Doe"));
		printOutput(cityCollection.find(eq("name", "John Doe"))); // should return nothing
	}
	
	
	private void oneToOne() {
		System.out.println("\n------------- ONE TO ONE -------------");
		
		OneToOneStudent student = new OneToOneStudent(150200, "1To1_TEST_STUDENT", null);
		OneToOneCity city = new OneToOneCity(112312, "1To1_TEST_CITY", student);
		student.setCity(city);
		
		// Insert the student
		Document studentDocument = new Document("_id", student.getStudentId())
			  .append("name", student.getName())
			  .append("cityId", city.getCityId());
		
		// Insert the city
		Document cityDocument = new Document("_id", city.getCityId())
			  .append("name", city.getName())
			  .append("studentId", student.getStudentId());
		
		relationshipCollection.insertMany(Arrays.asList(cityDocument, studentDocument));
		
		// print the result
		printOutput(this.relationshipCollection.find());
		relationshipCollection.drop();
	}
	
	
	private void oneToMany() {
		System.out.println("\n------------- ONE TO MANY -------------");
		
		// One city
		OneToManyCity city = new OneToManyCity(10000, "New York City", null);
		
		// Many students
		OneToManyStudent s1 = new OneToManyStudent(15000, "Student 1", city);
		OneToManyStudent s2 = new OneToManyStudent(15001, "Student 2", city);
		OneToManyStudent s3 = new OneToManyStudent(15002, "Student 3", city);
		List<OneToManyStudent> students = Arrays.asList(s1, s2, s3);
		city.setStudents(students);
		
		// insert the city
		relationshipCollection.insertOne(new Document("_id", city.getCityId()).append("name", city.getName()));
		
		// insert all students as well as another type of row which contains the relationship between city and student
		// Ideally, the "other type of row" would be in a different collection (but ignored due to time constraint)
		for (OneToManyStudent student : city.getStudents()) {
			// insert the student
			relationshipCollection.insertOne(new Document("_id", student.getStudentId()).append("name", student.getName()));
			
			// insert a row that links the student and the city
			Document cityStudentDocument = new Document()
				  .append("studentId", student.getStudentId())
				  .append("cityId", city.getCityId());
			relationshipCollection.insertOne(cityStudentDocument);
		}
		
		printOutput(this.relationshipCollection.find());
		
		System.out.println("\n[EXAMPLE: GET ALL STUDENTS IN New York City]");
		Object nycId = this.relationshipCollection.find(eq("name", "New York City")).first().get("_id"); // city.getCityId() [10000]
		printOutput(this.relationshipCollection.find(eq("cityId", nycId)));
		
		relationshipCollection.drop();
	}
	
	
	private void manyToOne() {
		System.out.println("\n------------- MANY TO ONE -------------");
		// Same thing as one to many
	}
	
	
	/**
	 * Proof of concept for many to many relationships.
	 * Due to the complexity of many-to-many relationships, I decided to not use beans for this
	 */
	private void manyToMany() {
		System.out.println("\n------------- MANY TO MANY -------------");
		
		Document city1Document = new Document("_id", 30000).append("name", "Montreal");
		Document city2Document = new Document("_id", 30001).append("name", "Laval");
		
		Document student1Document = new Document("_id", 40000).append("name", "Johnny");
		Document student2Document = new Document("_id", 40001).append("name", "Janette");
		
		Document student1City1 = new Document("studentId", 40000).append("cityId", 30000);
		Document student1City2 = new Document("studentId", 40000).append("cityId", 30001);
		Document student2City1 = new Document("studentId", 40001).append("cityId", 30000);
		Document student2City2 = new Document("studentId", 40001).append("cityId", 30001);
		
		relationshipCollection.insertMany(Arrays.asList(city1Document, city2Document, student1Document, student2Document,
			  student1City1, student1City2, student2City1, student2City2));
		
		printOutput(this.relationshipCollection.find());
		
		System.out.println("\n[EXAMPLE: GET ALL STUDENTS IN MONTREAL]");
		Object montrealId = this.relationshipCollection.find(eq("name", "Montreal")).first().get("_id"); // city.getCityId() [30000]
		printOutput(this.relationshipCollection.find(eq("cityId", montrealId)));
		
		relationshipCollection.drop();
	}
	
	
	public static void main(String... args) {
		Exercice02 exercice02 = new Exercice02();
		exercice02.run();
	}
	
}
