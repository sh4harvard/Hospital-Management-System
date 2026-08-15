package Hospital.GUI;

import Hospital.Core.Charge;
import Hospital.Core.HospitalSystem;
import Hospital.Core.Patient;

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

        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        JLabel title = new JLabel("Hospital Billing");
        title.setFont(new Font("Arial", Font.BOLD, 24));
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
        billTable.setRowHeight(30);

        add(new JScrollPane(billTable), BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        JPanel summaryPanel = new JPanel(new GridLayout(3, 2));

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

        JButton deleteBtn = new JButton("Delete Charge");
        deleteBtn.addActionListener(e -> deleteCharge());

        JButton payBtn = new JButton("Mark as Paid");
        payBtn.addActionListener(e -> markAsPaid());
        btn.add(deleteBtn);
        btn.add(payBtn);

        footer.add(btn, BorderLayout.EAST);

        add(footer, BorderLayout.SOUTH);

        refreshTable();
    }

    public void refreshTable() {

        tableModel.setRowCount(0);

        double paid = 0;
        double unpaid = 0;

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