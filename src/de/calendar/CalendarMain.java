package de.calendar;

import de.calendar.controller.Controller;

import javax.swing.*;

public class CalendarMain {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(Controller::new);
  }
}
