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

        setLayout(new BorderLayout(20, 20));
        setBackground(AppColors.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel title = new JLabel("Add Patient");
        UIStyle.styleTitle(title);
        add(title, BorderLayout.NORTH);

        //
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 15, 15));
        formPanel.setBackground(AppColors.WHITE);
        formPanel.setBorder(UIStyle.createPanelBorder());

        nameField = new JTextField();
        ageField = new JTextField();
        genderBox = new JComboBox<>(Gender.values());
        phoneField = new JTextField();
        wardBox = new JComboBox<>();

        refreshWards();

        UIStyle.styleField(nameField);
        UIStyle.styleField(ageField);
        UIStyle.styleField(phoneField);
        UIStyle.styleComboBox(genderBox);
        UIStyle.styleComboBox(wardBox);

        JLabel nameTitle = new JLabel("Name:");
        JLabel ageTitle = new JLabel("Age:");
        JLabel genderTitle = new JLabel("Gender:");
        JLabel phoneTitle = new JLabel("Phone:");
        JLabel wardTitle = new JLabel("Ward:");

        UIStyle.styleFormTitle(nameTitle);
        UIStyle.styleFormTitle(ageTitle);
        UIStyle.styleFormTitle(genderTitle);
        UIStyle.styleFormTitle(phoneTitle);
        UIStyle.styleFormTitle(wardTitle);

        formPanel.add(nameTitle);
        formPanel.add(nameField);
        formPanel.add(ageTitle);
        formPanel.add(ageField);
        formPanel.add(genderTitle);
        formPanel.add(genderBox);
        formPanel.add(phoneTitle);
        formPanel.add(phoneField);
        formPanel.add(wardTitle);
        formPanel.add(wardBox);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(AppColors.BACKGROUND);

        JButton cancelBtn = new JButton("Cancel");
        JButton saveBtn = new JButton("Add Patient");

        UIStyle.styleSecondaryButton(cancelBtn);
        UIStyle.styleButton(saveBtn);

        cancelBtn.setPreferredSize(new Dimension(110, 38));
        saveBtn.setPreferredSize(new Dimension(130, 38));

        cancelBtn.addActionListener(e -> contentPanel.showPatients());
        saveBtn.addActionListener(e -> savePatient());

        buttonPanel.add(cancelBtn);
        buttonPanel.add(saveBtn);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void savePatient() {
        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || ageText.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        int age;

        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Age must be a number.");
            return;
        }

        if (age <= 0) {
            JOptionPane.showMessageDialog(this, "Age must be greater than 0.");
            return;
        }

        Gender gender = (Gender) genderBox.getSelectedItem();
        Ward ward = (Ward) wardBox.getSelectedItem();

        if (ward == null) {
            JOptionPane.showMessageDialog(this, "Please select a ward.");
            return;
        }

        if (ward.getPatients().size() >= ward.getCapacity()) {
            if (hospital.isHospitalFull()) {
                int emergencyResult = JOptionPane.showConfirmDialog(
                        this,
                        "The hospital is completely full.\n\nIs this an emergency patient?",
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
                        "The selected ward is full.",
                        "Ward Full",
                        JOptionPane.WARNING_MESSAGE
                );
            }

            return;
        }

        int id = hospital.generatePatientId();

        Patient patient = new Patient(id, name, age, gender, phone);

        hospital.getPatients().add(patient);

        if (!hospital.admitPatient(patient, ward)) {
            hospital.getPatients().remove(patient);

            JOptionPane.showMessageDialog(
                    this,
                    "The patient could not be admitted."
            );

            return;
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