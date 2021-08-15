package de.calendar.controller;

import de.calendar.model.CalendarModel;
import de.calendar.view.View;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Controller implements AppCalendar{
  private final PropertyChangeSupport propertyChangeSupport;
  private final List<CalendarModel> appointments =new ArrayList<>();
  private int incrementId;

  public Controller() {
    var myView = new View(this);
    this.propertyChangeSupport  = new PropertyChangeSupport(myView);

    propertyChangeSupport.addPropertyChangeListener("reminder", myView);
    propertyChangeSupport.addPropertyChangeListener("new", myView);
    propertyChangeSupport.addPropertyChangeListener("show", myView);
  }

//  @Override
//  public void changeReminder(CalendarModel model) {
//    var oldModel = !model.isReminder();
//    model.setReminder(model.isReminder());
//    propertyChangeSupport.firePropertyChange("reminder", oldModel, model);
//  }

  @Override
  public void changeReminder(boolean reminder) {
    var oldModel = !reminder;
//    model.setReminder(model.isReminder());
    propertyChangeSupport.firePropertyChange("reminder", oldModel, reminder);
  }

  @Override
  public void saveAppointment(CalendarModel model) {
    var oldSize = appointments.size();
    incrementId = (appointments.isEmpty()) ? 1 : ++ incrementId;
    model.setId(incrementId);
    appointments.add(model);
    propertyChangeSupport.firePropertyChange("new", oldSize, appointments.size());
  }

//  @Override
//  public List<CalendarModel> showAppointments() {
//    propertyChangeSupport.firePropertyChange("show", null, appointments);
//    return appointments;
//  }

  @Override
  public void showAppointments() {
    propertyChangeSupport.firePropertyChange("show", null, appointments);
  }
}
