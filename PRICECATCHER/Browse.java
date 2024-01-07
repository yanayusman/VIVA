package PRICECATCHER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Browse extends JFrame {

    private Font font = new Font("Segoe Print", Font.BOLD, 18);

    public JPanel initialize() {
        setTitle("Browse Page");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 900);
    
        // main content
        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));
    
        JLabel lmain = new JLabel("Browse by Category");
        lmain.setFont(new Font("Segoe print", Font.BOLD, 30));
        lmain.setHorizontalAlignment(JLabel.CENTER);
        main.add(lmain, BorderLayout.CENTER);
    
        JButton brgBungkus = new JButton();
        brgBungkus.setFont(font);
        brgBungkus.setPreferredSize(new Dimension(150, 10));
        brgBungkus.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                // BrgBungkus brgbungkus = new BrgBungkus();
                // brgbungkus.initialize();
            }
        });

        JButton brgSerbaneka = new JButton();
        brgSerbaneka.setFont(font);
        brgSerbaneka.setPreferredSize(new Dimension(150, 10));
        brgSerbaneka.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                // BrgSerbaneka brgserbaneka = new BrgSerbaneka();
                // brgserbaneka.initialize();
            }
        });

        JButton brgKering = new JButton();
        brgKering.setFont(font);
        brgKering.setPreferredSize(new Dimension(150, 10));
        brgKering.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                // BrgKering brgkering = new BrgKering();
                // brgkering.initialize();
            }
        });

        JButton brgSegar = new JButton();
        brgSegar.setFont(font);
        brgSegar.setPreferredSize(new Dimension(150, 10));
        brgSegar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                // BrgSegar brgsegar = new BrgSegar();
                // brgsegar.initialize();
            }
        });

        JButton brgSiapMasak = new JButton();
        brgSiapMasak.setFont(font);
        brgSiapMasak.setPreferredSize(new Dimension(150, 10));
        brgSiapMasak.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                // BrgSiapMasak brgsiapmasak = new BrgSiapMasak();
                // brgsiapmasak.initialize();
            }
        });

        JButton minuman = new JButton();
        minuman.setFont(font);
        minuman.setPreferredSize(new Dimension(150, 10));
        minuman.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                // Minuman minum = new Minuman();
                // minum.initialize();
            }
        });

        JButton produk = new JButton();
        produk.setFont(font);
        produk.setPreferredSize(new Dimension(150, 10));
        produk.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                // Produk produk = new Produk();
                // produk.initialize();
            }
        });

        JButton brgBayi = new JButton();
        brgBayi.setFont(font);
        brgBayi.setPreferredSize(new Dimension(150, 10));
        brgBayi.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                // BrgBayi brgbayi = new BrgBayi();
                // brgbayi.initialize();
            }
        });

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 100)); 
        formPanel.add(lmain);
        formPanel.add(brgBungkus); 
        formPanel.add(brgSerbaneka);
        formPanel.add(brgKering); 
        formPanel.add(brgSegar);
        formPanel.add(brgSiapMasak);
        formPanel.add(minuman); 
        formPanel.add(produk);
        formPanel.add(brgBayi);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setLocationRelativeTo(null);
        setVisible(true);
        return main;
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Search search = new Search();
        });
    }
}
