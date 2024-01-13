package PRICECATCHER;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class ItemFrame extends JFrame {
    private final Font font = new Font("Segoe Print", Font.BOLD, 18);
    private final Dimension dimension = new Dimension(150, 50);
    private final Dimension maxsize = new Dimension(600, 100);
    private final Dimension minsize = new Dimension(400, 100);

    private final String filename = "lookup_item.csv";
    private final String group;
    private final String category;
    private List<Item> itemList;
    private String username;

    public ItemFrame(String username, String group, String category) {
        itemList = new ArrayList<>();

        this.username = username;
        this.group = group;
        this.category = category;
    }

    public void initialize() {
        setTitle("Browse by Categories " + username);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 900);
    
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));
    
        // Top panel with Sign Out button and title
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton signOutButton = new JButton("Sign Out");
        signOutButton.setPreferredSize(dimension);
        signOutButton.setFont(font);
        signOutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(ItemFrame.this, "Sign Out button clicked", "Sign Out", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new Login();
        });
    
        JLabel titleLabel = new JLabel("Items for " + category, SwingConstants.CENTER);
        titleLabel.setFont(font);
    
        topPanel.add(signOutButton, BorderLayout.EAST);
        topPanel.add(new JSeparator(), BorderLayout.SOUTH);
        topPanel.add(titleLabel, BorderLayout.CENTER);
    
        mainPanel.add(topPanel, BorderLayout.NORTH);
    
        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Unit");
    
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Segoe Print", Font.PLAIN, 18));
    
        // Adjust the row height
        table.setRowHeight(30);
    
        // Load items for the specified category from CSV file
        loadItemData();
    
        // Add items to the table model
        itemList.stream().map(item -> new Object[]{item.getItem(), item.getUnit()}).forEach(tableModel::addRow);
    
        table.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    String itemName = (String) table.getValueAt(row, 0);
                    String unit = (String) table.getValueAt(row, 1);
                    new ItemTable(username, itemName, unit).initialize();
                }
            }
        });
    
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    
        // Back button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(dimension);
        backButton.setFont(font);
        backButton.addActionListener(e -> dispose());
    
        // Bottom panel with Back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(backButton);
    
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    
        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        String[] buttonLabels = {"Home", "Browse by Category", "Search for Product", "View Shopping Cart", "Account Settings"};
    
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setPreferredSize(dimension);
            button.setMaximumSize(maxsize);
            button.setMinimumSize(minsize);
            button.setFont(font);
            button.addActionListener(arg0 -> btnClick(label));
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

    private void loadItemData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            reader.lines()
                    .skip(2)
                    .map(line -> line.split(","))
                    .map(this::createItem)
                    .filter(item -> item != null)
                    .forEach(itemList::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Item createItem(String[] parts) {
        String itemCode = parts[0];
        String itemName = parts[1];
        String unit = parts[2];
        String itemGroup = parts[3];
        String itemCategory = parts[4];

        if (itemGroup.equals(group) && itemCategory.equals(category) && !itemName.isEmpty() && !unit.isEmpty() && !itemCategory.equals("1kg")) {
            return new Item(itemCode, itemName, unit, itemGroup, itemCategory);
        } else {
            return null;
        }
    }
}
