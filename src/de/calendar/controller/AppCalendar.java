package de.calendar.controller;

import de.calendar.model.CalendarModel;

public interface AppCalendar {
  void editAppointment(CalendarModel model, int row);
  void saveAppointment(CalendarModel model);
  void loadAppointments();
}
