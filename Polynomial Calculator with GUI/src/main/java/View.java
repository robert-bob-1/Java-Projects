package main.java;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class View extends JFrame {

    private JTextField firstPolynomial=new JTextField(15);
    private JTextField secondPolynomial=new JTextField(15);
    private JTextField finalPolynomial=new JTextField(15);

    private JButton addBtn= new JButton("Add");
    private JButton subBtn= new JButton("Subtract");
    private JButton mulBtn= new JButton("Multiply");
    private JButton divBtn= new JButton("Divide");
    private JButton derBtn= new JButton("Derive");
    private JButton intBtn= new JButton("Integrate");


    View() {

        JPanel content= new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));

        JPanel polynomialPanel=new JPanel();
        polynomialPanel.setLayout(new BoxLayout(polynomialPanel, BoxLayout.Y_AXIS));
        polynomialPanel.add(new JLabel("First Polynomial"));
        polynomialPanel.add(firstPolynomial);
        polynomialPanel.add(new JLabel("Second Polynomial"));
        polynomialPanel.add(secondPolynomial);
        polynomialPanel.add(new JLabel("Final Polynomial"));
        polynomialPanel.add(finalPolynomial);
        content.add(polynomialPanel);

        JPanel operationsPanel=new JPanel();
        operationsPanel.setLayout(new GridLayout(3,2));
        operationsPanel.add(addBtn);
        operationsPanel.add(subBtn);
        operationsPanel.add(mulBtn);
        operationsPanel.add(divBtn);
        operationsPanel.add(derBtn);
        operationsPanel.add(intBtn);
        content.add(operationsPanel);


        this.setContentPane(content);
        this.pack();
        this.setTitle("Polynomial Calculator");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void reset(){
        firstPolynomial.setText("");
        secondPolynomial.setText("");
        finalPolynomial.setText("");
    }
    String getFirstPolynomial(){ return firstPolynomial.getText(); }

    String getSecondPolynomial(){ return secondPolynomial.getText(); }

    void setFinalPolynomial(String polynomial){ finalPolynomial.setText(polynomial);}

    void showError(String errMessage){ JOptionPane.showMessageDialog(this, errMessage);}

    void addListener(ActionListener aal) {
        addBtn.addActionListener(aal);
    }

    void subListener(ActionListener sal) { subBtn.addActionListener(sal); }

    void mulListener(ActionListener mal) { mulBtn.addActionListener(mal); }

    void divListener(ActionListener dal) { divBtn.addActionListener(dal); }

    void derListener(ActionListener dal) { derBtn.addActionListener(dal); }

    void intListener(ActionListener ial) { intBtn.addActionListener(ial); }


    public static void main(String[] args){
        View view = new View();
        view.setVisible(true);
    }



}
