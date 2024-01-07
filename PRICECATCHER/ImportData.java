package PRICECATCHER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ImportData extends JFrame {
    private final Font font = new Font("Segoe Print", Font.BOLD, 18);
    private final Dimension buttonSize = new Dimension(150, 50);
    private final Dimension maxSize = new Dimension(600, 100);
    private final Dimension minSize = new Dimension(400, 100);

    private JTextField selectedFilePath;

    public void initialize() {
        setTitle("Import Data Page");
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
                JOptionPane.showMessageDialog(ImportData.this, "Sign Out button clicked", "Sign Out", JOptionPane.INFORMATION_MESSAGE);
                Login loginForm = new Login();
                loginForm.initialize();
            }
        });

        JLabel titleLabel = new JLabel("Import Data", SwingConstants.CENTER);
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

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainPanel);
        splitPane.setDividerLocation(220);

        // Create a panel for the main content
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 100));

        // File selection components
        JLabel fileLabel = new JLabel("Select File:");
        fileLabel.setFont(font);

        selectedFilePath = new JTextField();
        selectedFilePath.setFont(font);
        selectedFilePath.setEditable(false);

        JButton chooseFileButton = new JButton("Choose File");
        chooseFileButton.setPreferredSize(buttonSize);
        chooseFileButton.setMaximumSize(maxSize);
        chooseFileButton.setMinimumSize(minSize);
        chooseFileButton.setFont(font);
        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseFile();
            }
        });

        JButton importButton = new JButton("Import File");
        importButton.setPreferredSize(buttonSize);
        importButton.setMaximumSize(maxSize);
        importButton.setMinimumSize(minSize);
        importButton.setFont(font);
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importDataFromFile();
            }
        });

        contentPanel.add(fileLabel, BorderLayout.WEST);
        contentPanel.add(selectedFilePath, BorderLayout.CENTER);
        contentPanel.add(chooseFileButton, BorderLayout.CENTER);
        contentPanel.add(importButton, BorderLayout.EAST);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Set up the frame
        setContentPane(splitPane);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            // User selected a file
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            selectedFilePath.setText(filePath);
        }
    }

    private void handleSidebarButtonClick(String label) {
        switch (label) {
            case "Home":
                new MainFrame().initialize();
                break;
            case "Import Data":
                JOptionPane.showMessageDialog(this, "Import Data button clicked", "Import Data", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "Browse by Category":
                new Browse().initialize();
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

    private void importDataFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
    
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
    
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("lookup_item.csv", true))) {
                    while ((line = reader.readLine()) != null) {
                        // Modify this part according to your file format and CSV structure
                        String[] parts = line.split(",");
    
                        // Check if the item already exists to avoid duplicates
                        String newItemCode = parts[0].trim();
                        if (!itemExists(newItemCode)) {
                            for (String part : parts) {
                                writer.write(part);
                                writer.write(",");
                            }
                            // Move to the next line in the CSV file
                            writer.newLine();
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Data imported successfully", "Import Data", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error importing data", "Import Data", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private boolean itemExists(String newItemCode) throws IOException {
        // Check if the item code already exists in your CSV
        try (BufferedReader existingReader = new BufferedReader(new FileReader("lookup_item.csv"))) {
            String line;
            while ((line = existingReader.readLine()) != null) {
                String[] parts = line.split(",");
                String existingItemCode = parts[0].trim();
                if (existingItemCode.equals(newItemCode)) {
                    // Item already exists
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImportData importData = new ImportData();
            importData.initialize();
        });
    }
}
