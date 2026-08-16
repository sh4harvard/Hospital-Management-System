package Hospital.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class UIStyle {

    private UIStyle() {
        // Prevent creating objects of this class
    }

    public static void styleTitle(JLabel label) {
        label.setFont(AppFonts.PAGE_TITLE);
        label.setForeground(AppColors.DARK_BLUE);
    }

    public static void styleButton(JButton button) {
        button.setFont(AppFonts.BUTTON);
        button.setForeground(AppColors.WHITE);
        button.setBackground(AppColors.PRIMARY_BLUE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }

    public static void styleMenuButton(JButton button) {

        button.setFont(AppFonts.BUTTON);

        button.setForeground(AppColors.DARK_BLUE);

        button.setBackground(AppColors.WHITE);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        // button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void styleSecondaryButton(JButton button) {
        button.setFont(AppFonts.BUTTON);
        button.setForeground(AppColors.PRIMARY_BLUE);
        button.setBackground(AppColors.WHITE);
        button.setFocusPainted(false);
    }

    public static void styleDeleteButton(JButton button) {
        button.setFont(AppFonts.BUTTON);
        button.setForeground(AppColors.DANGER);
        button.setBackground(AppColors.WHITE);
        button.setFocusPainted(false);
    }

    public static Border createPanelBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppColors.BORDER),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );
    }

    public static void styleTable(JTable table) {

        table.setFont(AppFonts.NORMAL);
        table.setForeground(AppColors.TEXT);
        table.setBackground(AppColors.WHITE);

        table.setSelectionBackground(AppColors.LIGHT_BLUE);
        table.setSelectionForeground(AppColors.TEXT);

        table.setGridColor(AppColors.BORDER);

        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);

        table.setRowHeight(30);

        JTableHeader header = table.getTableHeader();

        header.setFont(AppFonts.BUTTON);
        header.setForeground(AppColors.WHITE);
        header.setBackground(AppColors.DARK_BLUE);

        header.setReorderingAllowed(false);
    }
}