package de.calendar.controller;

import de.calendar.model.CalendarModel;

public interface AppCalendar {
  void changeAppointment(CalendarModel model, int row);
  void saveAppointment(CalendarModel model);
  void showAppointments();
}
