package Hospital.GUI;

import Hospital.Core.Doctor;
import Hospital.Core.HospitalSystem;
import Hospital.Core.Ward;
import Hospital.Core.enums.Gender;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class DoctorAddPanel extends JPanel {

    private final HospitalSystem hospital;
    private final ContentPanel contentPanel;

    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<Gender> genderBox;
    private JTextField phoneField;

    private JTextField specialtyField;
    private JComboBox<Ward> wardBox;
    private JTextField shiftStartField;
    private JTextField shiftEndField;
    private JTextField capacityField;

    public DoctorAddPanel(HospitalSystem hospital, ContentPanel contentPanel) {

        this.hospital = hospital;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout());

        // header
        JLabel title = new JLabel("Add Doctor");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Main
        JPanel addFormPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        // Personal Information
        JPanel personalPanel = new JPanel(new BorderLayout());
        personalPanel.add(
                new JLabel("Personal Information"),
                BorderLayout.NORTH
        );

        JPanel personalFormPanel =
                new JPanel(new GridLayout(4, 2, 10, 10));

        personalFormPanel.add(new JLabel("Name:"));

        nameField = new JTextField();
        personalFormPanel.add(nameField);

        personalFormPanel.add(new JLabel("Age:"));

        ageField = new JTextField();
        personalFormPanel.add(ageField);

        personalFormPanel.add(new JLabel("Gender:"));

        genderBox = new JComboBox<>(Gender.values());
        personalFormPanel.add(genderBox);

        personalFormPanel.add(new JLabel("Phone:"));

        phoneField = new JTextField();
        personalFormPanel.add(phoneField);

        personalPanel.add(
                personalFormPanel,
                BorderLayout.CENTER
        );

        addFormPanel.add(personalPanel);

        // Professional Information
        JPanel professionalPanel = new JPanel(new BorderLayout());

        professionalPanel.add(
                new JLabel("Professional Information"),
                BorderLayout.NORTH
        );

        JPanel professionalFormPanel =
                new JPanel(new GridLayout(5, 2, 10, 10));

        professionalFormPanel.add(new JLabel("Specialty:"));

        specialtyField = new JTextField();
        professionalFormPanel.add(specialtyField);

        professionalFormPanel.add(new JLabel("Ward:"));

        wardBox = new JComboBox<>();

        for (Ward ward : hospital.getWards()) {
            wardBox.addItem(ward);
        }

        professionalFormPanel.add(wardBox);

        professionalFormPanel.add(new JLabel("Shift Start:"));

        shiftStartField = new JTextField();
        professionalFormPanel.add(shiftStartField);

        professionalFormPanel.add(new JLabel("Shift End:"));

        shiftEndField = new JTextField();
        professionalFormPanel.add(shiftEndField);

        professionalFormPanel.add(new JLabel("Capacity:"));

        capacityField = new JTextField();
        professionalFormPanel.add(capacityField);

        professionalPanel.add(
                professionalFormPanel,
                BorderLayout.CENTER
        );

        addFormPanel.add(professionalPanel);

        add(addFormPanel, BorderLayout.CENTER);

        // footer
        JPanel footerBtns =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton cancelBtn = new JButton("Cancel");
        JButton addDoctorBtn = new JButton("Add Doctor");

        footerBtns.add(cancelBtn);
        footerBtns.add(addDoctorBtn);

        add(footerBtns, BorderLayout.SOUTH);


        cancelBtn.addActionListener(e ->
                contentPanel.showDoctors()
        );

        addDoctorBtn.addActionListener(e ->
                addDoctor()
        );
    }

    private void addDoctor() {

        try {

            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String phone = phoneField.getText().trim();
            String specialty = specialtyField.getText().trim();
            String capacityText = capacityField.getText().trim();
            String shiftStartText = shiftStartField.getText().trim();
            String shiftEndText = shiftEndField.getText().trim();

            if (name.isEmpty()
                    || ageText.isEmpty()
                    || phone.isEmpty()
                    || specialty.isEmpty()
                    || capacityText.isEmpty()
                    || shiftStartText.isEmpty()
                    || shiftEndText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill in all required fields."
                );

                return;
            }

            int age = Integer.parseInt(ageText);
            int capacity = Integer.parseInt(capacityText);

            LocalTime shiftStart =
                    LocalTime.parse(shiftStartText);

            LocalTime shiftEnd =
                    LocalTime.parse(shiftEndText);

            Gender gender =
                    (Gender) genderBox.getSelectedItem();

            Ward ward =
                    (Ward) wardBox.getSelectedItem();

            if (age <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Age must be greater than zero."
                );
                return;
            }

            if (capacity <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Capacity must be greater than zero."
                );
                return;
            }

            if (!shiftStart.isBefore(shiftEnd)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Shift start must be before shift end."
                );
                return;
            }

            int id = hospital.generateDoctorId();

            Doctor doctor = new Doctor(
                    id,
                    name,
                    age,
                    gender,
                    phone,
                    specialty,
                    null,
                    capacity,
                    shiftStart,
                    shiftEnd
            );

            hospital.addDoctor(doctor);

            if (ward != null) {
                ward.addDoctor(doctor);
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Doctor added successfully."
            );

            clearFields();

            contentPanel.showDoctors();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age and capacity must be valid numbers."
            );

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter time in HH:mm format."
            );
        }
    }

    public void clearFields() {

        nameField.setText("");
        ageField.setText("");
        phoneField.setText("");

        specialtyField.setText("");
        shiftStartField.setText("");
        shiftEndField.setText("");
        capacityField.setText("");

        genderBox.setSelectedIndex(0);

        wardBox.removeAllItems();

        for (Ward ward : hospital.getWards()) {
            wardBox.addItem(ward);
        }
    }

    public void refreshWards() {

        wardBox.removeAllItems();

        for (Ward ward : hospital.getWards()) {
            wardBox.addItem(ward);
        }
    }
}