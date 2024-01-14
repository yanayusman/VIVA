package PRICECATCHER;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class Login extends JFrame{
    final private Font mainFont = new Font("Segeo print", Font.BOLD, 18);
    private final Dimension buttonSize = new Dimension(150, 50);
    private final Dimension maxsize = new Dimension(600, 100);
    private final Dimension minsize = new Dimension(400, 100);

    private JTextField tfusername;
    private JPasswordField pfpswd;
    private String username;

    public Login() {
        initialize();
    }

    public void initialize() {
        setTitle("Login Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 900);
    
        // Create a JLabel to hold the background image
        JLabel background = new JLabel(new ImageIcon("/home/liyana/Documents/priceCatcher/background_1.jpg"));
        setContentPane(background);
        setLayout(new BorderLayout());
    
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false); // Make the mainPanel transparent to show the background
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));
    
        //form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setOpaque(false); // Make the formPanel transparent
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 100));
    
        JLabel lblogin = new JLabel("Login Form", SwingConstants.CENTER);
        lblogin.setFont(mainFont);
    
        JLabel lbusername = new JLabel("Username: ");
        lbusername.setFont(mainFont);
    
        tfusername = new JTextField();
        tfusername.setFont(mainFont);
    
        JLabel lbpswd = new JLabel("Password: ");
        lbpswd.setFont(mainFont);
    
        pfpswd = new JPasswordField();
        pfpswd.setFont(mainFont);
    
        // sign up
        JLabel signup = new JLabel("<html><u>Don't have an account? Sign up now!</u></html>", SwingConstants.LEFT);
        signup.setFont(mainFont);
        signup.setForeground(Color.BLUE);
        signup.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Signup signUp = new Signup();
                signUp.initialize();
                dispose();
            }
        });
    
        formPanel.add(lblogin);
        formPanel.add(lbusername);
        formPanel.add(tfusername);
        formPanel.add(lbpswd);
        formPanel.add(pfpswd);
        formPanel.add(signup);
    
        //button panel
        JButton btnlogin = new JButton("Login");
        btnlogin.setPreferredSize(buttonSize);
        btnlogin.setMaximumSize(maxsize);
        btnlogin.setMinimumSize(minsize);
        btnlogin.setFont(mainFont);
        btnlogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String username = tfusername.getText();
                String pswd = String.valueOf(pfpswd.getPassword());
    
                User user = getAuthenticatedUser(username, pswd);
    
                if (user != null) {
                    MainFrame mainframe = new MainFrame(username);
                    mainframe.initialize();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Username or Password Invalid", "Try Again", JOptionPane.ERROR_MESSAGE);
                    tfusername.setText("");
                    pfpswd.setText("");
                }
            }
        });
    
        // Bottom panel with login button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false); // Make the bottomPanel transparent
        bottomPanel.add(btnlogin);
    
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(formPanel, BorderLayout.NORTH); // Add formPanel after setting the background
    
        setContentPane(mainPanel);
    
        setMinimumSize(new Dimension(500, 450));
    
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
   
    //retrieve from db
    private User getAuthenticatedUser(String username, String pswd){
        User user = null;

        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pricecatcher", "sqluser", "welcome1");){
            String sql = "SELECT * FROM user WHERE username=? AND password=?";
            PreparedStatement pS = conn.prepareStatement(sql);
            pS.setString(1, username);
            pS.setString(2, pswd);

            ResultSet resultSet = pS.executeQuery();

            if(resultSet.next()){
                user = new User();
                user.username = resultSet.getString("username");
                user.password = resultSet.getString("password"); 
            }
            pS.close();

        }catch(Exception e){
            System.out.println("Database connection failed");
        }
        return user;
    }

    public String getUsername(){
        return username;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login();
        });       
    }
}