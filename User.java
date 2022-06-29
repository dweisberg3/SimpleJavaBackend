
public class User {

	private String name;
	private String dateOfBirth;
	private String address;
	
	User(String name, String dateOfBirth, String address){
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.address= address;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	
}
