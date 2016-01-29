package uk.ac.aber.cs221.group5.jUnit;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.aber.cs221.group5.logic.Task;
import uk.ac.aber.cs221.group5.logic.TaskStatuses;

public class TaskTest {

	private Task toTest;
	private static final String taskID = "3";
	private static final String taskName = "JavaDoc";
	private static final String taskStart = "03/05/2011";
	private static final String taskEnd = "16/07/2011";
	private static final String Members = "far19@aber.ac.uk";
	private static final TaskStatuses Status = TaskStatuses.Completed;
	 
	@Before
    public void setUpTest() {
		
		this.toTest = new Task(taskID, taskName, taskStart, taskEnd, Members, Status);
		
    }
	
	@Test
	public void getIDTest(){
		boolean result;
		result = this.toTest.getID().equals(taskID);
		assertTrue("TaskID return was wrong!", result);
	}
	
	@Test
	public void getName(){
		boolean result;
		result = this.toTest.getName().equals(taskName);
		assertTrue("TaskName return was wrong!", result);
	}
	
	@Test
	public void getStart(){
		boolean result;
		result = this.toTest.getStart().equals(taskStart);
		assertTrue("TaskStart return was wrong!", result);
	}
	
	@Test
	public void getEnd(){
		boolean result;
		result = this.toTest.getEnd().equals(taskEnd);
		assertTrue("TaskEnd return was wrong!", result);
	}
	
	@Test 
	public void getMembers(){
		boolean result;
		result = this.toTest.getMembers().equals(Members);
		assertTrue("getMembers return was wrong!", result);
	}
	
	@Test
    public void getStatus(){
		boolean result;
		result = this.toTest.getStatus().equals(Status.toString());
	    assertTrue("getStatus return was wrong!", result);
	}
	
	@Test
	public void setStatus(){
		boolean result;
		this.toTest.setStatus(TaskStatuses.Completed);
		result = this.toTest.getStatus().equals(TaskStatuses.Completed.toString());
		assertTrue("setStatus return was wrong!", result);
		
	}	
	
	public class ElementsTest{
	
	private ElementsTest toTest;
	private static final String elementName = "aaaaa";
	private static final String elementComment = "not good";
	private static final String elementIndex = "2";
	
	}
	

}
	
	
		
		
		
	

