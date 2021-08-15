package de.calendar;

import de.calendar.controller.Controller;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CalendarMain {
  public static void main(String[] args) {
//   new Controller();
    SwingUtilities.invokeLater(Controller::new);


//    JPanel middlePanel = new JPanel ();
//    middlePanel.setBorder ( new TitledBorder( new EtchedBorder(), "Display Area" ) );
//
//    // create the middle panel components
//
//    JTextArea display = new JTextArea ( 16, 58 );
//    display.setEditable ( false ); // set textArea non-editable
//    JScrollPane scroll = new JScrollPane ( display );
//    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
//
//    //Add Textarea in to middle panel
//    middlePanel.add ( scroll );
//
//    // My code
//    JFrame frame = new JFrame ();
//    frame.add ( middlePanel );
//    frame.pack ();
//    frame.setLocationRelativeTo ( null );
//    frame.setVisible ( true );

//    JTable table = new JTable(5, 5);
//
//    int rowIndex = 1;
//    int vColIndex = 2;
//    scrollToCenter(table, rowIndex, vColIndex);
//    var frame = new JFrame();
//    frame.setSize(new Dimension(300,400));
//    frame.setVisible(true);
//    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
////    frame.setComponentOrientation(null);
//    frame.add(table);
  }

  public static void scrollToCenter(JTable table, int rowIndex, int vColIndex) {
    if (!(table.getParent() instanceof JViewport)) {
      return;
    }
    JViewport viewport = (JViewport) table.getParent();
    Rectangle rect = table.getCellRect(rowIndex, vColIndex, true);
    Rectangle viewRect = viewport.getViewRect();
    rect.setLocation(rect.x - viewRect.x, rect.y - viewRect.y);

    int centerX = (viewRect.width - rect.width) / 2;
    int centerY = (viewRect.height - rect.height) / 2;
    if (rect.x < centerX) {
      centerX = -centerX;
    }
    if (rect.y < centerY) {
      centerY = -centerY;
    }
    rect.translate(centerX, centerY);
    viewport.scrollRectToVisible(rect);
  }
}
