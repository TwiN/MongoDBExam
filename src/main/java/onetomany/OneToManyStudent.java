package onetomany;


public class OneToManyStudent {
	
	private int studentId;
	private String name;
	private OneToManyCity city;
	
	
	public OneToManyStudent(int studentId, String name, OneToManyCity city) {
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
	
	
	public OneToManyCity getCity() {
		return city;
	}
	
	
	public void setCity(OneToManyCity city) {
		this.city = city;
	}
}
