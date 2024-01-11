package PRICECATCHER;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class ItemFrame extends JFrame {
    private final Font font = new Font("Segoe print", Font.BOLD, 18);
    private final Dimension buttonSize = new Dimension(150, 50);
    private final String filename = "lookup_item.csv";
    private final String group;
    private final String category;

    public ItemFrame(String group, String category) {
        this.group = group;
        this.category = category;
    }

    public void initialize() {
        JFrame itemFrame = new JFrame("Items for " + category);
        itemFrame.setSize(1000, 900);
        itemFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Load items for the specified category from CSV file
        List<Item> items = getItemsForCategory(group, category);

        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Unit");

        // Add items to the table model
        for (Item item : items) {
            tableModel.addRow(new Object[]{item.getName(), item.getUnit()});
        }

        JTable table = new JTable(tableModel);
        table.setFont(font);
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);
        itemFrame.setContentPane(scrollPane);
        itemFrame.setLocationRelativeTo(null);
        itemFrame.setVisible(true);
    }

    private List<Item> getItemsForCategory(String group, String category) {
        List<Item> items = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineCount = 0;

            while ((line = reader.readLine()) != null) {
                lineCount++;
                if (lineCount <= 2) {
                    // Skip the 1st and 2nd lines
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String itemGroup = parts[3].trim();
                    String itemCategory = parts[4].trim();
                    String itemName = parts[1].trim();
                    String itemUnit = parts[2].trim();

                    if (itemGroup.equals(group) && itemCategory.equals(category) &&
                            !itemName.isEmpty() && !itemUnit.isEmpty() && !itemCategory.equals("1kg")) {
                        items.add(new Item(itemName, itemUnit));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    private static class Item {
        private final String name;
        private final String unit;

        public Item(String name, String unit) {
            this.name = name;
            this.unit = unit;
        }

        public String getName() {
            return name;
        }

        public String getUnit() {
            return unit;
        }
    }
}
