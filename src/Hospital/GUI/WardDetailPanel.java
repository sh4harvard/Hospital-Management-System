package Hospital.GUI;

import javax.swing.*;
import java.awt.*;

public class WardDetailPanel extends JPanel {
    public WardDetailPanel(){

        setLayout(new BorderLayout());

        //

        JPanel headerPanel = new JPanel(new BorderLayout());

        JButton backtoBtn = new JButton("<- Wards");
        JLabel title = new JLabel("Ward Detail");
        headerPanel.add(backtoBtn, BorderLayout.WEST);
        headerPanel.add(title, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        //

        JPanel mainBodyPanel = new JPanel(new BorderLayout());


        JPanel infoPanel = new JPanel(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleWard = new JLabel("Emergency");
        titlePanel.add(titleWard);
        infoPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.add(new JLabel("Ward Information"), BorderLayout.NORTH);

        JPanel detailWard = new JPanel(new GridLayout(3, 2));
        detailWard.add(new JLabel("Capacity:"));
        detailWard.add(new JLabel("200"));
        detailWard.add(new JLabel("Occupied:"));
        detailWard.add(new JLabel("150"));
        detailWard.add(new JLabel("Available:"));
        detailWard.add(new JLabel("50"));

        detailPanel.add(detailWard, BorderLayout.CENTER);
        infoPanel.add(detailPanel, BorderLayout.CENTER);

        mainBodyPanel.add(infoPanel, BorderLayout.NORTH);



        JPanel pdlistPanel = new JPanel(new GridLayout(2, 1, 10, 10));


        JPanel patientsPanel = new JPanel(new BorderLayout());

        patientsPanel.add(new JLabel("Patients"), BorderLayout.NORTH);

        String[] patientTitles = {
            "ID", "Name", "Age", "Gender", "Phone"
        };
        Object[][] patientData = {
            {101, "Ali Ahmadi", 25, "Male", "091294837294"},
            {102, "Sara Mohammadi", 19, "Female", "09129483949"}
        };

        JTable patientTable = new JTable(patientData, patientTitles);
        patientTable.setRowHeight(30);

        patientsPanel.add(new JScrollPane(patientTable), BorderLayout.CENTER);

        pdlistPanel.add(patientsPanel);



        JPanel doctorsPanel = new JPanel(new BorderLayout());

        doctorsPanel.add(new JLabel("Doctors"), BorderLayout.NORTH);

        String[] doctorTitles = {
            "ID", "Name", "Specialty", "Shift", "Capacity"
        };
        Object[][] doctorData = {
            {201, "Dr. Ahmadi", "Emergency", "08:00 - 16:00", "20"},
            {202, "Dr. Karimi", "Emergency", "10:00 - 18:00", "15"}
        };

        JTable doctorTable = new JTable(doctorData, doctorTitles);
        doctorTable.setRowHeight(30);

        doctorsPanel.add(new JScrollPane(doctorTable), BorderLayout.CENTER);

        pdlistPanel.add(doctorsPanel);

        mainBodyPanel.add(pdlistPanel, BorderLayout.CENTER);

        add(mainBodyPanel, BorderLayout.CENTER);
    }
}
