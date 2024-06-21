package Acosta_gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import Acosta_menu.Menu;

class MenuFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public MenuFrame() {
        setTitle("Cashier Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JLabel lblMenu = new JLabel("Welcome to the Cashier Menu");
        lblMenu.setFont(new Font("Harrington", Font.BOLD, 40));
        lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblMenu, BorderLayout.CENTER);

       
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Close the menu frame
                openMenu(); // Open the Menu class
            }
        });
        timer.setRepeats(false); // Only run once
        timer.start();
    }

    private void openMenu() {
        Menu menu = new Menu();
        menu.setVisible(true);
    }
}


public class Proto extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField_User;
    private JButton btnEnter;
    private JPasswordField passwordField;
    private JLabel labelResult;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Proto frame = new Proto();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Proto() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ADMIN\\Pictures\\S_CUP.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1645, 900);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblLogin = new JLabel("Log In");
        lblLogin.setForeground(new Color(0, 0, 0));
        lblLogin.setFont(new Font("Harrington", Font.BOLD, 60));
        lblLogin.setBounds(1135, 116, 189, 152);
        contentPane.add(lblLogin);

        textField_User = new JTextField();
        textField_User.setFont(new Font("Tahoma", Font.ITALIC, 13));
        textField_User.setBounds(1058, 336, 343, 33);
        contentPane.add(textField_User);
        textField_User.setColumns(10);

        JLabel lblUser = new JLabel("Username");
        lblUser.setForeground(new Color(0, 0, 0));
        lblUser.setFont(new Font("Harrington", Font.BOLD, 16));
        lblUser.setBounds(1058, 305, 89, 20);
        contentPane.add(lblUser);

        JLabel lblPW = new JLabel("Password");
        lblPW.setFont(new Font("Harrington", Font.BOLD, 16));
        lblPW.setBounds(1058, 411, 89, 14);
        contentPane.add(lblPW);

        passwordField = new JPasswordField();
        passwordField.setBounds(1058, 436, 343, 33);
        contentPane.add(passwordField);

        labelResult = new JLabel("");
        labelResult.setFont(new Font("Tahoma", Font.ITALIC, 13));
        labelResult.setForeground(Color.RED);
        labelResult.setBounds(1058, 500, 343, 20);
        contentPane.add(labelResult);

        btnEnter = new JButton("Enter");
        btnEnter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userText = textField_User.getText();
                String pwdText = new String(passwordField.getPassword());

                if (userText.isEmpty() && pwdText.isEmpty()) {
                    labelResult.setText("Please enter your username and password first");
                } else if (userText.isEmpty()) {
                    labelResult.setText("Please enter your username");
                } else if (pwdText.isEmpty()) {
                    labelResult.setText("Please enter your password");
                } else if (Authentication.authenticate(userText, pwdText)) {
                    labelResult.setText("Login successful");
                    dispose();  // Close the login window

                    // Open MenuFrame
                    MenuFrame menuFrame = new MenuFrame();
                    menuFrame.setVisible(true);
                } else {
                    labelResult.setText("Invalid username or password");
                }
            }
        });
        btnEnter.setFont(new Font("Harrington", Font.BOLD, 14));
        btnEnter.setForeground(new Color(46, 139, 87));
        btnEnter.setBounds(1151, 566, 173, 33);
        contentPane.add(btnEnter);

        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnExit.setFont(new Font("Harrington", Font.BOLD, 14));
        btnExit.setForeground(new Color(255, 0, 0));
        btnExit.setBounds(1436, 783, 144, 33);
        contentPane.add(btnExit);

        JLabel lbl_littleC = new JLabel("");
        lbl_littleC.setIcon(new ImageIcon("C:\\Users\\ADMIN\\Pictures\\C1.png"));
        lbl_littleC.setBounds(0, -13, 115, 123);
        contentPane.add(lbl_littleC);

        JLabel lbl_bigC = new JLabel("");
        lbl_bigC.setIcon(new ImageIcon("C:\\Users\\ADMIN\\Pictures\\C2.png"));
        lbl_bigC.setBounds(262, 250, 372, 295);
        contentPane.add(lbl_bigC);

        JLabel lblWelcome = new JLabel("Welcome to");
        lblWelcome.setFont(new Font("Harrington", Font.BOLD | Font.ITALIC, 80));
        lblWelcome.setBounds(232, 82, 535, 150);
        contentPane.add(lblWelcome);

        JLabel lblCode = new JLabel("Code");
        lblCode.setFont(new Font("Harrington", Font.PLAIN, 220));
        lblCode.setBounds(149, 229, 636, 312);
        contentPane.add(lblCode);

        JLabel lblBrew = new JLabel("Brew");
        lblBrew.setFont(new Font("Harrington", lblBrew.getFont().getStyle(), 200));
        lblBrew.setBounds(301, 512, 520, 201);
        contentPane.add(lblBrew);

        JLabel lblBG = new JLabel("");
        lblBG.setHorizontalAlignment(SwingConstants.CENTER);
        lblBG.setVerticalAlignment(SwingConstants.TOP);
        lblBG.setIcon(new ImageIcon("C:\\Users\\ADMIN\\Pictures\\BG.jpg"));
        lblBG.setBounds(-17, -35, 1645, 900);
        contentPane.add(lblBG);
    }
}
