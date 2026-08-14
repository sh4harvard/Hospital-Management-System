package Hospital.GUI;

import javax.swing.*;
import java.awt.*;

public class HospitalBillPanel extends JPanel {
    public HospitalBillPanel(){

        setLayout(new BorderLayout());

        //

        JPanel headerPanel = new JPanel();
        JLabel title = new JLabel("Hospital Billing");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(title);

        add(headerPanel, BorderLayout.NORTH);

        //

        String[] titles = {
            "Patient",
            "Service",
            "Cost",
            "Status"
        };
        Object[][] data = {
            {"Ali Ahmadi", "Consultation", "$50", "Paid"},
            {"Sara Mohammadi", "MRI", "$200", "UnPaid"},
            {"Reza Akbary", "X-Ray", "$80", "UnPaid"}
        };

        JTable billTable = new JTable(data, titles);
        billTable.setRowHeight(30);

        add(new JScrollPane(billTable), BorderLayout.CENTER);

        //

        JPanel summaryPanel = new JPanel(new GridLayout(3, 2));

        summaryPanel.add(new JLabel("Paid:"));
        summaryPanel.add(new JLabel("$200"));
        summaryPanel.add(new JLabel("Unpaid:"));
        summaryPanel.add(new JLabel("$150"));
        summaryPanel.add(new JLabel("Total:"));
        summaryPanel.add(new JLabel("$350"));

        add(summaryPanel, BorderLayout.SOUTH);

    }
}
