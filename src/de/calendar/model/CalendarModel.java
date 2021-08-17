package de.calendar.model;

import java.io.Serializable;
import java.util.Objects;

public class CalendarModel implements Serializable {
  private static final long serialVersionUID = 1905122041950251207L;
  private int id;
  private String appointment;
  private String dateTime;
  private boolean reminder;

  public CalendarModel(String appointment, String dateTime, boolean reminder) {
    this.appointment = appointment;
    this.dateTime = dateTime;
    this.reminder = reminder;
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

  public String getDateTime() {
    return dateTime;
  }

  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }

  public boolean isReminder() {
    return reminder;
  }

  public void setReminder(boolean reminder) {
    this.reminder = reminder;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CalendarModel)) return false;
    CalendarModel that = (CalendarModel) o;
    return id == that.id && reminder == that.reminder
        && appointment.equals(that.appointment) && dateTime.equals(that.dateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, appointment, dateTime, reminder);
  }

  @Override
  public String toString() {
    return "[" +
        "ID: " + id +
        ", Appointment: " + appointment +
        ", Date & Time: " + dateTime +
        ", Reminder: " + reminder +
        ']';
  }
}
