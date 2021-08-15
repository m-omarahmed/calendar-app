package de.calendar.view;

import de.calendar.controller.Controller;
import de.calendar.model.CalendarModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class View implements PropertyChangeListener, Serializable {
  private static final long serialVersionUID = 10000000000L;
  private final JLabel appointmentLabel;
  private final JLabel appointmentDateLabel;
  private final JTextField appointmentField;
  private final JTextField appointmentDate;
  private final JPanel appPanel;
  private final JLabel reminderLabel;
  private final JCheckBox reminder;
  private final JButton save;
  private final JButton show;
  private final CalendarTableModel tableModel;
  private JTable table;

  private final Controller controller;

  public View(Controller controller) {
    this.controller = controller;
    tableModel = new CalendarTableModel(new ArrayList<>(), controller);

    var appFrame = new JFrame("Calendar");
    appPanel = new JPanel();
    appointmentLabel = new JLabel("Appointment: ");
    appointmentDateLabel = new JLabel("Date & Time: ");
    reminderLabel = new JLabel("Reminder: ");
    appointmentField = new JFormattedTextField();
    appointmentDate = new JFormattedTextField();
    reminder = new JCheckBox();

    save = new JButton("Save");
    show = new JButton("Show");

    appFrame.setContentPane(appPanel);
    appPanel.setBorder(new EmptyBorder(5,5,5,5));
    appPanel.setPreferredSize(new Dimension(550,480));
    appPanel.setLayout(null);
    appPanel.setBackground(new Color(255,230,0));
    reminder.setBackground(appPanel.getBackground());

    buildAppointment();
    saveAppointment();
    showAppointments();

    save.setBounds(180,190 , 75,30);
    show.setBounds(405,190 , 75,30);

    appPanel.add(save);
    appPanel.add(show);
    createTable();

    appFrame.setVisible(true);
    appFrame.pack();
    appFrame.setMinimumSize(new Dimension(appPanel.getWidth()+20,
        appPanel.getHeight()+20));
    appFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  private void showAppointments() {
    show.addActionListener(e -> controller.showAppointments());
  }

  private void saveAppointment() {
    save.addActionListener(e -> {
      if (!appointmentField.getText().equals("")
      && !appointmentDate.getText().equals("") ) {
        controller.saveAppointment(
            new CalendarModel(appointmentField.getText(),
                appointmentDate.getText(), reminder.isSelected())
        );
      }
    });
  }

  private void buildAppointment() {
    appointmentLabel.setBounds(20,70 , 150,30);
    appointmentField.setBounds(180,70 , 300,30);
//    appointmentField.setBounds(180,70 , 350,30);

    appointmentDateLabel.setBounds(20,110 , 150,30);
    appointmentDate.setBounds(180,110 , 300,30);

    reminderLabel.setBounds(20,150 , 150,30);
    reminder.setBounds(176,150 , 300,30);

    appPanel.add(appointmentLabel);
    appPanel.add(appointmentField);
    appPanel.add(appointmentDateLabel);
    appPanel.add(appointmentDate);
    appPanel.add(reminderLabel);
    appPanel.add(reminder);
  }

  private void createTable() {
    table = new JTable(tableModel);
    var scrollPane=new JScrollPane(table);
    scrollPane.setBounds(20,250 , 515,150);
    table.setBounds(20,250 , 515,150);
    table.setPreferredScrollableViewportSize(new Dimension(515, 150));
    alignColumns();

    scrollPane.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
    scrollPane.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
    appPanel.add(scrollPane);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals("new")) {
//      JOptionPane.showMessageDialog(null, "Appointment was saved");
      System.err.println("Appointment was saved");
      appointmentField.setText("");
      appointmentDate.setText("");
      reminder.setSelected(false);
    }else if (evt.getPropertyName().equals("show")){
      List<CalendarModel> appointments = (List<CalendarModel>) evt.getNewValue();
      tableModel.setCalendarModels(appointments);
    }else {
      System.err.println("Old value: "+evt.getOldValue());
      System.err.println("New value: "+evt.getNewValue());
    }
  }

  private void alignColumns() {
    table.setAutoCreateColumnsFromModel(false);
    table.getColumnModel().getColumn(0).setPreferredWidth(10);
    table.getColumnModel().getColumn(1).setPreferredWidth(200);
    table.getColumnModel().getColumn(2).setPreferredWidth(100);
    table.getColumnModel().getColumn(3).setPreferredWidth(40);
  }

}
