package Hospital.GUI;

import Hospital.Core.Doctor;
import Hospital.Core.HospitalSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DoctorPanel extends JPanel {

    private final HospitalSystem hospital;
    private final ContentPanel contentPanel;

    private JTable tableDoctors;
    private DefaultTableModel tableModel;

    public DoctorPanel(HospitalSystem hospital, ContentPanel contentPanel) {

        this.hospital = hospital;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout(0, 15));
        setBackground(AppColors.BACKGROUND);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(AppColors.BACKGROUND);

        JPanel doctorSectionPanel = new JPanel(new BorderLayout());
        doctorSectionPanel.setBackground(AppColors.BACKGROUND);

        JLabel section = new JLabel("Doctors");
        UIStyle.styleTitle(section);
        doctorSectionPanel.add(section, BorderLayout.WEST);

        JButton addDoctor = new JButton("Add Doctor");
        UIStyle.styleButton(addDoctor);

        doctorSectionPanel.add(addDoctor, BorderLayout.EAST);

        headerPanel.add(doctorSectionPanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(AppColors.BACKGROUND);

        JLabel searchTitle = new JLabel("Search:");
        searchTitle.setFont(AppFonts.NORMAL);
        searchTitle.setForeground(AppColors.TEXT);

        JTextField searchField = new JTextField(20);
        searchField.setFont(AppFonts.NORMAL);

        JButton searchBtn = new JButton("Search");
        UIStyle.styleSecondaryButton(searchBtn);

        searchBtn.addActionListener(e -> {

            String searchText =
                    searchField.getText().trim().toLowerCase();

            if (searchText.isEmpty()) {
                refreshDoctorTable();
                return;
            }

            ArrayList<Doctor> results = new ArrayList<>();

            for (Doctor doctor : hospital.getDoctors()) {

                // Search Doctor by Name / ID / Specialty
                if (doctor.getName().toLowerCase().contains(searchText)
                        || String.valueOf(doctor.getId()).contains(searchText)
                        || doctor.getSpecialty().toLowerCase().contains(searchText)) {

                    results.add(doctor);
                }
            }

            refreshDoctorTable(results);
        });

        searchPanel.add(searchTitle);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        headerPanel.add(searchPanel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);

        // Center
        String[] tableTitles = {
                "ID",
                "Name",
                "Specialty",
                "Ward",
                "Shift",
                "Capacity"
        };

        tableModel = new DefaultTableModel(
                tableTitles,
                0
        );

        tableDoctors = new JTable(tableModel);
        UIStyle.styleTable(tableDoctors);


        JScrollPane scrollTable = new JScrollPane(tableDoctors);

        add(scrollTable, BorderLayout.CENTER);

        // Footer
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(AppColors.BACKGROUND);

        JButton viewBtn = new JButton("View Doctor");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        UIStyle.styleSecondaryButton(viewBtn);
        UIStyle.styleSecondaryButton(editBtn);
        UIStyle.styleDeleteButton(deleteBtn);

        buttonPanel.add(viewBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // Initial table data
        refreshTable();

        // Events
        addDoctor.addActionListener(e ->
                contentPanel.showDoctorAdd()
        );

        viewBtn.addActionListener(e ->
                viewSelectedDoctor()
        );

        editBtn.addActionListener(e ->
                editSelectedDoctor()
        );

        deleteBtn.addActionListener(e -> {

            int row = tableDoctors.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a doctor first."
                );
                return;
            }

            int doctorId =
                    (int) tableDoctors.getValueAt(row, 0);

            Doctor doctor =
                    hospital.findDoctorbyId(doctorId);

            if (doctor == null) {
                return;
            }

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete Dr. "
                            + doctor.getName()
                            + "?",
                    "Delete Doctor",
                    JOptionPane.YES_NO_OPTION
            );

            if (result != JOptionPane.YES_OPTION) {
                return;
            }

            hospital.removeDoctor(doctor);

            refreshTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Doctor deleted successfully."
            );
        });
    }

    public void refreshTable() {

        String[] tableTitles = {
                "ID",
                "Name",
                "Specialty",
                "Ward",
                "Shift",
                "Capacity"
        };

        Object[][] tableData =
                new Object[hospital.getDoctors().size()][6];

        for (int i = 0; i < hospital.getDoctors().size(); i++) {

            Doctor doctor = hospital.getDoctors().get(i);

            tableData[i][0] = doctor.getId();
            tableData[i][1] = doctor.getName();
            tableData[i][2] = doctor.getSpecialty();

            if (doctor.getWard() != null) {
                tableData[i][3] = doctor.getWard().getName();
            } else {
                tableData[i][3] = "None";
            }

            tableData[i][4] =
                    doctor.getShiftStart() +
                            " - " +
                            doctor.getShiftEnd();

            tableData[i][5] = doctor.getDailyCapacity();
        }

        tableDoctors.setModel(
                new javax.swing.table.DefaultTableModel(
                        tableData,
                        tableTitles
                ) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                }
        );

        UIStyle.styleTable(tableDoctors);
    }

    private void refreshDoctorTable() {
        refreshDoctorTable(hospital.getDoctors());
    }

    private void refreshDoctorTable(ArrayList<Doctor> doctors) {

        String[] tableTitles = {
                "ID",
                "Name",
                "Specialty",
                "Ward",
                "Shift",
                "Capacity"
        };

        Object[][] tableData =
                new Object[doctors.size()][6];

        for (int i = 0; i < doctors.size(); i++) {

            Doctor doctor = doctors.get(i);

            tableData[i][0] = doctor.getId();
            tableData[i][1] = doctor.getName();
            tableData[i][2] = doctor.getSpecialty();

            if (doctor.getWard() != null) {
                tableData[i][3] =
                        doctor.getWard().getName();
            } else {
                tableData[i][3] = "None";
            }

            tableData[i][4] =
                    doctor.getShiftStart()
                            + " - "
                            + doctor.getShiftEnd();

            tableData[i][5] =
                    doctor.getDailyCapacity();
        }

        tableDoctors.setModel(
                new javax.swing.table.DefaultTableModel(
                        tableData,
                        tableTitles
                ) {
                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                }
        );

        UIStyle.styleTable(tableDoctors);
    }


    private Doctor getSelectedDoctor() {

        int selectedRow = tableDoctors.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a doctor first."
            );
            return null;
        }

        int doctorId =
                (int) tableDoctors.getValueAt(selectedRow, 0);

        return hospital.findDoctorbyId(doctorId);
    }

    private void viewSelectedDoctor() {

        Doctor doctor = getSelectedDoctor();

        if (doctor == null) {
            return;
        }

        contentPanel.showDoctorDetail(doctor);
    }

    private void editSelectedDoctor() {

        Doctor doctor = getSelectedDoctor();

        if (doctor == null) {
            return;
        }

        contentPanel.showDoctorEdit(doctor);
    }

}