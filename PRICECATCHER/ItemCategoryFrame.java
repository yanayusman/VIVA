package PRICECATCHER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.Set;

public class ItemCategoryFrame extends JFrame {
    private final Font font = new Font("Segoe print", Font.BOLD, 18);
    private final Dimension buttonSize = new Dimension(150, 50);
    private final String filename = "lookup_item.csv";
    private final String group;

    public ItemCategoryFrame(String group) {
        this.group = group;
    }

    public void initialize() {
        JFrame itemCategory = new JFrame("Item Category for " + group);
        itemCategory.setSize(1000, 900);
        itemCategory.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Load item categories for the specified group from CSV file
        Set<String> uniqueItemCategory = getCategoryForGroup(group);

        JPanel itemCategoryPanel = new JPanel();
        itemCategoryPanel.setLayout(new BoxLayout(itemCategoryPanel, BoxLayout.Y_AXIS));

        for (String category : uniqueItemCategory) {
            JButton itemCategoryBtn = new JButton(category);
            itemCategoryBtn.setFont(font);
            itemCategoryBtn.setPreferredSize(buttonSize);
            itemCategoryBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ItemFrame(group, category).initialize();
                }
            });
            itemCategoryPanel.add(Box.createVerticalStrut(10));
            itemCategoryPanel.add(itemCategoryBtn);
        }

        itemCategory.setContentPane(itemCategoryPanel);
        itemCategory.setLocationRelativeTo(null);
        itemCategory.setVisible(true);
    }

    private Set<String> getCategoryForGroup(String group) {
        Set<String> categories = new HashSet<>();

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
                    if (itemGroup.equals(group) && !itemCategory.isEmpty() && !itemCategory.equals("1kg")) {
                        categories.add(itemCategory);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return categories;
    }
}

