package uk.ac.aber.cs221.group5.gui;

import java.io.IOException;

public class ViewElementsWindow {

   private ViewElementsWindowGUI childWindow;

   public ViewElementsWindow(int tableRow) throws IOException {
      childWindow = new ViewElementsWindowGUI(tableRow);
   }

}
