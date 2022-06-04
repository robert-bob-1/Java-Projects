import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    @Override
    public int addTask(List<Server> servers, Task t, int maxClientsPerServer) {
        //TODO Auto-generated method stub
        int minWaitingPeriod = Integer.MAX_VALUE;
        Server serverToGiveTask = null;
        for (Server s : servers) {
            if (s.getWaitingPeriod() < minWaitingPeriod && s.getTasks().size()<maxClientsPerServer-1) {
                serverToGiveTask = s;
                minWaitingPeriod = s.getWaitingPeriod();
            }
        }
        if(serverToGiveTask==null) return -1;

        serverToGiveTask.addTask(t);
        return 0;
    }
}
