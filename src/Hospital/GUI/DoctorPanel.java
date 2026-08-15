package Hospital.GUI;

import Hospital.Core.Doctor;
import Hospital.Core.HospitalSystem;

import javax.swing.*;
import java.awt.*;

public class DoctorPanel extends JPanel {

    private final HospitalSystem hospital;
    private final ContentPanel contentPanel;

    private JTable tableDoctors;

    public DoctorPanel(HospitalSystem hospital, ContentPanel contentPanel) {

        this.hospital = hospital;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());

        JPanel doctorSectionPanel = new JPanel(new BorderLayout());

        JLabel section = new JLabel("Doctors");
        doctorSectionPanel.add(section, BorderLayout.WEST);

        JButton addDoctor = new JButton("Add Doctor");

        doctorSectionPanel.add(addDoctor, BorderLayout.EAST);

        headerPanel.add(doctorSectionPanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel searchTitle = new JLabel("Search:");
        JTextField searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");

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

        tableDoctors = new JTable(
                new Object[0][tableTitles.length],
                tableTitles
        );


        JScrollPane scrollTable = new JScrollPane(tableDoctors);

        add(scrollTable, BorderLayout.CENTER);

        // Footer
        JPanel buttonPanel = new JPanel();

        JButton viewBtn = new JButton("View Doctor");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

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

    private void deleteSelectedDoctor() {

        Doctor doctor = getSelectedDoctor();

        if (doctor == null) {
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Delete functionality will be added next."
        );
    }
}