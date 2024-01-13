package PRICECATCHER;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Cart extends JFrame {
    private final Font font = new Font("Segoe print", Font.BOLD, 18);
    private final Dimension dimension = new Dimension(150, 50);
    private final Dimension maxSize = new Dimension(600, 100);
    private final Dimension minSize = new Dimension(400, 100);

    private JTable cartTable;
    private DefaultTableModel tableModel;

    private String username;
    private Map<String, Object[]> cartData;

    public Cart(String username) {
        this.username = username;
        this.cartData = new HashMap<>();

        initialize();
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pricecatcher", "sqluser", "welcome1");
             Statement statement = connection.createStatement()) {

            // Check if cart data for this user exists in the database
            String checkQuery = "SELECT item_code, premise_code, item_name, unit, quantity, price FROM cart WHERE username = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, username);
                ResultSet resultSet = checkStatement.executeQuery();

                while (resultSet.next()) {
                    int itemCode = resultSet.getInt("item_code");
                    String premiseCode = resultSet.getString("premise_code");
                    String itemName = resultSet.getString("item_name");
                    String unit = resultSet.getString("unit");
                    int quantity = resultSet.getInt("quantity");
                    double price = resultSet.getDouble("price");

                    // Add data to the table model
                    tableModel.addRow(new Object[]{itemCode, premiseCode, itemName, unit, quantity, price});
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        setTitle("Shopping Cart - " + username);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 900);

        // Create the main panel with BoxLayout
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));

        // Create the top panel with Sign Out button and title
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton signOutButton = new JButton("Sign Out");
        signOutButton.setPreferredSize(new Dimension(150, 50));
        signOutButton.setFont(font);
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Cart.this, "Sign Out button clicked", "Sign Out", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new Login();
            }
        });

        JLabel mainLabel = new JLabel("Shopping Cart Table");
        mainLabel.setFont(new Font("Segoe print", Font.BOLD, 30));

        topPanel.add(signOutButton, BorderLayout.EAST);
        topPanel.add(new JSeparator(), BorderLayout.SOUTH);
        topPanel.add(mainLabel, BorderLayout.CENTER);

        // Create the table model and table
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == getColumnCount() - 1;
            }
        };

        tableModel.addColumn("Item Code");
        tableModel.addColumn("Item Name");
        tableModel.addColumn("Premise Code");
        tableModel.addColumn("Unit");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Price");
        tableModel.addColumn("Remove");

        cartTable = new JTable(tableModel);

        // Add remove button column renderer and editor
        cartTable.getColumnModel().getColumn(tableModel.getColumnCount() - 1).setCellRenderer(new ButtonRenderer());
        cartTable.getColumnModel().getColumn(tableModel.getColumnCount() - 1).setCellEditor(new ButtonEditor(new JCheckBox(), this));

        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setPreferredSize(new Dimension(900, 900));

        // Add components to the main panel
        main.add(topPanel);
        main.add(scrollPane);

        // Set preferred size for the main panel
        main.setPreferredSize(new Dimension(600, 500));

        // Create the sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        String[] buttonLabels = {"Home", "Browse by Category", "Search for Product", "View Shopping Cart", "Account Settings"};

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setPreferredSize(dimension);
            button.setMaximumSize(maxSize);
            button.setMinimumSize(minSize);
            button.setFont(font);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    handleSidebarButtonClick(label);
                }
            });
            sidebar.add(button);
        }

        // Set up the split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, main);
        splitPane.setDividerLocation(220);

        // Add the split pane to the content pane
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(splitPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Remove" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isClicked;
        private Cart cart;
        private int selectedRow;
    
        public ButtonEditor(JCheckBox checkBox, Cart cart) {
            super(checkBox);
            this.cart = cart;
            button = new JButton("Remove");
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }
    
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = "Remove";
            button.setText(label);
            isClicked = true;
            selectedRow = row;
            return button;
        }
    
        @Override
        public Object getCellEditorValue() {
            if (isClicked) {
                // Get the premise code from the selected row
                String premiseCode = (String) cartTable.getValueAt(selectedRow, 1);
    
                // Remove the row from the database
                cart.removeFromDatabase(premiseCode);
    
                // Remove the row from the table
                cart.removeRow(selectedRow);
            }
            isClicked = false;
            return label;
        }
    
        @Override
        public boolean stopCellEditing() {
            isClicked = false;
            return super.stopCellEditing();
        }
    }
    
    

    public void removeFromDatabase(String premiseCode) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pricecatcher", "sqluser", "welcome1");
             PreparedStatement statement = connection.prepareStatement("DELETE FROM cart WHERE username = ? AND premise_code = ?")) {

            statement.setString(1, username);
            statement.setString(2, premiseCode);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Row deleted successfully.");
            } else {
                System.out.println("Row deletion failed. Row not found or other error occurred.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add this method to remove the selected row
    public void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < tableModel.getRowCount()) {
            tableModel.removeRow(rowIndex);
        } else {
            System.out.println("Invalid row index: " + rowIndex);
        }
    }


    private void handleSidebarButtonClick(String label) {
        switch (label) {
            case "Home":
                new MainFrame(username);
                break;
            case "Browse by Category":
                new Browse(username);
                break;
            case "Search for Product":
                new Search(username);
                break;
            case "View Shopping Cart":
                new Cart(username);
                break;
            case "Account Settings":
                new Acc(username);
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login loginUsername = new Login();
            new Cart(loginUsername.getUsername());
        });
    }
}
