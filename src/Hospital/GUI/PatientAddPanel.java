package Hospital.GUI;

import Hospital.Core.HospitalSystem;
import Hospital.Core.Patient;
import Hospital.Core.Ward;
import Hospital.Core.enums.Gender;

import javax.swing.*;
import java.awt.*;

public class PatientAddPanel extends JPanel {

    private final HospitalSystem hospital;
    private final ContentPanel contentPanel;

    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<Gender> genderBox;
    private JTextField phoneField;
    private JComboBox<Ward> wardBox;

    public PatientAddPanel(HospitalSystem hospital, ContentPanel contentPanel) {

        this.hospital = hospital;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout());

        //

        JLabel title = new JLabel("Add Patient");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        //

        JPanel addFormPanel = new JPanel(new BorderLayout());

        addFormPanel.add(new JLabel("Personal Information"), BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        nameField = new JTextField();

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);

        ageField = new JTextField();

        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);

        genderBox = new JComboBox<>(Gender.values());

        formPanel.add(new JLabel("Gender:"));
        formPanel.add(genderBox);

        phoneField = new JTextField();

        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);

        wardBox = new JComboBox<>();
        for (Ward ward : hospital.getWards()) {
            wardBox.addItem(ward);
        }

        formPanel.add(new JLabel("Ward:"));
        formPanel.add(wardBox);


        addFormPanel.add(formPanel, BorderLayout.CENTER);
        add(addFormPanel, BorderLayout.CENTER);

        //

        JPanel footerBtns = new JPanel(new FlowLayout((FlowLayout.RIGHT)));

        JButton cancelBtn = new JButton("Cancel");
        JButton saveBtn = new JButton("Add Patient");

        cancelBtn.addActionListener(e ->
                contentPanel.showPatients()
        );

        saveBtn.addActionListener(e -> savePatient());

        footerBtns.add(cancelBtn);
        footerBtns.add(saveBtn);

        add(footerBtns, BorderLayout.SOUTH);
    }

    private void savePatient() {

        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || ageText.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all required fields."
            );
            return;
        }

        int age;

        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Age must be a number."
            );
            return;
        }

        Gender gender = (Gender) genderBox.getSelectedItem();
        Ward ward = (Ward) wardBox.getSelectedItem();

        int id = hospital.generatePatientId();

        Patient patient = new Patient(
                id,
                name,
                age,
                gender,
                phone
        );

        hospital.addPatient(patient);

        if (ward != null) {
            hospital.admitPatient(patient, ward);
        }

        JOptionPane.showMessageDialog(
                this,
                "Patient added successfully."
        );

        contentPanel.showPatients();
    }
}
