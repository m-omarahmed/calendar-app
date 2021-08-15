package de.calendar.controller;

import de.calendar.model.CalendarModel;

import java.util.List;

public interface AppCalendar {

//  void changeReminder(CalendarModel model);
  void changeReminder(boolean reminder);
  void saveAppointment(CalendarModel model);
//  List<CalendarModel> showAppointments();
  void showAppointments();

}
