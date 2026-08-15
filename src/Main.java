import Database.DatabaseInitializer;
import Database.DatabaseLoader;
import Hospital.Core.HospitalSystem;
import Hospital.GUI.MainFrame;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        HospitalSystem hospital = new HospitalSystem();
        DatabaseInitializer.initialize();

        try {
            DatabaseLoader loader = new DatabaseLoader(hospital);
            loader.load();

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Start GUI after everything fully loaded
        SwingUtilities.invokeLater(() -> {

            MainFrame frame = new MainFrame(hospital);

            frame.setVisible(true);
        });
    }
}