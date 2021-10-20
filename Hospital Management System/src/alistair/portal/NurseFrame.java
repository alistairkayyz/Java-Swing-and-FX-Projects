package alistair.portal;

import alistair.business.Patient;
import alistair.business.Record;
import alistair.business.ResultSetTableModel;
import alistair.business.User;
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
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static alistair.portal.DoctorFrame.getStrings;

public class NurseFrame extends JFrame {
    private Patient patient;
    private Record record;

    private static final String defaultQuery = "SELECT * FROM records";
    private static ResultSetTableModel tableModel;

    private final String[] titles = {"Please Select","Mr.", "Mrs.", "Miss", "Ms.", "Mx.",
                    "Sir.", "Dr.", "Cllr.", "Lady", "Lord"};
    private final String[] genders = {"Please Select", "Male", "Female", "Other"};
    private final String[] maritalStatus = {"Please Select","Single", "Married", "Separated", "Widowed", "Divorced"};
    private final String[] times = {"Please Select","15:00:00","16:00:00","17:00:00","18:00:00","19:00:00","20:00:00",
            "21:00:00","22:00:00","23:00:00"};
    private final String[] countries = {"Please Select","South Africa","Ghana","Zimbabwe","Zambia","Nigeria","Other"};
    private final String[] languages = {"Please Select", "Afrikaans","English","Sepedi","Setswana","Sesotho","isiZulu",
            "isiNdebele", "isiXhosa","Xitsonga","Tshivenda", "Other"};

    // patient text fields declaration
    private final JTextField pidTF = new JTextField();
    private final JComboBox titleTF = new JComboBox(titles);
    private final JTextField firstnameTF = new JTextField();
    private final JTextField lastnameTF = new JTextField();

    // date picker
    private final UtilDateModel model = new UtilDateModel();
    private final JDatePanelImpl datePanel = new JDatePanelImpl(model,new Properties());
    private final JDatePickerImpl dobPicker = new JDatePickerImpl(datePanel,new DateFormatter());

    //private final JTextField dobTF = new JTextField();
    private final JComboBox genderTF = new JComboBox(genders);
    private final JComboBox maritalStatusTF = new JComboBox(maritalStatus);
    private final JTextField phoneTF = new JTextField();
    private final JTextField emailTF = new JTextField();
    private final JComboBox homeLanguageTF = new JComboBox(languages);
    private final JComboBox nationalityTF = new JComboBox(countries);
    private final JTextField streetAddressTF = new JTextField();
    private final JTextField suburbTF = new JTextField();
    private final JTextField cityTF = new JTextField();
    private final JTextField postCodeTF = new JTextField();
    private final JTextField regDateTF = new JTextField();
    private final JTextField searchPatientTF = new JTextField();

    // appointment text fields declaration
    private final JTextField searchAppTF = new JTextField();
    private final JTextField ridTF = new JTextField();
    private final JTextField symptomsTF = new JTextField();

    private final UtilDateModel modelApp = new UtilDateModel();
    private final JDatePanelImpl datePanelApp = new JDatePanelImpl(modelApp,new Properties());
    private final JDatePickerImpl appDatePicker = new JDatePickerImpl(datePanelApp,new DateFormatter());

    private final JComboBox<String> appTimeTF = new JComboBox<>(times);
    private final JComboBox statusTF = new JComboBox(new String[]{"Please Select", "0", "1"});
    private final JTextField diagnosisTF = new JTextField();
    private final JTextField prescriptionTF = new JTextField();
    private final JTextField patientIDTF = new JTextField();
    private  final JComboBox doctorIDTF = new JComboBox(getDoctorsNames().toArray());

    // patient buttons
    private final JButton addPatient = new JButton("Add");
    private final JButton deletePatient = new JButton("Delete");
    private final JButton updatePatient = new JButton("Update");
    private final JButton searchPatient = new JButton("Search");
    private final JButton newPatient = new JButton("New");

    // record buttons
    private final JButton addRecord = new JButton("Add");
    private final JButton deleteRecord = new JButton("Delete");
    private final JButton updateRecord = new JButton("Update");
    private final JButton searchRecord = new JButton("Search");
    private final JButton newRecord = new JButton("New");

    // display buttons
    private final JButton getAllRecords = new JButton("Get All Records");
    private final JButton getAllPatients = new JButton("Get All Patients");
    private final JButton getPatientRecords = new JButton("Get Records");
    private final JButton signOut = new JButton("Sign out");
    private final JTextField enterPatientIDTF = new JTextField("Enter PatientID");

    public NurseFrame(){
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
        final JLabel searchPatientLabel = new JLabel("Enter patientID:");

        // appointment labels declaration
        final JLabel searchAppLabel = new JLabel("Enter RecordID:");
        final JLabel ridLabel = new JLabel("Record ID");
        final JLabel symptomsLabel = new JLabel("Symptoms");
        final JLabel appDateLabel = new JLabel("Date");
        final JLabel appTimeLabel = new JLabel("Time");
        final JLabel statusLabel = new JLabel("Status");
        final JLabel diagnosisLabel = new JLabel("Diagnosis");
        final JLabel prescriptionLabel = new JLabel("Prescription");
        final JLabel patientIDLabel = new JLabel("PatientID");
        final JLabel doctorIDLabel = new JLabel("DoctorID");
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
        searchPatientLabel.setFont(new Font("Roboto",Font.PLAIN,14));

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

        searchPatientTF.setFont(new Font("Roboto",Font.PLAIN,14));
        searchPatientTF.setPreferredSize(new Dimension( 150,24));

        // define appointment labels
        searchAppLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        ridLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        //ridTF.setEnabled(false);
        symptomsLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        appDateLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        appTimeLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        statusLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        diagnosisLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        prescriptionLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        patientIDLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        doctorIDLabel.setFont(new Font("Roboto",Font.PLAIN,14));

        // define appointment fields
        searchAppTF.setFont(new Font("Roboto",Font.PLAIN,14));
        ridTF.setFont(new Font("Roboto",Font.PLAIN,14));
        ridTF.setPreferredSize(new Dimension( 150,24));

        symptomsTF.setFont(new Font("Roboto",Font.PLAIN,14));
        symptomsTF.setPreferredSize(new Dimension( 150,24));

        appDatePicker.setFont(new Font("Roboto",Font.PLAIN,14));
        appDatePicker.setPreferredSize(new Dimension( 150,24));

        appTimeTF.setFont(new Font("Roboto",Font.PLAIN,14));
        appTimeTF.setPreferredSize(new Dimension( 150,24));

        statusTF.setFont(new Font("Roboto",Font.PLAIN,14));
        statusTF.setPreferredSize(new Dimension( 150,24));

        diagnosisTF.setFont(new Font("Roboto",Font.PLAIN,14));
        diagnosisTF.setPreferredSize(new Dimension( 150,24));

        prescriptionTF.setFont(new Font("Roboto",Font.PLAIN,14));
        prescriptionTF.setPreferredSize(new Dimension( 150,24));

        patientIDTF.setFont(new Font("Roboto",Font.PLAIN,14));
        patientIDTF.setPreferredSize(new Dimension( 150,24));

        doctorIDTF.setFont(new Font("Roboto",Font.PLAIN,14));
        doctorIDTF.setPreferredSize(new Dimension( 150,24));

        // define buttons
        searchRecord.setSize(10,10);
        enterPatientIDTF.setSize(10,10);

        //signOut.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.mangoOrange, 2), "Log Out"));
        signOut.setForeground(Color.mangoOrange);
        signOut.setBackground(Color.fireFly);
        signOut.setFont(new Font("Roboto",Font.BOLD,14));
        signOut.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // container panels
        final JPanel searchPatientPanel = new JPanel();
        searchPatientPanel.setPreferredSize(new Dimension(340,20));
        GridLayout gLayout = new GridLayout();
        gLayout.setHgap(5);
        searchPatientPanel.setLayout(gLayout);
        searchPatientPanel.add(searchPatientLabel);
        searchPatientPanel.add(searchPatientTF);
        searchPatientPanel.add(searchPatient);

        final JPanel buttonsPatientPanel = new JPanel();
        buttonsPatientPanel.setPreferredSize(new Dimension(340,50));
        buttonsPatientPanel.setLayout(new FlowLayout());
        buttonsPatientPanel.add(newPatient);
        buttonsPatientPanel.add(addPatient);
        buttonsPatientPanel.add(updatePatient);
        buttonsPatientPanel.add(deletePatient);

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
        patientPanel.add(searchPatientPanel,BorderLayout.NORTH);
        patientPanel.add(fieldsPatientPanel,BorderLayout.CENTER);
        patientPanel.add(buttonsPatientPanel,BorderLayout.SOUTH);

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
        buttonsAppPanel.add(newRecord);
        buttonsAppPanel.add(addRecord);
        buttonsAppPanel.add(updateRecord);
        buttonsAppPanel.add(deleteRecord);

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

        final JPanel appPanel = new JPanel();
        BorderLayout borderLayout1 = new BorderLayout();
        borderLayout1.setHgap(10);
        borderLayout1.setVgap(20);
        appPanel.setLayout(borderLayout1);
        appPanel.setPreferredSize(new Dimension(340,400));
        appPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.fireFly, 3), "Appointment"));
        appPanel.add(searchAppPanel,BorderLayout.NORTH);
        appPanel.add(fieldsAppPanel,BorderLayout.CENTER);
        appPanel.add(buttonsAppPanel,BorderLayout.SOUTH);

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
        GridLayout gridLayout3 = new GridLayout();
        gridLayout3.setVgap(10);
        containerPanel.setLayout(gridLayout3);
        containerPanel.setPreferredSize(new Dimension(550,200));

        // panel for query buttons
        final JPanel displayButtonsPanel = new JPanel();
        GridLayout gridLayout4 = new GridLayout(3,2);
        gridLayout4.setHgap(5);
        gridLayout4.setVgap(20);
        displayButtonsPanel.setLayout(gridLayout4);
        displayButtonsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.fireFly, 3), "Display Buttons"));

        displayButtonsPanel.add(getAllPatients);
        displayButtonsPanel.add(getAllRecords);
        displayButtonsPanel.add(enterPatientIDTF);
        displayButtonsPanel.add(getPatientRecords);
        displayButtonsPanel.add(signOut);


        containerPanel.add(displayButtonsPanel);

        displayPanel.add(new JScrollPane(table,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS),BorderLayout.CENTER);
        displayPanel.add(containerPanel,BorderLayout.SOUTH);

        final JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(700,600));
        leftPanel.add(patientPanel,BorderLayout.WEST);
        leftPanel.add(appPanel,BorderLayout.EAST);

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
        recordButtonsActions();
        queryButtonsActions();
    }

    public void patientButtonActions(){
        // clear all fields for new data entry
        newPatient.addActionListener(e -> {
            if (PatientDB.getLastPatientID() < 1)
                pidTF.setText("0");
            else
                pidTF.setText(PatientDB.getLastPatientID() + 1 + "");
            titleTF.setSelectedIndex(0);
            firstnameTF.setText("");
            lastnameTF.setText("");
            model.setValue(Calendar.getInstance().getTime());
            model.setSelected(true);
            genderTF.setSelectedIndex(0);
            maritalStatusTF.setSelectedIndex(0);
            phoneTF.setText("");
            emailTF.setText("");
            homeLanguageTF.setSelectedIndex(0);
            nationalityTF.setSelectedIndex(0);
            streetAddressTF.setText("");
            suburbTF.setText("");
            cityTF.setText("");
            postCodeTF.setText("");
            regDateTF.setText("");
        });

        searchPatient.addActionListener(e -> {
            int id = Integer.parseInt(searchPatientTF.getText());
            if (Validate.id("" + id)){
                if (PatientDB.patientExists(id)){
                    patient = PatientDB.getPatient(id);

                    pidTF.setText((patient != null ? patient.getId() : 0) + "");
                    titleTF.setSelectedItem(patient.getTitle());
                    firstnameTF.setText(patient.getFirstname());
                    lastnameTF.setText(patient.getLastname());
                    model.setValue(patient.getDateOfBirth());
                    model.setSelected(true);
                    genderTF.setSelectedItem(patient.getGender());
                    maritalStatusTF.setSelectedItem(patient.getMaritalStatus());
                    phoneTF.setText(patient.getPhone());
                    emailTF.setText(patient.getEmail());
                    homeLanguageTF.setSelectedItem(patient.getLanguage());
                    nationalityTF.setSelectedItem(patient.getNationality());
                    streetAddressTF.setText(patient.getStreetAddress());
                    suburbTF.setText(patient.getSuburb());
                    cityTF.setText(patient.getCity());
                    postCodeTF.setText(patient.getPostCode());
                    regDateTF.setText(patient.getRegDate().toString());
                }
                else
                    JOptionPane.showMessageDialog(this,"Patient with this ID does not exists.",
                            "Error",JOptionPane.ERROR_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(this,"Please enter a valid patientID.",
                        "Error",JOptionPane.ERROR_MESSAGE);
        });

        addPatient.addActionListener(e -> {
            patient = Patient.getInstance();
            ArrayList<String> validationMessage = new ArrayList<>();

            try {
                // validate and store a patientID
                if (Validate.id(pidTF.getText()) && !pidTF.getText().isEmpty() && pidTF.getText().length() == 5)
                    patient.setId(Integer.parseInt(pidTF.getText()));
                else
                    validationMessage.add("PatientID");

                // validate and store a title
                if (Arrays.stream(titles).anyMatch(title ->
                        title.equals(Objects.requireNonNull(titleTF.getSelectedItem()).toString()) &&
                                !title.equalsIgnoreCase("please select"))) {
                    patient.setTitle(titleTF.getSelectedItem().toString());
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
                if (Validate.word(Objects.requireNonNull(genderTF.getSelectedItem()).toString())
                        && !genderTF.getSelectedItem().toString().equalsIgnoreCase("please select"))
                    patient.setGender(genderTF.getSelectedItem().toString());
                else
                    validationMessage.add("Gender");

                // validate and store marital status
                if (Validate.word(Objects.requireNonNull(maritalStatusTF.getSelectedItem()).toString())
                        && !maritalStatusTF.getSelectedItem().toString().equalsIgnoreCase("please select"))
                    patient.setMaritalStatus(maritalStatusTF.getSelectedItem().toString());
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
                if (Validate.word(Objects.requireNonNull(homeLanguageTF.getSelectedItem()).toString())
                        && !homeLanguageTF.getSelectedItem().toString().equalsIgnoreCase("please select"))
                    patient.setLanguage(homeLanguageTF.getSelectedItem().toString());
                else
                    validationMessage.add("Home Language");

                // validate and store nationality
                if (Validate.city(Objects.requireNonNull(nationalityTF.getSelectedItem()).toString())
                        && !nationalityTF.getSelectedItem().toString().equalsIgnoreCase("please select"))
                    patient.setNationality(nationalityTF.getSelectedItem().toString());
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

                // get current date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate now = LocalDate.now();
                String date = formatter.format(now);

                // validate and store date
                if (Validate.date(date))
                    patient.setRegDate(Date.valueOf(date));
                else
                    validationMessage.add("Reg Date");

            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(this,exception.getStackTrace(),
                        "Error!",JOptionPane.ERROR_MESSAGE);
            }

            if (validationMessage.isEmpty())
                if (PatientDB.patientExists(Integer.parseInt(pidTF.getText())))
                    JOptionPane.showMessageDialog(this,"Patient with this ID already exists",
                            "Warning",JOptionPane.WARNING_MESSAGE);
                else {
                    if (PatientDB.insertPatient(patient)) {
                        JOptionPane.showMessageDialog(this, "Successfully added a new PATIENT",
                                "Success", JOptionPane.INFORMATION_MESSAGE);

                        User user = new User();
                        user.setId(patient.getId());
                        user.setPassword("default");
                        user.setRole(2);
                        if (UserDB.insert(user))
                            tableModel.setQuery("SELECT * FROM patients");
                    }
                    else
                        JOptionPane.showMessageDialog(this,"Failed to insert a new Patient",
                                "Error",JOptionPane.ERROR_MESSAGE);
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

        updatePatient.addActionListener(e -> {
            patient = Patient.getInstance();
            ArrayList<String> validationMessage = new ArrayList<>();

            try {
                // validate and store a title
                if (Arrays.stream(titles).anyMatch(title ->
                        title.equals(Objects.requireNonNull(titleTF.getSelectedItem()).toString()) &&
                                !title.equalsIgnoreCase("please select"))) {
                    patient.setTitle(titleTF.getSelectedItem().toString());
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
                if (Validate.word(Objects.requireNonNull(genderTF.getSelectedItem()).toString())
                        && !genderTF.getSelectedItem().toString().equalsIgnoreCase("please select"))
                    patient.setGender(genderTF.getSelectedItem().toString());
                else
                    validationMessage.add("Gender");

                // validate and store marital status
                if (Validate.word(Objects.requireNonNull(maritalStatusTF.getSelectedItem()).toString())
                        && !maritalStatusTF.getSelectedItem().toString().equalsIgnoreCase("please select"))
                    patient.setMaritalStatus(maritalStatusTF.getSelectedItem().toString());
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
                if (Validate.word(Objects.requireNonNull(homeLanguageTF.getSelectedItem()).toString())
                        && !homeLanguageTF.getSelectedItem().toString().equalsIgnoreCase("please select"))
                    patient.setLanguage(homeLanguageTF.getSelectedItem().toString());
                else
                    validationMessage.add("Home Language");

                // validate and store nationality
                if (Validate.city(Objects.requireNonNull(nationalityTF.getSelectedItem()).toString())
                        && !nationalityTF.getSelectedItem().toString().equalsIgnoreCase("please select"))
                    patient.setNationality(nationalityTF.getSelectedItem().toString());
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

        deletePatient.addActionListener(e -> {
            if (Validate.id(pidTF.getText())){
                int id = Integer.parseInt(pidTF.getText());

                if (PatientDB.patientExists(id)){
                    if (PatientDB.patientDeleted(id)) {
                        JOptionPane.showMessageDialog(this, "Patient was successfully deleted.",
                                "Success", JOptionPane.INFORMATION_MESSAGE);

                        tableModel.setQuery("SELECT * FROM patients");

                        if (PatientDB.getLastPatientID() < 1)
                            pidTF.setText("0");
                        else
                            pidTF.setText(PatientDB.getLastPatientID() + 1 + "");
                        titleTF.setSelectedIndex(0);
                        firstnameTF.setText("");
                        lastnameTF.setText("");
                        model.setValue(Calendar.getInstance().getTime());
                        model.setSelected(true);
                        genderTF.setSelectedIndex(0);
                        maritalStatusTF.setSelectedIndex(0);
                        phoneTF.setText("");
                        emailTF.setText("");
                        homeLanguageTF.setSelectedIndex(0);
                        nationalityTF.setSelectedIndex(0);
                        streetAddressTF.setText("");
                        suburbTF.setText("");
                        cityTF.setText("");
                        postCodeTF.setText("");
                        regDateTF.setText("");
                    }
                    else
                        JOptionPane.showMessageDialog(this,"Failed to delete patient",
                                "Error",JOptionPane.ERROR_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(this,"Patient with this PatientID does not exists",
                            "Error",JOptionPane.ERROR_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(this,"Please a valid PatientID",
                        "Error",JOptionPane.ERROR_MESSAGE);

        });
    }

    public void recordButtonsActions(){
        ArrayList<String> validationMessage = new ArrayList<>();
        AtomicBoolean doctorHasAppointment = new AtomicBoolean(false);

        newRecord.addActionListener(e -> clearPatientFields());

        // delete a record
        deleteRecord.addActionListener(e -> {
            if (Validate.rid(ridTF.getText())){
                int id = Integer.parseInt(ridTF.getText());

                if (RecordDB.recordExists(id)){
                    if (RecordDB.recordDeleted(id)) {
                        JOptionPane.showMessageDialog(this, "Record was successfully deleted.",
                                "Success", JOptionPane.INFORMATION_MESSAGE);

                        tableModel.setQuery("SELECT * FROM records");
                        clearPatientFields();
                    }
                    else
                        JOptionPane.showMessageDialog(this,"Failed to delete a record",
                                "Error",JOptionPane.ERROR_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(this,"Record with this ID does not exists",
                            "Error",JOptionPane.ERROR_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(this,"Please a valid recordID",
                        "Error",JOptionPane.ERROR_MESSAGE);
        });

        // search record
        searchRecord.addActionListener(e -> {
            int recordID;
            if (!searchAppTF.getText().isEmpty()){
                if (Validate.rid(searchAppTF.getText()) && RecordDB.recordExists(Integer.parseInt(searchAppTF.getText()))){
                    recordID = Integer.parseInt(searchAppTF.getText());
                    record = RecordDB.getRecord(recordID);

                    ridTF.setText(recordID + "");
                    ridTF.setEnabled(false);
                    symptomsTF.setText(record.getSymptoms());
                    modelApp.setValue(record.getAppDate());
                    modelApp.setSelected(true);
                    appTimeTF.setSelectedItem(record.getAppTime() + "");
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
                    doctorIDTF.setSelectedItem(doctorName);
                }
                else
                    JOptionPane.showMessageDialog(this,"Record not found",
                            "Information",JOptionPane.INFORMATION_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(this,"Please enter a record ID",
                        "Error", JOptionPane.ERROR_MESSAGE);
        });

        addRecord.addActionListener(e -> {
            record = Record.getInstance();
            String recordID = ridTF.getText();

            try {
                // validate and store record id
                if (Validate.rid(recordID) && !RecordDB.recordExists(Integer.parseInt(recordID))) {
                    record.setRecordID(Integer.parseInt(ridTF.getText()));
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
                    if (Validate.time(Objects.requireNonNull(appTimeTF.getSelectedItem()).toString()))
                        record.setAppTime(Time.valueOf(appTimeTF.getSelectedItem().toString()));
                    else
                        validationMessage.add("Appointment Time");

                    // validate and store status value ( 0 = unprocessed and 1 = processed by the doctor)
                    if (statusTF.getSelectedIndex() == 1)
                        record.setStatus(Integer.parseInt(Objects.requireNonNull(statusTF.getSelectedItem()).toString()));
                    else
                        validationMessage.add("Make sure STATUS is set to [0]");

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
                    if (doctorIDTF.getSelectedIndex() != 0){
                        String doctorID = Objects.requireNonNull(doctorIDTF.getSelectedItem()).toString();
                        doctorID = doctorID.substring(0,3);

                        if (Validate.id(doctorID) && DoctorDB.doctorExists(Integer.parseInt(doctorID))) {
                            // confirm that the doctor has no appointment at the chosen time
                            // and the schedule is not full
                            int finalDoctorID = Integer.parseInt(doctorID);
                            if (RecordDB.isDoctorScheduleFull(finalDoctorID, appDatePicker.getJFormattedTextField().getText()))
                                JOptionPane.showMessageDialog(this,"Doctor has a full schedule",
                                        "Alert",JOptionPane.WARNING_MESSAGE);
                            else {
                                if (RecordDB.doctorHasAppointment(finalDoctorID,
                                        appDatePicker.getJFormattedTextField().getText(),
                                        appTimeTF.getSelectedItem().toString())){
                                    doctorHasAppointment.set(true);
                                    JOptionPane.showMessageDialog(this,"Doctor already has an appointment at this time",
                                            "Alert",JOptionPane.WARNING_MESSAGE);
                                }
                                else {
                                    record.setDoctorID(finalDoctorID);
                                }
                            }
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

            if (!doctorHasAppointment.get()){
                if (validationMessage.isEmpty())
                    if (RecordDB.insert(record)) {
                        JOptionPane.showMessageDialog(this, "Successfully added a new Appointment",
                                "Success", JOptionPane.INFORMATION_MESSAGE);

                        tableModel.setQuery("SELECT * FROM records");
                    }
                    else
                        JOptionPane.showMessageDialog(this,"Failed to insert a new Appointment",
                                "Error",JOptionPane.ERROR_MESSAGE);
                else {
                    StringBuilder message = new StringBuilder("Please check the following fields and correct:");
                    for (String s : validationMessage){
                        message.append("\n- ").append(s);
                    }
                    JOptionPane.showMessageDialog(this,message,"Error",JOptionPane.ERROR_MESSAGE);
                    validationMessage.clear();
                }
            }
        });

        // update record
        updateRecord.addActionListener(e -> {
            record = Record.getInstance();
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
                        if (Validate.time(Objects.requireNonNull(appTimeTF.getSelectedItem()).toString()))
                            record.setAppTime(Time.valueOf(appTimeTF.getSelectedItem().toString()));
                        else
                            validationMessage.add("Appointment Time");

                        // validate and store status value ( 0 = unprocessed and 1 = processed by the doctor)
                        if (statusTF.getSelectedIndex() == 1)
                            record.setStatus(Integer.parseInt(Objects.requireNonNull(statusTF.getSelectedItem()).toString()));
                        else
                            validationMessage.add("Make sure STATUS is set to [0]");

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
                        if (doctorIDTF.getSelectedIndex() != 0){
                            String doctorID = Objects.requireNonNull(doctorIDTF.getSelectedItem()).toString();
                            doctorID = doctorID.substring(0,3);

                            if (Validate.id(doctorID) && DoctorDB.doctorExists(Integer.parseInt(doctorID))) {
                                // confirm that the doctor has no appointment at the chosen time
                                // and the schedule is not full
                                int finalDoctorID = Integer.parseInt(doctorID);

                                if (RecordDB.isDoctorScheduleFull(finalDoctorID, appDatePicker.getJFormattedTextField().getText()))
                                    JOptionPane.showMessageDialog(this,"Doctor has a full schedule",
                                            "Alert",JOptionPane.WARNING_MESSAGE);
                                else {
                                    String date = appDatePicker.getJFormattedTextField().getText();
                                    String time = appTimeTF.getSelectedItem().toString();

                                    if (RecordDB.getRecordID(date,time,finalDoctorID,recordID) != -1){
                                        doctorHasAppointment.set(true);
                                        JOptionPane.showMessageDialog(this,"Doctor already has an appointment at this time",
                                                "Alert",JOptionPane.WARNING_MESSAGE);
                                    }
                                    else {
                                        record.setDoctorID(finalDoctorID);
                                    }
                                }
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
                doctorHasAppointment.set(true);
            }

            // update the record only if the information is valid
            if (!doctorHasAppointment.get()){
                if (validationMessage.isEmpty())
                    if (RecordDB.update(record, recordID)) {
                        JOptionPane.showMessageDialog(this, "Successfully updated an Appointment",
                                "Success", JOptionPane.INFORMATION_MESSAGE);

                        tableModel.setQuery("SELECT * FROM records");
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
            }

        });
    }

    private void clearPatientFields() {
        ridTF.setEnabled(false);
        if (RecordDB.getLastRecordID() < 0)
            ridTF.setText("1");
        else
            ridTF.setText(RecordDB.getLastRecordID() + 1 + "");
        symptomsTF.setText("");
        modelApp.setValue(Calendar.getInstance().getTime());
        modelApp.setSelected(true);
        appTimeTF.setSelectedIndex(0);
        statusTF.setSelectedIndex(0);
        diagnosisTF.setText("");
        prescriptionTF.setText("");
        patientIDTF.setText("");
        doctorIDTF.setSelectedIndex(0);
    }

    public void queryButtonsActions(){
        // query all patients
        getAllPatients.addActionListener(e -> {
            String query = "SELECT * FROM patients";
            tableModel.setQuery(query);
        });

        // query all records
        getAllRecords.addActionListener(e -> {
            String query = "SELECT * FROM records";
            tableModel.setQuery(query);
        });

        // query patient records
        getPatientRecords.addActionListener(e -> {
            try {
                int id = Integer.parseInt(enterPatientIDTF.getText());
                String query;
                if (Validate.id(id + "") && PatientDB.patientExists(id)){
                    if (RecordDB.patientHasRecords(id)) {
                        query = "SELECT * FROM records WHERE patientID = " + id;
                        tableModel.setQuery(query);
                    }
                    else
                        JOptionPane.showMessageDialog(this,"Patient has no records",
                                "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(this,"Invalid patientID or Patient with this ID does not exist",
                            "Error",JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(this,exception.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // logout and close the frame
        signOut.addActionListener(e -> {
            int x = JOptionPane.showConfirmDialog(this,
                    "Are sure you want to sign out?","Confirm",JOptionPane.YES_NO_OPTION);
            if (x == 0)
                this.dispose();
        });
    }

    public ArrayList<String> getDoctorsNames(){
        return getStrings();
    }
}
