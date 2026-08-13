package GUI;

import javax.swing.*;
import java.awt.*;

public class DoctorPanel extends JPanel {
    public DoctorPanel(){

        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());

        JPanel doctorsectionPanel = new JPanel(new BorderLayout());
        JLabel section = new JLabel("Doctors");
        doctorsectionPanel.add(section, BorderLayout.WEST);
        JButton addDoctor = new JButton("Add Doctor");
        doctorsectionPanel.add(addDoctor, BorderLayout.EAST);
        headerPanel.add(doctorsectionPanel, BorderLayout.NORTH);

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

        Object[][] tableDataTemp = {
                {101, "Ali Ahmadi", "Cardiology", "Cardiology", "08:00 - 16:00", 20},
                {102, "Tina Mohamadi", "Neurology", "Neurology", "10:00 - 18:00", 15},
                {103, "ALiof", "General", "General", "08:00 - 14:00", 12}
        };
        JTable tableDoctors = new JTable(tableDataTemp, tableTitles);
        JScrollPane scroltable = new JScrollPane(tableDoctors);
        add(scroltable, BorderLayout.CENTER);


        // Footer
        JPanel buttonPanel = new JPanel();
        JButton viewBtn = new JButton("View Doctor");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        buttonPanel.add(viewBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
