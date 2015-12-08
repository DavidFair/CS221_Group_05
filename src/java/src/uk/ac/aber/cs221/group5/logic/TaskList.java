package uk.ac.aber.cs221.group5.logic;

import java.util.ArrayList;

public class TaskList {
	private ArrayList<Task> list;
	
	public TaskList(){
		ArrayList<Task> list = new ArrayList<Task>();
	}
	
	public void addTask(Task task){
		list.add(task);
	}
	
	public Task getTask(int index){
		return list.get(index);
	}
	
	public int getListSize(){
		return list.size();
	}
}
