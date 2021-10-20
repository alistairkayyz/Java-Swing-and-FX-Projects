package alistair;

import alistair.business.User;
import alistair.data.UserDB;
import alistair.portal.DoctorFrame;
import alistair.portal.NurseFrame;
import alistair.portal.PatientFrame;
import alistair.utility.Color;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static JRadioButton doctorRadio;
    private static JRadioButton nurseRadio;
    private static JRadioButton patientRadio;

    private static JTextField usernameField;
    private static JPasswordField passwordField;
    private static JButton loginButton;
    private static JButton resetButton;

    private static JFrame frame;

    public static void main(String[] args) {

        // labels
        JLabel topLabel = new JLabel("Hospital Management System");
        JLabel headingLabel = new JLabel("Log in as:");

        // set top heading label
        topLabel.setFont(new Font("Roboto",Font.BOLD,24));
        topLabel.setForeground(Color.bluish);
        topLabel.setSize(720,32);
        topLabel.setHorizontalAlignment(JLabel.CENTER);
        topLabel.setVerticalAlignment(JLabel.CENTER);

        // set option label
        headingLabel.setFont(new Font("Roboto",Font.BOLD,18));
        headingLabel.setForeground(Color.darkGray);
        headingLabel.setSize(720,32);
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        headingLabel.setVerticalAlignment(JLabel.TOP);

        // Radio Buttons
        doctorRadio = new JRadioButton("Doctor");
        doctorRadio.setFont(new Font("Roboto",Font.PLAIN,14));
        doctorRadio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        doctorRadio.setBackground(null);
        doctorRadio.setFocusable(false);

        nurseRadio = new JRadioButton("Nurse");
        nurseRadio.setFont(new Font("Roboto",Font.PLAIN,14));
        nurseRadio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        nurseRadio.setBackground(null);
        nurseRadio.setFocusable(false);

        patientRadio = new JRadioButton("Patient");
        patientRadio.setFont(new Font("Roboto",Font.PLAIN,14));
        patientRadio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        patientRadio.setBackground(null);
        patientRadio.setFocusable(false);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(doctorRadio);
        buttonGroup.add(nurseRadio);
        buttonGroup.add(patientRadio);

        // login panel
        JPanel usernamePanel = new JPanel();
        usernamePanel.setBackground(Color.lavenderMist);
        usernamePanel.setLayout(new FlowLayout());
        usernamePanel.setPreferredSize(new Dimension(720,40));

        JPanel passwordPanel = new JPanel();
        passwordPanel.setBackground(Color.lavenderMist);
        passwordPanel.setLayout(new FlowLayout());
        passwordPanel.setPreferredSize(new Dimension(720,40));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.lavenderMist);
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setPreferredSize(new Dimension(720,60));

        // login components
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        usernameLabel.setBackground(null);
        usernameLabel.setLayout(null);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        passwordLabel.setBackground(null);
        passwordLabel.setLayout(null);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Roboto",Font.PLAIN,14));
        usernameField.setPreferredSize(new Dimension( 150,24));
        usernameField.setLayout(null);
        usernameField.setText("101");

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Roboto",Font.PLAIN,14));
        passwordField.setPreferredSize(new Dimension( 150,24));
        passwordField.setLayout(null);
        passwordField.setText("default");

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Roboto",Font.BOLD,14));
        loginButton.setSize(36,18);
        loginButton.setBackground(Color.fireFly);
        loginButton.setForeground(Color.white);
        loginButton.setFocusable(false);

        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Roboto",Font.BOLD,14));
        resetButton.setSize(36,18);
        resetButton.setBackground(Color.bluish);
        resetButton.setForeground(Color.white);
        resetButton.setFocusable(false);


        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        buttonsPanel.add(loginButton);
        buttonsPanel.add(resetButton);

        // North Panel
        JPanel northPanel = new JPanel();
        northPanel.setBackground(Color.lavenderMist);
        northPanel.setBounds(0,0,720,60);
        northPanel.add(topLabel);

        // Center Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBounds(0,60,720,60);
        centerPanel.setBackground(Color.lavenderMist);
        centerPanel.setLayout(new FlowLayout());

        centerPanel.add(headingLabel);
        centerPanel.add(doctorRadio);
        centerPanel.add(nurseRadio);
        centerPanel.add(patientRadio);

        //South Panel
        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.fireFly);
        southPanel.setBounds(0,120,720,150);
        southPanel.setLayout(new GridLayout(3,1));

        southPanel.add(usernamePanel);
        southPanel.add(passwordPanel);
        southPanel.add(buttonsPanel);

        // login
        loginButton.addActionListener(e -> login());

        // frame components
        frame = new JFrame("Login");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(720,310);
        frame.setVisible(true);

        frame.add(northPanel);
        frame.add(centerPanel);
        frame.add(southPanel);
    }

    private static void login() {
        int userID = 0;
        try {
            userID = Integer.parseInt(usernameField.getText());
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(frame,"Please enter a numeric username!",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }

        char[] chars = passwordField.getPassword(); // get password as char

        // parse chars to a string
        StringBuilder password = new StringBuilder();
        for (char c : chars)
          password.append(c);

        if (userID != 0 && UserDB.getUser(userID) != null){
            User user = UserDB.getUser(userID);

            assert user != null;
            if ((password.length() > 0) && password.toString().equals(user.getPassword())){

                if (user.getRole() == 0  && doctorRadio.isSelected())
                    new DoctorFrame(user.getId());
                else if (user.getRole() == 1 && nurseRadio.isSelected())
                    new NurseFrame();
                else if (user.getRole() == 2 && patientRadio.isSelected()) {
                    new PatientFrame(userID);
                }
                else{
                    JOptionPane.showMessageDialog(frame,"Please select your role.",
                            "Alert",JOptionPane.WARNING_MESSAGE);
                }
            }
            else
                JOptionPane.showMessageDialog(frame,"Invalid password!",
                        "Error",JOptionPane.ERROR_MESSAGE);
        }
        else
            JOptionPane.showMessageDialog(frame,"User does not exists!",
                    "Error",JOptionPane.ERROR_MESSAGE);

    }
}
