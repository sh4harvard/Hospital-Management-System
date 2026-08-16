package Hospital.GUI;

import Hospital.Core.MedicalRecord;
import Hospital.Core.Patient;

import javax.swing.*;
import java.awt.*;

public class MedicalRecordPanel extends JPanel {

    private Patient patient;

    private JTextArea diagnosisINFO;
    private JTextArea prescribINFO;
    private JTextArea noteINFO;

    private JLabel lastPrescrib;

    public MedicalRecordPanel(){

        setLayout(new BorderLayout());



        // Header
        JLabel title = new JLabel("Medical Record");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);


        // Main Body
        JPanel medicalINFOPanel = new JPanel();
        medicalINFOPanel.setLayout(new BoxLayout(medicalINFOPanel, BoxLayout.Y_AXIS));


        // Diagnosis
        JLabel diagnosisField = new JLabel("Diagnosis");

        diagnosisINFO = new JTextArea(4, 40);
        diagnosisINFO.setLineWrap(true);
        diagnosisINFO.setWrapStyleWord(true);
        diagnosisINFO.setEditable(false);


        JScrollPane diagnosisScroll = new JScrollPane(diagnosisINFO);


        // Prescriptions & Medications
        JLabel prescribField = new JLabel("Prescriptions & Medications");

        prescribINFO = new JTextArea(4, 40);
        prescribINFO.setLineWrap(true);
        prescribINFO.setWrapStyleWord(true);
        prescribINFO.setEditable(false);



        JScrollPane prescribScroll = new JScrollPane(prescribINFO);


        // Notes
        JLabel noteField = new JLabel("Notes");

        noteINFO = new JTextArea(4, 40);
        noteINFO.setLineWrap(true);
        noteINFO.setWrapStyleWord(true);
        noteINFO.setEditable(false);


        JScrollPane noteScroll = new JScrollPane(noteINFO);

        medicalINFOPanel.add(diagnosisField);
        medicalINFOPanel.add(diagnosisScroll);
        medicalINFOPanel.add(prescribField);
        medicalINFOPanel.add(prescribScroll);
        medicalINFOPanel.add(noteField);
        medicalINFOPanel.add(noteScroll);

        // Vertical magin between items
        medicalINFOPanel.add(Box.createVerticalStrut(10));
        add(medicalINFOPanel, BorderLayout.CENTER);


        // Footer
        JPanel medicalPanelFooter = new JPanel(new BorderLayout());

        lastPrescrib = new JLabel();
        lastPrescrib.setText("No diagnosis date");
        medicalPanelFooter.add(lastPrescrib, BorderLayout.WEST);

        JButton editMedicalINFO = new JButton("Edit");
        editMedicalINFO.setAlignmentX(JButton.EAST);
        medicalPanelFooter.add(editMedicalINFO, BorderLayout.EAST);

        editMedicalINFO.addActionListener(e -> {

            if (editMedicalINFO.getText().equals("Edit")) {

                diagnosisINFO.setEditable(true);
                prescribINFO.setEditable(true);
                noteINFO.setEditable(true);

                editMedicalINFO.setText("Save");

            } else {

                if (patient != null && patient.getMedicalRecord() != null) {

                    MedicalRecord record = patient.getMedicalRecord();

                    record.setDiagnosis(diagnosisINFO.getText());
                    record.setPrescription(prescribINFO.getText());
                    record.setNotes(noteINFO.getText());
                    record.setLastDiagnoseDate(java.time.LocalDate.now());

                    diagnosisINFO.setEditable(false);
                    prescribINFO.setEditable(false);
                    noteINFO.setEditable(false);

                    editMedicalINFO.setText("Edit");
                }
            }
        });

        add(medicalPanelFooter, BorderLayout.SOUTH);
    }

    public void setPatient(Patient patient) {
        this.patient = patient;

        updateMedicalRecord();
    }

    private void updateMedicalRecord() {

        if (patient == null || patient.getMedicalRecord() == null) {
            diagnosisINFO.setText("");
            prescribINFO.setText("");
            noteINFO.setText("");
            lastPrescrib.setText("");
            return;
        }

        MedicalRecord record = patient.getMedicalRecord();

        diagnosisINFO.setText(record.getDiagnosis());
        prescribINFO.setText(record.getPrescription());
        noteINFO.setText(record.getNotes());

        if (record.getLastDiagnoseDate() != null) {
            lastPrescrib.setText(
                    record.getLastDiagnoseDate().toString()
            );
        } else {
            lastPrescrib.setText("No diagnosis date");
        }
    }
}
