package PRICECATCHER;

import javax.swing.*;
import java.awt.*;

public class Cart extends JFrame {
    public JPanel initialize() {
        setTitle("Main Page");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 900);
    
        // main content
        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));
    
        JLabel lmain = new JLabel("Welcome to Price Tracker!");
        lmain.setFont(new Font("Segoe print", Font.BOLD, 30));
        lmain.setHorizontalAlignment(JLabel.CENTER);
        main.add(lmain, BorderLayout.CENTER);
    
    
        setLocationRelativeTo(null);
        setVisible(true);
        return main;
    }
    

    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.initialize();
    }
}

