package PRICECATCHER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class Search extends JFrame {
    final private Font font = new Font("Segoe print", Font.BOLD, 18);
    private JTextField tosearch;
    private JTextField searchUnit;
    private JTextArea result;
    private List<Item> itemList;
    private List<Premise> premiseList;
    private List<PriceCatcherData> priceList;
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
    
        JLabel lmain = new JLabel("Looking for an Item? Search now!");
        lmain.setFont(new Font("Segoe print", Font.BOLD, 30));
        lmain.setHorizontalAlignment(JLabel.CENTER);
        main.add(lmain, BorderLayout.CENTER);

        JLabel lname = new JLabel("Item Name: ");
        lname.setFont(new Font("Segoe print", Font.BOLD, 25));
        lname.setHorizontalAlignment(JLabel.CENTER);
        main.add(lname, BorderLayout.CENTER);

        JLabel lunit = new JLabel("Item Unit: ");
        lunit.setFont(new Font("Segoe print", Font.BOLD, 25));
        lunit.setHorizontalAlignment(JLabel.CENTER);
        main.add(lunit, BorderLayout.CENTER);

        tosearch = new JTextField();
        tosearch.setHorizontalAlignment(JLabel.CENTER);
        main.add(tosearch, BorderLayout.CENTER);
        tosearch.setFont(font);

        searchUnit = new JTextField();
        searchUnit.setHorizontalAlignment(JLabel.CENTER);
        main.add(searchUnit, BorderLayout.CENTER);
        searchUnit.setFont(font);

        result = new JTextArea(10, 30);
        main.add(result, BorderLayout.CENTER);
        result.setFont(font);

        JButton btnSearch = new JButton("Search");
        btnSearch.setFont(font);
        btnSearch.setPreferredSize(new Dimension(150, 10));
        btnSearch.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                // System.out.println("Search button pressed");
                searchItem();
                // searchPrice();
                // searchPremise();
            }
        });
        

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 100)); 
        formPanel.add(lmain);
        formPanel.add(lname); 
        formPanel.add(tosearch);
        formPanel.add(lunit); 
        formPanel.add(searchUnit);
        formPanel.add(btnSearch);

        add(formPanel, BorderLayout.NORTH);
        getContentPane().add(formPanel, "North");
        getContentPane().add(new JScrollPane(result), "Center");

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
                Item item = createItemFromLookupItem(parts);
                itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        // System.out.println("Item List Size: " + itemList.size()); 
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
    //     System.out.println("Item Premise Size: " + premiseList.size());
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
        // System.out.println("Item Price Size: " + priceList.size());
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
        
        for (Item item : itemList) {
            if (item.getItem().toLowerCase().trim().equals(itemName) && item.getUnit().toLowerCase().trim().equals(itemUnit)) {
                String itemCode = item.getItemCode();
                System.out.println("\nItem Code: " + itemCode);
                result.append("Item Name: " + item.getItem() + "\n");
                result.append("Unit: " + item.getUnit() + "\n");
                // result.append("Item Group: " + item.getItemGroup() + "\n");
                // result.append("Item Category: " + item.getItemCategory() + "\n");
    
                // Search for the associated premiseCode in the priceList
                for (PriceCatcherData price : priceList) {    
                    if (price.getItemCode().toLowerCase().trim().equals(itemCode)) {
                        String premiseCode = price.getPremiseCode();
                        // Search for details related to premiseCode in the premiseList
                        for (Premise premise : premiseList) {
                            if (premise.getPremiseCode().toLowerCase().trim().equals(premiseCode)) {
                                result.append("Premise Name: " + premise.getPremise() + "\n");
                                result.append("Address: " + premise.getAddress() + "\n");
                                // result.append("Premise Type: " + premise.getPremiseType() + "\n");
                                result.append("State: " + premise.getState() + "\n");
                                result.append("District: " + premise.getDistrict() + "\n");
                                // result.append("\n");
                                // return;  // Stop searching after finding the first match
                            }
                        }
                        result.append("Price: RM " + price.getPrice() + "0\n");


                    }
                }
            }
        }
    
        result.append("Item not found.");
    }

    // private void searchPrice() {
        
    // }

    // private void searchPremise() {
        
    // }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Search search = new Search();
        });
    }
}
