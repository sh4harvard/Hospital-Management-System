package Hospital.GUI;

import Hospital.Core.HospitalSystem;
import Hospital.Core.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PatientPanel extends JPanel {

    private final ContentPanel contentPanel;
    private final HospitalSystem hospital;


    private JTable tablePatients;
    private DefaultTableModel tableModel;
    private final String[] tableTitles = {"ID", "Name", "Age", "Gender", "Phone", "Ward"};
    private final int tableCol = tableTitles.length;

    public PatientPanel(HospitalSystem hospital, ContentPanel contentPanel) {
        this.hospital = hospital;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());

        JPanel patientsectionPanel = new JPanel(new BorderLayout());
        JLabel section = new JLabel("Patients");
        patientsectionPanel.add(section, BorderLayout.WEST);
        JButton addPatient = new JButton("Add Patient");

        addPatient.addActionListener(e ->
                contentPanel.showPatientAdd()
        );

        patientsectionPanel.add(addPatient, BorderLayout.EAST);
        headerPanel.add(patientsectionPanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchTitle = new JLabel("Search:");
        JTextField searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");

        // Search Button Functionality
        searchBtn.addActionListener(e -> {

            String searchText = searchField.getText().trim().toLowerCase();

            if (searchText.isEmpty()) {
                refreshPatientTable();
                return;
            }

            ArrayList<Patient> results = new ArrayList<>();

            for (Patient patient : hospital.getPatients()) {

                // Search Patient by Name / ID / Phone Number
                if (patient.getName().toLowerCase().contains(searchText)
                        || String.valueOf(patient.getId()).contains(searchText)
                        || patient.getPhoneNumber().contains(searchText)) {

                    results.add(patient);
                }
            }

            refreshPatientTable(results);
        });


        searchPanel.add(searchTitle);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        headerPanel.add(searchPanel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);

        // Center

        tableModel = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Name",
                        "Age",
                        "Gender",
                        "Phone",
                        "Ward"
                },
                0
        );

        tablePatients = new JTable(tableModel);

        refreshTable();

        JScrollPane scrollTable = new JScrollPane(tablePatients);
        add(scrollTable, BorderLayout.CENTER);


        // Footer
        JPanel buttonPanel = new JPanel();
        JButton viewBtn = new JButton("View Patient");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.addActionListener(e -> deleteSelectedPatient());

        // View Button Func
        viewBtn.addActionListener(e -> {

            int selectedRow = tablePatients.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a patient first."
                );
                return;
            }

            int patientId =
                    (int) tablePatients.getValueAt(selectedRow, 0);

            Patient patient =
                    hospital.findPatientById(patientId);

            if (patient == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Patient not found."
                );
                return;
            }

            contentPanel.showPatientDetail(patient);
        });

        buttonPanel.add(viewBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);

        add(buttonPanel, BorderLayout.SOUTH);

    }

    private void refreshPatientTable() {
        refreshPatientTable(hospital.getPatients());
    }

    private void refreshPatientTable(ArrayList<Patient> patients) {

        Object[][] tableData = new Object[patients.size()][tableCol];

        for (int i = 0; i < patients.size(); i++) {

            Patient patient = patients.get(i);

            tableData[i][0] = patient.getId();
            tableData[i][1] = patient.getName();
            tableData[i][2] = patient.getAge();
            tableData[i][3] = patient.getGender();
            tableData[i][4] = patient.getPhoneNumber();

            if (patient.getWard() != null) {
                tableData[i][5] = patient.getWard().getName();
            } else {
                tableData[i][5] = "None";
            }
        }

        tablePatients.setModel(new DefaultTableModel(tableData, tableTitles));
    }

    public void refreshTable() {

        System.out.println("PatientPanel.refreshTable()");
        System.out.println("Patients: " +
                hospital.getPatients().size());

        tableModel.setRowCount(0);

        for (Patient patient : hospital.getPatients()) {

            Object[] row = {
                    patient.getId(),
                    patient.getName(),
                    patient.getAge(),
                    patient.getGender(),
                    patient.getPhoneNumber(),
                    patient.getWard() != null
                            ? patient.getWard().getName()
                            : "None"
            };

            tableModel.addRow(row);
        }

        System.out.println("Rows in model: " +
                tableModel.getRowCount());
    }

    private void deleteSelectedPatient() {

        int selectedRow = tablePatients.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a patient first."
            );

            return;
        }

        int patientId =
                (int) tablePatients.getValueAt(selectedRow, 0);

        Patient patient =
                hospital.findPatientById(patientId);

        if (patient == null) {
            return;
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete " +
                        patient.getName() + "?",
                "Delete Patient",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        hospital.deletePatient(patient);

        refreshTable();

        JOptionPane.showMessageDialog(
                this,
                "Patient deleted successfully."
        );
    }
}
