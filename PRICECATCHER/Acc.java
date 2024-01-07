package PRICECATCHER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Acc extends JFrame {
    final private Font font = new Font("Segoe print", Font.BOLD, 18);
    final private Dimension dimension = new Dimension(150, 50);
    final private Dimension maxsize = new Dimension(600, 100);
    final private Dimension minsize = new Dimension(400, 100);
    
    private JTextField username, email, contactnum, address, city, state, poscode;
    private JPasswordField pswd;

    public void initialize() {
        setTitle("Account Settings");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 900);
        setMinimumSize(new Dimension(500, 450));

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));

        // Create a panel for the top section
        JPanel topPanel = new JPanel(new BorderLayout());

        // Add the "Sign Out" button to the top panel
        JButton signOut = new JButton("Sign Out");
        signOut.setPreferredSize(dimension);
        signOut.setFont(font);
        signOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the sign-out action
                JOptionPane.showMessageDialog(Acc.this, "Sign Out button clicked", "Sign Out", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                Login loginForm = new Login();
                loginForm.initialize();
            }
        });

        //form
        JLabel modify = new JLabel("Modify Account Information", SwingConstants.CENTER);
        modify.setFont(font);
        main.add(modify, BorderLayout.CENTER);
        
        topPanel.add(signOut, BorderLayout.EAST);
        topPanel.add(new JSeparator(), BorderLayout.SOUTH);

        main.add(topPanel, BorderLayout.NORTH);

        JLabel lbusername = new JLabel("Username: ");
        lbusername.setFont(font);

        username = new JTextField();
        username.setFont(font);

        JLabel lbpswd = new JLabel("Password: ");
        lbpswd.setFont(font);

        pswd = new JPasswordField();
        pswd.setFont(font);

        JLabel lbemail = new JLabel("E-mail: ");
        lbemail.setFont(font);

        email = new JTextField();
        email.setFont(font);

        JLabel lbcontactnum = new JLabel("Contact Number: ");
        lbcontactnum.setFont(font);

        contactnum = new JTextField();
        contactnum.setFont(font);

        JLabel lbaddress = new JLabel("Address: ");
        lbaddress.setFont(font);

        address = new JTextField();
        address.setFont(font);

        JLabel lbcity = new JLabel("City: ");
        lbcity.setFont(font);

        city = new JTextField();
        city.setFont(font);

        JLabel lbstate = new JLabel("State: ");
        lbstate.setFont(font);

        state = new JTextField();
        state.setFont(font);

        JLabel lbposcode = new JLabel("Poscode: ");
        lbposcode.setFont(font);

        poscode = new JTextField();
        poscode.setFont(font);

        JButton btnsave = new JButton("Save Changes");
        btnsave.setFont(font);
        btnsave.setPreferredSize(new Dimension(150, 40));
        btnsave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                update();                    
            } 
        });

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 100));
        formPanel.add(modify);
        formPanel.add(lbusername);
        formPanel.add(username);
        formPanel.add(lbpswd);
        formPanel.add(pswd);
        formPanel.add(lbemail);
        formPanel.add(email);
        formPanel.add(lbcontactnum);
        formPanel.add(contactnum);
        formPanel.add(lbaddress);
        formPanel.add(address);
        formPanel.add(lbcity);
        formPanel.add(city);
        formPanel.add(lbstate);
        formPanel.add(state);
        formPanel.add(lbposcode);
        formPanel.add(poscode);

        add(formPanel, BorderLayout.NORTH);
        add(btnsave, BorderLayout.SOUTH);

        //sidebar
        JButton home = new JButton("Home");
        home.setPreferredSize(dimension);
        home.setMaximumSize(maxsize);
        home.setMinimumSize(minsize);
        home.setFont(font);
        home.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                System.out.println("Home button clicked");                   
            } 
        });

        JButton importdata = new JButton("Import Data");
        importdata.setPreferredSize(dimension);
        importdata.setMaximumSize(maxsize);
        importdata.setMinimumSize(minsize);
        importdata.setFont(font);
        importdata.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                ImportData importData = new ImportData();
                importData.initialize();                    
            } 
        });

        JButton browse = new JButton("Browse by Category");
        browse.setPreferredSize(dimension);
        browse.setMaximumSize(maxsize);
        browse.setMinimumSize(minsize);
        browse.setFont(font);
        browse.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                Browse browse = new Browse();
                browse.initialize();                    
            } 
        });

        JButton search = new JButton("Search for Product");
        search.setPreferredSize(dimension);
        search.setMaximumSize(maxsize);
        search.setMinimumSize(minsize);
        search.setFont(font);
        search.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                Search Btnbrowse = new Search();
                Btnbrowse.initialize();                    
            } 
        });

        JButton cart = new JButton("View Shopping Cart");
        cart.setPreferredSize(dimension);
        cart.setMaximumSize(maxsize);
        cart.setMinimumSize(minsize);
        cart.setFont(font);
        cart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                Cart btnCart = new Cart();
                btnCart.initialize();                    
            } 
        });

        JButton acc = new JButton("Account Settings");
        acc.setPreferredSize(dimension);
        acc.setMaximumSize(maxsize);
        acc.setMinimumSize(minsize);
        acc.setFont(font);
        acc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                Acc btnAcc = new Acc();
                btnAcc.initialize();                    
            } 
        });

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.add(home);
        sidebar.add(importdata);
        sidebar.add(browse);
        sidebar.add(search);
        sidebar.add(cart);
        sidebar.add(acc);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, formPanel);
        splitPane.setDividerLocation(220);
        
        add(splitPane);

        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void update() {
        String Username = username.getText();
        String Password = new String(pswd.getPassword());
        String Email = email.getText();
        String contactNum = contactnum.getText();
        String Address = address.getText();
        String City = city.getText();
        String State = state.getText();
        String Poscode = poscode.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pricecatcher", "sqluser", "welcome1");
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
                System.out.println("User information updated successfully.");
            } else {
                System.out.println("No user found with the specified username.");
            }

            pS.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error. Please try again.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Acc acc = new Acc();
            acc.initialize();
        });
    }
}
