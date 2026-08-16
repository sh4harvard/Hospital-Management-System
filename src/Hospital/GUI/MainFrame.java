package Hospital.GUI;

import Database.DatabaseSaver;
import Hospital.Core.HospitalSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class MainFrame extends JFrame{

    private final HospitalSystem hospital;

    public MainFrame(HospitalSystem hospital){
        super("Hospital Management System");
        this.hospital = hospital;


        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        getContentPane().setBackground(AppColors.BACKGROUND);


        add(new TitlePanel(), BorderLayout.NORTH);


        ContentPanel contentPanel = new ContentPanel(hospital);

        add(new MenuPanel(contentPanel), BorderLayout.WEST);

        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);

        // Save data on program close
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                try {
                    DatabaseSaver saver = new DatabaseSaver(hospital);
                    saver.save();

                    System.out.println("Database saved successfully.");

                    dispose();

                } catch (SQLException ex) {
                    ex.printStackTrace();

                    JOptionPane.showMessageDialog(
                            MainFrame.this,
                            "Could not save the database.\n" +
                                    "The application will remain open.",
                            "Database Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }
}
