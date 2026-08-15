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
                new FlowLayout(FlowLayout.LEFT)
        );

        JLabel title = new JLabel("Wards");
        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        headerPanel.add(title);

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

        JButton viewBtn =
                new JButton("View");

        JButton editBtn =
                new JButton("Edit");

        footerBtnPanel.add(viewBtn);
        footerBtnPanel.add(editBtn);

        add(
                footerBtnPanel,
                BorderLayout.SOUTH
        );


        // Events
        viewBtn.addActionListener(e ->
                viewSelectedWard()
        );

        // Edit will be implemented next
        editBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Ward editing will be added next."
                )
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
}
