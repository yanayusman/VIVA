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

        JLabel lmain = new JLabel("Welcome to Price Checker!");
        lmain.setFont(new Font("Segoe print", Font.BOLD, 30));
        lmain.setHorizontalAlignment(JLabel.CENTER);
        main.add(lmain, BorderLayout.CENTER);

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

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class MainFrame extends JFrame {
//     final private Font font = new Font("Segoe print", Font.BOLD, 18);
//     final private Dimension dimension = new Dimension(150, 50);
//     final private Dimension maxsize = new Dimension(600, 100);
//     final private Dimension minsize = new Dimension(400, 100);

//     private JPanel mainPanel;
//     private JPanel importDataPanel;
//     private JPanel browsePanel;
//     private JPanel searchPanel;
//     private JPanel cartPanel;
//     private JPanel accPanel;

//     private CardLayout cardLayout;

//     public void initialize() {
//         setTitle("Main Page");
//         setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//         setSize(1000, 900);

//         // Initialize panels for different pages
//         mainPanel = new JPanel(new BorderLayout());
//         mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));

//         importDataPanel = new ImportData().initialize();
//         browsePanel = new Browse().initialize();
//         searchPanel = new Search().initialize();
//         cartPanel = new Cart().initialize();
//         accPanel = new Acc().initialize();

//         // Set up CardLayout
//         cardLayout = new CardLayout();
//         setLayout(cardLayout);

//         // Add panels to the frame with unique names
//         add(mainPanel, "mainPanel");
//         add(importDataPanel, "importDataPanel");
//         add(browsePanel, "browsePanel");
//         add(searchPanel, "searchPanel");
//         add(cartPanel, "cartPanel");
//         add(accPanel, "accPanel");

//         // Show the main panel initially
//         cardLayout.show(this.getContentPane(), "mainPanel");

//         // Create buttons with actions
//         JButton home = createButton("Home", "mainPanel");
//         JButton importdata = createButton("Import Data", "importDataPanel");
//         JButton browse = createButton("Browse by Category", "browsePanel");
//         JButton search = createButton("Search for Product", "searchPanel");
//         JButton cart = createButton("View Shopping Cart", "cartPanel");
//         JButton acc = createButton("Account Settings", "accPanel");
        

//         JPanel sidebar = new JPanel();
//         sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
//         sidebar.add(home);
//         sidebar.add(importdata);
//         sidebar.add(browse);
//         sidebar.add(search);
//         sidebar.add(cart);
//         sidebar.add(acc);

//         JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainPanel);
//         splitPane.setDividerLocation(250);

//         add(splitPane);

//         setLocationRelativeTo(null);
//         setVisible(true);
//     }

//     private JButton createButton(String buttonText, String panelName) {
//         JButton button = new JButton(buttonText);
//         button.setPreferredSize(dimension);
//         button.setMaximumSize(maxsize);
//         button.setMinimumSize(minsize);
//         button.setFont(font);
//         button.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent arg0) {
//                 cardLayout.show(getContentPane(), panelName);
//             }
//         });
//         return button;
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> {
//             MainFrame main = new MainFrame();
//             main.initialize();
//         });
//     }
// }
