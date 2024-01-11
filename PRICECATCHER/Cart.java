package PRICECATCHER;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JTextField tusername;
    private Map<String, Object[]> cartData; // To store cart data for each user

    public Cart() {
        this.cartData = new HashMap<>();

        // Initialize the cart with a specific user (you can modify this as needed)
        setUsernameAndLoadData(this.username);

        initialize();
        // loadDataFromDatabase();
    }

    // New method to set the username and load data
    private void setUsernameAndLoadData(String username) {
        this.username = username;
        // Clear existing data in the table model
        // tableModel.setRowCount(0);
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pricecatcher", "sqluser", "welcome1");
                 Statement statement = connection.createStatement()) {

                // Check if cart data for this user exists in the database
                String checkQuery = "SELECT item_code, item_name, unit, quantity, price FROM cart WHERE username = ?";
                try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                    checkStatement.setString(1, username);
                    ResultSet resultSet = checkStatement.executeQuery();

                    while (resultSet.next()) {
                        int itemCode = resultSet.getInt("item_code");
                        String itemName = resultSet.getString("item_name");
                        String unit = resultSet.getString("unit");
                        int quantity = resultSet.getInt("quantity");
                        double price = resultSet.getDouble("price");

                        // Add data to the table model
                        tableModel.addRow(new Object[]{itemCode, itemName, unit, quantity, price});
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public void initialize() {
        setTitle("Shopping Cart");
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
                Login loginForm = new Login();
                loginForm.initialize();
            }
        });

        JLabel mainLabel = new JLabel("Shopping Cart Table");
        mainLabel.setFont(new Font("Segoe print", Font.BOLD, 30));

        topPanel.add(signOutButton, BorderLayout.EAST);
        topPanel.add(new JSeparator(), BorderLayout.SOUTH);
        topPanel.add(mainLabel, BorderLayout.CENTER);

        // Create the form panel with FlowLayout
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 10, 50));

        tusername = new JTextField();
        tusername.setFont(font);
        tusername.setPreferredSize(new Dimension(150, 40));
        JLabel lusername = new JLabel("Username: ");
        lusername.setFont(font);

        formPanel.add(lusername);
        formPanel.add(tusername);

        // Create a button to submit the username
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(font);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the username from the text box and set it
                String enteredUsername = tusername.getText();
                setUsernameAndLoadData(enteredUsername);
            }
        });
        formPanel.add(submitButton);

        // Create the table model and table
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Item Code");
        tableModel.addColumn("Item Name");
        tableModel.addColumn("Unit");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Price");

        cartTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setPreferredSize(new Dimension(900, 900));

        // Add components to the main panel
        main.add(topPanel);
        main.add(formPanel);
        main.add(scrollPane);

        // Create the sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        String[] buttonLabels = {"Home", "Import Data", "Browse by Category", "Search for Product", "View Shopping Cart", "Account Settings"};

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

    private void handleSidebarButtonClick(String label) {
        switch (label) {
            case "Home":
                new MainFrame().initialize();
                break;
            case "Import Data":
                new ImportData().initialize();
                break;
            case "Browse by Category":
                new Browse().initialize();
                break;
            case "Search for Product":
                new Search().initialize();
                break;
            case "View Shopping Cart":
                initialize();
                break;
            case "Account Settings":
                new Acc().initialize();
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Cart();
        });
    }
}
