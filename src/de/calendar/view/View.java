package de.calendar.view;

import de.calendar.controller.Controller;
import de.calendar.model.CalendarModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class View implements PropertyChangeListener {

  private final JLabel appointmentLabel;
  private final JLabel appointmentDateLabel;
  private final JTextField appointmentField;
  private final JTextField appointmentDate;
  private final JPanel appPanel;
  private final JLabel reminderLabel;
  private final JCheckBox reminder;
  private final JButton save;
  private final JButton show;
  private final JTextArea console = new JTextArea(3,3);
  private DefaultTableModel tableModel;
  private Object[][] data;
  private JTable table;

  private final Controller controller;

  public View(Controller controller) {
    this.controller = controller;

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
    listenReminder();
    saveAppointment();
    showAppointments();

    save.setBounds(180,190 , 75,30);
    show.setBounds(405,190 , 75,30);

    appPanel.add(save);
    appPanel.add(show);
    data = new Object[0][4];
    createTable();

    appFrame.setVisible(true);
    appFrame.pack();
    appFrame.setMinimumSize(new Dimension(appPanel.getWidth()+20,
        appPanel.getHeight()+20));
    appFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  private void showAppointments() {
    show.addActionListener(e -> {
//      if (console.isVisible()) {
//        show.setText("Show");
//        console.setVisible(false);
//      }else {
        controller.showAppointments();
//      }
    });
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

  private void listenReminder() {
    reminder.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
//        controller.changeReminder(
//            new CalendarModel(appointmentField.getText(),
//            appointmentDate.getText(), reminder.isSelected())
//            );

        controller.changeReminder(reminder.isSelected());
        System.err.println("Hello");
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

    tableModel = getTableModelInstance();
    table = new JTable(tableModel);
    var scrollPane=new JScrollPane(table);
    scrollPane.setBounds(20,250 , 515,150);
    table.setBounds(20,250 , 515,150);
    table.getSelectionModel().addListSelectionListener(evt -> {
      System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
    });

    table.setPreferredScrollableViewportSize(new Dimension(515, 150));

    scrollPane.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
    scrollPane.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
    appPanel.add(scrollPane);

  }

  private DefaultTableModel getTableModelInstance() {

    String[] column ={"ID","Appointment","Date","Reminder"};
    return new DefaultTableModel( data, column) {
      @Override
      public int getRowCount() {
        return data.length;
      }

      @Override
      public int getColumnCount() {
        return column.length;
      }

      @Override
      public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
      }
      @Override
      public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
      }
      @Override
      public boolean isCellEditable(int row, int col) {
        return col >= 2;
      }

      @Override
      public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
      }

      public void sample() {
        var length = data.length;
      }

    };
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
      data = convertToRows(appointments);
      tableModel.setDataVector(data, new Object[]{"ID","Appointment","Date","Reminder"});
      alignColumns();
      console.setText("");
//      appointments.forEach(model -> console.append(model.toString()+"\n"));
//      console.setVisible(true);
//      show.setText("Close");
    }else {
      System.err.println("New value: "+evt.getNewValue());
    }

  }

  private  Object[][] convertToRows(List<CalendarModel> modelList) {
    Object[][] data = new Object[modelList.size()][4] ;
    for (var i=0; i < data.length; i++) {
      var model = modelList.get(i);
      data[i] = new Object[] {
          model.getId(), model.getAppointment(), model.getAppointment(), model.isReminder()
      };
    }

    return data;
  }

  private void alignColumns() {
    table.setAutoCreateColumnsFromModel(false);
    table.getColumnModel().getColumn(0).setPreferredWidth(10);
    table.getColumnModel().getColumn(1).setPreferredWidth(200);
    table.getColumnModel().getColumn(2).setPreferredWidth(100);
    table.getColumnModel().getColumn(3).setPreferredWidth(40);
  }

}
