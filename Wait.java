/**
 * Models a Wait Class that extends from Event,
 * used to simulate the WAIT event.
 */
public class Wait extends Event {
    private final int serverNum;
    private final boolean isWaited;
    private final int numOfServers;

    /**
     * Build Wait constructor.
     * @param time the time of the event.
     * @param eventNum the number of the event.
     * @param customer the customer of the event.
     * @param serverNum the number of the server.
     */
    public Wait(double time, int eventNum, Customer customer, int serverNum, int numOfServers) {
        super(time, eventNum, customer);
        this.serverNum = serverNum;
        this.isWaited = false;
        this.numOfServers = numOfServers;
    }

    /**
     * Build Wait constructor.
     * @param time the time of the event.
     * @param eventNum the number of the event.
     * @param customer the customer of the event.
     * @param serverNum the number of the server.
     * @param isWaited boolean to indicate the status of the class.
     */
    public Wait(double time, int eventNum, Customer customer, int serverNum, int numOfServers,
                boolean isWaited) {
        super(time, eventNum, customer);
        this.serverNum = serverNum;
        this.numOfServers = numOfServers;
        this.isWaited = isWaited;
    }

    @Override
    int getServerNum() {
        return serverNum;
    }

    @Override
    Pair<Event, ImList<Server>> updateStatus(ImList<Server> servers, int numOfServers) {
        Event nextEvent = new Leave(getCustomer().getArrivalTime(), getCustomer().getCustomerNum(),
                getCustomer());

        if (serverNum > numOfServers) {
            boolean isServed = false;
            for (int i = numOfServers; i < servers.size(); i++) {
                if (servers.get(i).checkQueueAndAvailability(getCustomer(), getTime())) {
                    servers = updateCheckoutQueue(servers, numOfServers);
                    nextEvent = new Serve(getTime(), getCustomer().getCustomerNum(), getCustomer(),
                            i + 1, numOfServers);
                    isServed = true;
                }
            }

            if (!isServed) {
                double nextTime = 100;

                if (numOfServers == 0) {
                    for (int i = 0; i < servers.size(); i++) {
                        double curr = servers.get(i).getAvailableTime();
                        if (curr < nextTime) {
                            nextTime = curr;
                        }
                    }
                } else {
                    for (int i = numOfServers; i < servers.size(); i++) {
                        double curr = servers.get(i).getAvailableTime();
                        if (curr < nextTime) {
                            nextTime = curr;
                        }
                    }
                }

                nextEvent = new Wait(nextTime,
                        getCustomer().getCustomerNum(), getCustomer(), serverNum, numOfServers,
                        true);
            }

        } else {
            if (servers.get(serverNum - 1).checkQueueAndAvailability(getCustomer(), getTime())) {
                servers = servers.set(serverNum - 1, servers.get(serverNum - 1).updateQueue(-1));
                nextEvent = new Serve(getTime(), getCustomer().getCustomerNum(), getCustomer(),
                        serverNum, numOfServers);
            } else {
                double nextTime = servers.get(serverNum - 1).getAvailableTime();
                nextEvent = new Wait(nextTime,
                        getCustomer().getCustomerNum(), getCustomer(), serverNum, numOfServers,
                        true);
            }
        }

        return new Pair<>(nextEvent, servers);
    }

    ImList<Server> updateCheckoutQueue(ImList<Server> servers, int numOfServers) {
        for (int i = numOfServers; i < servers.size(); i++) {
            servers = servers.set(i, servers.get(i).updateQueue(-1));
        }

        return servers;
    }

    @Override
    public String toString() {
        if (isWaited) {
            return "";
        }

        if (serverNum > numOfServers) {
            return String.format("%.3f " + getCustomer().getCustomerNum() +
                    " waits at self-check " + getServerNum(), getTime());
        } else {
            return String.format("%.3f " + getCustomer().getCustomerNum() + " waits at " +
                    getServerNum(), getTime());
        }
    }
}
