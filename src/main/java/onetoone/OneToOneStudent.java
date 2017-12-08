package onetoone;


public class OneToOneStudent {
	
	private int studentId;
	private String name;
	private OneToOneCity city;
	
	
	public OneToOneStudent(int studentId, String name, OneToOneCity city) {
		this.studentId = studentId;
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
	
	
	public OneToOneCity getCity() {
		return city;
	}
	
	
	public void setCity(OneToOneCity city) {
		this.city = city;
	}
}
