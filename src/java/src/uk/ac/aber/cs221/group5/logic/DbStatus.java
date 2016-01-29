package uk.ac.aber.cs221.group5.logic;

/**
 * This enum holds the three possible values of the Database Connection Status
 * 
 * @see Database.java
 */

public enum DbStatus {

   /**
    * successfully connected to db
    */

   CONNECTED,

   /**
    * could no establish a connection to the db
    */

   DISCONNECTED,

   /**
    * This is currently interacting with the db
    */

   SYNCHRONIZING
}
