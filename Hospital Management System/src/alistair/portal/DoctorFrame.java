package alistair.portal;

import alistair.business.AppointmentRecord;
import alistair.business.Employee;
import alistair.business.Record;
import alistair.data.DoctorDB;
import alistair.data.PatientDB;
import alistair.data.RecordDB;
import alistair.utility.Color;
import alistair.utility.DateFormatter;
import alistair.utility.Validate;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;

public class DoctorFrame extends JFrame {
    private int doctorID;

    private ArrayList<AppointmentRecord> records;
    private final JList<String> upcomingList;
    private final JList<String> missedList;

    // appointment text fields declaration
    private final JTextField searchAppTF = new JTextField();
    private final JTextField ridTF = new JTextField();
    private final JTextField symptomsTF = new JTextField();

    private final UtilDateModel modelApp = new UtilDateModel();
    private final JDatePanelImpl datePanelApp = new JDatePanelImpl(modelApp,new Properties());
    private final JDatePickerImpl appDatePicker = new JDatePickerImpl(datePanelApp,new DateFormatter());

    private final JTextField appTimeTF = new JTextField();
    private final JComboBox statusTF = new JComboBox(new String[]{"Please Select", "0", "1"});
    private final JTextField diagnosisTF = new JTextField();
    private final JTextField prescriptionTF = new JTextField();
    private final JTextField patientIDTF = new JTextField();
    private  final JTextField doctorIDTF = new JTextField();

    // record buttons
    private final JButton updateRecord = new JButton("Update");
    private final JButton searchRecord = new JButton("Search");

    public DoctorFrame(int doctorID) {
        // set session id for this doctor
        setDoctorID(doctorID);

        // define appointment labels
        // appointment labels declaration
        JLabel searchAppLabel = new JLabel("Enter RecordID:");
        searchAppLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        JLabel ridLabel = new JLabel("Record ID");
        ridLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        JLabel symptomsLabel = new JLabel("Symptoms");
        symptomsLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        JLabel appDateLabel = new JLabel("Date");
        appDateLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        JLabel appTimeLabel = new JLabel("Time");
        appTimeLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        JLabel statusLabel = new JLabel("Status");
        statusLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        JLabel diagnosisLabel = new JLabel("Diagnosis");
        diagnosisLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        JLabel prescriptionLabel = new JLabel("Prescription");
        prescriptionLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        JLabel patientIDLabel = new JLabel("PatientID");
        patientIDLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        JLabel doctorIDLabel = new JLabel("DoctorID");
        doctorIDLabel.setFont(new Font("Roboto",Font.PLAIN,14));

        // define appointment fields
        searchAppTF.setFont(new Font("Roboto",Font.PLAIN,14));
        ridTF.setFont(new Font("Roboto",Font.PLAIN,14));
        ridTF.setPreferredSize(new Dimension( 150,24));
        ridTF.setEnabled(false);

        symptomsTF.setFont(new Font("Roboto",Font.PLAIN,14));
        symptomsTF.setPreferredSize(new Dimension( 150,24));

        appDatePicker.setFont(new Font("Roboto",Font.PLAIN,14));
        appDatePicker.setPreferredSize(new Dimension( 150,24));
        appDatePicker.setEnabled(false);

        appTimeTF.setFont(new Font("Roboto",Font.PLAIN,14));
        appTimeTF.setPreferredSize(new Dimension( 150,24));
        appTimeTF.setEnabled(false);

        statusTF.setFont(new Font("Roboto",Font.PLAIN,14));
        statusTF.setPreferredSize(new Dimension( 150,24));

        diagnosisTF.setFont(new Font("Roboto",Font.PLAIN,14));
        diagnosisTF.setPreferredSize(new Dimension( 150,24));

        prescriptionTF.setFont(new Font("Roboto",Font.PLAIN,14));
        prescriptionTF.setPreferredSize(new Dimension( 150,24));

        patientIDTF.setFont(new Font("Roboto",Font.PLAIN,14));
        patientIDTF.setPreferredSize(new Dimension( 150,24));
        patientIDTF.setEnabled(false);

        doctorIDTF.setFont(new Font("Roboto",Font.PLAIN,14));
        doctorIDTF.setPreferredSize(new Dimension( 150,24));
        doctorIDTF.setEnabled(false);

        // define buttons
        searchRecord.setSize(10,10);

        final JPanel searchAppPanel = new JPanel();
        searchAppPanel.setPreferredSize(new Dimension(340,20));
        GridLayout gridLayout1 = new GridLayout();
        gridLayout1.setHgap(5);
        searchAppPanel.setLayout(gridLayout1);
        searchAppPanel.add(searchAppLabel);
        searchAppPanel.add(searchAppTF);
        searchAppPanel.add(searchRecord);

        final JPanel buttonsAppPanel = new JPanel();
        buttonsAppPanel.setPreferredSize(new Dimension(340,50));
        buttonsAppPanel.setLayout(new FlowLayout());
        buttonsAppPanel.add(updateRecord);

        final JPanel fieldsAppPanel = new JPanel();
        fieldsAppPanel.setPreferredSize(new Dimension(340,600));
        GridLayout gridLayout2 = new GridLayout(18,1);
        gridLayout2.setVgap(10);
        fieldsAppPanel.setLayout(gridLayout2);

        fieldsAppPanel.add(ridLabel);
        fieldsAppPanel.add(ridTF);
        fieldsAppPanel.add(symptomsLabel);
        fieldsAppPanel.add(symptomsTF);
        fieldsAppPanel.add(appDateLabel);
        fieldsAppPanel.add(appDatePicker);
        fieldsAppPanel.add(appTimeLabel);
        fieldsAppPanel.add(appTimeTF);
        fieldsAppPanel.add(statusLabel);
        fieldsAppPanel.add(statusTF);
        fieldsAppPanel.add(diagnosisLabel);
        fieldsAppPanel.add(diagnosisTF);
        fieldsAppPanel.add(prescriptionLabel);
        fieldsAppPanel.add(prescriptionTF);
        fieldsAppPanel.add(patientIDLabel);
        fieldsAppPanel.add(patientIDTF);
        fieldsAppPanel.add(doctorIDLabel);
        fieldsAppPanel.add(doctorIDTF);

        upcomingList = new JList<>(getUpcomingAppointmentsListModel());
        upcomingList.setFont(new Font("Roboto",Font.PLAIN,12));
        upcomingList.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.bluish, 2),
                        "Upcoming Appointments",TitledBorder.CENTER,TitledBorder.CENTER));

        missedList = new JList<>(getMissedAppointmentsListModel());
        missedList.setFont(new Font("Roboto",Font.BOLD,12));
        //missedList.setPreferredSize(new Dimension(240,200));
        missedList.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.mangoOrange, 2),
                        "Missed Appointments",TitledBorder.CENTER,TitledBorder.CENTER));


        // appointment list panel
        JPanel appointmentListPanel = new JPanel();
        appointmentListPanel.setLayout(new BorderLayout());
        appointmentListPanel.setPreferredSize(new Dimension(280,600));
        appointmentListPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.fireFly, 3),
                        "Appointments List"));

        appointmentListPanel.add(new JScrollPane(upcomingList,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        appointmentListPanel.add(new JScrollPane(missedList,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.SOUTH);

        // appointment list panel
        JPanel recordPanel = new JPanel();
        recordPanel.setPreferredSize(new Dimension(320,600));
        recordPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.fireFly, 3),
                        "Update Record"));
        BorderLayout borderLayout1 = new BorderLayout();
        borderLayout1.setHgap(10);
        borderLayout1.setVgap(20);
        recordPanel.setLayout(borderLayout1);
        recordPanel.add(searchAppPanel,BorderLayout.NORTH);
        recordPanel.add(fieldsAppPanel,BorderLayout.CENTER);
        recordPanel.add(buttonsAppPanel,BorderLayout.SOUTH);

        // left panel container
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout());
        leftPanel.setPreferredSize(new Dimension(620,600));
        leftPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.fireFly, 3),
                        "Manage Appointments", TitledBorder.CENTER,TitledBorder.TOP));

        leftPanel.add(appointmentListPanel);
        leftPanel.add(recordPanel);



        this.add(leftPanel,BorderLayout.CENTER);

        this.setTitle("Doctor");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280,720);
        this.setVisible(true);

        actionListeners();
    }

    public void actionListeners(){
        populateLists(upcomingList);

        populateLists(missedList);

        // search record
        searchRecord.addActionListener(e -> {
            int recordID;
            if (!searchAppTF.getText().isEmpty()){
                if (Validate.rid(searchAppTF.getText()) && RecordDB.recordExists(Integer.parseInt(searchAppTF.getText()))){
                    recordID = Integer.parseInt(searchAppTF.getText());
                    Record record = null;

                    for (AppointmentRecord r : records){
                        if (recordID == r.getRecord().getRecordID()) {
                            record = r.getRecord();
                            break;
                        }
                    }

                    if (record != null){
                        ridTF.setText(recordID + "");
                        ridTF.setEnabled(false);
                        symptomsTF.setText(record.getSymptoms());
                        modelApp.setValue(record.getAppDate());
                        modelApp.setSelected(true);
                        appTimeTF.setText(record.getAppTime() + "");
                        statusTF.setSelectedItem(record.getStatus() + "");
                        diagnosisTF.setText(record.getDiagnosis());
                        prescriptionTF.setText(record.getPrescription());
                        patientIDTF.setText(record.getPatientID() + "");

                        String doctorName = "";
                        for (String name : getDoctorsNames()) {
                            if (name.substring(0, 3).equals(record.getDoctorID() + "")){
                                doctorName = name;
                                break;
                            }
                        }
                        doctorIDTF.setText(doctorName);
                    }
                    else
                        JOptionPane.showMessageDialog(this,"You have no appointment for this record",
                                "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(this,"Record not found",
                            "Information",JOptionPane.INFORMATION_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(this,"Please enter a record ID",
                        "Error", JOptionPane.ERROR_MESSAGE);
        });

        // update record
        updateRecord.addActionListener(e -> {
            Record record;
            ArrayList<String> validationMessage = new ArrayList<>();

            int recordID = Integer.parseInt(ridTF.getText());

            record = RecordDB.getRecord(recordID);
            if (record != null){
                try {
                    // validate and store record id
                    if (Validate.rid(String.valueOf(recordID)) && recordID == record.getRecordID()) {

                        // check symptoms text field then store
                        if (!symptomsTF.getText().isEmpty())
                            record.setSymptoms(symptomsTF.getText());
                        else
                            validationMessage.add("Symptoms");

                        // validate and store appointment date
                        if (Validate.date(appDatePicker.getJFormattedTextField().getText()))
                            record.setAppDate(Date.valueOf(appDatePicker.getJFormattedTextField().getText()));
                        else
                            validationMessage.add("Appointment Date");

                        // validate and store appointment time
                        if (Validate.time(appTimeTF.getText()))
                            record.setAppTime(Time.valueOf(appTimeTF.getText()));
                        else
                            validationMessage.add("Appointment Time");

                        // validate and store status value ( 0 = unprocessed and 1 = processed by the doctor)
                        if (statusTF.getSelectedIndex() == 2)
                            record.setStatus(Integer.parseInt(statusTF.getSelectedItem().toString()));
                        else
                            validationMessage.add("Make sure STATUS is set to [1]");

                        // check symptoms text field then store
                        if (!diagnosisTF.getText().isEmpty())
                            record.setDiagnosis(diagnosisTF.getText());
                        else
                            validationMessage.add("Diagnosis");

                        // check symptoms text field then store
                        if (!prescriptionTF.getText().isEmpty())
                            record.setPrescription(prescriptionTF.getText());
                        else
                            validationMessage.add("Prescription");

                        // validate and store the patient ID
                        if (Validate.id(patientIDTF.getText()) && patientIDTF.getText().length() == 5){
                            if (PatientDB.patientExists(Integer.parseInt(patientIDTF.getText())))
                                record.setPatientID(Integer.parseInt(patientIDTF.getText()));
                            else
                                JOptionPane.showMessageDialog(this,"Patient with this ID does not exists",
                                        "Error",JOptionPane.ERROR_MESSAGE);
                        }
                        else
                            validationMessage.add("PatientID");

                        // validate and store doctorID
                        if (!doctorIDTF.getText().isEmpty()){
                            String doctorID = doctorIDTF.getText();
                            doctorID = doctorID.substring(0,3);

                            if (Validate.id(doctorID) && DoctorDB.doctorExists(Integer.parseInt(doctorID))) {
                                record.setDoctorID(Integer.parseInt(doctorID));
                            }
                            else
                                JOptionPane.showMessageDialog(this,"Doctor with this ID does not exists",
                                        "Error",JOptionPane.ERROR_MESSAGE);
                        }
                        else
                            validationMessage.add("DoctorID");
                    }
                    else
                        JOptionPane.showMessageDialog(this,"Invalid RecordID or Record with this ID already exists",
                                "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception exception){
                    JOptionPane.showMessageDialog(this, exception.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Record with this ID does not exists",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

            // update the record only if the information is valid
            if (validationMessage.isEmpty())
                if (RecordDB.update(record, recordID)) {
                    JOptionPane.showMessageDialog(this, "Successfully updated an Appointment",
                            "Success", JOptionPane.INFORMATION_MESSAGE);

                }
                else
                    JOptionPane.showMessageDialog(this,"Failed to insert anAppointment",
                            "Error",JOptionPane.ERROR_MESSAGE);
            else {
                StringBuilder message = new StringBuilder("Please check the following fields and correct:");
                for (String s : validationMessage){
                    message.append("\n- ").append(s);
                }
                JOptionPane.showMessageDialog(this,message,"Error",JOptionPane.ERROR_MESSAGE);
                validationMessage.clear();
            }
        });
    }

    private void populateLists(JList<String> upcomingList) {
        upcomingList.addListSelectionListener(e -> {
            int index = upcomingList.getSelectedIndex();

            records = RecordDB.getAppointmentRecords(doctorID);

            if (records != null){
                Record record = records.get(index).getRecord();

                ridTF.setText(record.getRecordID() + "");
                symptomsTF.setText(record.getSymptoms());
                modelApp.setValue(record.getAppDate());
                modelApp.setSelected(true);
                appTimeTF.setText(record.getAppTime() + "");
                statusTF.setSelectedItem(record.getStatus() + "");
                diagnosisTF.setText(record.getDiagnosis());
                prescriptionTF.setText(record.getPrescription());
                patientIDTF.setText(record.getPatientID() + "");

                String doctorName = "";
                for (String name : getDoctorsNames()) {
                    if (name.substring(0, 3).equals(record.getDoctorID() + "")){
                        doctorName = name;
                        break;
                    }
                }
                doctorIDTF.setText(doctorName);
            }
        });
    }

    public DefaultListModel<String> getUpcomingAppointmentsListModel(){
        records = RecordDB.getAppointmentRecords(getDoctorID());
        Record record;
        String firstname;
        String lastname;

        DefaultListModel<String> listModel = new DefaultListModel<>();
        if (records != null){
            // get today's date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
            LocalDate today = LocalDate.now();
            String date = formatter.format(today);

            LocalTime now = LocalTime.now();
            Time time = Time.valueOf(timeFormatter.format(now));

            // populate the list model with today's appointments
            for (AppointmentRecord appRecord : records){
                record = appRecord.getRecord();
                firstname = appRecord.getFirstname();
                lastname = appRecord.getLastname();

                if (date.equals(String.valueOf(record.getAppDate())) && time.before(record.getAppTime()))
                    listModel.addElement("Meeting " + firstname.charAt(0) + " " + lastname + " at " + record.getAppTime());
            }

            // let the doctor know that today he has no appointments
            if (listModel.isEmpty())
                listModel.addElement("You have no appointments today.");
        }
        else {
            listModel.addElement("You have no appointments.");
        }
        return listModel;
    }

    public DefaultListModel<String> getMissedAppointmentsListModel(){
        records = RecordDB.getAppointmentRecords(getDoctorID());
        Record record;
        String firstname;
        String lastname;

        DefaultListModel<String> listModel = new DefaultListModel<>();
        if (records != null){
            // get today's date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
            LocalDate today = LocalDate.now();
            Date date = Date.valueOf(formatter.format(today));

            LocalTime now = LocalTime.now();
            Time time = Time.valueOf(timeFormatter.format(now));

            // populate the list model with today's appointments
            for (AppointmentRecord appRecord : records){
                record = appRecord.getRecord();
                firstname = appRecord.getFirstname();
                lastname = appRecord.getLastname();

                if (date.after(record.getAppDate()))
                    listModel.addElement(record.getAppDate() + " " + record.getAppTime() + " with " + firstname.charAt(0) + " " + lastname);
                else if (date.equals(record.getAppDate()) && time.after(record.getAppTime()))
                    listModel.addElement(record.getAppDate() + " " + record.getAppTime() + " with " + firstname.charAt(0) + " " + lastname);
            }

            // let the doctor know that today he has no appointments
            if (listModel.isEmpty())
                listModel.addElement("You have not missed an appointments.");
        }
        else {
            listModel.addElement("You have not missed an appointments.");
        }
        return listModel;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }
    public ArrayList<String> getDoctorsNames(){
        return getStrings();
    }

    static ArrayList<String> getStrings() {
        ArrayList<Object> employees = DoctorDB.getDoctors();

        ArrayList<String> names = new ArrayList<>();
        names.add("Please Select"); // default option

        Employee employee;
        assert employees != null;
        for (Object o : employees) {
            employee = (Employee) o;
            names.add(employee.getId() + ": " + employee.getTitle() +
                    " " + employee.getFirstname() + " " + employee.getLastname());
        }
        return names;
    }
}
