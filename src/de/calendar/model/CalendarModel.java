package de.calendar.model;

import java.time.LocalDateTime;
import java.util.Random;

public class CalendarModel {

  private int id;
  private String appointment;
//  private LocalDateTime dateTime;
  private String dateTime;
  private boolean reminder;

  public CalendarModel(String appointment, String dateTime, boolean reminder) {
//    this.id = new Random().nextInt(100);
    this.appointment = appointment;
    this.dateTime = dateTime;
    this.reminder = reminder;

//    dateTime = LocalDateTime.now();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAppointment() {
    return appointment;
  }

  public void setAppointment(String appointment) {
    this.appointment = appointment;
  }

//  public LocalDateTime getDateTime() {
//    return dateTime;
//  }
//
//  public void setDateTime(LocalDateTime dateTime) {
//    this.dateTime = dateTime;
//  }

  public boolean isReminder() {
    return reminder;
  }

  public void setReminder(boolean reminder) {
    this.reminder = reminder;
  }

  @Override
  public String toString() {
    return "[" +
        "id: " + id +
        ", appointment: " + appointment +
        ", dateTime: " + dateTime  +
        ", reminder: " + reminder +
        ']';
  }
}
