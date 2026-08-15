package Hospital.GUI;

import Hospital.Core.Doctor;
import Hospital.Core.HospitalSystem;
import Hospital.Core.Ward;
import Hospital.Core.enums.Gender;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class DoctorEditPanel extends JPanel {

    private final HospitalSystem hospital;
    private final ContentPanel contentPanel;

    private Doctor doctor;

    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<Gender> genderBox;
    private JTextField phoneField;

    private JTextField specialtyField;
    private JComboBox<Ward> wardBox;
    private JTextField shiftStartField;
    private JTextField shiftEndField;
    private JTextField capacityField;

    public DoctorEditPanel(
            HospitalSystem hospital,
            ContentPanel contentPanel) {

        this.hospital = hospital;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Edit Doctor");
        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        add(title, BorderLayout.NORTH);

        // Main form
        JPanel editFormPanel =
                new JPanel(new GridLayout(2, 1, 10, 10));

        // Personal Information
        JPanel personalPanel =
                new JPanel(new BorderLayout());

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

        genderBox =
                new JComboBox<>(Gender.values());

        personalFormPanel.add(genderBox);

        personalFormPanel.add(new JLabel("Phone:"));

        phoneField = new JTextField();
        personalFormPanel.add(phoneField);

        personalPanel.add(
                personalFormPanel,
                BorderLayout.CENTER
        );

        editFormPanel.add(personalPanel);

        // Professional Information
        JPanel professionalPanel =
                new JPanel(new BorderLayout());

        professionalPanel.add(
                new JLabel("Professional Information"),
                BorderLayout.NORTH
        );

        JPanel professionalFormPanel =
                new JPanel(new GridLayout(5, 2, 10, 10));

        professionalFormPanel.add(
                new JLabel("Specialty:")
        );

        specialtyField = new JTextField();
        professionalFormPanel.add(specialtyField);

        professionalFormPanel.add(
                new JLabel("Ward:")
        );

        wardBox = new JComboBox<>();

        for (Ward ward : hospital.getWards()) {
            wardBox.addItem(ward);
        }

        professionalFormPanel.add(wardBox);

        professionalFormPanel.add(
                new JLabel("Shift Start:")
        );

        shiftStartField = new JTextField();
        professionalFormPanel.add(shiftStartField);

        professionalFormPanel.add(
                new JLabel("Shift End:")
        );

        shiftEndField = new JTextField();
        professionalFormPanel.add(shiftEndField);

        professionalFormPanel.add(
                new JLabel("Capacity:")
        );

        capacityField = new JTextField();
        professionalFormPanel.add(capacityField);

        professionalPanel.add(
                professionalFormPanel,
                BorderLayout.CENTER
        );

        editFormPanel.add(professionalPanel);

        add(
                editFormPanel,
                BorderLayout.CENTER
        );

        // footer
        JPanel footerBtns =
                new JPanel(
                        new FlowLayout(FlowLayout.RIGHT)
                );

        JButton cancelBtn =
                new JButton("Cancel");

        JButton saveBtn =
                new JButton("Save Changes");

        footerBtns.add(cancelBtn);
        footerBtns.add(saveBtn);

        add(
                footerBtns,
                BorderLayout.SOUTH
        );

        // Events
        cancelBtn.addActionListener(e ->
                contentPanel.showDoctors()
        );

        saveBtn.addActionListener(e ->
                saveChanges()
        );
    }

    public void setDoctor(Doctor doctor) {

        this.doctor = doctor;

        nameField.setText(
                doctor.getName()
        );

        ageField.setText(
                String.valueOf(doctor.getAge())
        );

        phoneField.setText(
                doctor.getPhoneNumber()
        );

        genderBox.setSelectedItem(
                doctor.getGender()
        );

        specialtyField.setText(
                doctor.getSpecialty()
        );

        shiftStartField.setText(
                doctor.getShiftStart().toString()
        );

        shiftEndField.setText(
                doctor.getShiftEnd().toString()
        );

        capacityField.setText(
                String.valueOf(
                        doctor.getDailyCapacity()
                )
        );

        wardBox.removeAllItems();

        for (Ward ward : hospital.getWards()) {
            wardBox.addItem(ward);
        }

        wardBox.setSelectedItem(
                doctor.getWard()
        );
    }

    private void saveChanges() {

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

            Ward newWard =
                    (Ward) wardBox.getSelectedItem();

            // Validation
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

            // Update personal information
            doctor.setName(name);
            doctor.setAge(age);
            doctor.setGender(gender);
            doctor.setPhoneNumber(phone);

            // Update professional information
            doctor.setSpecialty(specialty);
            doctor.setDailyCapacity(capacity);
            doctor.setShiftStart(shiftStart);
            doctor.setShiftEnd(shiftEnd);

            // Update ward
            if (doctor.getWard() != newWard) {
                hospital.transferWardDoctor(
                        doctor,
                        newWard
                );
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Doctor updated successfully."
            );

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
}