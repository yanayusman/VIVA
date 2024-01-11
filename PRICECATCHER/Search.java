package PRICECATCHER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;

public class Search extends JFrame {
    private final Font font = new Font("Segoe print", Font.BOLD, 18);
    private final Dimension dimension = new Dimension(150, 50);
    private final Dimension maxSize = new Dimension(600, 100);
    private final Dimension minSize = new Dimension(400, 100);
    
    private JTextField tosearch;
    private JTextField searchUnit;
    private JTextArea result;
    private List<Item> itemList;
    private List<Premise> premiseList;
    private List<PriceCatcherData> priceList;
    private String username;
    private String itemCode;
    private String premiseCode;
    private String itemUnit;

    public Search(){
        itemList = new ArrayList<>();
        priceList = new ArrayList<>();
        premiseList = new ArrayList<>();

        this.itemCode = null;
        this.premiseCode = null;

        initialize();
        loadItemData();
        loadItemPrice();
        loadItemPremise();
    }

    public void initialize() {
        setTitle("Search Page");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 900);
    
        // main content
        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));

        //top panel with signout button and title 
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton signOut = new JButton("Sign Out");
        signOut.setPreferredSize(dimension);
        signOut.setFont(font);
        signOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Search.this, "Sign Out button clicked", "Sign Out", JOptionPane.INFORMATION_MESSAGE);
                Login loginForm = new Login();
                loginForm.initialize();
            }
        });

        JLabel titleLabel = new JLabel("Looking for an Item? Search now!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe print", Font.BOLD, 30));

        topPanel.add(signOut, BorderLayout.EAST);
        topPanel.add(new JSeparator(), BorderLayout.SOUTH);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        main.add(topPanel, BorderLayout.NORTH);

        //form panel
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 100));

        tosearch = new JTextField();
        tosearch.setFont(font);
        tosearch.setPreferredSize(new Dimension(150, 40)); 
        addFormField(formPanel, "Item Name:", tosearch);

        searchUnit = new JTextField();
        searchUnit.setFont(font);
        searchUnit.setPreferredSize(new Dimension(90, 20));
        addFormField(formPanel, "Item Unit:", searchUnit);


        result = new JTextArea(10, 30);
        result.setFont(font);

        JScrollPane resultScrollPane = new JScrollPane(result);
        resultScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        resultScrollPane.setPreferredSize(new Dimension(900, 600));

        main.add(resultScrollPane, BorderLayout.SOUTH);

        JButton searchbtn = new JButton("Search");
        searchbtn.setFont(font);
        searchbtn.setPreferredSize(dimension);
        searchbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                searchItem();
            }
        });
        formPanel.add(searchbtn);

        main.add(formPanel, BorderLayout.CENTER);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        String[] buttonLabels = {"Home", "Import Data", "Browse by Category", "Search for Product", "View Shopping Cart", "Account Settings"};

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setPreferredSize(dimension);
            button.setMaximumSize(maxSize);
            button.setMinimumSize(minSize);
            button.setFont(font);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    btnClick(label);
                }
            });
            sidebar.add(button);
        }

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, main);
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
        component.setPreferredSize(dimension);
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
                new Browse().initialize();
                break;
            case "Search for Product":
                initialize();
                break;
            case "View Shopping Cart":
                new Cart().initialize();
                break;
            case "Account Settings":
                new Acc().initialize();
                break;
        }
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
                Item item = createItemFromLookupItem(parts);
                itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        }

    private void loadItemPremise() {
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
                Premise premise = createPremiseFromLookupPremise((parts));
                premiseList.add(premise);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
    

    private Item createItemFromLookupItem(String[] parts) {
        String itemCode = parts[0];
        String itemName = parts[1];
        String unit = parts[2];
        String itemGroup = parts[3];
        String itemCategory = parts[4];
        return new Item(itemCode, itemName, unit, itemGroup, itemCategory);
    }

    private static Premise createPremiseFromLookupPremise(String[] parts) {
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
    
    private void searchItem() {
        String itemName = tosearch.getText().trim().toLowerCase();
        String itemUnit = searchUnit.getText().trim().toLowerCase();
        result.setText(""); 
        System.out.println("Input Item Name: '" + itemName + "'");
    
        boolean itemFound = false;
    
        for (Item item : itemList) {
            if (item.getItem().toLowerCase().trim().equals(itemName) && item.getUnit().toLowerCase().trim().equals(itemUnit)) {
                itemFound = true;
                String itemCode = item.getItemCode();
                System.out.println("\nItem Code: " + itemCode);
                result.append("Item Name: " + item.getItem() + "\n");
                result.append("Unit: " + item.getUnit() + "\n");
    
                // Create a list to store prices for the current item
                List<PriceCatcherData> pricesForItem = new ArrayList<>();
    
                for (PriceCatcherData price : priceList) {
                    if (price.getItemCode().toLowerCase().trim().equals(itemCode)) {
                        pricesForItem.add(price);
                    }
                }
    
                // Sort prices in ascending order
                Collections.sort(pricesForItem, (p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
    
                for (PriceCatcherData sortedPrice : pricesForItem) {
                    String premiseCode = sortedPrice.getPremiseCode();
    
                    for (Premise premise : premiseList) {
                        if (premise.getPremiseCode().toLowerCase().trim().equals(premiseCode)) {
                            result.append("\nPremise Name: " + premise.getPremise() + "\n");
                            result.append("Address: " + premise.getAddress() + "\n");
                            result.append("State: " + premise.getState() + "\n");
                            result.append("District: " + premise.getDistrict() + "\n");
                        }
                    }
                    result.append("Price: RM " + sortedPrice.getPrice() + "0\n");
                }
            }
        }
    
        if (!itemFound) {
            result.append("Item not found.");
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Search();
        });
    }
}

