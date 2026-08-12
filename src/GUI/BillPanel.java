package GUI;

import com.oracle.xmlns.internal.webservices.jaxws_databinding.JavaParam;

import javax.swing.*;
import java.awt.*;

public class BillPanel extends JPanel{
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
}
