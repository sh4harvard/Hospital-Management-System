package GUI;

import javax.swing.*;
import java.awt.*;

public class WardPanel extends JPanel {
    public WardPanel(){

        setLayout(new BorderLayout());

        //

        JPanel headerPanel = new JPanel();
        JLabel title = new JLabel("Wards");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(title);

        add(headerPanel, BorderLayout.NORTH);
        //

        JPanel mainBody = new JPanel(new BorderLayout());

        String[] titles = {
            "Ward",
            "Capacity",
            "Patients",
            "Doctors"
        };
        Object[][] data = {
            {"Cardiology", 30, 20, 5},
            {"Neurology", 20, 15, 5},
            {"Emengency", 40, 3, 10},
            {"General", 40, 3, 10}
        };

        JTable wardTable = new JTable(data, titles);
        wardTable.setRowHeight(30);

        mainBody.add(wardTable, BorderLayout.CENTER);

        add(mainBody, BorderLayout.CENTER);

        //

        JPanel footerBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton viewBtn = new JButton("View");
        JButton editBtn = new JButton("Edit");
        footerBtnPanel.add(viewBtn);
        footerBtnPanel.add(editBtn);

        add(footerBtnPanel, BorderLayout.SOUTH);

    }
}
