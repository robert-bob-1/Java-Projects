package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;

import Model.BaseProduct;
import Model.OrderedProduct;


public class LoginView extends View{
    
    private JPanel loginPanel = new JPanel();
    private JLabel usernameLabel = new JLabel("Username");
    private JTextField usernameText = new JTextField(15);
    private JLabel passwordLabel = new JLabel("Password");
    private JTextField passwordText = new JTextField(15);

    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton ("Register new user");

    

    public LoginView(){
        frame = new JFrame();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setMinimumSize(new Dimension(400, 300));
        GridBagConstraints gbc = new GridBagConstraints();

        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(usernameLabel,gbc);
        gbc.gridx = 1;
        loginPanel.add(usernameText,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(passwordLabel,gbc);
        gbc.gridx = 1;
        loginPanel.add(passwordText,gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        loginPanel.add(loginButton,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        loginPanel.add(registerButton,gbc);
        
        frame.setLocation(500, 400);
        frame.setContentPane(loginPanel);
        frame.setTitle("Login");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }
    public void viewFrame(){
        frame.setLocation(500, 400);
        frame.setSize(400, 300);
        frame.setContentPane(loginPanel);
        frame.setTitle("Login");

        frame.setVisible(true);
        frame.pack();
    }
    public void loginListener(ActionListener al){ loginButton.addActionListener(al);}
    public void registerListener(ActionListener al){ registerButton.addActionListener(al);}

    public String getUsernameText() {return usernameText.getText();}
    public String getPasswordText() {return passwordText.getText();}

    public static void main(String[] args){
        LoginView view = new LoginView();
    }
}


