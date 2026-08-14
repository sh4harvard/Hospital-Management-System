package Hospital.GUI;

import javax.swing.*;
import java.awt.*;

public class PatientDetailPanel extends JPanel {
    public PatientDetailPanel(){

        setLayout(new BorderLayout());


        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JButton backtoBtn = new JButton("<- Patients");
        JLabel patientID = new JLabel("Patient #10");

        headerPanel.add(backtoBtn, BorderLayout.WEST);
        headerPanel.add(patientID, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);


        // Main Body
        JPanel patientDisplayINFO = new JPanel(new BorderLayout());

        //
        JPanel basicINFO = new JPanel(new BorderLayout());

        JPanel patientIdentity = new JPanel(new GridLayout(0, 1));
        JLabel namePatient = new JLabel("'Patient ICON' Ali Mohammdi");
        JLabel idPatient = new JLabel("Patient ID: 10");
        patientIdentity.add(namePatient);
        patientIdentity.add(idPatient);

        basicINFO.add(patientIdentity, BorderLayout.NORTH);

        //

        JPanel patientINFO = new JPanel(new BorderLayout());

        JLabel personalInfo = new JLabel("Personal Informetion");
        patientINFO.add(personalInfo, BorderLayout.NORTH);

        JPanel infoTable = new JPanel(new GridLayout(2, 4));
        infoTable.add(new JLabel("Age:"));
        infoTable.add(new JLabel("24"));
        infoTable.add(new JLabel("Phone:"));
        infoTable.add(new JLabel("09124837294"));
        infoTable.add(new JLabel("Gender:"));
        infoTable.add(new JLabel("Male"));
        infoTable.add(new JLabel("Ward:"));
        infoTable.add(new JLabel("Emergency"));
        patientINFO.add(infoTable, BorderLayout.CENTER);

        basicINFO.add(patientINFO, BorderLayout.CENTER);

        patientDisplayINFO.add(basicINFO, BorderLayout.NORTH);

        //

        JPanel detailINFO = new JPanel();

        JTabbedPane infotabs = new JTabbedPane();


        infotabs.add("Medical Record", new MedicalRecordPanel());
        infotabs.add("Bill", new BillPanel());
        infotabs.add("Appointments", new AppointmentsPanel());


        // Footer

    }
}
