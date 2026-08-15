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

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        nameField = new JTextField();
        ageField = new JTextField();
        genderBox = new JComboBox<>(Gender.values());
        phoneField = new JTextField();
        wardBox = new JComboBox<>();

        refreshWards();

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

        add(formPanel, BorderLayout.CENTER);

        //

        JPanel buttonPanel = new JPanel();

        JButton cancelBtn = new JButton("Cancel");
        JButton saveBtn = new JButton("Add Patient");

        cancelBtn.addActionListener(e ->
                contentPanel.showPatients()
        );

        saveBtn.addActionListener(e ->
                savePatient()
        );

        buttonPanel.add(cancelBtn);
        buttonPanel.add(saveBtn);

        add(buttonPanel, BorderLayout.SOUTH);

    }

    private void savePatient() {

        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        String phone = phoneField.getText().trim();


        if (name.isEmpty() ||
                ageText.isEmpty() ||
                phone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields."
            );

            return;
        }

        int age;

        try {
            age = Integer.parseInt(ageText);
        }
        catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age must be a number."
            );

            return;
        }

        if (age <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age must be greater than 0."
            );

            return;
        }

        Gender gender = (Gender) genderBox.getSelectedItem();

        Ward ward = (Ward) wardBox.getSelectedItem();


        int id = hospital.generatePatientId();

        Patient patient = new Patient(id, name, age, gender, phone);

        hospital.getPatients().add(patient);


        if (ward != null) {

            if (!hospital.admitPatient(patient, ward)) {

                JOptionPane.showMessageDialog(
                        this,
                        "The selected ward is full."
                );

                // remove the patient because the admission failed
                hospital.getPatients().remove(patient);

                return;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Patient added successfully."
        );

        clearFields();

        contentPanel.showPatients();
    }

    private void refreshWards() {

        wardBox.removeAllItems();

        for (Ward ward : hospital.getWards()) {
            wardBox.addItem(ward);
        }
    }

    public void clearFields() {

        nameField.setText("");
        ageField.setText("");
        phoneField.setText("");

        genderBox.setSelectedIndex(0);

        wardBox.removeAllItems();

        for (Ward ward : hospital.getWards()) {
            wardBox.addItem(ward);
        }
    }
}