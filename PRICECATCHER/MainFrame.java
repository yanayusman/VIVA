package PRICECATCHER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    final private Font font = new Font("Segoe print", Font.BOLD, 18);
    final private Dimension dimension = new Dimension(150, 50);
    final private Dimension maxsize = new Dimension(600, 100);
    final private Dimension minsize = new Dimension(400, 100);

    public void initialize() {
        setTitle("Main Page");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 900);

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
                JOptionPane.showMessageDialog(MainFrame.this, "Sign Out button clicked", "Sign Out", JOptionPane.INFORMATION_MESSAGE);
                Login loginForm = new Login();
                loginForm.initialize();
            }
        });

        JLabel lmain = new JLabel("Welcome to Price Checker!");
        lmain.setFont(new Font("Segoe print", Font.BOLD, 30));
        lmain.setHorizontalAlignment(JLabel.CENTER);
        main.add(lmain, BorderLayout.CENTER);

        
        //add the "Sign Out" button to the top panel
        topPanel.add(signOut, BorderLayout.EAST);
        
        //separator
        topPanel.add(new JSeparator(), BorderLayout.SOUTH);

        //add the top panel to the main panel
        main.add(topPanel, BorderLayout.NORTH);

        JButton home = new JButton("Home");
        home.setPreferredSize(dimension);
        home.setMaximumSize(maxsize);
        home.setMinimumSize(minsize);
        home.setFont(font);
        home.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                JOptionPane.showMessageDialog(MainFrame.this, "Home button clicked", "Home", JOptionPane.INFORMATION_MESSAGE);
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
                Search Btnsearch = new Search();
                Btnsearch.initialize();                    
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

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, main);
        splitPane.setDividerLocation(220);

        add(splitPane);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame main = new MainFrame();
            main.initialize();
        });
    }
}
