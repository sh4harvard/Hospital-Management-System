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

        setLayout(new BorderLayout(20, 20));
        setBackground(AppColors.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // header
        JLabel title = new JLabel("Add Doctor");
        UIStyle.styleTitle(title);
        add(title, BorderLayout.NORTH);

        // Main
        JPanel addFormPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        addFormPanel.setBackground(AppColors.WHITE);
        addFormPanel.setBorder(UIStyle.createPanelBorder());

        // Personal Information
        JPanel personalPanel = new JPanel(new BorderLayout());
        personalPanel.setBackground(AppColors.WHITE);
        personalPanel.setBorder(UIStyle.createPanelBorder());

        JLabel personalInfo = new JLabel("Personal Information");
        UIStyle.styleSubTitle(personalInfo);
        personalPanel.add(personalInfo, BorderLayout.NORTH);

        JPanel personalFormPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        personalFormPanel.setBackground(AppColors.WHITE);

        JLabel nameTitle = new JLabel("Name:");
        JLabel ageTitle = new JLabel("Age:");
        JLabel genderTitle = new JLabel("Gender:");
        JLabel phoneTitle = new JLabel("Phone:");

        UIStyle.styleFormTitle(nameTitle);
        UIStyle.styleFormTitle(ageTitle);
        UIStyle.styleFormTitle(genderTitle);
        UIStyle.styleFormTitle(phoneTitle);

        personalFormPanel.add(nameTitle);
        nameField = new JTextField();
        UIStyle.styleField(nameField);
        personalFormPanel.add(nameField);

        personalFormPanel.add(ageTitle);
        ageField = new JTextField();
        UIStyle.styleField(ageField);
        personalFormPanel.add(ageField);

        personalFormPanel.add(genderTitle);
        genderBox = new JComboBox<>(Gender.values());
        UIStyle.styleComboBox(genderBox);
        personalFormPanel.add(genderBox);

        personalFormPanel.add(phoneTitle);
        phoneField = new JTextField();
        UIStyle.styleField(phoneField);
        personalFormPanel.add(phoneField);

        personalPanel.add(
                personalFormPanel,
                BorderLayout.CENTER
        );

        addFormPanel.add(personalPanel);

        // Professional Information
        JPanel professionalPanel = new JPanel(new BorderLayout());
        professionalPanel.setBackground(AppColors.WHITE);
        professionalPanel.setBorder(UIStyle.createPanelBorder());

        JLabel professInfo = new JLabel("Professional Information");
        UIStyle.styleSubTitle(professInfo);
        professionalPanel.add(professInfo, BorderLayout.NORTH);

        JPanel professionalFormPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        professionalFormPanel.setBackground(AppColors.WHITE);

        JLabel specialtyTitle = new JLabel("Specialty:");
        UIStyle.styleFormTitle(specialtyTitle);
        professionalFormPanel.add(specialtyTitle);

        specialtyField = new JTextField();
        UIStyle.styleField(specialtyField);
        professionalFormPanel.add(specialtyField);

        JLabel wardTitle = new JLabel("Ward:");
        UIStyle.styleFormTitle(wardTitle);
        professionalFormPanel.add(wardTitle);

        wardBox = new JComboBox<>();
        UIStyle.styleComboBox(wardBox);

        for (Ward ward : hospital.getWards()) {
            wardBox.addItem(ward);
        }

        professionalFormPanel.add(wardBox);

        JLabel shiftSTitle = new JLabel("Shift Start:");
        UIStyle.styleFormTitle(shiftSTitle);
        professionalFormPanel.add(shiftSTitle);

        shiftStartField = new JTextField();
        UIStyle.styleField(shiftStartField);
        professionalFormPanel.add(shiftStartField);

        JLabel shiftETitle = new JLabel("Shift End:");
        UIStyle.styleFormTitle(shiftETitle);
        professionalFormPanel.add(shiftETitle);

        shiftEndField = new JTextField();
        UIStyle.styleField(shiftEndField);
        professionalFormPanel.add(shiftEndField);

        JLabel capacityTitle = new JLabel("Capacity:");
        UIStyle.styleFormTitle(capacityTitle);
        professionalFormPanel.add(capacityTitle);

        capacityField = new JTextField();
        UIStyle.styleField(capacityField);
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

        UIStyle.styleSecondaryButton(cancelBtn);
        UIStyle.styleButton(addDoctorBtn);

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