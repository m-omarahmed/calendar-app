package de.calendar.view;

import de.calendar.controller.Controller;
import de.calendar.model.CalendarModel;

import javax.swing.table.AbstractTableModel;
import java.io.Serializable;
import java.util.List;

public class CalendarTableModel extends AbstractTableModel implements Serializable {
  private static final long serialVersionUID = 2405172041950251807L;
  private final String[] columnsNames = {"ID", "Appointment", "Date&Time", "Reminder"};
  private final Controller controller;
  private List<CalendarModel> calendarModels;

  public CalendarTableModel(List<CalendarModel> calendarModels, Controller controller) {
    this.calendarModels = calendarModels;
    this.controller = controller;
  }

  @Override
  public int getRowCount() {
    return calendarModels.size();
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
    var calenderModel = calendarModels.get(row);
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
        calendarModels.get(row).setAppointment((String) value);
        break;
      case 2:
        calendarModels.get(row).setDateTime((String) value);
        break;
      case 3:
        calendarModels.get(row).setReminder((Boolean) value);
        break;
      default:
        break;
    }
    controller.changeAppointment(oldModel, row);
  }

  public void setCalendarModels(List<CalendarModel> calendarModels) {
    this.calendarModels = calendarModels;
    fireTableDataChanged();
  }

  private CalendarModel clone(int row) {
    var cm = calendarModels.get(row);
    CalendarModel toReturnModel = new CalendarModel( cm.getAppointment(), cm.getDateTime(), cm.isReminder());
    toReturnModel.setId(cm.getId());

    return toReturnModel ;
  }
}
