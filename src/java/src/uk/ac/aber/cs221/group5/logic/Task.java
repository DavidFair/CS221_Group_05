package uk.ac.aber.cs221.group5.logic;

import java.util.ArrayList;

public class Task {
	String taskID;
	String taskName;
	String taskStart;
	String taskEnd;
	String taskMembers;
	
	TaskStatuses status;
	
	ArrayList<Element> taskElements;
	
	public Task(String id, String name, String start, String end, String members, TaskStatuses status){
		this.taskID    = id;
		this.taskName  = name;
		this.taskStart = start;
		this.taskEnd   = end;
		
		this.taskMembers = members;
		
		this.status = status;
		
		taskElements = new ArrayList<Element>();
	}
	
	public String getID(){
		return this.taskID;
	}
	
	public String getName(){
		return this.taskName;
	}
	
	public String getStart(){
		return this.taskStart;
	}
	
	public String getEnd(){
		return this.taskEnd;
	}
	
	public String getMembers(){
		return this.taskMembers;
	}
	
	public String getStatus(){
		return this.status.toString();
	}
	
	public void addElement(String elementName, String elementComment){
		Element newElement = new Element(elementName, elementComment);
		taskElements.add(newElement);
	}
	
	public Element getElement(int index){
		return taskElements.get(index);
	}
}
