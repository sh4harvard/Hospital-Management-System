package Hospital.GUI;

import Hospital.Core.HospitalSystem;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {
    private CardLayout cardLayout;
    private HospitalSystem hospital;

    public ContentPanel(HospitalSystem hospital){

        this.hospital = hospital;

        cardLayout = new CardLayout();
        setLayout(cardLayout);

        add(new HospitalPanel(), "HOSPITAL");
        add(new PatientPanel(), "PATIENTS");
        add(new DoctorPanel(), "DOCTORS");
        add(new HAppointmentsPanel(), "APPOINTMENTS");
    }

    public void showPanel(String name) {
        cardLayout.show(this, name);
    }
}
