import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Scheduler {
    public List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServers=100;
    private Strategy strategy;
    private List<Thread> threads;

    public Scheduler(int maxNoServers, String selectionPolicy) {
        threads = new ArrayList<>();
        servers = new ArrayList<>();
        for (int i = 0; i < maxNoServers; i++) {
            BlockingQueue<Task> newQueue = new ArrayBlockingQueue<Task>(maxTasksPerServers);
            servers.add(new Server(newQueue));
            threads.add(new Thread(servers.get(i)));
        }

        if (selectionPolicy.compareTo("time") == 0)
            changeStrategy(SelectionPolicy.SHORTEST_TIME);
        else if (selectionPolicy.compareTo("length") == 0)
            changeStrategy(SelectionPolicy.SHORTEST_QUEUE);
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        } else if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public int dispatchTask(Task t) {
        //call the strategy addTask method
        return strategy.addTask(servers, t, maxTasksPerServers);
    }

    public void startServing(){
        for(Thread t: threads){
            t.start();
        }
    }
}
