package PRICECATCHER;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class Login extends JFrame{
    final private Font mainFont = new Font("Segeo print", Font.BOLD, 18);
    private JTextField tfusername;
    private JPasswordField pfpswd;

    public void initialize(){
        //form panel
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
        
        JLabel signup = new JLabel("Don't have an account? Sign up now!", SwingConstants.LEFT);
        signup.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 100));
        formPanel.add(lblogin);
        formPanel.add(lbusername);
        formPanel.add(tfusername);
        formPanel.add(lbpswd);
        formPanel.add(pfpswd);
        formPanel.add(signup);

        //to the signup page
        // signup.setForeground(Color.)
        signup.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Signup signUp = new Signup();
                signUp.initialize();
                dispose();
            }
        });

        //button panel
        JButton btnlogin = new JButton("Login");
        btnlogin.setFont(mainFont);
        btnlogin.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                
                String username = tfusername.getText();
                String pswd = String.valueOf(pfpswd.getPassword());

                User user = getAuthenticatedUser(username, pswd);

                if(user != null){
                    MainFrame mainframe = new MainFrame();
                    mainframe.initialize();
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(Login.this, "Username or Password Invalid", "Try Again", JOptionPane.ERROR_MESSAGE);
                    tfusername.setText("");
                    pfpswd.setText("");
                } 
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }

        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonPanel.add(btnlogin);

        //init frame
        add(formPanel, BorderLayout.NORTH);
        add(btnlogin, BorderLayout.SOUTH);

        setTitle("Login Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 900);
        setMinimumSize(new Dimension(500, 450));
        setLocationRelativeTo(null);
        setVisible(true);
    }
   
    //retrieve from db
    private User getAuthenticatedUser(String username, String pswd){
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost:3306/pricecatcher";
        final String USERNAME = "sqluser";
        final String PASSWORD = "welcome1";

        try(Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)){
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

    public static void main(String[] args) {
        Login loginForm = new Login();
        loginForm.initialize();
        
    }
}
