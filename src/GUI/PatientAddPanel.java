package GUI;

import javax.swing.*;
import java.awt.*;

public class PatientAddPanel extends JPanel {
    public PatientAddPanel(){

        setLayout(new BorderLayout());

        //

        JLabel title = new JLabel("Add Patient");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        //

        JPanel addFormPanel = new JPanel(new BorderLayout());

        addFormPanel.add(new JLabel("Personal Information"), BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        formPanel.add(new JLabel("Name:"));
        formPanel.add(new JTextField());

        formPanel.add(new JLabel("Age:"));
        formPanel.add(new JTextField());

        formPanel.add(new JLabel("Gender:"));
        String[] genders = {"Male", "Female"};
        formPanel.add(new JComboBox<>(genders));

        formPanel.add(new JLabel("Phone:"));
        formPanel.add(new JTextField());

        formPanel.add(new JLabel("Ward:"));
        String[] wards = {
                "Cardiology",
                "Neurology",
                "General"
        };
        formPanel.add(new JComboBox<>(wards));


        addFormPanel.add(formPanel, BorderLayout.CENTER);
        add(addFormPanel, BorderLayout.CENTER);

        //

        JPanel footerBtns = new JPanel(new FlowLayout((FlowLayout.RIGHT)));

        JButton cancelBtn = new JButton("Cancel");
        JButton saveBtn = new JButton("Add Patient");
        footerBtns.add(cancelBtn);
        footerBtns.add(saveBtn);

        add(footerBtns, BorderLayout.SOUTH);
    }
}
