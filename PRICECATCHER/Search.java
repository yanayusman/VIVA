package PRICECATCHER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Search extends JFrame {
    private final Font font = new Font("Segoe print", Font.BOLD, 18);
    private final Dimension buttonSize = new Dimension(200, 50);
    private final Dimension maxsize = new Dimension(600, 100);
    private final Dimension minsize = new Dimension(400, 100);

    private JTextField itemNameField;
    private JTextField unitField;
    private JButton showTableButton;
    private String username;

    public Search(String username) {
        this.username = username;

        initialize();
    }

    private void initialize() {
        setTitle("Search Page - " + username);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 900);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));

        // Top panel with Sign Out button and title
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton signOutButton = new JButton("Sign Out");
        signOutButton.setPreferredSize(buttonSize);
        signOutButton.setFont(font);
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Search.this, "Sign Out button clicked", "Sign Out", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new Login();
            }
        });

        JLabel titleLabel = new JLabel("Looking for an Item? Search now!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe print", Font.BOLD, 30));

        topPanel.add(signOutButton, BorderLayout.EAST);
        topPanel.add(new JSeparator(), BorderLayout.SOUTH);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel itemNameLabel = new JLabel("Item Name:");
        itemNameField = new JTextField();
        itemNameLabel.setFont(font);

        // Set the preferred size for the item name field
        itemNameField.setPreferredSize(new Dimension(500, 50));

        JLabel unitLabel = new JLabel("Unit:");
        unitField = new JTextField();
        unitLabel.setFont(font);

        // Set the preferred size for the unit field
        unitField.setPreferredSize(new Dimension(500, 50));

        formPanel.add(itemNameLabel, gbc);
        gbc.gridy++;
        formPanel.add(itemNameField, gbc);
        gbc.gridy++;
        formPanel.add(unitLabel, gbc);
        gbc.gridy++;
        formPanel.add(unitField, gbc);

        // Button panel
        showTableButton = new JButton("Show Item Table");
        showTableButton.setPreferredSize(buttonSize);
        showTableButton.setMaximumSize(maxsize);
        showTableButton.setMinimumSize(minsize);
        showTableButton.setFont(font);
        showTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showItemTable();
            }
        });

        // Bottom panel with login button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(showTableButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        //init frame
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        String[] buttonLabels = {"Home", "Browse by Category", "Search for Product", "View Shopping Cart", "Account Settings"};

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setPreferredSize(buttonSize);
            button.setMaximumSize(maxsize);
            button.setMinimumSize(minsize);
            button.setFont(font);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    btnClick(label);
                }
            });
            sidebar.add(button);
        }

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainPanel);
        splitPane.setDividerLocation(220);

        setContentPane(splitPane);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void btnClick(String label) {
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


    private void showItemTable() {
        String partialItemName = "%" + itemNameField.getText().trim().toUpperCase() + "%";
        String unit = unitField.getText().trim().toUpperCase();

        if (partialItemName.isEmpty() || unit.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both item name and unit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pricecatcher", "sqluser", "welcome1");
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM lookup_item WHERE UPPER(item) LIKE ? AND UPPER(unit) = ?")) {

            preparedStatement.setString(1, partialItemName);
            preparedStatement.setString(2, unit);

            ResultSet resultSet = preparedStatement.executeQuery();

            new SearchTable(username, resultSet);
            dispose();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login loginUsername = new Login();
            new Search(loginUsername.getUsername());
        });
    }
}
