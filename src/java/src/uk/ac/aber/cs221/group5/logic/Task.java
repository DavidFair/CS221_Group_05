package uk.ac.aber.cs221.group5.logic;


public class Task {
	String taskID;
	String taskName;
	String taskStart;
	String taskEnd;
	String taskMembers;
	
	TaskStatuses status;
	
	public Task(String id, String name, String start, String end, String members, TaskStatuses status){
		this.taskID    = id;
		this.taskName  = name;
		this.taskStart = start;
		this.taskEnd   = end;
		
		this.taskMembers = members;
		
		this.status = status;
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
}
