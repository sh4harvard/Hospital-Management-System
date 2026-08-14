package Hospital.GUI;

import javax.swing.*;
import java.awt.*;

public class AppointmentsPanel extends JPanel {
    public AppointmentsPanel(){

        setLayout(new BorderLayout());


        JLabel title = new JLabel("Appointments");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);



        String[] titles = {
            "Date", "Time", "Doctor", "Status"
        };

        Object[][] data = {
                {"12 Mar 2026", "10:00", "Dr. Ali", "Scheduled"},
                {"15 Nov 2026", "14:00", "Dr. Ahmad", "Completed"}
        };

        JTable appointmentsTable = new JTable(data, titles);
        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        add(scrollPane, BorderLayout.CENTER);


        JPanel buttonFooterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton newAppointmentButton = new JButton("New Appointment");
        buttonFooterPanel.add(newAppointmentButton);
        add(buttonFooterPanel, BorderLayout.SOUTH);
    }
}
