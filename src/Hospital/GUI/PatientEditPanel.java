package Hospital.GUI;

import Hospital.Core.HospitalSystem;
import Hospital.Core.Patient;
import Hospital.Core.Ward;
import Hospital.Core.enums.Gender;

import javax.swing.*;
import java.awt.*;

public class PatientEditPanel extends JPanel {

    private final ContentPanel contentPanel;
    private final HospitalSystem hospital;
    private Patient patient;

    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<Gender> genderBox;
    private JTextField phoneField;
    private JComboBox<Ward> wardBox;

    public PatientEditPanel(HospitalSystem hospital, ContentPanel contentPanel) {
        this.hospital = hospital;
        this.contentPanel = contentPanel;

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

        genderBox = new JComboBox<>(Gender.values());


        phoneField = new JTextField();

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
        cancelBtn.addActionListener(e ->
                contentPanel.showPatients()
        );

        JButton saveBtn = new JButton("Save Changes");
        saveBtn.addActionListener(e -> saveChanges());
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

        // Refresh ward list
        wardBox.removeAllItems();

        for (Ward ward : hospital.getWards()) {
            wardBox.addItem(ward);
        }

        wardBox.setSelectedItem(patient.getWard());
    }


    private void saveChanges() {

        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || ageText.isEmpty() || phone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields."
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

        if (age <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age must be greater than 0."
            );

            return;
        }

        Gender gender = (Gender) genderBox.getSelectedItem();
        Ward newWard = (Ward) wardBox.getSelectedItem();

        Ward oldWard = patient.getWard();


        if (oldWard != newWard) {

            if (newWard != null &&
                    newWard.getPatients().size() >= newWard.getCapacity()) {


                if (hospital.isHospitalFull()) {

                    int emergencyResult =
                            JOptionPane.showConfirmDialog(
                                    this,
                                    "The hospital is completely full.\n\n" +
                                            "Is this an emergency patient?",
                                    "Hospital Full",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.WARNING_MESSAGE
                            );

                    if (emergencyResult == JOptionPane.YES_OPTION) {

                        JOptionPane.showMessageDialog(
                                this,
                                "CRISIS ALERT!\n\n" +
                                        "The hospital is completely full " +
                                        "and an emergency patient has arrived.",
                                "HOSPITAL CRISIS",
                                JOptionPane.ERROR_MESSAGE
                        );

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "The hospital is completely full.\n" +
                                        "The patient cannot be admitted.",
                                "Hospital Full",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "The selected ward is full.\n",
                            "Ward Full",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

                return;
            }


            if (!hospital.transferWardPatient(patient, newWard)) {

                JOptionPane.showMessageDialog(
                        this,
                        "The patient could not be transferred.",
                        "Transfer Failed",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }
        }


        patient.setName(name);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setPhoneNumber(phone);

        JOptionPane.showMessageDialog(
                this,
                "Patient updated successfully."
        );

        contentPanel.showPatients();
    }
}
