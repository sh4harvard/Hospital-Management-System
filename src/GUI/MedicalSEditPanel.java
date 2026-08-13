package GUI;

import javax.swing.*;
import java.awt.*;

public class MedicalSEditPanel extends JPanel {
    public MedicalSEditPanel(){

        setLayout(new BorderLayout());



        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel title = new JLabel("Edit Medical Service");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(title);

        add(titlePanel, BorderLayout.NORTH);


        //


        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));


        formPanel.add(new JLabel("Service Name:"));
        formPanel.add(new JTextField("X-Ray"));
        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JLabel("Chest X-Ray"));
        formPanel.add(new JLabel("Cost:"));
        formPanel.add(new JTextField("$80"));

        add(formPanel, BorderLayout.CENTER);


        //


        JPanel footerButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton cancelBtn = new JButton("Cancel");
        JButton saveBtn = new JButton("Save Changes");

        footerButtonPanel.add(cancelBtn);
        footerButtonPanel.add(saveBtn);

        add(footerButtonPanel, BorderLayout.SOUTH);
    }
}
