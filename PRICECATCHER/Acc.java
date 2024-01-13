package PRICECATCHER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Acc extends JFrame {
    private final Font font = new Font("Segoe print", Font.BOLD, 18);
    private final Dimension buttonSize = new Dimension(150, 50);
    private final Dimension maxsize = new Dimension(600, 100);
    private final Dimension minsize = new Dimension(400, 100);

    private JTextField tusername, email, contactnum, address, city, state, poscode;
    private JPasswordField pswd;
    private String username;

    public Acc(String username) {
        this.username = username;

        initialize();
    }

    public void initialize() {
        setTitle("Account Settings Page - " + username);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 900);
        setMinimumSize(new Dimension(500, 450));

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
                JOptionPane.showMessageDialog(Acc.this, "Sign Out button clicked", "Sign Out", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new Login();
            }
        });

        JLabel titleLabel = new JLabel("Modify Account Information", SwingConstants.CENTER);
        titleLabel.setFont(font);

        topPanel.add(signOutButton, BorderLayout.EAST);
        topPanel.add(new JSeparator(), BorderLayout.SOUTH);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 100));

        addFormField(formPanel, "Username:", tusername = new JTextField());
        addFormField(formPanel, "Password:", pswd = new JPasswordField());
        addFormField(formPanel, "E-mail:", email = new JTextField());
        addFormField(formPanel, "Contact Number:", contactnum = new JTextField());
        addFormField(formPanel, "Address:", address = new JTextField());
        addFormField(formPanel, "City:", city = new JTextField());
        addFormField(formPanel, "State:", state = new JTextField());
        addFormField(formPanel, "Poscode:", poscode = new JTextField());

        JButton saveChangesButton = new JButton("Save Changes");
        saveChangesButton.setFont(font);
        saveChangesButton.setPreferredSize(new Dimension(150, 40));
        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                update();
            }
        });
        formPanel.add(saveChangesButton);

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

    private void addFormField(JPanel panel, String label, JComponent component) {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(font);
        panel.add(fieldLabel);
        panel.add(component);
    }

    private void update() {
        String Username = tusername.getText();
        String Password = new String(pswd.getPassword());
        String Email = email.getText();
        String contactNum = contactnum.getText();
        String Address = address.getText();
        String City = city.getText();
        String State = state.getText();
        String Poscode = poscode.getText();
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pricecatcher", "sqluser", "welcome1")){
            String query = "UPDATE `user` SET `password`=?, `email`=?, `contactnum`=?, `address`=?, `city`=?, `state`=?, `poscode`=? WHERE `username`=?";
            PreparedStatement pS = connection.prepareStatement(query);
            pS.setString(1, Password);
            pS.setString(2, Email);
            pS.setString(3, contactNum);
            pS.setString(4, Address);
            pS.setString(5, City);
            pS.setString(6, State);
            pS.setString(7, Poscode);
            pS.setString(8, Username);

            int rowsAffected = pS.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(Acc.this, "Account details updated successfully", "Acc", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(Acc.this, "No user found with the specified username.", "Acc", JOptionPane.INFORMATION_MESSAGE);
            }
        
            pS.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error. Please try again.");
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login loginUsername = new Login();
            new Acc(loginUsername.getUsername());
        });
    }
}
