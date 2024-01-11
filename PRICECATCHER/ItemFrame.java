package PRICECATCHER;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class ItemFrame extends JFrame {
    private final Font font = new Font("Segoe Print", Font.BOLD, 18);
    private final Dimension dimension = new Dimension(150, 50);

    private final String filename = "lookup_item.csv";
    private final String group;
    private final String category;
    private List<Item> itemList;

    public ItemFrame(String group, String category) {
        itemList = new ArrayList<>();
        this.group = group;
        this.category = category;
    }

    public void initialize() {
        JFrame itemFrame = new JFrame("Items for " + category);
        itemFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        itemFrame.setSize(1000, 900);

        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Unit");

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Segoe Print", Font.PLAIN, 18));

        // Adjust the row height
        table.setRowHeight(30);

        // Load items for the specified category from CSV file
        loadItemData();

        // Add items to the table model
        itemList.stream().map(item -> new Object[]{item.getItem(), item.getUnit()}).forEach(tableModel::addRow);
        
        table.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int row = table.getSelectedRow();
                if(row >= 0){
                    String itemName = (String) table.getValueAt(row, 0);
                    String unit = (String) table.getValueAt(row, 1);
                    new ItemTable(itemName, unit).initialize();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        itemFrame.setContentPane(scrollPane);

        itemFrame.setLocationRelativeTo(null);
        itemFrame.setVisible(true);
    }

    private void loadItemData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            reader.lines()
                    .skip(2)
                    .map(line -> line.split(","))
                    .map(this::createItem)
                    .filter(item -> item != null)
                    .forEach(itemList::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Item createItem(String[] parts) {
        String itemCode = parts[0];
        String itemName = parts[1];
        String unit = parts[2];
        String itemGroup = parts[3];
        String itemCategory = parts[4];

        if (itemGroup.equals(group) && itemCategory.equals(category) && !itemName.isEmpty() && !unit.isEmpty() && !itemCategory.equals("1kg")) {
            return new Item(itemCode, itemName, unit, itemGroup, itemCategory);
        } else {
            return null;
        }
    }
}
