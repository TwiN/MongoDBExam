package onetoone;

public class City {
	
	private int cityId;
	private String name;
	private Student student;
	
	
	public City(String name, Student student) {
		this.name = name;
		this.student = student;
	}
	
	
	public int getCityId() {
		return cityId;
	}
	
	
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	
	
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public Student getStudent() {
		return student;
	}
	
	
	public void setStudent(Student student) {
		this.student = student;
	}
}

