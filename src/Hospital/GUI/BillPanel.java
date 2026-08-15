package Hospital.GUI;

import Hospital.Core.Charge;
import Hospital.Core.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BillPanel extends JPanel{

    private Patient patient;

    private JLabel totalLabel;
    private JLabel paidLabel;
    private JLabel remainingLabel;
    private JTable chargesTable;
    private DefaultTableModel tableModel;

    public BillPanel(){

        setLayout(new BorderLayout());


        JLabel title = new JLabel("Bill");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);



        String[] titles = {
            "Service",
            "Cost",
            "Status"
        };
        Object[][] data = {
                {"Appointment", "$50", "Paid"},
                {"X-Ray", "$80", "Unpaid"},
                {"Medication", "$25", "Paid"}
        };
        JTable billTable = new JTable(data, titles);
        JScrollPane scrollPane = new JScrollPane(billTable);
        add(scrollPane, BorderLayout.CENTER);



        JPanel summaryPanel = new JPanel(new GridLayout(3, 2));
        summaryPanel.add(new JLabel("Total:"));
        summaryPanel.add(new JLabel("$180"));

        summaryPanel.add(new JLabel("Paid:"));
        summaryPanel.add(new JLabel("$100"));

        summaryPanel.add(new JLabel("Remaining:"));
        summaryPanel.add(new JLabel("$80"));
        add(summaryPanel, BorderLayout.SOUTH);
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        updateBill();
    }

    private void updateBill() {

        if (patient == null || patient.getBill() == null) {
            return;
        }

        tableModel.setRowCount(0);

        double total = 0;
        double paid = 0;

        for (Charge charge : patient.getBill().getCharges()) {

            double amount = charge.getService().getCost();

            total += amount;

            if (charge.getPayStatus()) {
                paid += amount;
            }

            tableModel.addRow(new Object[] {
                    charge.getId(),
                    charge.getService().getName(),
                    amount,
                    charge.getPayStatus() ? "Paid" : "Unpaid",
                    charge.getDate()
            });
        }

        double remaining = total - paid;

        totalLabel.setText(String.valueOf(total));
        paidLabel.setText(String.valueOf(paid));
        remainingLabel.setText(String.valueOf(remaining));
    }
}
