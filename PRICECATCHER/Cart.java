package PRICECATCHER;

// import javax.swing.*;
// import java.awt.*;

// public class Cart extends JFrame {
//     public JPanel initialize() {
//         setTitle("Main Page");
//         setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//         setSize(1000, 900);
    
//         // main content
//         JPanel main = new JPanel(new BorderLayout());
//         main.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));
    
//         JLabel lmain = new JLabel("Welcome to Price Tracker!");
//         lmain.setFont(new Font("Segoe print", Font.BOLD, 30));
//         lmain.setHorizontalAlignment(JLabel.CENTER);
//         main.add(lmain, BorderLayout.CENTER);
    
    
//         setLocationRelativeTo(null);
//         setVisible(true);
//         return main;
//     }
    

//     public static void main(String[] args) {
//         Cart cart = new Cart();
//         cart.initialize();
//     }
// }


// import javax.swing.*;
// import javax.swing.table.DefaultTableModel;
// import javax.swing.table.TableCellRenderer;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class Cart extends JFrame {
//     private final Font mainFont = new Font("Segoe Print", Font.BOLD, 18);

//     private DefaultTableModel tableModel;
//     private JTable table;

//     public Cart() {
//         initialize();
//     }

//     public void initialize() {
//         setTitle("Shopping Cart Page");
//         setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//         setSize(1000, 900);
//         setLocationRelativeTo(null);

//         // Create a panel with GridBagLayout
//         JPanel mainPanel = new JPanel(new GridBagLayout());
//         GridBagConstraints gbc = new GridBagConstraints();

//         // Add the "Shopping Cart" title at the top
//         JLabel titleLabel = new JLabel("Shopping Cart");
//         titleLabel.setFont(mainFont);
//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         gbc.insets = new Insets(10, 10, 20, 10); // Insets for spacing
//         mainPanel.add(titleLabel, gbc);

//         tableModel = new DefaultTableModel();
//         tableModel.addColumn("Item");
//         tableModel.addColumn("Unit");
//         tableModel.addColumn("Quantity");
//         tableModel.addColumn("Price");
//         tableModel.addColumn("Remove");

//         table = new JTable(tableModel);
//         table.setFont(mainFont);

//         addRemoveButtonToTable();

//         table.getColumn("Remove").setCellRenderer((TableCellRenderer) new ButtonRenderer());
//         table.getColumn("Remove").setCellEditor(new ButtonEditor(new JCheckBox()));

//         // Add the table to the panel
//         gbc.gridx = 0;
//         gbc.gridy = 1;
//         gbc.weightx = 1;
//         gbc.weighty = 1;
//         gbc.fill = GridBagConstraints.BOTH;
//         mainPanel.add(new JScrollPane(table), gbc);

//         // Set the main panel as the content pane
//         setContentPane(mainPanel);

//         setVisible(true);
//     }

//     class ButtonRenderer extends JButton implements TableCellRenderer {
//         public ButtonRenderer() {
//             setOpaque(true);
//         }

//         @Override
//         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//             setText((value == null) ? "" : value.toString());
//             return this;
//         }
//     }

//     class ButtonEditor extends DefaultCellEditor {
//         private JButton button;

//         public ButtonEditor(JCheckBox checkBox) {
//             super(checkBox);
//             button = new JButton("Remove Item");
//             button.setOpaque(true);
//             button.addActionListener(new ActionListener() {
//                 @Override
//                 public void actionPerformed(ActionEvent e) {
//                     int selectedRow = table.getSelectedRow();
//                     if (selectedRow != -1) {
//                         tableModel.removeRow(selectedRow);
//                     }
//                 }
//             });
//         }

//         @Override
//         public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
//             return button;
//         }
//     }

//     private void addRemoveButtonToTable() {
//         // Initially, the table is empty
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> {
//             new Cart();
//         });
//     }
// }


// import javax.swing.*;
// import javax.swing.table.DefaultTableModel;
// import javax.swing.table.TableCellRenderer;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.sql.*;

// public class Cart extends MainFrame {
//     private final Font font = new Font("Segoe Print", Font.BOLD, 18);
//     private final Dimension buttonSize = new Dimension(150, 50);
//     private final Dimension maxsize = new Dimension(600, 100);
//     private final Dimension minsize = new Dimension(400, 100);

//     private DefaultTableModel tableModel;
//     private JTable table;
//     private Connection connection;
//     private Statement statement;

//     public Cart() {
//         initialize();
//         connectToDatabase();
//         loadCartData();
//     }

//     private void connectToDatabase() {
//         String url = "jdbc:mysql://localhost:3306/pricecatcher";
//         String username = "sqluser";
//         String password = "welcome1";

//         try {
//             connection = DriverManager.getConnection(url, username, password);
//             statement = connection.createStatement();
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     private void loadCartData() {
//         try {
//             String query = "SELECT item_code, unit, quantity, price FROM cart";
//             ResultSet resultSet = statement.executeQuery(query);

//             tableModel.setRowCount(0);

//             while (resultSet.next()) {
//                 String item = resultSet.getString("item");
//                 String unit = resultSet.getString("unit");
//                 int quantity = resultSet.getInt("quantity");
//                 double price = resultSet.getDouble("price");

//                 tableModel.addRow(new Object[]{item, unit, quantity, price, "Remove"});
//             }

//             resultSet.close();
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     public void initialize() {
//         setTitle("Cart Page");
//         setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//         setSize(1000, 900);
//         setMinimumSize(new Dimension(500, 450));

//         JPanel main = new JPanel(new BorderLayout());
//         main.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));

//         // Top panel with Sign Out button and title
//         JPanel topPanel = new JPanel(new BorderLayout());
//         JButton signOutButton = new JButton("Sign Out");
//         signOutButton.setPreferredSize(buttonSize);
//         signOutButton.setFont(font);
//         signOutButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 // Handle the sign-out action
//                 JOptionPane.showMessageDialog(Cart.this, "Sign Out button clicked", "Sign Out", JOptionPane.INFORMATION_MESSAGE);
//                 dispose();
//                 Login loginForm = new Login();
//                 loginForm.initialize();
//             }
//         });

//         JPanel mainPanel = new JPanel(new GridBagLayout());
//         GridBagConstraints gbc = new GridBagConstraints();

//         JLabel titleLabel = new JLabel("Shopping Cart");
//         titleLabel.setFont(font);
//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         gbc.insets = new Insets(10, 10, 20, 10);
//         mainPanel.add(titleLabel, gbc);

//         tableModel = new DefaultTableModel();
//         tableModel.addColumn("Item");
//         tableModel.addColumn("Unit");
//         tableModel.addColumn("Quantity");
//         tableModel.addColumn("Price");
//         tableModel.addColumn("Remove");

//         table = new JTable(tableModel);
//         table.setFont(font);

//         addRemoveButtonToTable();

//         table.getColumn("Remove").setCellRenderer((TableCellRenderer) new ButtonRenderer());
//         table.getColumn("Remove").setCellEditor(new ButtonEditor(new JCheckBox()));

//         gbc.gridx = 0;
//         gbc.gridy = 1;
//         gbc.weightx = 1;
//         gbc.weighty = 1;
//         gbc.fill = GridBagConstraints.BOTH;
//         mainPanel.add(new JScrollPane(table), gbc);

//         setContentPane(mainPanel);

//         setVisible(true);
//     }

//     class ButtonRenderer extends JButton implements TableCellRenderer {
//         public ButtonRenderer() {
//             setOpaque(true);
//         }

//         @Override
//         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//             setText((value == null) ? "" : value.toString());
//             return this;
//         }
//     }

//     class ButtonEditor extends DefaultCellEditor {
//         private JButton button;

//         public ButtonEditor(JCheckBox checkBox) {
//             super(checkBox);
//             button = new JButton("Remove Item");
//             button.setOpaque(true);
//             button.addActionListener(new ActionListener() {
//                 @Override
//                 public void actionPerformed(ActionEvent e) {
//                     int selectedRow = table.getSelectedRow();
//                     if (selectedRow != -1) {
//                         tableModel.removeRow(selectedRow);
//                     }
//                 }
//             });
//         }

//         @Override
//         public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
//             return button;
//         }
//     }

//     private void addRemoveButtonToTable() {
//         // Initially, the table is empty
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> {
//             new Cart();
//         });
//     }
// }

// import javax.swing.*;
// import javax.swing.table.DefaultTableModel;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.util.HashMap;
// import java.util.Map;
// import java.sql.*;

// public class Cart extends JFrame {
//     private final Font font = new Font("Segoe print", Font.BOLD, 18);
//     private final Dimension dimension = new Dimension(150,50);
//     private final Dimension maxSize = new Dimension(600, 100);
//     private final Dimension minSize = new Dimension(400, 100);

//     private JTable cartTable;
//     private DefaultTableModel tableModel;

//     private String username;
//     private JTextField tusername;
//     private Map<String, Object[]> cartData; // To store cart data for each user

//     public Cart() {
//         this.cartData = new HashMap<>();

//         // Initialize the cart with a specific user (you can modify this as needed)
//         setUsernameAndLoadData(this.username);

//         initialize();
//         // loadDataFromDatabase();
//     }

//     // New method to set the username and load data
//     private void setUsernameAndLoadData(String username) {
//         this.username = username;
//         // Clear existing data in the table model
//         tableModel.setRowCount(0);
//         loadDataFromDatabase();
//     }

//     public void initialize() {
//         setTitle("Shopping Cart");
//         setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//         setSize(1000, 900);

//         // Create the main panel with BoxLayout
//         JPanel main = new JPanel();
//         main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
//         main.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));

//         // Create the top panel with Sign Out button and title
//         JPanel topPanel = new JPanel(new BorderLayout());
//         JButton signOutButton = new JButton("Sign Out");
//         signOutButton.setPreferredSize(new Dimension(150, 50));
//         signOutButton.setFont(font);
//         signOutButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 JOptionPane.showMessageDialog(Cart.this, "Sign Out button clicked", "Sign Out", JOptionPane.INFORMATION_MESSAGE);
//                 Login loginForm = new Login();
//                 loginForm.initialize();
//             }
//         });

//         JLabel mainLabel = new JLabel("Shopping Cart Table");
//         mainLabel.setFont(new Font("Segoe print", Font.BOLD, 30));

//         topPanel.add(signOutButton, BorderLayout.EAST);
//         topPanel.add(new JSeparator(), BorderLayout.SOUTH);
//         topPanel.add(mainLabel, BorderLayout.CENTER);

//         // Create the form panel with FlowLayout
//         JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//         formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 10, 50));

//         tusername = new JTextField();
//         tusername.setFont(font);
//         tusername.setPreferredSize(new Dimension(150, 40));
//         JLabel lusername = new JLabel("Username: ");
//         lusername.setFont(font);

//         formPanel.add(lusername);
//         formPanel.add(tusername);

//          // Create a button to submit the username
//         JButton submitButton = new JButton("Submit");
//         submitButton.setFont(font);
//         submitButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 // Get the username from the text box and set it
//                 String enteredUsername = tusername.getText();
//                 setUsernameAndLoadData(enteredUsername);
//             }
//         });
//         formPanel.add(submitButton);

//         // Create the table model and table
//         tableModel = new DefaultTableModel();
//         tableModel.addColumn("Item Code");
//         tableModel.addColumn("Item Name");
//         tableModel.addColumn("Unit");
//         tableModel.addColumn("Quantity");
//         tableModel.addColumn("Price");

//         cartTable = new JTable(tableModel);

//         JScrollPane scrollPane = new JScrollPane(cartTable);
//         scrollPane.setPreferredSize(new Dimension(900, 900));


//         // Add components to the main panel
//         main.add(topPanel);
//         main.add(formPanel);
//         main.add(scrollPane);

//         // Create the sidebar
//         JPanel sidebar = new JPanel();
//         sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
//         String[] buttonLabels = {"Home", "Import Data", "Browse by Category", "Search for Product", "View Shopping Cart", "Account Settings"};

//         for (String label : buttonLabels) {
//             JButton button = new JButton(label);
//             button.setPreferredSize(dimension);
//             button.setMaximumSize(maxSize);
//             button.setMinimumSize(minSize);
//             button.setFont(font);
//             button.addActionListener(new ActionListener() {
//                 @Override
//                 public void actionPerformed(ActionEvent arg0) {
//                     handleSidebarButtonClick(label);
//                 }
//             });
//             sidebar.add(button);
//         }

//         // Set up the split pane
//         JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, main);
//         splitPane.setDividerLocation(220);

//         // Add the split pane to the content pane
//         Container container = getContentPane();
//         container.setLayout(new BorderLayout());
//         container.add(splitPane, BorderLayout.CENTER);

//         setLocationRelativeTo(null);
//         setVisible(true);
//     }

//     private void loadDataFromDatabase() {
//         try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pricecatcher", "sqluser", "welcome1");
//              Statement statement = connection.createStatement()) {

//             // Check if cart data for this user exists in the database
//             String checkQuery = "SELECT item_code, item_name, unit, quantity, price FROM shopping_cart WHERE username = ?";
//             try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
//                 checkStatement.setString(1, username);
//                 ResultSet resultSet = checkStatement.executeQuery();

//                 while (resultSet.next()) {
//                     int itemCode = resultSet.getInt("item_code");
//                     String itemName = resultSet.getString("item_name");
//                     String unit = resultSet.getString("unit");
//                     int quantity = resultSet.getInt("quantity");
//                     double price = resultSet.getDouble("price");

//                     // Add data to the table model
//                     tableModel.addRow(new Object[]{itemCode, itemName, unit, quantity, price});
//                 }
//             }

//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

//     private void handleSidebarButtonClick(String label) {
//         switch (label) {
//             case "Home":
//                 new MainFrame().initialize();
//                 break;
//             case "Import Data":
//                 new ImportData().initialize();
//                 break;
//             case "Browse by Category":
//                 new Browse().initialize();
//                 break;
//             case "Search for Product":
//                 new Search().initialize();
//                 break;
//             case "View Shopping Cart":
//                 initialize();
//                 break;
//             case "Account Settings":
//                 new Acc().initialize();
//                 break;
//         }
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> {
//             new Cart();
//         });
//     }
// }


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
                return column == getColumnCount() - 1; // Make only the last column editable (remove button)
            }
        };

        tableModel.addColumn("Item Code");
        tableModel.addColumn("Item Name");
        tableModel.addColumn("Unit");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Price");
        tableModel.addColumn("Remove"); // New column for remove button

        cartTable = new JTable(tableModel);

        // Add remove button column renderer and editor
        cartTable.getColumnModel().getColumn(tableModel.getColumnCount() - 1).setCellRenderer(new ButtonRenderer());
        cartTable.getColumnModel().getColumn(tableModel.getColumnCount() - 1).setCellEditor(new ButtonEditor(new JCheckBox(), this));

        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setPreferredSize(new Dimension(900, 900));

        // Add components to the main panel
        main.add(topPanel);
        main.add(scrollPane);

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
        setText((value == null) ? "" : value.toString());
        return this;
    }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isClicked;
        private Cart cart;
    
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
            return button;
        }
    
        @Override
        public Object getCellEditorValue() {
            if (isClicked) {
                cart.removeRow(cartTable.getSelectedRow());
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

    // Add this method to remove the selected row
    public void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < tableModel.getRowCount()) {
            tableModel.removeRow(rowIndex);
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

