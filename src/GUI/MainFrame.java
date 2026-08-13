package GUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{

    public MainFrame(){
        super("Hospital Management System");

        setSize(1100,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());


        add(new TitlePanel(), BorderLayout.NORTH);


        ContentPanel contentPanel = new ContentPanel();

        add(new MenuPanel(contentPanel), BorderLayout.WEST);

        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
