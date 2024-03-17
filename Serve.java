/**
 * Models a Serve Class that extends from Event,
 * used to simulate the SERVE event.
 */
public class Serve extends Event {
    private final int serverNum;
    private final int numOfServers;

    /**
     * Build the Serve constructor.
     * @param time the time of the event.
     * @param eventNum the number of the event.
     * @param customer the customer of the event.
     * @param serverNum the number of the server.
     */
    public Serve(double time, int eventNum, Customer customer, int serverNum, int numOfServers) {
        super(time, eventNum, customer);
        this.serverNum = serverNum;
        this.numOfServers = numOfServers;
    }

    @Override
    int getServerNum() {
        return serverNum;
    }

    @Override
    Pair<Event, ImList<Server>> updateStatus(ImList<Server> servers, int numOfServers) {
        double serviceTime = getCustomer().getServiceTime();

        servers = servers.set(serverNum - 1,
                servers.get(serverNum - 1).updateAvailableTime(getTime(), serviceTime));

        if (serverNum > numOfServers) {
            servers = removeCheckoutQueue(servers, numOfServers);
        }

        Event nextEvent = new Done(getTime() + serviceTime, getCustomer().getCustomerNum(),
                getCustomer(), serverNum, numOfServers);

        return new Pair<>(nextEvent, servers);
    }

    ImList<Server> removeCheckoutQueue(ImList<Server> servers, int numOfServers) {
        for (int i = numOfServers; i < servers.size(); i++) {
            servers = servers.set(i, servers.get(i).removeQueue());
        }

        return servers;
    }

    @Override
    double updateWaitTime(double waitTime, ImList<Server> servers) {
        double current = getTime() - getCustomer().getArrivalTime();
        return waitTime + current;
    }

    @Override
    int updateServed(int servedCustomer) {
        return servedCustomer + 1;
    }

    @Override
    public String toString() {
        if (serverNum > numOfServers) {
            return String.format("%.3f " + getCustomer().getCustomerNum() +
                    " serves by self-check " + getServerNum(), getTime());
        }

        return String.format("%.3f " + getCustomer().getCustomerNum() + " serves by " +
                getServerNum(), getTime());
    }
}
