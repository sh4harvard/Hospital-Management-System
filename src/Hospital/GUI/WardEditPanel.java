package Hospital.GUI;

import Hospital.Core.HospitalSystem;
import Hospital.Core.Ward;

import javax.swing.*;
import java.awt.*;

public class WardEditPanel extends JPanel {

    private final HospitalSystem hospital;
    private final ContentPanel contentPanel;

    private Ward ward;

    private JTextField nameField;
    private JTextField capacityField;

    public WardEditPanel(
            HospitalSystem hospital,
            ContentPanel contentPanel) {

        this.hospital = hospital;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout());

        // Header

        JPanel titlePanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel title =
                new JLabel("Edit Ward");

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titlePanel.add(title);

        add(
                titlePanel,
                BorderLayout.NORTH
        );


        // Form

        JPanel formPanel =
                new JPanel(
                        new GridLayout(2, 2, 10, 10)
                );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        formPanel.add(
                new JLabel("Ward Name:")
        );

        nameField =
                new JTextField();

        formPanel.add(nameField);


        formPanel.add(
                new JLabel("Capacity:")
        );

        capacityField =
                new JTextField();

        formPanel.add(capacityField);


        add(
                formPanel,
                BorderLayout.CENTER
        );


        // footer

        JPanel footerButtonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        JButton cancelBtn =
                new JButton("Cancel");

        JButton saveBtn =
                new JButton("Save Changes");


        cancelBtn.addActionListener(e ->
                contentPanel.showHospital()
        );

        saveBtn.addActionListener(e ->
                saveChanges()
        );


        footerButtonPanel.add(cancelBtn);
        footerButtonPanel.add(saveBtn);

        add(
                footerButtonPanel,
                BorderLayout.SOUTH
        );
    }


    public void setWard(Ward ward) {

        this.ward = ward;

        nameField.setText(
                ward.getName()
        );

        capacityField.setText(
                String.valueOf(
                        ward.getCapacity()
                )
        );
    }


    private void saveChanges() {

        if (ward == null) {
            return;
        }


        String name =
                nameField.getText().trim();

        String capacityText =
                capacityField.getText().trim();


        // Empty fields

        if (name.isEmpty()
                || capacityText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields."
            );

            return;
        }


        // Capacity number

        int capacity;

        try {

            capacity =
                    Integer.parseInt(
                            capacityText
                    );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Capacity must be a whole number."
            );

            return;
        }


        // Capacity must be positive

        if (capacity <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Capacity must be greater than 0."
            );

            return;
        }


        // Cannot make capacity smaller
        // than current patient count

        if (capacity < ward.getPatients().size()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Capacity cannot be smaller than the current number of patients.\n\n"
                            + "Current patients: "
                            + ward.getPatients().size()
            );

            return;
        }


        // Check duplicate name

        Ward existingWard =
                hospital.findWardbyName(name);

        if (existingWard != null
                && existingWard != ward) {

            JOptionPane.showMessageDialog(
                    this,
                    "A ward with this name already exists."
            );

            return;
        }


        // Save

        ward.setName(name);
        ward.setCapacity(capacity);


        JOptionPane.showMessageDialog(
                this,
                "Ward updated successfully."
        );


        contentPanel.showHospital();
    }
}
