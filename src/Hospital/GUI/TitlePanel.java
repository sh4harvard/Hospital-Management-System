package Hospital.GUI;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {

    public TitlePanel() {

        setLayout(new BorderLayout());
        setBackground(AppColors.WHITE);

        setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 20, 25
                )
        );

        JLabel title =
                new JLabel(
                        "Welcome to ShMonfared's Hospital MS!",
                        JLabel.LEFT
                );

        title.setFont(AppFonts.PAGE_TITLE);

        title.setForeground(
                AppColors.DARK_BLUE
        );

        add(title, BorderLayout.WEST);
    }
}