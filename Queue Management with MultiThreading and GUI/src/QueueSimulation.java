import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class QueueSimulation extends JFrame {
    public TextField tasks;
    public TextField statistics;
    public ArrayList<TextField> textFields;

    private JLabel currentTimeLabel = new JLabel("Time: ");
    private JButton submitBtn = new JButton("Submit");

    QueueSimulation(int numberOfServers) {
        JPanel simulationPanel = new JPanel();
        simulationPanel.setLayout(new BoxLayout(simulationPanel, BoxLayout.Y_AXIS));

        simulationPanel.add(new JLabel("Statistics"));
        statistics = new TextField(50);
        statistics.setEditable(false);
        simulationPanel.add(statistics);

        simulationPanel.add(currentTimeLabel);
        simulationPanel.add(new JLabel("Task List (ID arrivalTime serviceTime)"));
        tasks = new TextField(80);
        tasks.setEditable(false);
        simulationPanel.add(tasks);
        textFields = new ArrayList<>();

        for (int i = 1; i <= numberOfServers; i++) {
            simulationPanel.add(new JLabel("Queue " + Integer.toString(i)));

            TextField newTextField = new TextField(80);
            newTextField.setEditable(false);
            textFields.add(newTextField);
            simulationPanel.add(newTextField);
        }
        JScrollPane scrollPane = new JScrollPane(simulationPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50, 30, 900, 750);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(1000, 800));
        contentPane.add(scrollPane);

        this.setContentPane(contentPane);
        this.pack();
        this.setTitle("Simulation Panel");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void updateQueues(List<Server> servers) {
        for (int i = 0; i < servers.size(); i++) {
            Server s = servers.get(i);
            if (s.working != null) {
                textFields.get(i).setText("Working on " + s.working.toString() + " Queue: " + s.getTasks().toString());
            } else {
                textFields.get(i).setText(s.getTasks().toString());
            }
        }
    }

    public void updateTasks(List<Task> generatedTasks) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Task t : generatedTasks) {
            stringBuilder.append(t.toString());
        }
        tasks.setText(generatedTasks.toString());
    }

    public void updateTime(int time) {
        currentTimeLabel.setText("Time: " + time);
    }

    public void updateStatistics(int peakHour, int totalWaitingTime, List<Server> servers, int totalServiceTime) {
        int totalCustomers = 0;
        int totalProcessedCustomers = 0;
        for (Server s : servers) {
            totalCustomers += s.clientsQueued;
            totalProcessedCustomers += s.clientsProcessed;
        }
        if (totalProcessedCustomers == 0)
            totalProcessedCustomers = 1;
        statistics.setText("Peak Hour: " + peakHour +
                " Average Waiting Time: " + (float) totalWaitingTime / totalCustomers +
                " Average Service Time: " + (float) totalServiceTime / totalProcessedCustomers);
    }

    public void updateAll(int currentTime, List<Task> generatedTasks, List<Server> servers, int peakHour, int totalWaitingTime, int totalServiceTime) {
        this.updateTime(currentTime);
        this.updateTasks(generatedTasks);
        this.updateQueues(servers);
        this.updateStatistics(peakHour, totalWaitingTime, servers, totalServiceTime);
    }
}