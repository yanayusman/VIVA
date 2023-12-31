package PRICECATCHER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Browse extends JFrame {

    private Font font = new Font("Segoe Print", Font.BOLD, 18);

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
    
        // Sidebar side = new Sidebar();
        // side.initialize();
    
        // // split panel
        // JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, side, main);
        // splitPane.setDividerLocation(250);
    
        // add(splitPane);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addButton("BARANGAN BERBUNGKUS");
        addButton("BARANGAN KEDAI SERBANEKA");
        addButton("BARANGAN KERING");
        addButton("BARANGAN SEGAR");
        addButton("BARANGAN SIAP MASAK");
        addButton("MINUMAN");
        addButton("PRODUK KEBERSIHAN");
        addButton("SUSU DAN BARANGAN BAYI");

        setLocationRelativeTo(null);
        setVisible(true);
        return main;
    }
    
    private void addButton(String btn) {
        JButton button = new JButton(btn);
        button.setFont(font);
        button.setPreferredSize(new Dimension(150, 50));
        button.setMaximumSize(new Dimension(600, 100));
        button.setMinimumSize(new Dimension(400, 100));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(btn.equals("BARANGAN BERBUNGKUS")){
                   
                }else if(btn.equals("BARANGAN KEDAI SERBANEKA")){
                    
                    
                }else if(btn.equals("BARANGAN KERING")){
                    
                    
                }else if(btn.equals("BARANGAN SEGAR")){
                   
                    
                }else if(btn.equals("BARANGAN SIAP MASAK")){
                   
                    
                }else if(btn.equals("MINUMAN")){
                    
                    
                }else{

                }
            }
        });

        add(button);
    }

    public static void main(String[] args) {
        Browse browse = new Browse();
        browse.initialize();
    }
}
