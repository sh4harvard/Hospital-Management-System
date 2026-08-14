package Hospital.GUI;

import javax.swing.*;
import java.awt.*;

public class DoctorEditPanel extends JPanel {
    public DoctorEditPanel(){

        setLayout(new BorderLayout());

        //

        JLabel title = new JLabel("Edit Doctor");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        //

        JPanel editFormPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        //


        JPanel personalPanel = new JPanel(new BorderLayout());
        personalPanel.add(new JLabel("Personal Information"), BorderLayout.NORTH);

        JPanel personalFormPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        personalFormPanel.add(new JLabel("Name:"));
        personalFormPanel.add(new JTextField());

        personalFormPanel.add(new JLabel("Age:"));
        personalFormPanel.add(new JTextField());

        personalFormPanel.add(new JLabel("Gender:"));
        String[] genders = {"Male", "Female"};
        personalFormPanel.add(new JComboBox<>(genders));

        personalFormPanel.add(new JLabel("Phone:"));
        personalFormPanel.add(new JTextField());

        personalPanel.add(personalFormPanel, BorderLayout.CENTER);
        editFormPanel.add(personalPanel);
        //

        JPanel professionalPanel = new JPanel(new BorderLayout());
        professionalPanel.add(new JLabel("Professional Information"), BorderLayout.NORTH);

        JPanel professionalFormPanel = new JPanel(new GridLayout(5, 2, 10, 10));


        professionalFormPanel.add(new JLabel("Specialty:"));
        professionalFormPanel.add(new JTextField());

        professionalFormPanel.add(new JLabel("Ward:"));
        String[] wards = {
            "Cardiology",
            "Emergency",
            "Neurology",
            "General"
        };
        professionalFormPanel.add(new JComboBox<>(wards));

        professionalFormPanel.add(new JLabel("Shift Start:"));
        professionalFormPanel.add(new JTextField());

        professionalFormPanel.add(new JLabel("Shift End:"));
        professionalFormPanel.add(new JTextField());

        professionalFormPanel.add(new JLabel("Capacity:"));
        professionalFormPanel.add(new JTextField());


        professionalPanel.add(professionalFormPanel, BorderLayout.CENTER);
        editFormPanel.add(professionalPanel);

        //

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
