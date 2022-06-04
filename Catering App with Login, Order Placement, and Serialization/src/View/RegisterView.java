package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;

import Model.BaseProduct;
import Model.OrderedProduct;


public class RegisterView extends View{
    private JFrame registerFrame = new JFrame();

    private JPanel registerPanel = new JPanel();
    private JLabel usernameLabel = new JLabel("Username");
    private JTextField usernameText = new JTextField(15);
    private JLabel passwordLabel = new JLabel("Password");
    private JTextField passwordText = new JTextField(15);

    private String[] userTypes = {"ADMIN", "CLIENT"};
    private JComboBox<String> typeCombo = new JComboBox<>(userTypes);

    private JButton registerButton = new JButton("Register");



    public RegisterView(){
        frame = new JFrame();
        registerPanel.setLayout(new GridBagLayout());
        registerPanel.setMinimumSize(new Dimension(400, 300));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        registerPanel.add(usernameLabel,gbc);
        gbc.gridx = 1;
        registerPanel.add(usernameText,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        registerPanel.add(passwordLabel,gbc);
        gbc.gridx = 1;
        registerPanel.add(passwordText,gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        registerPanel.add(typeCombo,gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        registerPanel.add(registerButton,gbc);

    }
    public void viewFrame(){
        frame.setLocation(500, 400);
        frame.setSize(400, 300);
        frame.setContentPane(registerPanel);
        frame.setTitle("Register User");

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }
    public String getUsername(){ return usernameText.getText();}
    public String getPassword(){ return passwordText.getText();}
    public String getRole(){ return (String) typeCombo.getSelectedItem();}
    public void registerListener(ActionListener al){ registerButton.addActionListener(al);}
    public static void main(String[] args){
        RegisterView view = new RegisterView();
        view.viewFrame();
    }
}


