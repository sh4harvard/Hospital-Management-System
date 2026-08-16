package Hospital.GUI;

import Hospital.Core.Charge;
import Hospital.Core.HospitalSystem;
import Hospital.Core.MedicalService;
import Hospital.Core.Patient;
import Hospital.Core.HospitalIncome;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HospitalBillPanel extends JPanel {

    private final HospitalSystem hospital;
    private JTable billTable;
    private DefaultTableModel tableModel;

    private JLabel paidLabel;
    private JLabel unpaidLabel;
    private JLabel totalLabel;

    public HospitalBillPanel(HospitalSystem hospital) {

        this.hospital = hospital;

        setLayout(new BorderLayout(0, 15));
        setBackground(AppColors.BACKGROUND);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(AppColors.BACKGROUND);

        JLabel title = new JLabel("Hospital Billing");
        UIStyle.styleTitle(title);

        headerPanel.add(title);

        add(headerPanel, BorderLayout.NORTH);

        String[] titles = {
                "ID",
                "Patient",
                "Service",
                "Cost",
                "Status"
        };

        tableModel = new DefaultTableModel(titles, 0);

        billTable = new JTable(tableModel);
        UIStyle.styleTable(billTable);

        add(new JScrollPane(billTable), BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout(15, 0));
        footer.setBackground(AppColors.BACKGROUND);

        JPanel summaryPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        summaryPanel.setBackground(AppColors.BACKGROUND);

        summaryPanel.add(new JLabel("Paid:"));
        paidLabel = new JLabel();

        summaryPanel.add(paidLabel);

        summaryPanel.add(new JLabel("Unpaid:"));
        unpaidLabel = new JLabel();

        summaryPanel.add(unpaidLabel);

        summaryPanel.add(new JLabel("Total:"));
        totalLabel = new JLabel();

        summaryPanel.add(totalLabel);

        footer.add(summaryPanel, BorderLayout.CENTER);

        JPanel btn = new JPanel();

        JButton addBtn = new JButton("Add Charge");
        UIStyle.styleButton(addBtn);
        addBtn.addActionListener(e -> addCharge());

        JButton deleteBtn = new JButton("Delete Charge");
        UIStyle.styleDeleteButton(deleteBtn);
        deleteBtn.addActionListener(e -> deleteCharge());

        JButton payBtn = new JButton("Mark as Paid");
        UIStyle.styleButton(payBtn);
        payBtn.addActionListener(e -> markAsPaid());

        btn.add(addBtn);
        btn.add(payBtn);
        btn.add(deleteBtn);


        footer.add(btn, BorderLayout.EAST);

        add(footer, BorderLayout.SOUTH);

        refreshTable();
    }

    public void refreshTable() {

        tableModel.setRowCount(0);

        double paid = 0;
        double unpaid = 0;

        // Patient charges
        for (Patient patient : hospital.getPatients()) {

            for (Charge charge : patient.getBill().getCharges()) {

                double cost = charge.getService().getCost();

                tableModel.addRow(new Object[]{
                        charge.getId(),
                        patient.getName(),
                        charge.getService().getName(),
                        cost,
                        charge.getPayStatus() ? "Paid" : "Unpaid"
                });

                if (charge.getPayStatus()) {
                    paid += cost;
                } else {
                    unpaid += cost;
                }
            }
        }

        // Hospital incomes
        for (HospitalIncome income : hospital.getHospitalIncomes()) {

            if (income.getType().equals("WARD_BONUS")) {

                tableModel.addRow(new Object[]{
                        "-",
                        "-",
                        income.getName(),
                        income.getAmount(),
                        "Income"
                });

                paid += income.getAmount();
            }
        }

        paidLabel.setText(String.valueOf(paid));
        unpaidLabel.setText(String.valueOf(unpaid));
        totalLabel.setText(String.valueOf(paid + unpaid));
    }

    private void markAsPaid() {

        int selectedRow = billTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a charge first."
            );
            return;
        }

        int chargeId =
                (int) tableModel.getValueAt(selectedRow, 0);

        for (Patient patient : hospital.getPatients()) {

            for (Charge charge :
                    patient.getBill().getCharges()) {

                if (charge.getId() == chargeId) {

                    if (charge.getPayStatus()) {
                        JOptionPane.showMessageDialog(
                                this,
                                "This charge is already paid."
                        );
                        return;
                    }

                    charge.setPayStatus(true);

                    refreshTable();

                    JOptionPane.showMessageDialog(
                            this,
                            "Charge marked as paid."
                    );

                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Charge not found."
        );
    }

    private void addCharge() {

        if (hospital.getPatients().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "There are no patients."
            );

            return;
        }

        if (hospital.getMedicalServices().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "There are no medical services."
            );

            return;
        }


        // Patient selection

        JComboBox<Patient> patientBox =
                new JComboBox<>();

        for (Patient patient : hospital.getPatients()) {
            patientBox.addItem(patient);
        }


        // Medical service selection

        JComboBox<MedicalService> serviceBox =
                new JComboBox<>();

        for (Hospital.Core.MedicalService service :
                hospital.getMedicalServices()) {

            serviceBox.addItem(service);
        }


        // Show selected service cost

        JLabel costLabel = new JLabel();

        serviceBox.addActionListener(e -> {

            Hospital.Core.MedicalService service =
                    (Hospital.Core.MedicalService)
                            serviceBox.getSelectedItem();

            if (service != null) {
                costLabel.setText(
                        String.valueOf(service.getCost())
                );
            }
        });


        JPanel panel =
                new JPanel(new GridLayout(3, 2, 10, 10));

        panel.add(new JLabel("Patient:"));
        panel.add(patientBox);

        panel.add(new JLabel("Medical Service:"));
        panel.add(serviceBox);

        panel.add(new JLabel("Cost:"));
        panel.add(costLabel);


        // Set initial cost

        if (!hospital.getMedicalServices().isEmpty()) {

            Hospital.Core.MedicalService service =
                    hospital.getMedicalServices().get(0);

            costLabel.setText(
                    String.valueOf(service.getCost())
            );
        }


        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Add Medical Charge",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }


        Patient patient =
                (Patient) patientBox.getSelectedItem();

        Hospital.Core.MedicalService service =
                (Hospital.Core.MedicalService)
                        serviceBox.getSelectedItem();


        if (patient == null || service == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a patient and medical service."
            );

            return;
        }


        hospital.createCharge(patient, service);

        refreshTable();


        JOptionPane.showMessageDialog(
                this,
                "Charge added successfully.\n\n" +
                        "Patient: " + patient.getName() +
                        "\nService: " + service.getName() +
                        "\nCost: " + service.getCost()
        );
    }

    private void deleteCharge() {

        int selectedRow = billTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a charge first."
            );
            return;
        }

        int chargeId =
                (int) tableModel.getValueAt(selectedRow, 0);

        for (Patient patient : hospital.getPatients()) {

            for (Charge charge :
                    patient.getBill().getCharges()) {

                if (charge.getId() == chargeId) {

                    int result =
                            JOptionPane.showConfirmDialog(
                                    this,
                                    "Delete this charge?",
                                    "Confirm Delete",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.WARNING_MESSAGE
                            );

                    if (result != JOptionPane.YES_OPTION) {
                        return;
                    }

                    patient.getBill().removeCharge(charge);

                    refreshTable();

                    JOptionPane.showMessageDialog(
                            this,
                            "Charge deleted successfully."
                    );

                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Charge not found."
        );
    }
}