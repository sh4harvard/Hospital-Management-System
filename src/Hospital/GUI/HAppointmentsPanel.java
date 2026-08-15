package Hospital.GUI;

import Hospital.Core.*;
import Hospital.Core.enums.AppointmentStatus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HAppointmentsPanel extends JPanel {

    private final HospitalSystem hospital;
    private final ContentPanel contentPanel;

    private JTable table;
    private DefaultTableModel tableModel;


    public HAppointmentsPanel(HospitalSystem hospital, ContentPanel contentPanel) {

        this.hospital = hospital;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel title =
                new JLabel("Appointments");

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        headerPanel.add(title);

        add(headerPanel, BorderLayout.NORTH);


        String[] titles = {
                "ID",
                "Patient",
                "Doctor",
                "Date",
                "Time",
                "Status"
        };

        tableModel = new DefaultTableModel(titles, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);

        table.setRowHeight(30);

        add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

        // Footer
        JPanel footerButtonPanel =
                new JPanel(
                        new FlowLayout(FlowLayout.RIGHT)
                );

        JButton addBtn =
                new JButton("Add");

        JButton completeBtn =
                new JButton("Complete");

        JButton cancelBtn =
                new JButton("Cancel");

        footerButtonPanel.add(addBtn);
        footerButtonPanel.add(completeBtn);
        footerButtonPanel.add(cancelBtn);

        add(
                footerButtonPanel,
                BorderLayout.SOUTH
        );


        refreshTable();


        addBtn.addActionListener(e ->
                contentPanel.showAppointmentAdd()
        );

        completeBtn.addActionListener(e ->
                completeSelectedAppointment()
        );

        cancelBtn.addActionListener(e ->
                cancelSelectedAppointment()
        );
    }

    public void refreshTable() {

        tableModel.setRowCount(0);

        for (Appointment appointment : hospital.getAppointments()) {

            Patient patient = appointment.getPatient();
            Doctor doctor = appointment.getDoctor();

            Object[] row = {
                    appointment.getId(),
                    patient.getName(),
                    doctor.getName(),
                    appointment.getDate(),
                    appointment.getTime(),
                    appointment.getApStatus()
            };

            tableModel.addRow(row);
        }
    }

    private Appointment getSelectedAppointment() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an appointment first."
            );

            return null;
        }

        int appointmentId =
                (int) table.getValueAt(row, 0);

        for (Appointment appointment :
                hospital.getAppointments()) {

            if (appointment.getId() == appointmentId) {
                return appointment;
            }
        }

        return null;
    }

    private void completeSelectedAppointment() {

        Appointment appointment = getSelectedAppointment();

        if (appointment == null) {
            return;
        }

        if (appointment.getApStatus() != AppointmentStatus.SCHEDULED) {
            JOptionPane.showMessageDialog(
                    this,
                    "Only scheduled appointments can be completed."
            );
            return;
        }

        hospital.closeAppointment(appointment);

        refreshTable();
    }

    private void cancelSelectedAppointment() {

        Appointment appointment = getSelectedAppointment();

        if (appointment == null) {
            return;
        }

        if (appointment.getApStatus() != AppointmentStatus.SCHEDULED) {
            JOptionPane.showMessageDialog(
                    this,
                    "Only scheduled appointments can be cancelled."
            );
            return;
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to cancel this appointment?",
                "Cancel Appointment",
                JOptionPane.YES_NO_OPTION
        );

        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        hospital.cancelAppointment(appointment);

        refreshTable();
    }
}