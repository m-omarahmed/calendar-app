package de.calendar.view;

import de.calendar.controller.Controller;
import de.calendar.model.CalendarModel;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CalendarTableModel extends AbstractTableModel {
  private final String[] columnsNames = {"ID", "Appointment", "Date & Time", "Reminder"};
  private final Controller controller;
  private List<CalendarModel> appointments;

  public CalendarTableModel(List<CalendarModel> appointments, Controller controller) {
    this.appointments = appointments;
    this.controller = controller;
  }

  @Override
  public int getRowCount() {
    return appointments.size();
  }

  @Override
  public int getColumnCount() {
    return columnsNames.length;
  }

  @Override
  public String getColumnName(int column) {
    return columnsNames[column];
  }

  @Override
  public Object getValueAt(int row, int column) {
    var calenderModel = appointments.get(row);
    switch (column) {
      case 0:
        return calenderModel.getId();
      case 1:
        return calenderModel.getAppointment();
      case 2:
        return calenderModel.getDateTime();
      case 3:
        return calenderModel.isReminder();
      default:
        break;
    }
    return null;
  }

  @Override
  public Class<?> getColumnClass(int c) {
    return getValueAt(0, c).getClass();
  }

  @Override
  public boolean isCellEditable(int row, int col) {
    return col > 0;
  }

  @Override
  public void setValueAt(Object value, int row, int col) {
    CalendarModel oldModel = clone(row);
    switch (col) {
      case 1:
        appointments.get(row).setAppointment((String) value);
        break;
      case 2:
        appointments.get(row).setDateTime((String) value);
        break;
      case 3:
        appointments.get(row).setReminder((Boolean) value);
        break;
      default:
        break;
    }
    controller.editAppointment(oldModel, row);
  }

  public void rebuild(List<CalendarModel> calendarModels) {
    this.appointments = calendarModels;
    fireTableDataChanged();
  }

  private CalendarModel clone(int row) {
    var cm = appointments.get(row);
    var toReturnModel = new CalendarModel(cm.getAppointment(), cm.getDateTime(), cm.isReminder());
    toReturnModel.setId(cm.getId());

    return toReturnModel ;
  }
}
