package GUI;

import javax.swing.*;
import java.awt.*;

public class HAppointmentsPanel extends JPanel {
    public HAppointmentsPanel(){

        setLayout(new BorderLayout());



        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel title = new JLabel(" App icon  Appointments");
        title.setFont(new Font("Arial", Font.BOLD, 24));

        headerPanel.add(title);

        add(headerPanel, BorderLayout.NORTH);


        //


        String[] titles = {
            "ID",
            "Patient",
            "Doctor",
            "Date",
            "Time",
            "Status"
        };
        Object[][] data = {
            {1, "Ali Ahmadi", "Dr. Karimi", "2026-08-15", "10:00", "Completed"},
            {2, "Sara Mohammadi", "Dr. Ahmadi", "2026-09-12", "11:30", "Completed"},
            {3, "Reza Karimi", "Dr. Hosseini", "2027-03-16", "09:00", "Scheduled"}
        };

        JTable table = new JTable(data, titles);
        table.setRowHeight(30);

        add(new JScrollPane(table), BorderLayout.CENTER);


        //


        JPanel footerButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton cancelBtn = new JButton("Cancel");

        footerButtonPanel.add(addBtn);
        footerButtonPanel.add(editBtn);
        footerButtonPanel.add(cancelBtn);

        add(footerButtonPanel, BorderLayout.SOUTH);
    }
}
