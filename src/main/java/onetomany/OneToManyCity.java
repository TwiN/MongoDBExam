package onetomany;


import java.util.List;


public class OneToManyCity {
	
	private int cityId;
	private String name;
	private List<OneToManyStudent> students;
	
	
	public OneToManyCity(int cityId, String name, List<OneToManyStudent> students) {
		this.cityId = cityId;
		this.name = name;
		this.students = students;
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
	
	
	public List<OneToManyStudent> getStudents() {
		return students;
	}
	
	
	public void setStudents(List<OneToManyStudent> students) {
		this.students = students;
	}
}

