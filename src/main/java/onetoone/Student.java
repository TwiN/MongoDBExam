package onetoone;

public class Student {
	
	private int studentId;
	private String name;
	private City city;
	
	
	public Student(String name, City city) {
		this.name = name;
		this.city = city;
	}
	
	
	public int getStudentId() {
		return studentId;
	}
	
	
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public City getCity() {
		return city;
	}
	
	
	public void setCity(City city) {
		this.city = city;
	}
}
