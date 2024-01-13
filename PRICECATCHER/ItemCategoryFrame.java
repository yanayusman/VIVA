package PRICECATCHER;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class ItemCategoryFrame extends JFrame {
    private final Font font = new Font("Segoe print", Font.BOLD, 18);
    private final Dimension buttonSize = new Dimension(150, 50);
    private final Dimension maxsize = new Dimension(600, 100);
    private final Dimension minsize = new Dimension(400, 100);

    private final String filename = "lookup_item.csv";
    private final String group;
    private String username;

    public ItemCategoryFrame(String username, String group) {
        this.username = username;
        this.group = group;
    }

    public void initialize() {
        setTitle("Browse page - " + username);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 900);
    
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));
    
        // Top panel with Sign Out button and title
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton signOutButton = new JButton("Sign Out");
        signOutButton.setPreferredSize(buttonSize);
        signOutButton.setFont(font);
        signOutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(ItemCategoryFrame.this, "Sign Out button clicked", "Sign Out", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new Login();
        });
    
        JLabel titleLabel = new JLabel("Item Category for " + group, SwingConstants.CENTER);
        titleLabel.setFont(font);
    
        topPanel.add(signOutButton, BorderLayout.EAST);
        topPanel.add(new JSeparator(), BorderLayout.SOUTH);
        topPanel.add(titleLabel, BorderLayout.CENTER);
    
        mainPanel.add(topPanel, BorderLayout.NORTH);
    
        // Load item categories for the specified group from CSV file
        Set<String> uniqueItemCategory = getCategoryForGroup(group);
    
        JPanel itemCategoryPanel = new JPanel();
        itemCategoryPanel.setLayout(new BoxLayout(itemCategoryPanel, BoxLayout.Y_AXIS));
    
        for (String category : uniqueItemCategory) {
            JButton itemCategoryBtn = new JButton(category);
            itemCategoryBtn.setFont(font);
            itemCategoryBtn.setPreferredSize(buttonSize);
            itemCategoryBtn.addActionListener(e -> new ItemFrame(username, group, category).initialize());
            itemCategoryPanel.add(Box.createVerticalStrut(10));
            itemCategoryPanel.add(itemCategoryBtn);
        }
    
        mainPanel.add(itemCategoryPanel, BorderLayout.CENTER);
    
        // Back button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(buttonSize);
        backButton.setFont(font);
        backButton.addActionListener(e -> dispose());
    
        // Bottom panel with Back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(backButton);
    
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    
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

    private Set<String> getCategoryForGroup(String group) {
        Set<String> categories = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineCount = 0;

            while ((line = reader.readLine()) != null) {
                lineCount++;
                if (lineCount <= 2) {
                    // Skip the 1st and 2nd lines
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String itemGroup = parts[3].trim();
                    String itemCategory = parts[4].trim();
                    if (itemGroup.equals(group) && !itemCategory.isEmpty() && !itemCategory.equals("1kg")) {
                        categories.add(itemCategory);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return categories;
    }
}
