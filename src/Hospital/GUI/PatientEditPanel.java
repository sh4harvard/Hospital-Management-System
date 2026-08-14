package Hospital.GUI;

import javax.swing.*;
import java.awt.*;

public class PatientEditPanel extends JPanel {
    public PatientEditPanel(){

        setLayout(new BorderLayout());

        //

        JLabel title = new JLabel("Edit Patient");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        //

        JPanel editFormPanel = new JPanel(new BorderLayout());

        editFormPanel.add(new JLabel("Personal Information"), BorderLayout.NORTH);

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


        editFormPanel.add(formPanel, BorderLayout.CENTER);
        add(editFormPanel, BorderLayout.CENTER);

        //

        JPanel footerBtns = new JPanel(new FlowLayout((FlowLayout.RIGHT)));

        JButton cancelBtn = new JButton("Cancel");
        JButton saveBtn = new JButton("Save Changes");
        footerBtns.add(cancelBtn);
        footerBtns.add(saveBtn);

        add(footerBtns, BorderLayout.SOUTH);


    }
}
