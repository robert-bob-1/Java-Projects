import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimulationSetup extends JFrame {

    private JTextField timeLimit=new JTextField(15);
    private JTextField maxProcessingTime=new JTextField(15);
    private JTextField minProcessingTime=new JTextField(15);
    private JTextField maxArrivalTime=new JTextField(15);
    private JTextField minArrivalTime=new JTextField(15);
    private JTextField numberOfServers=new JTextField(15);
    private JTextField numberOfClients=new JTextField(15);
    private JTextField selectionPolicy=new JTextField(15);

    private JButton submitBtn= new JButton("Submit");

    SimulationSetup(){
        JPanel simulationPanel=new JPanel();
        simulationPanel.setLayout(new BoxLayout(simulationPanel, BoxLayout.Y_AXIS));

        simulationPanel.add(new JLabel("Time Limit"));
        simulationPanel.add(timeLimit);

        simulationPanel.add(new JLabel("Minimum Arrival Time"));
        simulationPanel.add(minArrivalTime);
        simulationPanel.add(new JLabel("Maximum Arrival Time"));
        simulationPanel.add(maxArrivalTime);
        simulationPanel.add(new JLabel("Minimum Service Time"));
        simulationPanel.add(minProcessingTime);
        simulationPanel.add(new JLabel("Maximum Service Time"));
        simulationPanel.add(maxProcessingTime);
        simulationPanel.add(new JLabel("Number of Servers"));
        simulationPanel.add(numberOfServers);
        simulationPanel.add(new JLabel("Number of Clients"));
        simulationPanel.add(numberOfClients);
        simulationPanel.add(new JLabel("Selection Policy (time ; length)"));
        simulationPanel.add(selectionPolicy);

        simulationPanel.add(submitBtn);

        this.setContentPane(simulationPanel);
        this.pack();
        this.setTitle("Simulation Panel");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    void submitListener(ActionListener aal) {
        submitBtn.addActionListener(aal);
    }
    void showError(String errMessage){ JOptionPane.showMessageDialog(this, errMessage);}
    public String getTimeLimit() {
        return timeLimit.getText();
    }
    public String getMaxProcessingTime() {
        return maxProcessingTime.getText();
    }
    public String getMinProcessingTime() {
        return minProcessingTime.getText();
    }
    public String getMaxArrivalTime() {
        return maxArrivalTime.getText();
    }
    public String getMinArrivalTime() {
        return minArrivalTime.getText();
    }
    public String getNumberOfServers() {
        return numberOfServers.getText();
    }
    public String getNumberOfClients() {
        return numberOfClients.getText();
    }
    public String getSelectionPolicy() {
        return selectionPolicy.getText();
    }

    public static void main(String[] args){
        SimulationSetup view = new SimulationSetup();
        view.setVisible(true);
    }
}
