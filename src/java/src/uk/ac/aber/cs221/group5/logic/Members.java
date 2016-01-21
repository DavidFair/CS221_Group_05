package uk.ac.aber.cs221.group5.logic;

public class Members {
	private String name;
	private String email;
	
	public Members(String name, String email){
		this.name  = name;
		this.email = email;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getEmail(){
		return this.email;
	}
}
