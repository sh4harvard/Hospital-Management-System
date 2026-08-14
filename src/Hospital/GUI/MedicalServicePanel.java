package Hospital.GUI;

import javax.swing.*;
import java.awt.*;

public class MedicalServicePanel extends JPanel {
    public MedicalServicePanel(){

        setLayout(new BorderLayout());


        //

        JPanel headerPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Medical Services");
        title.setFont(new Font("Arial", Font.BOLD, 25));

        JButton addBtn = new JButton("Add Service");

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(addBtn, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        //

        String[] titles = {
            "Service",
            "Description",
            "Cost"
        };
        Object[][] data = {
            {"Consultation", "General doctor consultation", "$50"},
            {"X-Ray", "Chest X-Ray", "$80"},
            {"MRI", "MRI scan", "$200"}
        };

        JTable table = new JTable(data, titles);
        table.setRowHeight(30);

        add(new JScrollPane(table), BorderLayout.CENTER);

        //

        JPanel footerButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        footerButtonPanel.add(new JButton("Edit"));
        footerButtonPanel.add(new JButton("Delete"));

        add(footerButtonPanel, BorderLayout.SOUTH);
    }
}
