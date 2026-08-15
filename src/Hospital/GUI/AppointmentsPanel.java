package Hospital.GUI;

import Hospital.Core.Patient;

import javax.swing.*;
import java.awt.*;

public class AppointmentsPanel extends JPanel {

    private Patient patient;

    public AppointmentsPanel(){

        setLayout(new BorderLayout());


        JLabel title = new JLabel("Appointments");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);




        JTable appointmentsTable = new JTable(data, titles);
        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        add(scrollPane, BorderLayout.CENTER);


        JPanel buttonFooterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton newAppointmentButton = new JButton("New Appointment");
        buttonFooterPanel.add(newAppointmentButton);
        add(buttonFooterPanel, BorderLayout.SOUTH);
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        updateAppointments();
    }

    private void updateAppointments() {

        tableModel.setRowCount(0);

        if (patient == null) {
            return;
        }

        for (Appointment appointment : patient.getAppointments()) {

            tableModel.addRow(new Object[] {
                    appointment.getId(),
                    appointment.getDoctor().getName(),
                    appointment.getDate(),
                    appointment.getTime(),
                    appointment.getApStatus()
            });
        }
    }
}
