package onetoone;


public class OneToOneCity {
	
	private int cityId;
	private String name;
	private OneToOneStudent student;
	
	
	public OneToOneCity(int cityId, String name, OneToOneStudent student) {
		this.cityId = cityId;
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
	
	
	public OneToOneStudent getStudent() {
		return student;
	}
	
	
	public void setStudent(OneToOneStudent student) {
		this.student = student;
	}
}

