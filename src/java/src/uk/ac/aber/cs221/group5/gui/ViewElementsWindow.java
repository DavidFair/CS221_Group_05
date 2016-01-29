/**
 * This Class creates the View Elements Window GUI.
 * 
 * @author Ben Dudley (bed19)
 * @author David Fairbrother (daf5)
 * @author Jonathan Englund (jee17)
 * @author Josh Doyle (jod32)
 * 
 * @version 1.0.0
 * @since 1.0.0
 */

package uk.ac.aber.cs221.group5.gui;

import java.io.IOException;

public class ViewElementsWindow {

   private ViewElementsWindowGUI childWindow;

   /**
    * Constructor for the View Elements Window. Passes through the index in the Task List of the Task
    * that was selected in Main Window.
    * @param tableRow The index in the Task List of the Task that was selected to View Elements.
    * @throws IOException if the local Task save file cannot be read.
    */
   public ViewElementsWindow(int tableRow) throws IOException {
      childWindow = new ViewElementsWindowGUI(tableRow);
   }

}
