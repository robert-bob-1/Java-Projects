import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    @Override
    public int addTask(List<Server> servers, Task t, int maxClientsPerServer) {
        //TODO Auto-generated method stub
        int minSize = Integer.MAX_VALUE;
        Server serverToGiveTask = null;
        for (Server s : servers) {
            int sSize = s.getTasks().size();
            if (s.working!=null)
                sSize++;
            if (sSize < minSize && s.getTasks().size() < maxClientsPerServer - 1) {
                serverToGiveTask = s;
                minSize = sSize;
            }
        }
        if (serverToGiveTask == null) return -1;

        serverToGiveTask.addTask(t);
        return 0;
    }
}
