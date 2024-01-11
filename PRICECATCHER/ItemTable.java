package PRICECATCHER;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ItemTable extends JFrame {
    private final Font font = new Font("Segoe print", Font.BOLD, 18);
    private final Dimension buttonSize = new Dimension(150, 50);
    private final Dimension maxsize = new Dimension(600, 100);
    private final Dimension minsize = new Dimension(400, 100);
    
    private List<Item> itemList;
    private List<Premise> premiseList;
    private List<PriceCatcherData> priceList;
    private final String itemName;
    private final String unit;
    private String itemCodes;
    private String premiseCode;

    public ItemTable(String itemName, String unit) {
        itemList = new ArrayList<>();
        priceList = new ArrayList<>();
        premiseList = new ArrayList<>();

        this.itemName = itemName;
        this.unit = unit;
        this.itemCodes = null;
        this.premiseCode = null;
    }

    public void initialize() {
        setTitle("Browse by Categories");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 900);

        loadItemData();

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
                JOptionPane.showMessageDialog(ItemTable.this, "Sign Out button clicked", "Sign Out", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new Login().initialize();
            }
        });

        // create text label
        JLabel topTextLabel = new JLabel("Top 5 Cheapest Sellers for " + itemName);
        topTextLabel.setFont(new Font("Segoe print", Font.PLAIN, 18));

        topPanel.add(signOutButton, BorderLayout.EAST);
        topPanel.add(new JSeparator(), BorderLayout.SOUTH);
        topPanel.add(topTextLabel, BorderLayout.NORTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // create table
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Seller");
        tableModel.addColumn("Price");
        tableModel.addColumn("Address");
        tableModel.addColumn(" ");

        JTable table = new JTable(tableModel);
        table.setFont(font);
        table.setRowHeight(30);

        for(Item item : itemList){
            if(item.getItem().equals(itemName))
                itemCodes = item.getItemCode();
        }

        for(PriceCatcherData price : priceList){
            if(price.getItemCode().equals(itemCodes))
                premiseCode = price.getPremiseCode(); 
        }

        // Load premise and price data
        List<Premise> premises = loadPremiseData();
        Map<String, Double> priceMap = loadPriceData();

        // Calculate the top 5 cheapest sellers
        List<Map.Entry<String, Double>> cheapestSellers = getTopCheapestSellers(priceMap, 5);

        // Add data to the table model
        for (Map.Entry<String, Double> seller : cheapestSellers) {
            Premise premise = findPremiseByName(premises, seller.getKey());
            tableModel.addRow(new Object[]{seller.getKey(), seller.getValue(), premise.getAddress()});
        }

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

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

    private Item createItem(String[] parts) {
        String itemCode = parts[0];
        String itemName = parts[1];
        String unit = parts[2];
        String itemGroup = parts[3];
        String itemCategory = parts[4];
        return new Item(itemCode, itemName, unit, itemGroup, itemCategory);
    }

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

    private Map<String, Double> loadPriceData() {
        Map<String, Double> priceMap = new HashMap<>();
        String file = "pricecatcher_2023-08.csv";
    
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
                    // Check if itemCode matches the selected item
                    String premiseName = findPremiseNameByCode(premiseList, premiseCode);
                    priceMap.put(premiseName, price);
                }                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return priceMap;
    }

    private List<Map.Entry<String, Double>> getTopCheapestSellers(Map<String, Double> priceMap, int topCount) {
        List<Map.Entry<String, Double>> sortedList = new ArrayList<>(priceMap.entrySet());

        // Sort the list by price in ascending order
        sortedList.sort(Map.Entry.comparingByValue());

        // Get the top N cheapest sellers
        return sortedList.subList(0, Math.min(topCount, sortedList.size()));
    }

    private String findPremiseNameByCode(List<Premise> premises, String code) {
        for (Premise premise : premises) {
            if (premise.getPremiseCode().equals(code)) {
                return premise.getPremise();
            }
        }
        return "";
    }

    private Premise findPremiseByName(List<Premise> premises, String name) {
        for (Premise premise : premises) {
            if (premise.getPremise().equals(name)) {
                return premise;
            }
        }
        return null;
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
}
