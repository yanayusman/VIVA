package PRICECATCHER;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.List;

public class ItemTable extends JFrame {
    private final Font font = new Font("Segoe print", Font.BOLD, 18);
    private final Dimension buttonSize = new Dimension(150, 50);
    private final Dimension maxsize = new Dimension(600, 100);
    private final Dimension minsize = new Dimension(400, 100);
    
    private Map<String, String> sellerPremiseMap;
    private List<Item> itemList;
    private List<Premise> premiseList;
    private List<PriceCatcherData> priceList;
    private String itemName;
    private String unit;
    private String itemCodes;
    private String premiseCode;
    private String username;
    private JTable table;

    public ItemTable(String username, String itemName, String unit) {
        sellerPremiseMap = new HashMap<>(); 
        itemList = new ArrayList<>();
        priceList = new ArrayList<>();
        premiseList = new ArrayList<>();

        this.username = username;
        this.itemName = itemName;
        this.unit = unit;
        this.itemCodes = null;
        this.premiseCode = null;
    }

    public void initialize() {
        setTitle("Browse by Categories - " + username);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 900);

        loadItemData();
        loadItemPrice();
        loadPremiseData();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));

        // top panel, sign out button and title
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton signOutButton = new JButton("Sign Out");
        signOutButton.setPreferredSize(buttonSize);
        signOutButton.setFont(font);
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ItemTable.this, "Sign Out button clicked", "Sign Out", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new Login();
            }
        });

        JLabel topTextLabel = new JLabel("Top 5 Cheapest Sellers for " + itemName);
        topTextLabel.setFont(new Font("Segoe print", Font.PLAIN, 18));

        // price trend button
        JButton showPriceTrendButton = new JButton("Show Price Trend");
        showPriceTrendButton.setPreferredSize(new Dimension(200, 50));
        showPriceTrendButton.setFont(font);
        showPriceTrendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, Double> priceMap = loadPriceData();
                new PriceTrend(username, itemName, priceMap);
            }
        });

        topPanel.add(signOutButton, BorderLayout.EAST);
        topPanel.add(new JSeparator(), BorderLayout.SOUTH);
        topPanel.add(topTextLabel, BorderLayout.NORTH);
        topPanel.add(showPriceTrendButton, BorderLayout.WEST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // create table
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // for all cells non-editable except for the "Add to Cart" column
                return column == getColumnCount() - 1;
            }
        };

        tableModel.addColumn("Seller");
        tableModel.addColumn("Price");
        tableModel.addColumn("Address");
        tableModel.addColumn("Add to Cart");

        table = new JTable(tableModel); 
        table.setFont(font);
        table.setRowHeight(30);

        table.getColumnModel().getColumn(table.getColumnCount() - 1).setCellRenderer(new ButtonRenderer(table));

        for(Item item : itemList){
            if(item.getItem().equals(itemName))
                itemCodes = item.getItemCode();
        }

        // load premise and price data
        List<Premise> premises = loadPremiseData();
        Map<String, Double> priceMap = loadPriceData();

        // calculate the top 5 cheapest sellers
        List<Map.Entry<String, Double>> cheapestSellers = getTopCheapestSellers(priceMap, 5);

        if (cheapestSellers.isEmpty()) {
            JOptionPane.showMessageDialog(ItemTable.this,"Sorry, no result for the item: " + itemName,"No Results", JOptionPane.INFORMATION_MESSAGE);
            new Browse(username).initialize(); 
            dispose();
            return;
        } else {
            DefaultTableModel tableModels = (DefaultTableModel) table.getModel();
            for (Map.Entry<String, Double> seller : cheapestSellers) {
                Premise premise = findPremiseByName(premises, seller.getKey());

                JButton addToCartButton = new JButton("Add to Cart");

                sellerPremiseMap.put(seller.getKey(), premise.getPremiseCode());

                tableModels.addRow(new Object[]{seller.getKey(), seller.getValue(), premise.getAddress(), addToCartButton});
            }
        }

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int column = table.getColumnModel().getColumnIndex("Add to Cart");
                int row = table.rowAtPoint(evt.getPoint());
                if (row >= 0 && column == table.columnAtPoint(evt.getPoint())) {
                    ((ButtonRenderer) table.getCellRenderer(row, column)).doClick();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // bottom panel with back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(buttonSize);
        backButton.setFont(font);
        backButton.addActionListener(e -> dispose());

        bottomPanel.add(backButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

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

        // splitpane for sidebar
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainPanel);
        splitPane.setDividerLocation(220);

        setContentPane(splitPane);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // button for add to cart
    private class ButtonRenderer extends JButton implements TableCellRenderer, ActionListener {
        private int selectedRow;

        public ButtonRenderer(JTable table) {
            setOpaque(true);
            addActionListener(this);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Add to Cart");
            this.selectedRow = row;
            return this;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String seller = (String) table.getValueAt(selectedRow, 0);
            double price = (double) table.getValueAt(selectedRow, 1);
            String address = (String) table.getValueAt(selectedRow, 2);

            String quantity = "1";

            premiseCode = sellerPremiseMap.get(seller);

            if (isItemInCart(username, premiseCode)) {
                updateCartItemQuantity(username, itemCodes, quantity, premiseCode);
            } else {
                addItemToCart(username, itemCodes, itemName, unit, quantity, price, premiseCode);
            }

            System.out.println("Premise code now is: " + premiseCode);
            JOptionPane.showMessageDialog(ItemTable.this, "Item added to cart:\nSeller: " + seller + "\nPrice: " + price + "\nAddress: " + address, "Add to Cart", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // check if the item is already in the cart
    private boolean isItemInCart(String username, String itemCode) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pricecatcher", "sqluser", "welcome1")) {
            String query = "SELECT COUNT(*) FROM cart WHERE username = ? AND item_code = ?";
            try (PreparedStatement pS = connection.prepareStatement(query)) {
                pS.setString(1, username);
                pS.setString(2, itemCode);

                try (ResultSet resultSet = pS.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error. Please try again.");
        }
        return false;
    }

    // update the quantity of an item in the cart
    private void updateCartItemQuantity(String username, String itemCode, String quantity, String premiseCode) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pricecatcher", "sqluser", "welcome1")) {
            String query = "UPDATE cart SET quantity = quantity + ?, premise_code = ? WHERE username = ? AND item_code = ?";
            try (PreparedStatement pS = connection.prepareStatement(query)) {
                pS.setString(1, quantity);
                pS.setString(2, premiseCode);
                pS.setString(3, username);
                pS.setString(4, itemCode);
    
                int result = pS.executeUpdate();
    
                if (result > 0) {
                    System.out.println("Quantity and premiseCode updated for item in cart for " + username);
                } else {
                    JOptionPane.showMessageDialog(this, "Quantity and premiseCode failed to be updated in the cart. Please try again.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error. Please try again.");
        }
    }
    
    
    // add the item to the cart
    private void addItemToCart(String username, String itemCode, String itemName, String unit, String quantity, double price, String premiseCode) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pricecatcher", "sqluser", "welcome1")) {
            String query = "INSERT INTO cart (username, item_code, premise_code, item_name, unit, quantity, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pS = connection.prepareStatement(query)) {
                pS.setString(1, username);
                pS.setString(2, itemCode);
                pS.setString(3, premiseCode);
                pS.setString(4, itemName);
                pS.setString(5, unit);
                pS.setString(6, quantity);
                pS.setDouble(7, price);
    
                int result = pS.executeUpdate();
    
                if (result > 0) {
                    System.out.println("Item added to cart for " + username);
                } else {
                    JOptionPane.showMessageDialog(this, "Item failed to be added to the cart. Please try again.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error. Please try again.");
        }
    }

    // load item data
    private void loadItemData() {
        String file = "lookup_item.csv";
        String line = "";
        int lineCount = 0;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                lineCount++;
                if (lineCount <= 2) {
                    //skip the 1st and 2nd lines
                    continue;
                }
    
                String[] parts = line.split(",");
                Item item = createItem(parts);
                itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // store item data in itemList
    private Item createItem(String[] parts) {
        String itemCode = parts[0];
        String itemName = parts[1];
        String unit = parts[2];
        String itemGroup = parts[3];
        String itemCategory = parts[4];
        return new Item(itemCode, itemName, unit, itemGroup, itemCategory);
    }

    // load premise data
    private List<Premise> loadPremiseData() {
        String file = "lookup_premise.csv";
        int lineCount = 0;
    
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                lineCount++;
                if(lineCount < 2 || lineCount == 2709){
                    continue;
                }

                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                
                for(int i = 0; i < parts.length; i++){
                    parts[i] = parts[i].replaceAll("^\"|\"$", "");
                }
                Premise premise = createPremise((parts));
                premiseList.add(premise);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return premiseList;
    }

    // store premise data into premiseList
    private Premise createPremise(String[] parts) {
        if (parts.length >= 6) {
            String premiseCodeString = parts[0];
            String premiseName = parts[1];
            String address = parts[2];
            String premiseType = parts[3];
            String state = parts[4];
            String district = parts[5];

            try {
                double premiseCode = Double.parseDouble(premiseCodeString);
                
                int premiseCodeInt = (int) premiseCode;

                premiseCodeString = String.valueOf(premiseCodeInt);

                return new Premise(premiseCodeString, premiseName, address, premiseType, state, district);
            } catch (NumberFormatException e) {
                System.err.println("Error: Unable to parse premiseCode as a double. Setting premiseCode to null.");
                return new Premise(null, premiseName, address, premiseType, state, district);
            }
        } else {
            System.err.println("Error: Insufficient elements in the parts array.");
            return null; 
        }
    }

    // load price data
    private void loadItemPrice() {
        String file = "pricecatcher_2023-08.csv";
        String line = "";
        int lineCount = 0;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                lineCount++;
                if (lineCount == 1) {
                    continue;
                }
                String[] parts = line.split(",");
                PriceCatcherData price = createPriceFromPriceCatcherData(parts);
                priceList.add(price);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // store price data into priceList
    private static PriceCatcherData createPriceFromPriceCatcherData(String[] parts) {
        String date = parts[0];
        String premiseCode = parts[1];
        String itemCode = parts[2];
    
        try {
            double price = Double.parseDouble(parts[3]);
            return new PriceCatcherData(date, premiseCode, itemCode, price);
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format in CSV: " + parts[3]);
            return new PriceCatcherData(date, premiseCode, itemCode, 0.0);
        }
    }
    
    // load price data and store into premise map
    private Map<String, Double> loadPriceData() {
        Map<String, Double> priceMap = new HashMap<>();
        String file = "pricecatcher_2023-08.csv";

        if (itemCodes == null) {
            System.err.println("Item codes are not initialized. Please set itemCodes before calling loadPriceData.");
            return priceMap;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineCount = 0;

            while ((line = reader.readLine()) != null) {
                lineCount++;
                if (lineCount <= 1) {
                    continue;
                }

                String[] parts = line.split(",");
                String premiseCode = parts[1].trim();
                String itemCode = parts[2].trim();
                double price = Double.parseDouble(parts[3].trim());

                if (itemCode.equals(this.itemCodes)) {
                    String premiseName = findPremiseNameByCode(premiseList, premiseCode);
                    priceMap.put(premiseName, price);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return priceMap;
    }


    // get top cheapest sellers
    private List<Map.Entry<String, Double>> getTopCheapestSellers(Map<String, Double> priceMap, int topCount) {
        List<Map.Entry<String, Double>> sortedList = new ArrayList<>(priceMap.entrySet());

        sortedList.sort(Map.Entry.comparingByValue());

        return sortedList.subList(0, Math.min(topCount, sortedList.size()));
    }

    // find premise name by premise code
    private String findPremiseNameByCode(List<Premise> premises, String code) {
        for (Premise premise : premises) {
            if (premise.getPremiseCode().equals(code)) {
                return premise.getPremise();
            }
        }
        return "";
    }

    // find premise name by premise name
    private Premise findPremiseByName(List<Premise> premises, String name) {
        for (Premise premise : premises) {
            if (premise.getPremise().equals(name)) {
                return premise;
            }
        }
        return null;
    }

    // sidebar button
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
}