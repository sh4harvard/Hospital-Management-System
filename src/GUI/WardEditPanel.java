package GUI;

import javax.swing.*;
import java.awt.*;

public class WardEditPanel extends JPanel {
    public WardEditPanel(){

        setLayout(new BorderLayout());


        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel title = new JLabel("Edit Ward");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(title);

        add(titlePanel, BorderLayout.NORTH);


        //


        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        formPanel.add(new JLabel("Ward Name:"));
        formPanel.add(new JTextField("Emergency"));
        formPanel.add(new JLabel("Capacity:"));
        formPanel.add(new JTextField("30"));

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
