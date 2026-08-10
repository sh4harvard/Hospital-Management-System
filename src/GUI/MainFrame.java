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

        add(new MenuPanel(), BorderLayout.WEST);

        add(new ContentPanel(), BorderLayout.CENTER);

        setVisible(true);
    }
}
