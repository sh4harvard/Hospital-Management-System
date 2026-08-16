package Hospital.GUI;

import Hospital.Core.HospitalSystem;
import Hospital.Core.Ward;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class WardPanel extends JPanel {

    private final HospitalSystem hospital;
    private final ContentPanel contentPanel;

    private JTable wardTable;

    public WardPanel(
            HospitalSystem hospital,
            ContentPanel contentPanel) {

        this.hospital = hospital;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(
                new BorderLayout()
        );

        JLabel title = new JLabel("Wards");
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JButton addWardBtn = new JButton("Add Ward");

        addWardBtn.addActionListener(e -> addNewWard());

        headerPanel.add(title, BorderLayout.WEST);

        headerPanel.add(addWardBtn, BorderLayout.EAST);


        add(headerPanel, BorderLayout.NORTH);


        // Table
        String[] titles = {
                "ID",
                "Ward",
                "Capacity",
                "Patients",
                "Doctors"
        };

        wardTable = new JTable(
                new DefaultTableModel(titles, 0)
        );

        wardTable.setRowHeight(30);

        add(
                new JScrollPane(wardTable),
                BorderLayout.CENTER
        );


        // Footer
        JPanel footerBtnPanel =
                new JPanel(
                        new FlowLayout(FlowLayout.RIGHT)
                );

        JButton viewBtn = new JButton("View");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        footerBtnPanel.add(viewBtn);
        footerBtnPanel.add(editBtn);
        footerBtnPanel.add(deleteBtn);

        add(
                footerBtnPanel,
                BorderLayout.SOUTH
        );


        // Events
        viewBtn.addActionListener(e ->
                viewSelectedWard()
        );

        editBtn.addActionListener(e ->
                editSelectedWard()
        );

        deleteBtn.addActionListener(e ->
                deleteSelectedWard()
        );

        refreshTable();
    }


    public void refreshTable() {

        String[] titles = {
                "ID",
                "Ward",
                "Capacity",
                "Patients",
                "Doctors"
        };

        Object[][] data =
                new Object[hospital.getWards().size()][5];

        for (int i = 0;
             i < hospital.getWards().size();
             i++) {

            Ward ward =
                    hospital.getWards().get(i);

            data[i][0] = ward.getId();
            data[i][1] = ward.getName();
            data[i][2] = ward.getCapacity();
            data[i][3] = ward.getPatients().size();
            data[i][4] = ward.getDoctors().size();
        }

        wardTable.setModel(
                new DefaultTableModel(
                        data,
                        titles
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                }
        );

        wardTable.setRowHeight(30);
    }


    private Ward getSelectedWard() {

        int selectedRow =
                wardTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a ward first."
            );

            return null;
        }

        int wardId =
                (int) wardTable.getValueAt(
                        selectedRow,
                        0
                );

        return hospital.findWardById(wardId);
    }


    private void viewSelectedWard() {

        Ward ward = getSelectedWard();

        if (ward == null) {
            return;
        }

        contentPanel.showWardDetail(ward);
    }

    private void addNewWard() {

        JTextField nameField = new JTextField();
        JTextField capacityField = new JTextField();

        JPanel panel =
                new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("Ward Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Capacity:"));
        panel.add(capacityField);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Add New Ward",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String name =
                nameField.getText().trim();

        String capacityText =
                capacityField.getText().trim();

        if (name.isEmpty() || capacityText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields."
            );

            return;
        }

        int capacity;

        try {

            capacity =
                    Integer.parseInt(capacityText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Capacity must be a whole number."
            );

            return;
        }

        if (capacity <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Capacity must be greater than 0."
            );

            return;
        }

        if (hospital.findWardbyName(name) != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "A ward with this name already exists."
            );

            return;
        }

        int newId =
                hospital.generateWardId();

        Ward ward =
                new Ward(
                        newId,
                        name,
                        capacity
                );

        hospital.addWard(ward);

        refreshTable();

        JOptionPane.showMessageDialog(
                this,
                "Ward added successfully."
        );
    }

    private void editSelectedWard() {

        Ward ward = getSelectedWard();

        if (ward == null) {
            return;
        }

        contentPanel.showWardEdit(ward);
    }

    private void deleteSelectedWard() {

        Ward ward = getSelectedWard();

        if (ward == null) {
            return;
        }


        // Cannot delete a ward containing patients

        if (!ward.getPatients().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "This ward cannot be deleted because it still has "
                            + ward.getPatients().size()
                            + " patient(s).",
                    "Cannot Delete Ward",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Cannot delete a ward containing doctors

        if (!ward.getDoctors().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "This ward cannot be deleted because it still has "
                            + ward.getDoctors().size()
                            + " doctor(s).",
                    "Cannot Delete Ward",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Confirmation

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete the ward \""
                                + ward.getName()
                                + "\"?",
                        "Delete Ward",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (result != JOptionPane.YES_OPTION) {
            return;
        }


        // Delete

        if (hospital.deleteWard(ward)) {

            refreshTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Ward deleted successfully.",
                    "Ward Deleted",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "The ward could not be deleted.",
                    "Delete Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        contentPanel.showHospital();
    }

}
