package uk.ac.aber.cs221.group5.logic;

import java.util.ArrayList;

public class TaskList {
	private ArrayList<Task> list = new ArrayList<Task>();
	
	public TaskList(){
		
	}
	
	public void addTask(Task task){
		this.list.add(task);
	}
	
	public Task getTask(int index){
		return this.list.get(index);
	}
	
	public int getListSize(){
		return this.list.size();
	}
}
