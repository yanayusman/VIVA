package PRICECATCHER;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Cart extends MainFrame {
    private final Font mainFont = new Font("Segoe Print", Font.BOLD, 18);

    private DefaultTableModel tableModel;
    private JTable table;

    private Connection connection;
    private Statement statement;

    public Cart() {
        initialize();
        connectToDatabase();
        loadCartData();
    }

    private void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/cart";
        String username = "sqluser";
        String password = "welcome1";

        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCartData() {
        try {
            String query = "SELECT item, unit, quantity, price FROM cart";
            ResultSet resultSet = statement.executeQuery(query);

            tableModel.setRowCount(0);

            while (resultSet.next()) {
                String item = resultSet.getString("item");
                String unit = resultSet.getString("unit");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");

                tableModel.addRow(new Object[]{item, unit, quantity, price, "Remove"});
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Shopping Cart");
        titleLabel.setFont(mainFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 20, 10);
        mainPanel.add(titleLabel, gbc);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Item");
        tableModel.addColumn("Unit");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Price");
        tableModel.addColumn("Remove");

        table = new JTable(tableModel);
        table.setFont(mainFont);

        addRemoveButtonToTable();

        table.getColumn("Remove").setCellRenderer((TableCellRenderer) new ButtonRenderer());
        table.getColumn("Remove").setCellEditor(new ButtonEditor(new JCheckBox()));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(new JScrollPane(table), gbc);

        setContentPane(mainPanel);

        setVisible(true);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Remove Item");
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        tableModel.removeRow(selectedRow);
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return button;
        }
    }

    private void addRemoveButtonToTable() {
        // Initially, the table is empty
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Cart();
        });
    }
}