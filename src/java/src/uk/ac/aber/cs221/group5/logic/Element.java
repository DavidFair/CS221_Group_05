package uk.ac.aber.cs221.group5.logic;

public class Element {
	String elementDesc;
	String comment;
	
	public Element(String description, String comment){
		this.elementDesc = description;
		this.comment = comment;	//If the element has no comment, this is an empty String
	}
	
	public String getName(){
		return this.elementDesc;
	}
	
	public String getComment(){
		return this.comment;
	}
	
	public void setComment(String newComment){
		this.comment = newComment;
	}
}
