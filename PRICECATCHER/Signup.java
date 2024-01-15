package PRICECATCHER;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import javax.swing.*;

public class Signup extends JFrame{
    final private Font font = new Font("Segeo print", Font.BOLD, 18);
    private final Dimension buttonSize = new Dimension(150, 50);
    private final Dimension maxsize = new Dimension(600, 100);
    private final Dimension minsize = new Dimension(400, 100);

    private JTextField username, email, contactnum, address, city, state, poscode;
    private JPasswordField pswd;
    
    public Signup() {
        initialize();
    }

    public void initialize(){
        setTitle("Signup Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 900);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));

        //form
        JLabel signup = new JLabel("Signup Form", SwingConstants.CENTER);
        signup.setFont(font);
        
        JLabel lbusername = new JLabel("Username: ");
        lbusername.setFont(font);

        username = new JTextField();
        username.setFont(font);

        JLabel lbpswd = new JLabel("Password: ");
        lbpswd.setFont(font);

        pswd = new JPasswordField();
        pswd.setFont(font);

        JLabel lbemail = new JLabel("E-mail: ");
        lbemail.setFont(font);

        email = new JTextField();
        email.setFont(font);

        JLabel lbcontactnum = new JLabel("Contact Number: ");
        lbcontactnum.setFont(font);

        contactnum = new JTextField();
        contactnum.setFont(font);

        JLabel lbaddress = new JLabel("Address: ");
        lbaddress.setFont(font);

        address = new JTextField();
        address.setFont(font);

        JLabel lbcity = new JLabel("City: ");
        lbcity.setFont(font);

        city = new JTextField();
        city.setFont(font);

        JLabel lbstate = new JLabel("State: ");
        lbstate.setFont(font);

        state = new JTextField();
        state.setFont(font);

        JLabel lbposcode = new JLabel("Poscode: ");
        lbposcode.setFont(font);

        poscode = new JTextField();
        poscode.setFont(font);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 100));
        formPanel.add(signup);
        formPanel.add(lbusername);
        formPanel.add(username);
        formPanel.add(lbpswd);
        formPanel.add(pswd);
        formPanel.add(lbemail);
        formPanel.add(email);
        formPanel.add(lbcontactnum);
        formPanel.add(contactnum);
        formPanel.add(lbaddress);
        formPanel.add(address);
        formPanel.add(lbcity);
        formPanel.add(city);
        formPanel.add(lbstate);
        formPanel.add(state);
        formPanel.add(lbposcode);
        formPanel.add(poscode);

        // signup button
        JButton btnsignup = new JButton("Signup");
        btnsignup.setPreferredSize(buttonSize);
        btnsignup.setMaximumSize(maxsize);
        btnsignup.setMinimumSize(minsize);
        btnsignup.setFont(font);
        btnsignup.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                signup();                    
            } 
        });

        // login button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(btnsignup);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);

        add(formPanel, BorderLayout.NORTH);

        setMinimumSize(new Dimension(500, 450));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // signup to db
    private void signup() {
        String Username = username.getText();
        String Password = new String(pswd.getPassword());
        String Email = email.getText();
        String contactNum = contactnum.getText();
        String Address = address.getText();
        String City = city.getText();
        String State = state.getText();
        String Poscode = poscode.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pricecatcher", "sqluser", "welcome1");
            String query = "INSERT INTO user (username, password, email, contactnum, address, city, state, poscode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pS = connection.prepareStatement(query);
            pS.setString(1, Username);
            pS.setString(2, Password);
            pS.setString(3, Email);
            pS.setString(4, contactNum);
            pS.setString(5, Address);
            pS.setString(6, City);
            pS.setString(7, State);
            pS.setString(8, Poscode);

            int result = pS.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Signup successful!");

                // write user details to CSV file
                writeUserToCSV(Username, Password, Email, contactNum, Address, City, State, Poscode);

                Login login = new Login();
                login.initialize();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Signup failed. Please try again.");
            }

            pS.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error. Please try again.");
        }
    }

    // write user to CSV
    private void writeUserToCSV(String username, String password, String email, String contactNum, String address, String city, String state, String poscode) {
        String csvFilePath = "userDetails.csv";

        try (FileWriter writer = new FileWriter(csvFilePath, true)) {
            writer.append(username).append(",").append(password).append(",").append(email)
                    .append(",").append(contactNum).append(",").append(address).append(",")
                    .append(city).append(",").append(state).append(",").append(poscode).append("\n");

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error writing user details to CSV file.");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Signup();
        });       
    }
}
