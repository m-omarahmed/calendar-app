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

// PropertyChangeListener impelementieren um Änderungen von View ueberwacht zu werden
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
  private final CalendarTableModel tableModel;
  private JTable table;

  private final Controller controller;
  // GUI Komponenten initializieren
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

    appFrame.setContentPane(appPanel);
    appPanel.setBackground(new Color(255,230,0));
    reminder.setBackground(appPanel.getBackground());

    alignComponents();
    saveAppointment();


    createTable();

    appFrame.setVisible(true);
    appFrame.pack();
    appFrame.setMinimumSize(
        new Dimension(appPanel.getWidth()+20, appPanel.getHeight()+20));
    appFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    appFrame.setLocationRelativeTo(null);
  }

  // Termin speichen
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
  // Komponenten anpassen
  private void alignComponents() {
    appPanel.setBorder(new EmptyBorder(5,5,5,5));
    appPanel.setPreferredSize(new Dimension(550,480));
    appPanel.setLayout(null);

    appointmentLabel.setBounds(20,70 , 150,30);
    appointmentField.setBounds(180,70 , 300,30);

    appointmentDateLabel.setBounds(20,110 , 150,30);
    appointmentDate.setBounds(180,110 , 300,30);

    reminderLabel.setBounds(20,150 , 150,30);
    reminder.setBounds(176,150 , 300,30);

    save.setBounds(180,190 , 300,30);


    appPanel.add(appointmentLabel);
    appPanel.add(appointmentField);
    appPanel.add(appointmentDateLabel);
    appPanel.add(appointmentDate);
    appPanel.add(reminderLabel);
    appPanel.add(reminder);
    appPanel.add(save);
  }
  // Table erzeugen
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
// die aus PropertyChangeListener Methode implementieren
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals("new")) {
      JOptionPane.showMessageDialog(null, "Appointment was saved");
      appointmentField.setText("");
      appointmentDate.setText("");
      reminder.setSelected(false);
      controller.loadAppointments();
    }else if (evt.getPropertyName().equals("load")){
      List<CalendarModel> appointments = (List<CalendarModel>) evt.getNewValue();
      tableModel.rebuild(appointments);
    }else {
      System.err.println("Old value: "+evt.getOldValue());
      System.err.println("New value: "+evt.getNewValue());
    }
  }
  // Spalten anpassen
  private void alignColumns() {
    table.setAutoCreateColumnsFromModel(false);
    table.getColumnModel().getColumn(0).setPreferredWidth(10);
    table.getColumnModel().getColumn(1).setPreferredWidth(200);
    table.getColumnModel().getColumn(2).setPreferredWidth(100);
    table.getColumnModel().getColumn(3).setPreferredWidth(40);
  }

}
