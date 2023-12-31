package PRICECATCHER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class Search extends JFrame {
    final private Font font = new Font("Segoe print", Font.BOLD, 18);
    private JTextField tosearch;
    private JTextArea result;
    private List<Item> itemList;
    // private List<Premise> allPremise;
    // private List<PriceCatcherData> allpcData;

    public Search(){
        itemList = new ArrayList<>();

        initialize();
        loadItemData();
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

        tosearch = new JTextField();
        tosearch.setHorizontalAlignment(JLabel.CENTER);
        main.add(tosearch, BorderLayout.CENTER);
        tosearch.setFont(font);

        result = new JTextArea(10, 30);
        main.add(result, BorderLayout.CENTER);
        result.setFont(font);

        JButton btnSearch = new JButton("Search");
        btnSearch.setFont(font);
        btnSearch.setPreferredSize(new Dimension(150, 10));
        btnSearch.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Search button pressed");  // Add this line
                searchItem();
            }
        });
        

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 100)); 
        formPanel.add(lmain);
        formPanel.add(tosearch);
        formPanel.add(btnSearch);

        add(formPanel, BorderLayout.NORTH);
        getContentPane().add(formPanel, "North");
        getContentPane().add(new JScrollPane(result), "Center");

        // loadItem();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // private void searchItem() {
    //     SwingUtilities.invokeLater(() -> {
    //         try {
    //             System.out.println("Search button pressed");
    
    //             String itemName = tosearch.getText();
    //             String itemCode = "", premiseCode = "";
    //             result.setText("");
    
    //             // Check if allItem is not empty
    //             if (!allItem.isEmpty()) {
    //                 for (Item item : allItem) {
    //                     if (item.getItem().equalsIgnoreCase(itemName)) {
    //                         itemCode = item.getItemCode();
    //                         System.out.println("Item found: " + item);
    //                         result.append("Item Name: " + item.getItem() + "\n");
    //                         result.append("Unit: " + item.getUnit() + "\n");
    //                         result.append("Item Group: " + item.getItemGroup() + "\n");
    //                         result.append("Item Category: " + item.getItemCategory() + "\n");
    //                         result.append("\n");
    //                         break;
    //                     }
    //                 }
    //             }
    
    //             // Check if allpcData is not empty
    //             if (!allpcData.isEmpty()) {
    //                 for (PriceCatcherData price : allpcData) {
    //                     if (price.getItemCode().equalsIgnoreCase(itemCode)) {
    //                         premiseCode = price.getPremiseCode();
    //                         System.out.println("Price found: " + price);
    //                         result.append("Price: " + price.getPrice() + "\n");
    //                         result.append("\n");
    //                         break;
    //                     }
    //                 }
    //             }
    
    //             // Check if allPremise is not empty
    //             if (!allPremise.isEmpty()) {
    //                 for (Premise premise : allPremise) {
    //                     if (premise.getPremiseCode().equalsIgnoreCase(premiseCode)) {
    //                         System.out.println("Premise found: " + premise);
    //                         result.append("Premise Name: " + premise.getPremise() + "\n");
    //                         result.append("Address: " + premise.getAddress() + "\n");
    //                         result.append("Premise Type: " + premise.getPremiseType() + "\n");
    //                         result.append("State: " + premise.getState() + "\n");
    //                         result.append("District: " + premise.getDistrict() + "\n");
    //                         result.append("\n");
    //                         break;
    //                     }
    //                 }
    //             }
    //             result.repaint();
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     });
    // }
    

    // private void loadItem(){
    //     List<Item> itemlookup = CSVReader.readItem("lookup_item.csv");
    //     List<Premise> premiselookup = CSVReader.readPremise("lookup_premise.csv");
    //     List<PriceCatcherData> priceCatcherData = CSVReader.readPriceCatcherData("pricecatcher_2023-08.csv");

    //     allItem = new ArrayList<>();
    //     allItem.addAll(itemlookup);

    //     allPremise = new ArrayList<>();
    //     allPremise.addAll(premiselookup);

    //     allpcData = new ArrayList<>();
    //     allpcData.addAll(priceCatcherData);
    // }

    // private void searchItem(){
    //     String file = "lookup_item.csv";
    //     String itemName = tosearch.getText();
    //     String line = "";
    //     try(BufferedReader reader = new BufferedReader(new FileReader(file))){
    //         while((line = reader.readLine()) != null){
    //             String[] row = line.split(",");
    //             for(String val : row){
    //                 if(item.getItemName().)
    //             }
    //         }
    //     }
    // }

    private void loadItemData() {
        String file = "lookup_item.csv";
        String line = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Item item = createItemFromLookupItem(parts);
                itemList.add(item);
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

    private void searchItem() {
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("Search button pressed");
    
                String itemName = tosearch.getText().trim().toLowerCase();
                System.out.println("Input Item Name: '" + itemName + "'"); // Print input for debugging
                result.setText("");
    
                // Check if itemList is not empty
                if (!itemList.isEmpty()) {
                    for (Item item : itemList) {
                        String listItemName = item.getItem().toLowerCase().trim();
                        System.out.println("List Item Name: '" + listItemName + "'"); // Print list item name for debugging
    
                        if (listItemName.equals(itemName)) {
                            result.append("Item Name: " + item.getItem() + "\n");
                            result.append("Unit: " + item.getUnit() + "\n");
                            result.append("Item Group: " + item.getItemGroup() + "\n");
                            result.append("Item Category: " + item.getItemCategory() + "\n");
                            result.append("\n");
                            return; // Stop searching after finding the first match
                        }
                    }
                }
    
                // If no match found
                result.append("Item not found.");
                result.repaint();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Search search = new Search();
            search.initialize();
        });
    }
}
