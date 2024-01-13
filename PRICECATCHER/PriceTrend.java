package PRICECATCHER;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class PriceTrend extends JFrame {
    private final Font font = new Font("Segoe print", Font.BOLD, 18);
    private final Dimension buttonSize = new Dimension(150, 50);
    private final Dimension maxsize = new Dimension(600, 100);
    private final Dimension minsize = new Dimension(400, 100);

    private String username;

    public PriceTrend(String username, String itemName, Map<String, Double> priceMap) {
        super("Price Trend for " + itemName + " Across Different Premises");

        this.username = username;

        setTitle("Browse by Categories - " + username);
        setSize(1000, 900);

        // Create dataset for the bar chart
        CategoryDataset dataset = createDataset(priceMap);

        // Create the bar chart
        JFreeChart barChart = ChartFactory.createBarChart("Price Trend for " + itemName + " Across Different Premises", "Premise", "Price", dataset, PlotOrientation.VERTICAL, true, true, false);

        // Customize the chart (optional)
        CategoryPlot plot = barChart.getCategoryPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        // Create and show the chart panel
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

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

        // Add Sign Out button to the top panel
        JButton signOutButton = new JButton("Sign Out");
        signOutButton.setPreferredSize(buttonSize);
        signOutButton.setFont(font);
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(PriceTrend.this, "Sign Out button clicked", "Sign Out", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new Login();
            }
        });

        // Top panel with Sign Out button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(signOutButton, BorderLayout.EAST);

        // Create a panel to hold the top panel, chart panel, and sidebar
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(chartPanel, BorderLayout.CENTER);

        // Bottom panel with Back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(buttonSize);
        backButton.setFont(font);
        backButton.addActionListener(e -> dispose()); 

        bottomPanel.add(backButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainPanel);
        splitPane.setDividerLocation(220);

        setContentPane(splitPane);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private CategoryDataset createDataset(Map<String, Double> priceMap) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Add data to the dataset
        for (Map.Entry<String, Double> entry : priceMap.entrySet()) {
            dataset.addValue(entry.getValue(), "Price", entry.getKey());
        }

        return dataset;
    }

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
