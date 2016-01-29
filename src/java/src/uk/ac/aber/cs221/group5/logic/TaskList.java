package uk.ac.aber.cs221.group5.logic;

import java.util.ArrayList;

/**
 * This Class represents a Task List, a list of many Task Objects.
 * 
 * @author Ben Dudley (bed19)
 * @author David Fairbrother (daf5)
 * @author Jonathan Englund (jee17)
 * @author Josh Doyle (jod32)
 * 
 * @version 1.0.0
 * @since 1.0.0
 * @see Task.java
 */

public class TaskList {
   private ArrayList<Task> list = new ArrayList<Task>();

   /**
    * The TaskList constructor.
    */
   public TaskList() {

   }

   /**
    * Gets the whole TaskList.
    * 
    * @return Returns an ArrayList containing every Task held in this Task List.
    */
   public ArrayList<Task> getTaskList() {
      return this.list;
   }

   /**
    * Adds a new Task to this Task List.
    * 
    * @param task
    *           The Task to be added to this Task List.
    */
   public void addTask(Task task) {
      this.list.add(task);
   }

   /**
    * Gets a Task from a given position in this TaskList.
    * 
    * @param index
    *           The position from which to get the Task.
    * @return Returns the Task at the position of index within this TaskList.
    */
   public Task getTask(int index) {
      return this.list.get(index);
   }

   /**
    * Gets the size of this Task List.
    * 
    * @return An int representing how many Tasks are held in this TaskList.
    */
   public int getListSize() {
      return this.list.size();
   }

   /**
    * Updates a single Task in this TaskList.
    * 
    * @param taskPos
    *           The position of the Task in this Task List that is changed
    * @param newTask
    *           The Task to add in the place of the Task at position taskPos in
    *           the Task List.
    */
   public void changeTask(int taskPos, Task newTask) {
      this.list.set(taskPos, newTask);
   }
}
