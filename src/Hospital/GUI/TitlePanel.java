package Hospital.GUI;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {
    public TitlePanel(){

        JLabel title = new JLabel("Welcome to ShMonfared's Hospital MS!", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));

        add(title);
    }
}
