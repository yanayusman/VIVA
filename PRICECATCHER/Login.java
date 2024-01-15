package PRICECATCHER;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.*;

public class Login extends JFrame {
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

        // background image
        JLabel background = new JLabel();
        try {
            String absolutePath = new File("bg.png").getAbsolutePath();
            Image img = new ImageIcon(absolutePath).getImage();

            int width = 1000;
            int height = 900;
            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            background.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(background);
        setLayout(new BorderLayout());

        // main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));

        // form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 80, 80, 100));

        JLabel resizedImageLabel = loadImage("loginbg_3.png", true,70, 70);
        formPanel.add(resizedImageLabel);

        JLabel lblogin = new JLabel("Login Form", SwingConstants.CENTER);
        lblogin.setFont(mainFont);
        formPanel.add(lblogin);

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

        formPanel.add(lbusername);
        formPanel.add(tfusername);
        formPanel.add(lbpswd);
        formPanel.add(pfpswd);
        formPanel.add(signup);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        // button panel
        JButton btnlogin = new JButton("Login");
        btnlogin.setPreferredSize(buttonSize);
        btnlogin.setMaximumSize(maxsize);
        btnlogin.setMinimumSize(minsize);
        btnlogin.setFont(mainFont);
        btnlogin.addActionListener(e -> {
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
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.add(btnlogin);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // load image
    public static JLabel loadImage(String fileName, boolean isResized, int targetWidth, int targetHeight) {
        BufferedImage image;
        JLabel imageContainer;

        try {
            image = ImageIO.read(new File(fileName));

            if (isResized) {
                image = resizeImage(image, targetWidth, targetHeight);
            }

            imageContainer = new JLabel(new ImageIcon(image));
            return imageContainer;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    // resize image
    public static BufferedImage resizeImage(BufferedImage image, int targetWidth, int targetHeight) {
        BufferedImage resizedImg = new BufferedImage(targetWidth,
                targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImg.createGraphics();
        graphics2D.drawImage(image, 0, 0, targetWidth,
                targetHeight, null);
        graphics2D.dispose();
        return resizedImg;
    }

    // retrieve from db
    private User getAuthenticatedUser(String username, String pswd) {
        User user = null;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pricecatcher", "sqluser", "welcome1");) {
            String sql = "SELECT * FROM user WHERE username=? AND password=?";
            PreparedStatement pS = conn.prepareStatement(sql);
            pS.setString(1, username);
            pS.setString(2, pswd);

            ResultSet resultSet = pS.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.username = resultSet.getString("username");
                user.password = resultSet.getString("password");
            }
            pS.close();

        } catch (Exception e) {
            System.out.println("Database connection failed");
        }
        return user;
    }

    public String getUsername() {
        return username;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login();
        });
    }
}
