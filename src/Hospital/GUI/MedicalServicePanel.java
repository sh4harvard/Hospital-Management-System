package Hospital.GUI;

import Hospital.Core.HospitalSystem;
import Hospital.Core.MedicalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MedicalServicePanel extends JPanel {

    private final HospitalSystem hospital;

    private JTable table;
    private DefaultTableModel tableModel;

    public MedicalServicePanel(HospitalSystem hospital) {

        this.hospital = hospital;

        setLayout(new BorderLayout());

        // Header

        JPanel headerPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Medical Services");
        title.setFont(new Font("Arial", Font.BOLD, 25));

        JButton addBtn = new JButton("Add Service");

        addBtn.addActionListener(e -> addService());

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(addBtn, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Table

        String[] titles = {
                "ID",
                "Service",
                "Cost"
        };

        tableModel = new DefaultTableModel(titles, 0);

        table = new JTable(tableModel);
        table.setRowHeight(30);

        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Footer

        JPanel footerButtonPanel =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        editBtn.addActionListener(e -> editService());
        deleteBtn.addActionListener(e -> deleteService());

        footerButtonPanel.add(editBtn);
        footerButtonPanel.add(deleteBtn);

        add(footerButtonPanel, BorderLayout.SOUTH);
    }

    private void refreshTable() {

        tableModel.setRowCount(0);

        for (MedicalService service :
                hospital.getMedicalServices()) {

            Object[] row = {
                    service.getId(),
                    service.getName(),
                    service.getCost()
            };

            tableModel.addRow(row);
        }
    }

    private void addService() {

        JTextField nameField =
                new JTextField();

        JTextField costField =
                new JTextField();

        JPanel panel =
                new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("Service Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Cost:"));
        panel.add(costField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Add Medical Service",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String name =
                nameField.getText().trim();

        String costText =
                costField.getText().trim();

        if (name.isEmpty() || costText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields."
            );

            return;
        }

        double cost;

        try {
            cost = Double.parseDouble(costText);
        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cost must be a number."
            );

            return;
        }

        if (cost < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cost cannot be negative."
            );

            return;
        }

        int newId = getNextId();

        MedicalService service =
                new MedicalService(
                        newId,
                        name,
                        cost
                );

        hospital.addMedicalService(service);

        refreshTable();

        JOptionPane.showMessageDialog(
                this,
                "Medical service added successfully."
        );
    }

    private void editService() {

        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a service first."
            );

            return;
        }

        int serviceId =
                (int) table.getValueAt(
                        selectedRow,
                        0
                );

        MedicalService service =
                hospital.findMedicalServicebyId(
                        serviceId
                );

        if (service == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Medical service not found."
            );

            return;
        }

        JTextField nameField =
                new JTextField(
                        service.getName()
                );

        JTextField costField =
                new JTextField(
                        String.valueOf(
                                service.getCost()
                        )
                );

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                10,
                                10
                        )
                );

        panel.add(
                new JLabel("Service Name:")
        );

        panel.add(nameField);

        panel.add(
                new JLabel("Cost:")
        );

        panel.add(costField);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Edit Medical Service",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String name =
                nameField.getText().trim();

        String costText =
                costField.getText().trim();

        if (name.isEmpty() || costText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields."
            );

            return;
        }

        double cost;

        try {
            cost = Double.parseDouble(costText);
        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cost must be a number."
            );

            return;
        }

        if (cost < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cost cannot be negative."
            );

            return;
        }

        service.setCost(cost);

        // MedicalService currently has no setName().
        // We will add it below.

        service.setName(name);

        refreshTable();

        JOptionPane.showMessageDialog(
                this,
                "Medical service updated successfully."
        );
    }

    private void deleteService() {

        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a service first."
            );

            return;
        }

        int serviceId =
                (int) table.getValueAt(
                        selectedRow,
                        0
                );

        MedicalService service =
                hospital.findMedicalServicebyId(
                        serviceId
                );

        if (service == null) {
            return;
        }

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete "
                                + service.getName()
                                + "?",
                        "Delete Medical Service",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        hospital.deleteMedicalService(service);

        refreshTable();

        JOptionPane.showMessageDialog(
                this,
                "Medical service deleted successfully."
        );
    }

    private int getNextId() {

        int maxId = 0;

        for (MedicalService service :
                hospital.getMedicalServices()) {

            if (service.getId() > maxId) {
                maxId = service.getId();
            }
        }

        return maxId + 1;
    }
}