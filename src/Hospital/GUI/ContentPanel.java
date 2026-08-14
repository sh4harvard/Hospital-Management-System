package Hospital.GUI;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {
    private CardLayout cardLayout;

    public ContentPanel(){

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
