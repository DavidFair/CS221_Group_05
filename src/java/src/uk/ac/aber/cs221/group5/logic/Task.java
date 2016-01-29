/**
 * This Class deals with a single Task in TaskerCLI. The Class contains methods to get and set the Task
 * attributes. This Class also contains an inner Class to deal with Task Elements, of which there can be
 * from 0 up to an unknown amount.
 */

package uk.ac.aber.cs221.group5.logic;

import java.util.ArrayList;

public class Task {
   private String taskID;
   private String taskName;
   private String taskStart;
   private String taskEnd;
   private String taskMembers;

   TaskStatuses status;

   ArrayList<Element> taskElements;

   /**
    * The constructor for the Task Object.
    * 
    * @param id
    *           The Task's unique identifier.
    * @param name
    *           The name of the Task.
    * @param start
    *           The start date of the Task.
    * @param end
    *           The expected end date for the Task.
    * @param members
    *           The Member that has been assigned to the Task.
    * @param status
    *           The Tasks current Status.
    */
   public Task(String id, String name, String start, String end, String members, TaskStatuses status) {
      this.taskID = id;
      this.taskName = name;
      this.taskStart = start;
      this.taskEnd = end;

      this.taskMembers = members;

      this.status = status;

      taskElements = new ArrayList<Element>();
   }

   /**
    * Get the Task's unique identifier.
    * 
    * @return The Task's unique identifier.
    */
   public String getID() {
      return this.taskID;
   }

   /**
    * Get the Task's name.
    * 
    * @return The name of the Task.
    */
   public String getName() {
      return this.taskName;
   }

   /**
    * Get the Task's start date.
    * 
    * @return The start date of the Task.
    */
   public String getStart() {
      return this.taskStart;
   }

   /**
    * Get the Task's expected end date.
    * 
    * @return The Task's expected end date.
    */
   public String getEnd() {
      return this.taskEnd;
   }

   /**
    * Gets the Member assigned to this Task.
    * 
    * @return The Member assigned to this Task.
    */
   public String getMembers() {
      return this.taskMembers;
   }

   /**
    * Gets the Task's current status.
    * 
    * @return The Task's current status.
    */
   public String getStatus() {
      return this.status.toString();
   }

   /**
    * Updates the Task's status.
    * 
    * @param newStatus
    *           The new status that the Task is being updated to.
    */
   public void setStatus(TaskStatuses newStatus) {
      this.status = newStatus;
   }

   /**
    * Adds a single Element to this Task.
    * 
    * @param elementName
    *           The name of the Element.
    * @param elementComment
    *           The Element's comment.
    * @param index
    *           The Element's unique index.
    */
   public void addElement(String elementName, String elementComment, String index) {
      Element newElement = new Element(elementName, elementComment, index);
      taskElements.add(newElement);
   }

   /**
    * Removes all Elements from this Task.
    */
   public void clearAllElements() {
      taskElements.clear();
   }

   /**
    * Gets a single Element from this Task.
    * 
    * @param index
    *           The Element's index within this Task's Element list.
    * @return The chosen element from this Task's Element List.
    */
   public Element getElement(int index) {
      return taskElements.get(index);
   }

   /**
    * Returns all of the Elements in this Task's Element List
    * 
    * @return A list of Elements which contains all of the Elements in this
    *         Task's Element list.
    */
   public ArrayList<Element> getAllElements() {
      return taskElements;
   }

   /**
    * Returns all of the Elements in this Task's Element List but with the
    * Element name and the Element comment seperated.
    * 
    * @return An ArrayList of seperated Element name-comment pairs.
    */
   public ArrayList<String[]> getAllElementPairs() {
      ArrayList<String[]> allPairs = new ArrayList<String[]>();

      for (Element index : taskElements) {
         String name = index.getName();
         String comment = index.getComment();

         String[] elementPair = { name, comment };
         allPairs.add(elementPair);
      }

      return allPairs;
   }

   /**
    * Gets the number of Elements held by this Task.
    * 
    * @return The number of Elements owned by this Task.
    */
   public int getNumElements() {
      return this.taskElements.size();
   }

   /**
    * This Class represents a single Task Element.
    * 
    * @author Ben Dudley (bed19)
    * @author David Fairbrother (daf5)
    * @author Jonathan Englund (jee17)
    * @author Josh Doyle (jod32)
    * 
    * @version 1.0.0
    * @since 1.0.0
    *
    */
   public class Element {
      String elementDesc;
      String comment;
      String index;

      /**
       * Constructor for the Element Object.
       * 
       * @param description
       *           The Element's description.
       * @param comment
       *           The Element's comment.
       * @param elementIndex
       *           The Element's unique identifier.
       */
      public Element(String description, String comment, String elementIndex) {
         this.elementDesc = description;
         this.comment = comment; // If the element has no comment, this is an
                                 // empty String
         this.index = elementIndex;
      }

      /**
       * Get the description of the Element.
       * 
       * @return This Element's description.
       */
      public String getName() {
         return this.elementDesc;
      }

      /**
       * Get the Element's comment.
       * 
       * @return The Element's comment.
       */
      public String getComment() {
         return this.comment;
      }

      /**
       * Get the Element's unique identifier.
       * 
       * @return The Element's unique identifier.
       */
      public String getIndex() {
         return this.index;
      }

      /**
       * Update the Element's comment.
       * 
       * @param newComment
       *           The Element's new comment.
       */
      public void setComment(String newComment) {
         this.comment = newComment;
      }
   }

}
