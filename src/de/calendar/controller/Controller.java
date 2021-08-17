package de.calendar.controller;

import de.calendar.model.CalendarModel;
import de.calendar.view.View;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Controller implements AppCalendar, Serializable {
  private static final long serialVersionUID = 1111111111111L;
  private final PropertyChangeSupport propertyChangeSupport;
  private final List<CalendarModel> appointments =new ArrayList<>();
  private int incrementId;

  public Controller() {
    var myView = new View(this);
    this.propertyChangeSupport  = new PropertyChangeSupport(this);

    propertyChangeSupport.addPropertyChangeListener("edit", myView);
    propertyChangeSupport.addPropertyChangeListener("new", myView);
    propertyChangeSupport.addPropertyChangeListener("load", myView);
  }

  @Override
  public void editAppointment(CalendarModel oldModel, int row) {
    propertyChangeSupport.firePropertyChange("edit", oldModel, appointments.get(row));
  }

  @Override
  public void saveAppointment(CalendarModel model) {
    var oldSize = appointments.size();
    incrementId = (appointments.isEmpty()) ? 1 : ++incrementId;
    model.setId(incrementId);
    appointments.add(model);
    propertyChangeSupport.firePropertyChange("new", oldSize, appointments.size());
  }

  @Override
  public void loadAppointments() {
    propertyChangeSupport.firePropertyChange("load", null, appointments);
  }
}
