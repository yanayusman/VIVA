package PRICECATCHER;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;

public class CheapestSeller extends JFrame {
    private final Font font = new Font("Segoe print", Font.BOLD, 18);
    private final Dimension buttonSize = new Dimension(150, 50);
    private final Dimension maxsize = new Dimension(600, 100);
    private final Dimension minsize = new Dimension(400, 100);

    private String username;
    private JLabel totalLabel;
    private JTable cheapestItemsTable;

    public CheapestSeller(String username) {
        this.username = username;

        initialize();
        displayCheapestItems();
    }

    // display cheapest items
    private void displayCheapestItems() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pricecatcher", "sqluser", "welcome1");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT item_name, premise_code, MIN(price) AS min_price FROM cart WHERE username = ? GROUP BY item_name, premise_code")) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            // map to store the cheapest items
            Map<String, Object[]> cheapestItemsMap = new HashMap<>();

            while (resultSet.next()) {
                String itemName = resultSet.getString("item_name");
                String premiseCode = resultSet.getString("premise_code");
                double minPrice = resultSet.getDouble("min_price");
                String premiseName = findPremiseNameFromCSV(premiseCode);

                cheapestItemsMap.put(itemName, new Object[]{itemName, premiseName, minPrice});
            }

            // table
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Item Name");
            tableModel.addColumn("Premise Name");
            tableModel.addColumn("Cheapest Price");

            for (Object[] cheapestItem : cheapestItemsMap.values()) {
                tableModel.addRow(cheapestItem);
            }

            cheapestItemsTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(cheapestItemsTable);

            JPanel mainPanel = (JPanel) getContentPane().getComponent(1);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            displayTotal();

            revalidate();
            repaint();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // display total
    public void displayTotal() {
        double total = 0;

        DefaultTableModel tableModel = (DefaultTableModel) cheapestItemsTable.getModel();

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            double cheapestPrice = (double) tableModel.getValueAt(i, 2);
            total += cheapestPrice;
        }

        DecimalFormat dec = new DecimalFormat("#0.00");
        String decTotal = dec.format(total);

        totalLabel.setText("Total Price: RM " + decTotal);
    }

    public void initialize() {
        setTitle("Shopping Cart Page - " + username);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 900);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));

        // top panel, sign out button and title
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton signOutButton = new JButton("Sign Out");
        signOutButton.setPreferredSize(buttonSize);
        signOutButton.setFont(font);
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(CheapestSeller.this, "Sign Out button clicked", "Sign Out", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                Login loginForm = new Login();
                loginForm.initialize();
            }
        });

        JLabel titleLabel = new JLabel("Cheapest Seller for All Selected Item", SwingConstants.CENTER);
        titleLabel.setFont(font);

        topPanel.add(signOutButton, BorderLayout.EAST);
        topPanel.add(new JSeparator(), BorderLayout.SOUTH);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

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

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // display the total
        totalLabel = new JLabel();
        totalLabel.setFont(font);
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // back button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(buttonSize);
        backButton.setFont(font);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Cart(username);
            }
        });

        bottomPanel.add(totalLabel);
        bottomPanel.add(Box.createHorizontalStrut(100));
        bottomPanel.add(backButton);


        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainPanel);
        splitPane.setDividerLocation(220);

        setContentPane(splitPane);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // find premise name
    private String findPremiseNameFromCSV(String premiseCode) {
        String csvFilePath = "lookup_premise.csv";
        int lineCount = 0;
        String line = "";
        String premiseName = null;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            while ((line = br.readLine()) != null) {
                lineCount++;
                if (lineCount < 2 || lineCount == 2709) {
                    continue;
                }

                String[] parts = line.split(",");
                String csvPremiseCode = parts[0].trim();
                if (isNumeric(csvPremiseCode) && Double.parseDouble(csvPremiseCode) == Double.parseDouble(premiseCode.trim())) {
                    premiseName = parts[1].trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return premiseName;
    }

    // check if its number
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // sidebar button
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
}
