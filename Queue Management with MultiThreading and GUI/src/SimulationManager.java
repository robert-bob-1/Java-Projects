import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;

public class SimulationManager implements Runnable {
    //data read from UI
    public int timeLimit = 10;       //maximum processing time read from ui
    public int maxProcessingTime = 10;
    public int minProcessingTime = 1;
    public int numberOfServers = 5;
    public int numberOfClients = 10;
    public int minArrivalTime = 1;
    public int maxArrivalTime = 10;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;

    //entity responsible with queue management and client distribution
    private Scheduler scheduler;
    //frame for displaying simulation
    private QueueSimulation queueSimulation;
    //pool of tasks
    private List<Task> generatedTasks;

    //File parameters
    File file = new File("log.txt");

    int totalWaitingTime;
    int totalServiceTime;
    int peakHour;


    public SimulationManager(SimulationSetup setup) throws IOException {
        generatedTasks = new ArrayList<>();

        if (setup.getTimeLimit().compareTo("") != 0) timeLimit = parseInt(setup.getTimeLimit());
        if (setup.getMaxProcessingTime().compareTo("") != 0) maxProcessingTime = parseInt(setup.getMaxProcessingTime());
        if (setup.getMinProcessingTime().compareTo("") != 0) minProcessingTime = parseInt(setup.getMinProcessingTime());
        if (setup.getMaxArrivalTime().compareTo("") != 0) maxArrivalTime = parseInt(setup.getMaxArrivalTime());
        if (setup.getMinArrivalTime().compareTo("") != 0) minArrivalTime = parseInt(setup.getMinArrivalTime());
        if (setup.getNumberOfServers().compareTo("") != 0) numberOfServers = parseInt(setup.getNumberOfServers());
        if (setup.getNumberOfClients().compareTo("") != 0) numberOfClients = parseInt(setup.getNumberOfClients());
        String selPolicy = "length";
        if (setup.getSelectionPolicy().compareTo("") != 0) {
            selPolicy = setup.getSelectionPolicy();
        }
        generateNRandomTasks();
        queueSimulation = new QueueSimulation(numberOfServers);
        queueSimulation.setVisible(true);
        queueSimulation.updateTasks(generatedTasks);
        scheduler = new Scheduler(numberOfServers, selPolicy);
        file.createNewFile();
    }

    public void generateNRandomTasks() {
        for (int i = 1; i <= numberOfClients; i++) {
            generatedTasks.add(new Task(i, minArrivalTime, maxArrivalTime, minProcessingTime, maxProcessingTime, timeLimit));
        }
        Collections.sort(generatedTasks);
    }

    @Override
    public void run() {
        int currentTime = 0;
        scheduler.startServing();
        FileWriter writer;
        try {
            writer = new FileWriter("logTest3.txt");
            writer.close();
            int peakCustomerNumber = 0;
            while (currentTime < timeLimit + 1 && checkQueuesAndTasks(currentTime)) {
                addClientsIfArrived(currentTime);
                peakCustomerNumber = checkPeakHour(currentTime, peakCustomerNumber);
                Thread.sleep(1);
                increaseWaitTimes(scheduler.servers);
                queueSimulation.updateAll(currentTime, generatedTasks, scheduler.servers, peakHour, totalWaitingTime, totalServiceTime);
                writeFile(currentTime, generatedTasks, scheduler.servers);
                currentTime++;
                Thread.sleep(999);
            }
            queueSimulation.updateStatistics(peakHour, totalWaitingTime, scheduler.servers, totalServiceTime);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkQueuesAndTasks(int currentTime) {
        if (generatedTasks.size() > 0)
            return true;
        for (Server s : scheduler.servers) {
            if (s.getTasks().size() > 0 || s.working != null)
                return true;
        }
        return false;
    }

    private void increaseWaitTimes(List<Server> servers) {
        for (Server s : servers) {
            if (s.working != null) {
                totalServiceTime++;
                totalWaitingTime++;
            }
            totalWaitingTime += s.getTasks().size();
        }
    }

    private int checkPeakHour(int currentTime, int peakCustomerNumber) {
        int currentClientNumber = 0;
        for (Server s : scheduler.servers) {
            currentClientNumber += s.getTasks().size();
            if (s.working != null)
                currentClientNumber++;
        }
        if (currentClientNumber > peakCustomerNumber) {
            peakHour = currentTime;
            peakCustomerNumber = currentClientNumber;
        }
        return peakCustomerNumber;
    }

    private void addClientsIfArrived(int currentTime) {
        int index = 0;
        while (index < generatedTasks.size()) {
            Task t = generatedTasks.get(index);
            if (t.arrivalTime == currentTime) {
                if (scheduler.dispatchTask(t) == -1) {
                    currentTime--;
                    break;
                }
                generatedTasks.remove(t);
            } else index++;
        }
    }

    public void writeFile(int currentTime, List<Task> generatedTasks, List<Server> servers) throws IOException {
        FileWriter writer = new FileWriter("logTest3.txt", true);//write time
        writer.write("Time " + currentTime + "\n");
        StringBuilder stringBuilder = new StringBuilder();//write remaining tasks
        for (Task t : generatedTasks) {
            stringBuilder.append(t.toString());
        }
        writer.write("Remaining Tasks:\n" + stringBuilder.toString() + "\n");
        int totalCustomers = 0;
        int totalProcessedCustomers = 0;
        for (int i = 0; i < servers.size(); i++) {
            Server s = servers.get(i);
            totalCustomers += s.clientsQueued;
            totalProcessedCustomers += s.clientsProcessed;
            if (s.working != null) {
                writer.write("Working on " + s.working.toString() + " Queue: " + s.getTasks().toString() + "\n");
            } else {
                writer.write(s.getTasks().toString() + "\n");
            }
        }
        if (totalProcessedCustomers == 0)
            totalProcessedCustomers = 1;
        writer.write("Statistics  Peak Hour: " + peakHour +
                " Average Waiting Time: " + (float) totalWaitingTime / totalCustomers +
                " Average Service Time: " + (float) totalServiceTime / totalProcessedCustomers + "\n");
        writer.write("\n");
        writer.close();
    }

    public static void main(String[] args) {
        SimulationSetup setup = new SimulationSetup();
        Controller controller = new Controller(setup);

    }
}
