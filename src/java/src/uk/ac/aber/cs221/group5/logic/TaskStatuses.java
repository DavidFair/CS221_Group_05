package uk.ac.aber.cs221.group5.logic;

/**
 * This enum details the 3 possible Task Statuses
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

public enum TaskStatuses {

   /**
    * The task has been abandoned
    */

   Abandoned,

   /**
    * This task has been allocated to a member
    */

   Allocated,

   /**
    * This task has been completed
    */

   Completed
}
