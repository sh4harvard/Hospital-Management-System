package Hospital.GUI;

import Hospital.Core.HospitalSystem;
import Hospital.Core.Patient;
import Hospital.Core.Ward;
import Hospital.Core.enums.Gender;

import javax.swing.*;
import java.awt.*;

public class PatientEditPanel extends JPanel {

    private Patient patient;

    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<Gender> genderBox;
    private JTextField phoneField;
    private JComboBox<Ward> wardBox;

    private final HospitalSystem hospital;

    public PatientEditPanel(HospitalSystem hospital) {
        this.hospital = hospital;

        setLayout(new BorderLayout());

        //

        JLabel title = new JLabel("Edit Patient");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        //

        JPanel editFormPanel = new JPanel(new BorderLayout());

        editFormPanel.add(new JLabel("Personal Information"), BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        nameField = new JTextField();
        ageField = new JTextField();

        String[] genders = {"Male", "Female"};
        genderBox = new JComboBox<>(Gender.values());


        phoneField = new JTextField();

        String[] wards = {
                "Cardiology",
                "Neurology",
                "General"
        };
        wardBox = new JComboBox<>();

        for (Ward ward : hospital.getWards()) {
            wardBox.addItem(ward);
        }

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);

        formPanel.add(new JLabel("Gender:"));
        formPanel.add(genderBox);

        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Ward:"));
        formPanel.add(wardBox);


        editFormPanel.add(formPanel, BorderLayout.CENTER);
        add(editFormPanel, BorderLayout.CENTER);

        //

        JPanel footerBtns = new JPanel(new FlowLayout((FlowLayout.RIGHT)));

        JButton cancelBtn = new JButton("Cancel");
        JButton saveBtn = new JButton("Save Changes");
        footerBtns.add(cancelBtn);
        footerBtns.add(saveBtn);

        add(footerBtns, BorderLayout.SOUTH);

    }

    public void setPatient(Patient patient) {

        this.patient = patient;

        nameField.setText(patient.getName());
        ageField.setText(String.valueOf(patient.getAge()));
        phoneField.setText(patient.getPhoneNumber());

        genderBox.setSelectedItem(patient.getGender());
        wardBox.setSelectedItem(patient.getWard());
    }
}
