package de.calendar;

import de.calendar.controller.Controller;

import javax.swing.*;

public class CalendarMain {
  public static void main(String[] args) {
    // GUI Thread verwenden
    SwingUtilities.invokeLater(Controller::new);
  }
}
