package PRICECATCHER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class Browse extends JFrame {
    private final Font font = new Font("Segoe print", Font.BOLD, 18);
    private final Dimension buttonSize = new Dimension(150, 50);
    private final Dimension maxsize = new Dimension(600, 100);
    private final Dimension minsize = new Dimension(400, 100);
    private final String filename = "lookup_item.csv";

    public Browse() {
        initialize();
    }

    public void initialize() {
        setTitle("Browse page");
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
                dispose();
                // Handle the sign-out action
                JOptionPane.showMessageDialog(Browse.this, "Sign Out button clicked", "Sign Out", JOptionPane.INFORMATION_MESSAGE);
                Login loginForm = new Login();
                loginForm.initialize();
            }
        });

        JLabel titleLabel = new JLabel("Browse by Categories", SwingConstants.CENTER);
        titleLabel.setFont(font);

        topPanel.add(signOutButton, BorderLayout.EAST);
        topPanel.add(new JSeparator(), BorderLayout.SOUTH);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Load groups from CSV file
        List<String> groups = getGroups();

        // Group buttons
        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));
        groupPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        for (String group : groups) {
            JButton groupButton = createGroupBtn(group);
            groupPanel.add(Box.createVerticalStrut(20));
            groupPanel.add(groupButton);
        }

        mainPanel.add(groupPanel, BorderLayout.CENTER);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        String[] buttonLabels = {"Home", "Import Data", "Browse by Category", "Search for Product", "View Shopping Cart", "Account Settings"};

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

    private JButton createGroupBtn(String groupName) {
        JButton button = new JButton(groupName);
        button.setFont(font);
        button.setPreferredSize(buttonSize);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ItemCategoryFrame itemCategoryFrame = new ItemCategoryFrame(groupName);
                itemCategoryFrame.initialize();
            }
        });
        return button;
    }

    private void btnClick(String label) {
        switch (label) {
            case "Home":
                new MainFrame().initialize();
                break;
            case "Import Data":
                new ImportData().initialize();
                break;
            case "Browse by Category":
                initialize();
                break;
            case "Search for Product":
                new Search().initialize();
                break;
            case "View Shopping Cart":
                new Cart().initialize();
                break;
            case "Account Settings":
                new Acc().initialize();
                break;
        }
    }

    private List<String> getGroups() {
        Set<String> groupSet = new HashSet<>();

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
                    String groups = parts[3].trim();
                    if (!groups.isEmpty() && !groups.equals("1kg")) {
                        groupSet.add(groups);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert the set to a sorted list
        List<String> groupList = new ArrayList<>(groupSet);
        Collections.sort(groupList);

        return groupList;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Browse();
        });
    }
}
