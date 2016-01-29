package uk.ac.aber.cs221.group5.logic;

/**
 * This Class deals with Members within TaskerCLI. It enables setting and getting of Member attributes.
 * 
 * @author Ben Dudley (bed19)
 * @author David Fairbrother (daf5)
 * @author Jonathan Englund (jee17)
 * @author Josh Doyle (jod32)
 * 
 * @version 1.0.0
 * @since 1.0.0
 * 
 * @see MemberList.java
 */

public class Members {
   private String name;
   private String email;

   /**
    * The constructor for the Member Object. Sets the Member's name and email
    * address.
    * 
    * @param name
    *           The new Member's name.
    * @param email
    *           The new Member's email address.
    */
   public Members(String name, String email) {
      this.name = name;
      this.email = email;
   }

   /**
    * Changes the Member's name.
    * 
    * @param name
    *           The Member's new name.
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Changes the Member's email address.
    * 
    * @param email
    *           The Member's new email address.
    */
   public void setEmail(String email) {
      this.email = email;
   }

   /**
    * Gets the Member's saved name.
    * 
    * @return The Member's name.
    */
   public String getName() {
      return this.name;
   }

   /**
    * Gets the Member's saved email address.
    * 
    * @return The Member's email address.
    */
   public String getEmail() {
      return this.email;
   }
}
