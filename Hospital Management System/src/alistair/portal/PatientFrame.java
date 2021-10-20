package alistair.portal;

import alistair.business.*;
import alistair.business.Record;
import alistair.data.DoctorDB;
import alistair.data.PatientDB;
import alistair.data.RecordDB;
import alistair.data.UserDB;
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
import java.util.*;

public class PatientFrame extends JFrame {
    private Patient patient;

    private int patientID;

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    private static String defaultQuery = "";
    private static ResultSetTableModel tableModel;

    private ArrayList<Record> records;
    private final JList<String> upcomingList;

    // patient text fields declaration
    private final JTextField pidTF = new JTextField();
    private final JTextField titleTF = new JTextField();
    private final JTextField firstnameTF = new JTextField();
    private final JTextField lastnameTF = new JTextField();

    // date picker
    private final UtilDateModel model = new UtilDateModel();
    private final JDatePanelImpl datePanel = new JDatePanelImpl(model,new Properties());
    private final JDatePickerImpl dobPicker = new JDatePickerImpl(datePanel,new DateFormatter());

    //private final JTextField dobTF = new JTextField();
    private final JTextField genderTF = new JTextField();
    private final JTextField maritalStatusTF = new JTextField();
    private final JTextField phoneTF = new JTextField();
    private final JTextField emailTF = new JTextField();
    private final JTextField homeLanguageTF = new JTextField();
    private final JTextField nationalityTF = new JTextField();
    private final JTextField streetAddressTF = new JTextField();
    private final JTextField suburbTF = new JTextField();
    private final JTextField cityTF = new JTextField();
    private final JTextField postCodeTF = new JTextField();
    private final JTextField regDateTF = new JTextField();

    private final JTextField password = new JTextField();
    private final JButton changePassword = new JButton("Change Password");

    // patient buttons
    private final JButton updatePatient = new JButton("Update");

    // display buttons
    private final JButton signOut = new JButton("Sign out");

    public PatientFrame(int patientID){
        setPatientID(patientID);
        defaultQuery = "SELECT * FROM records WHERE patientID = " + getPatientID();
        // patient labels declaration
        final JLabel idLabel = new JLabel("Patient ID");
        final JLabel titleLabel = new JLabel("Title");
        final JLabel firstnameLabel = new JLabel("First name");
        final JLabel lastnameLabel = new JLabel("Last name");
        final JLabel dobLabel = new JLabel("Date of Birth");
        final JLabel genderLabel = new JLabel("Gender");
        final JLabel maritalStatusLabel = new JLabel("Marital Status");
        final JLabel phoneLabel = new JLabel("Phone");
        final JLabel emailLabel = new JLabel("E-mail");
        final JLabel homeLanguageLabel = new JLabel("Home Language");
        final JLabel nationalityLabel = new JLabel("Nationality");
        final JLabel streetAddressLabel = new JLabel("Street Address");
        final JLabel suburbLabel = new JLabel("Suburb");
        final JLabel cityLabel = new JLabel("City");
        final JLabel postCodeLabel = new JLabel("Post Code");
        final JLabel regDateLabel = new JLabel("Reg Date");

        // define patient labels
        idLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        titleLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        firstnameLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        lastnameLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        dobLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        genderLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        maritalStatusLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        phoneLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        emailLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        homeLanguageLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        nationalityLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        streetAddressLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        suburbLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        cityLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        postCodeLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        regDateLabel.setFont(new Font("Roboto",Font.PLAIN,14));

        // define patient fields
        pidTF.setFont(new Font("Roboto",Font.PLAIN,14));
        titleTF.setFont(new Font("Roboto",Font.PLAIN,14));
        firstnameTF.setFont(new Font("Roboto",Font.PLAIN,14));
        lastnameTF.setFont(new Font("Roboto",Font.PLAIN,14));
        dobPicker.setFont(new Font("Roboto",Font.PLAIN,14));
        genderTF.setFont(new Font("Roboto",Font.PLAIN,14));
        maritalStatusTF.setFont(new Font("Roboto",Font.PLAIN,14));
        phoneTF.setFont(new Font("Roboto",Font.PLAIN,14));
        emailTF.setFont(new Font("Roboto",Font.PLAIN,14));
        homeLanguageTF.setFont(new Font("Roboto",Font.PLAIN,14));
        nationalityTF.setFont(new Font("Roboto",Font.PLAIN,14));
        streetAddressTF.setFont(new Font("Roboto",Font.PLAIN,14));
        suburbTF.setFont(new Font("Roboto",Font.PLAIN,14));
        cityTF.setFont(new Font("Roboto",Font.PLAIN,14));
        postCodeTF.setFont(new Font("Roboto",Font.PLAIN,14));

        regDateTF.setFont(new Font("Roboto",Font.PLAIN,14));
        regDateTF.setEnabled(false);

        password.setFont(new Font("Roboto",Font.PLAIN,14));
        changePassword.setFont(new Font("Roboto",Font.PLAIN,14));
        changePassword.setBackground(Color.mangoOrange);

        //signOut.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.mangoOrange, 2), "Log Out"));
        signOut.setForeground(Color.mangoOrange);
        signOut.setBackground(Color.fireFly);
        signOut.setFont(new Font("Roboto",Font.BOLD,14));
        signOut.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // container panels
        final JPanel buttonsPatientPanel = new JPanel();
        buttonsPatientPanel.setPreferredSize(new Dimension(340,50));
        buttonsPatientPanel.setLayout(new FlowLayout());
        buttonsPatientPanel.add(updatePatient);

        final JPanel fieldsPatientPanel = new JPanel();
        fieldsPatientPanel.setPreferredSize(new Dimension(340,600));
        GridLayout gridLayout = new GridLayout(16,2);
        gridLayout.setVgap(10);
        fieldsPatientPanel.setLayout(gridLayout);

        fieldsPatientPanel.add(idLabel);
        fieldsPatientPanel.add(pidTF);
        fieldsPatientPanel.add(titleLabel);
        fieldsPatientPanel.add(titleTF);
        fieldsPatientPanel.add(firstnameLabel);
        fieldsPatientPanel.add(firstnameTF);
        fieldsPatientPanel.add(lastnameLabel);
        fieldsPatientPanel.add(lastnameTF);
        fieldsPatientPanel.add(dobLabel);
        fieldsPatientPanel.add(dobPicker);
        fieldsPatientPanel.add(genderLabel);
        fieldsPatientPanel.add(genderTF);
        fieldsPatientPanel.add(maritalStatusLabel);
        fieldsPatientPanel.add(maritalStatusTF);
        fieldsPatientPanel.add(phoneLabel);
        fieldsPatientPanel.add(phoneTF);
        fieldsPatientPanel.add(emailLabel);
        fieldsPatientPanel.add(emailTF);
        fieldsPatientPanel.add(homeLanguageLabel);
        fieldsPatientPanel.add(homeLanguageTF);
        fieldsPatientPanel.add(nationalityLabel);
        fieldsPatientPanel.add(nationalityTF);
        fieldsPatientPanel.add(streetAddressLabel);
        fieldsPatientPanel.add(streetAddressTF);
        fieldsPatientPanel.add(suburbLabel);
        fieldsPatientPanel.add(suburbTF);
        fieldsPatientPanel.add(cityLabel);
        fieldsPatientPanel.add(cityTF);
        fieldsPatientPanel.add(postCodeLabel);
        fieldsPatientPanel.add(postCodeTF);
        fieldsPatientPanel.add(regDateLabel);
        fieldsPatientPanel.add(regDateTF);

        final JPanel patientPanel = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(10);
        borderLayout.setVgap(20);
        patientPanel.setLayout(borderLayout);
        patientPanel.setPreferredSize(new Dimension(340,600));
        patientPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.fireFly, 3), "Patient"));
        patientPanel.add(fieldsPatientPanel,BorderLayout.CENTER);
        patientPanel.add(buttonsPatientPanel,BorderLayout.SOUTH);
        
        final JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setPreferredSize(new Dimension(550,600));
        displayPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.fireFly, 3), "Records"));

        // setting up table to display the results
        tableModel = new ResultSetTableModel(defaultQuery);
        JTable table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // panel for showing results and updates
        final JPanel containerPanel = new JPanel();
        GridLayout gridLayout3 = new GridLayout(1,2);
        gridLayout3.setVgap(10);
        containerPanel.setLayout(gridLayout3);
        containerPanel.setPreferredSize(new Dimension(550,100));

        // panel for query buttons
        final JPanel displayButtonsPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setHgap(5);
        flowLayout.setVgap(20);
        displayButtonsPanel.setLayout(flowLayout);
        displayButtonsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.fireFly, 3), "Display Buttons"));
        displayButtonsPanel.add(signOut);

        // panel for updates from the doctor
        final JPanel displayUpdatesPanel = new JPanel();
        GridLayout  layout = new GridLayout(2,1);
        layout.setVgap(10);
        displayUpdatesPanel.setLayout(layout);
        displayUpdatesPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.fireFly, 3), "Set Password"));

        displayUpdatesPanel.add(password);
        displayUpdatesPanel.add(changePassword);

        containerPanel.add(displayButtonsPanel);
        containerPanel.add(displayUpdatesPanel);

        displayPanel.add(new JScrollPane(table,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS),BorderLayout.CENTER);
        displayPanel.add(containerPanel,BorderLayout.SOUTH);

        upcomingList = new JList<>(getUpcomingAppointmentsListModel());
        upcomingList.setFont(new Font("Roboto",Font.PLAIN,12));
        upcomingList.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.bluish, 2),
                        "Upcoming Appointments", TitledBorder.CENTER,TitledBorder.CENTER));

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

        final JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(700,600));
        leftPanel.add(patientPanel,BorderLayout.CENTER);
        leftPanel.add(appointmentListPanel,BorderLayout.WEST);

        final JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout());
        rightPanel.setPreferredSize(new Dimension(580,640));
        rightPanel.add(displayPanel,BorderLayout.EAST);

        // set top heading label
        final JLabel topLabel = new JLabel("Hospital Management System");
        topLabel.setFont(new Font("Roboto",Font.BOLD,24));
        topLabel.setForeground(Color.bluish);
        topLabel.setBackground(null);
        topLabel.setSize(720,32);
        topLabel.setHorizontalAlignment(JLabel.CENTER);
        topLabel.setVerticalAlignment(JLabel.CENTER);

        this.add(topLabel, BorderLayout.NORTH);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);

        this.setTitle("Nurse");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(1280,720);
        this.setVisible(true);

        patientButtonActions();
        queryButtonsActions();
        populateFields();
    }

    public void patientButtonActions(){
        changePassword.addActionListener(e -> {
            if (!password.getText().isEmpty()){
                String pass = password.getText();
                User user = new User();
                user.setPassword(pass);
                if (UserDB.updatePassword(user,getPatientID())){
                    password.setText("");
                    JOptionPane.showMessageDialog(this, "Successfully changed a password!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(this, "Failed to changed password",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        updatePatient.addActionListener(e -> {
            patient = Patient.getInstance();
            ArrayList<String> validationMessage = new ArrayList<>();

            try {
                // validate and store a title
                if (!titleTF.getText().isEmpty()) {
                    patient.setTitle(titleTF.getText());
                } else
                    validationMessage.add("Title");

                // validate and store a firstname
                if (Validate.firstName(firstnameTF.getText()) && !firstnameTF.getText().isEmpty())
                    patient.setFirstname(firstnameTF.getText());
                else
                    validationMessage.add("Firstname");

                // validate and store lastname
                if (Validate.lastName(lastnameTF.getText()) && !lastnameTF.getText().isEmpty())
                    patient.setLastname(lastnameTF.getText());
                else
                    validationMessage.add("Lastname");

                // validate and store date of birth
                if (Validate.date(dobPicker.getJFormattedTextField().getText())
                        && !dobPicker.getJFormattedTextField().getText().isEmpty())
                    patient.setDateOfBirth(Date.valueOf(dobPicker.getJFormattedTextField().getText()));
                else
                    validationMessage.add("Date of Birth");

                // validate and store gender
                if (Validate.word(genderTF.getText()))
                    patient.setGender(genderTF.getText());
                else
                    validationMessage.add("Gender");

                // validate and store marital status
                if (Validate.word(maritalStatusTF.getText()) && !maritalStatusTF.getText().equalsIgnoreCase("please select"))
                    patient.setMaritalStatus(maritalStatusTF.getText());
                else
                    validationMessage.add("Marital Status");

                // validate and store phone
                if (Validate.phone(phoneTF.getText()) && !phoneTF.getText().isEmpty())
                    patient.setPhone(phoneTF.getText());
                else
                    validationMessage.add("Phone");

                // validate and store email
                if (Validate.email(emailTF.getText()) && !emailTF.getText().isEmpty())
                    patient.setEmail(emailTF.getText());
                else
                    validationMessage.add("Email");

                // validate and store home language
                if (Validate.word(homeLanguageTF.getText()))
                    patient.setLanguage(homeLanguageTF.getText());
                else
                    validationMessage.add("Home Language");

                // validate and store nationality
                if (Validate.city(nationalityTF.getText()))
                    patient.setNationality(nationalityTF.getText());
                else
                    validationMessage.add("Nationality");

                // validate and store street address
                if (Validate.streetAddress(streetAddressTF.getText()) && !streetAddressTF.getText().isEmpty())
                    patient.setStreetAddress(streetAddressTF.getText());
                else
                    validationMessage.add("Street address");

                // validate and store suburb
                if (Validate.suburb(suburbTF.getText()) && !suburbTF.getText().isEmpty())
                    patient.setSuburb(suburbTF.getText());
                else
                    validationMessage.add("Suburb");

                // validate and store city
                if (Validate.city(cityTF.getText()) && !cityTF.getText().isEmpty())
                    patient.setCity(cityTF.getText());
                else
                    validationMessage.add("City");

                // validate and store post code
                if (Validate.postCode(postCodeTF.getText()) && !postCodeTF.getText().isEmpty())
                    patient.setPostCode(postCodeTF.getText());
                else
                    validationMessage.add("Post Code");

            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(this,exception.getStackTrace(),
                        "Error!",JOptionPane.ERROR_MESSAGE);
            }

            if (validationMessage.isEmpty())
                if (PatientDB.patientExists(Integer.parseInt(pidTF.getText()))){
                    if (PatientDB.updatePatient(patient,Integer.parseInt(pidTF.getText()))) {
                        JOptionPane.showMessageDialog(this, "Successfully updated a PATIENT",
                                "Success", JOptionPane.INFORMATION_MESSAGE);

                        tableModel.setQuery("SELECT * FROM patients");
                    }
                    else
                        JOptionPane.showMessageDialog(this,"Failed to update a Patient",
                                "Error",JOptionPane.ERROR_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(this,"Patient does not exists",
                            "Warning",JOptionPane.WARNING_MESSAGE);
                }
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

    public void queryButtonsActions(){
        // logout and close the frame
        signOut.addActionListener(e -> {
            int x = JOptionPane.showConfirmDialog(this,
                    "Are sure you want to sign out?","Confirm",JOptionPane.YES_NO_OPTION);
            if (x == 0)
                this.dispose();
        });
    }
    public DefaultListModel<String> getUpcomingAppointmentsListModel(){
        records = RecordDB.getPatientRecords(getPatientID());
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

            Employee doctor;
            // populate the list model with today's appointments
            for (Record appointment : records){
                doctor = DoctorDB.getDoctor(appointment.getDoctorID());
                firstname = doctor.getFirstname();
                lastname = doctor.getLastname();

                if (date.equals(String.valueOf(appointment.getAppDate())) && time.before(appointment.getAppTime()))
                    listModel.addElement("Meeting Dr. " + firstname.charAt(0) + " " + lastname + " at " + appointment.getAppTime());
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
    public void populateFields(){
        patient = PatientDB.getPatient(getPatientID());

        assert patient != null;
        pidTF.setText(patient.getId() + "");
        pidTF.setEnabled(false);
        titleTF.setText(patient.getTitle());
        firstnameTF.setText(patient.getFirstname());
        lastnameTF.setText(patient.getLastname());
        model.setValue(patient.getDateOfBirth());
        model.setSelected(true);
        genderTF.setText(patient.getGender());
        maritalStatusTF.setText(patient.getMaritalStatus());
        phoneTF.setText(patient.getPhone());
        emailTF.setText(patient.getEmail());
        homeLanguageTF.setText(patient.getLanguage());
        nationalityTF.setText(patient.getNationality());
        streetAddressTF.setText(patient.getStreetAddress());
        suburbTF.setText(patient.getSuburb());
        cityTF.setText(patient.getCity());
        postCodeTF.setText(patient.getPostCode());
        regDateTF.setText(patient.getRegDate().toString());
    }
}
