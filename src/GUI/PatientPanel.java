package GUI;

import javax.swing.*;
import java.awt.*;

public class PatientPanel extends JPanel {
    public PatientPanel(){

        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());

        JPanel patientsectionPanel = new JPanel(new BorderLayout());
        JLabel section = new JLabel("Patients");
        patientsectionPanel.add(section, BorderLayout.WEST);
        JButton addPatient = new JButton("Add Patient");
        patientsectionPanel.add(addPatient, BorderLayout.EAST);
        headerPanel.add(patientsectionPanel, BorderLayout.NORTH);

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
            "Age",
            "Gender",
            "Phone",
            "Ward"
        };

        Object[][] tableDataTemp = {
                {1, "Ali Ahmadi", 24, "Male", "09128473625", "Emergency"},
                {2, "Sara Mohammadi", 31, "Female", "09103728493", "ICU"},
                {3, "Reza Nazary", 45, "Male", "09193847364", "OR"}
        };
        JTable tablePatients = new JTable(tableDataTemp, tableTitles);
        JScrollPane scroltable = new JScrollPane(tablePatients);
        add(scroltable, BorderLayout.CENTER);


        // Footer
        JPanel buttonPanel = new JPanel();
        JButton viewBtn = new JButton("View Patient");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        buttonPanel.add(viewBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);

        add(buttonPanel, BorderLayout.SOUTH);

    }
}
