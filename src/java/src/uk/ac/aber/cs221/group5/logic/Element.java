package uk.ac.aber.cs221.group5.logic;

public class Element {
	String elementName;
	String comment;
	
	public Element(String name, String comment){
		this.elementName = name;
		this.comment = comment;	//If the element has no comment, this is an empty String
	}
	
	public String getName(){
		return this.elementName;
	}
	
	public String getComment(){
		return this.comment;
	}
	
	public void setComment(String newComment){
		this.comment = newComment;
	}
}
