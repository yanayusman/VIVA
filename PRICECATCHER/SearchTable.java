package PRICECATCHER;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SearchTable extends JFrame {
    private final Font font = new Font("Segoe print", Font.BOLD, 18);
    private final Dimension buttonSize = new Dimension(150, 50);
    private final Dimension maxSize = new Dimension(600, 100);
    private final Dimension minSize = new Dimension(400, 100);

    private JTable itemTable;
    private DefaultTableModel tableModel;
    private String username;
    private ResultSet resultSet;
    private JTextField searchField;

    public SearchTable(String username, ResultSet resultSet) {
        this.username = username;
        this.resultSet = resultSet;

        initialize();
    }

    private void initialize() {
        setTitle("Search Page - " + username);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 900);

        // main panel 
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));

        // top panel with Sign Out button and title
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton signOutButton = new JButton("Sign Out");
        signOutButton.setPreferredSize(buttonSize);
        signOutButton.setFont(font);
        signOutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(SearchTable.this, "Sign Out button clicked", "Sign Out", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new Login();
        });

        JLabel mainLabel = new JLabel("Search Table");
        mainLabel.setFont(new Font("Segoe print", Font.BOLD, 30));

        topPanel.add(signOutButton, BorderLayout.EAST);
        topPanel.add(new JSeparator(), BorderLayout.SOUTH);
        topPanel.add(mainLabel, BorderLayout.CENTER);

        tableModel = new DefaultTableModel();
        itemTable = new JTable(tableModel);

        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }

            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getObject(i);
                }
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        itemTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = itemTable.getSelectedRow();
                if (selectedRow != -1) {
                    String selectedUnit = (String) itemTable.getValueAt(selectedRow, 0);
                    String selectedItemName = (String) itemTable.getValueAt(selectedRow, 1); // Assuming unit is in the second column
                    showItemDetails(selectedItemName, selectedUnit);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(itemTable);
        scrollPane.setPreferredSize(new Dimension(900, 900));

        mainPanel.add(topPanel);
        mainPanel.add(scrollPane);

        mainPanel.setPreferredSize(new Dimension(600, 500));

        // sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        String[] buttonLabels = {"Home", "Browse by Category", "Search for Product", "View Shopping Cart", "Account Settings"};

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setPreferredSize(buttonSize);
            button.setMaximumSize(maxSize);
            button.setMinimumSize(minSize);
            button.setFont(font);
            button.addActionListener(e -> handleSidebarButtonClick(label));
            sidebar.add(button);
        }

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainPanel);
        splitPane.setDividerLocation(220);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(splitPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showItemDetails(String itemName, String unit) {
    System.out.println("Selected Item: " + itemName + ", Unit: " + unit);

    new ItemTable(username, itemName, unit).initialize();
    dispose();
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
}
