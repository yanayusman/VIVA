package PRICECATCHER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;

public class index extends JFrame{
    private final Font font = new Font("Segoe print", Font.BOLD, 18);
    private final Dimension buttonSize = new Dimension(150, 50);
    private final Dimension maxsize = new Dimension(600, 100);
    private final Dimension minsize = new Dimension(400, 100);
    
    public index(){
        initialize();
    }

    public void initialize(){
        setTitle("Page");
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
                // Handle the sign-out action
                JOptionPane.showMessageDialog(index.this, "Sign Out button clicked", "Sign Out", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                Login loginForm = new Login();
                loginForm.initialize();
            }
        });

        JLabel titleLabel = new JLabel("TITLE", SwingConstants.CENTER);
        titleLabel.setFont(font);

        topPanel.add(signOutButton, BorderLayout.EAST);
        topPanel.add(new JSeparator(), BorderLayout.SOUTH);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new index();
        });
    }
}