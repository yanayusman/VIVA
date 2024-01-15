package PRICECATCHER;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;

public class Cart extends JFrame {
    private final Font font = new Font("Segoe print", Font.BOLD, 18);
    private final Dimension dimension = new Dimension(150, 50);
    private final Dimension maxSize = new Dimension(600, 100);
    private final Dimension minSize = new Dimension(400, 100);

    private JTable cartTable;
    private JLabel totalLabel;
    private DefaultTableModel tableModel;
    private JButton viewCheapestSellerButton;
    private JButton findShopsButton;
    private String username;
    private Map<String, Object[]> cartData;

    public Cart(String username) {
        this.username = username;
        this.cartData = new HashMap<>();

        initialize();
        loadDataFromDatabase();
        displayTotal();
    }

    // load data from database
    private void loadDataFromDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pricecatcher", "sqluser", "welcome1");
             Statement statement = connection.createStatement()) {
    
            // check if cart data for this user exists
            String checkQuery = "SELECT item_code, premise_code, item_name, unit, quantity, price FROM cart WHERE username = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, username);
                ResultSet resultSet = checkStatement.executeQuery();
    
                // clear existing data before loading new data
                cartData.clear();
                tableModel.setRowCount(0);
    
                while (resultSet.next()) {
                    int itemCode = resultSet.getInt("item_code");
                    String premiseCode = resultSet.getString("premise_code");
                    String itemName = resultSet.getString("item_name");
                    String unit = resultSet.getString("unit");
                    int quantity = resultSet.getInt("quantity");
                    double price = resultSet.getDouble("price");
    
                    // add data to the table model
                    tableModel.addRow(new Object[]{itemCode, premiseCode, itemName, unit, quantity, price});
    
                    // add data to cartData map
                    cartData.put(premiseCode, new Object[]{itemCode, premiseCode, itemName, unit, quantity, price});
                }    
                writeCartToCSV(cartData);
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // display total 
    private void displayTotal() {
        double total = 0;
    
        for (Object[] cartItem : cartData.values()) {
            if (cartItem.length >= 6) {
                total += (double) cartItem[5] * (int) cartItem[4];
            } else {
                System.err.println("Invalid cart item array: " + Arrays.toString(cartItem));
            }
        }

        DecimalFormat dec = new DecimalFormat("#.00");
        String decTotal = dec.format(total);
    
        totalLabel.setText("Total Price: RM " + decTotal);
    }
    

    public void initialize() {
        setTitle("Shopping Cart Page - " + username);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 900);
    
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));
    
        // create the top panel, sign out button and title
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
    
        // create table 
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == getColumnCount() - 1;
            }
        };
    
        tableModel.addColumn("Item Code");
        tableModel.addColumn("Premise Code");
        tableModel.addColumn("Item Name");
        tableModel.addColumn("Unit");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Price");
        tableModel.addColumn("Remove");
    
        cartTable = new JTable(tableModel);
    
        // remove button column
        cartTable.getColumnModel().getColumn(tableModel.getColumnCount() - 1).setCellRenderer(new ButtonRenderer());
        cartTable.getColumnModel().getColumn(tableModel.getColumnCount() - 1).setCellEditor(new ButtonEditor(new JCheckBox(), this));
    
        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setPreferredSize(new Dimension(900, 900));
    
        main.add(topPanel);
        main.add(scrollPane);
    
        // sidebar
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
    
        // create the button panel with total label
        JPanel buttonPanel = new JPanel(new FlowLayout()); 

        // total label
        totalLabel = new JLabel();
        totalLabel.setFont(font);
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // view cheapest seller button
        viewCheapestSellerButton = new JButton("View Cheapest Seller");
        viewCheapestSellerButton.setPreferredSize(new Dimension(200, 50));
        viewCheapestSellerButton.setFont(new Font("Segoe print", Font.BOLD, 16));
        viewCheapestSellerButton.addActionListener(e -> viewCheapestSeller());

        // find shops button
        findShopsButton = new JButton("Find Shops to Buy Items in Cart");
        findShopsButton.setPreferredSize(new Dimension(250, 50));
        findShopsButton.setFont(new Font("Segoe print", Font.BOLD, 16));
        findShopsButton.addActionListener(e -> findShopsForItems());

        // add components to the button panel
        buttonPanel.add(totalLabel);
        buttonPanel.add(viewCheapestSellerButton);
        buttonPanel.add(findShopsButton);

        main.add(buttonPanel);    
        main.setPreferredSize(new Dimension(600, 500));
    
        // split pane for sidebar
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, main);
        splitPane.setDividerLocation(220);
    
        // sdd the split pane to the content pane
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(splitPane, BorderLayout.CENTER);
    
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // view cheapest seller page
    private void viewCheapestSeller() {
        dispose();
        new CheapestSeller(username);
    }

    // write cart to CSV
    private void writeCartToCSV(Map<String, Object[]> cartData) {
        String csvFilePath = "user_cart_" + username + ".csv";

        try (FileWriter writer = new FileWriter(csvFilePath, false)) {
            // Write CSV header
            writer.append("Item Code,Premise Code,Item Name,Unit,Quantity,Price\n");

            // Write cart details to CSV file
            for (Object[] cartItem : cartData.values()) {
                if (cartItem.length >= 6) { 
                    writer.append(cartItem[0].toString()).append(",").append(cartItem[1].toString())
                        .append(",").append(cartItem[2].toString()).append(",")
                        .append(cartItem[3].toString()).append(",").append(cartItem[4].toString())
                        .append(",").append(cartItem[5].toString()).append("\n");
                } else {
                    System.err.println("Invalid cart item array: " + Arrays.toString(cartItem));
                }
            }


            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error writing cart details to CSV file.");
        }
    }

    // find shops for all items page
    private void findShopsForItems() {
        dispose();
        new FindShops(username);
    }

    // remove button
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
                String premiseCode = (String) cartTable.getValueAt(selectedRow, 1);
    
                cart.removeFromDatabase(premiseCode);    
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
    
    // remove from database
    public void removeFromDatabase(String premiseCode) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pricecatcher", "sqluser", "welcome1");
             PreparedStatement statement = connection.prepareStatement("DELETE FROM cart WHERE username = ? AND premise_code = ?")) {
    
            statement.setString(1, username);
            statement.setString(2, premiseCode);
    
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Row deleted successfully.");
                
                cartData.remove(premiseCode);
    
                displayTotal();
            } else {
                System.out.println("Row deletion failed. Row not found or other error occurred.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    // remove the selected row
    public void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < tableModel.getRowCount()) {
            tableModel.removeRow(rowIndex);
        } else {
            System.out.println("Invalid row index: " + rowIndex);
        }
    }

    // sidebar button
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
