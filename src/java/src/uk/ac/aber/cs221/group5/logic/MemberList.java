package uk.ac.aber.cs221.group5.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This Class deals with the list of Members held in memory and used by TaskerCLI, namely loading Members, 
 * and providing validation that a provided email address belongs to a saved Member. 
 * 
 * @author Ben Dudley (bed19)
 * @author David Fairbrother (daf5)
 * @author Jonathan Englund (jee17)
 * @author Josh Doyle (jod32)
 * 
 * @version 1.0.0
 * @since 1.0.0
 * 
 * @see Members
 */

public class MemberList {
   // TODO implement updating memberList
   private ArrayList<Members> memberList = new ArrayList<Members>();

   /**
    * constructs an empty member list
    */
public MemberList() {

   }

   /**
    * Returns a Member from the list in a specified position
    * 
    * @param index
    *           The index position to get the Member from
    * @return A Member
    */
   public Members getMember(int index) {
      return memberList.get(index);
   }

   /**
    * Adds a Member to the Member List
    * 
    * @param member
    *           The Member to add to the Member List
    */
   public void addMember(Members member) {
      memberList.add(member);
   }

   /**
    * Loads a list of Members from the local Member save file into the Member
    * List in memory.
    * 
    * @param filename
    *           The filepath of the local Member save file
    * @throws NumberFormatException
    *            if the number of Members in the local save file cannot be read
    * @throws IOException
    *            if the local Member save file cannot be read
    */
   public void loadMembers(String filename) throws NumberFormatException, IOException {
      try {
         FileReader fileReader = new FileReader(filename);
         BufferedReader read = new BufferedReader(fileReader);
         String memberName;
         String memberEmail;
         int memberCount = Integer.parseInt(read.readLine());
         for (int loopCount = 0; loopCount < memberCount; loopCount++) {
            memberEmail = read.readLine();
            memberName = read.readLine();
            Members member = new Members(memberName, memberEmail);
            memberList.add(member);
         }
         read.close();
      } catch (Exception e) {
         FileWriter blankFile = new FileWriter(filename, true);
         blankFile.write("");
         blankFile.close();

      }
   }

   /**
    * A check to see if a given email address belongs to a saved Member.
    * 
    * @param email
    *           The email address to check against the Members' emails in the
    *           local save file
    * @return Whether or not the given email address belongs to a saved Member.
    */
   public boolean memberExists(String email) {
      for (int loopCount = 0; loopCount < memberList.size(); loopCount++) {
         if ((this.getMember(loopCount).getEmail()).equals(email)) {
            return true;
         }
      }
      return false;
   }

   /**
    * gets the length of the current member list
    * @return the length of the member list as an int
    */
   
public int getLength() {
      return this.memberList.size();
   }
}
