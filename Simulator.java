import java.util.function.Supplier;

/**
 * Models a Simulator class to simulate the interaction between
 * customer and the server.
 */
public class Simulator {
    private final ImList<Server> servers;
    private final ImList<Customer> customers;
    private final int numOfServers;

    /**
     * Build constructor for Simulator class.
     * @param numOfServers the total of servers.
     * @param qmax the max queue of each servers.
     * @param arrivalTimes the arrival times of customers.
     * @param serviceTime the service time of customers.
     */
    public Simulator(int numOfServers, int numOfSelfChecks, int qmax, ImList<Double> arrivalTimes,
                     Supplier<Double> serviceTime, Supplier<Double> restTimes) {

        this.numOfServers = numOfServers;

        ImList<Server> servers = new ImList<>();
        for (int i = 1; i <= numOfServers; i++) {
            servers = servers.add(new HumanServer(0.000, qmax, 0, i, restTimes));
        }

        for (int i = numOfServers + 1; i <= numOfServers + numOfSelfChecks; i++) {
            servers = servers.add(new SelfCheckOut(0.000, qmax, 0, i, restTimes));
        }

        this.servers = servers;

        ImList<Customer> customers = new ImList<>();
        for (int i = 1; i <= arrivalTimes.size(); i++) {
            customers = customers.add(new Customer(arrivalTimes.get(i - 1), i, serviceTime));
        }
        this.customers = customers;
    }

    /**
     * Method to simulate the all the events that already queued.
     * @return the String representation of the simulation.
     */
    public String simulate() {
        StringBuilder resultSb = new StringBuilder();
        PQ<Event> queue = new PQ<>(new EventComp());
        ImList<Server> currentServers = servers;

        int servedCustomer = 0;
        int leftCustomer = 0;
        double waitTime = 0;

        for (int i = 0; i < customers.size(); i++) {
            queue = queue.add(new Arrive(customers.get(i).getArrivalTime(),
                    customers.get(i).getCustomerNum(), customers.get(i)));
        }

        Pair<Event, PQ<Event>> pr;

        while (!queue.isEmpty()) {
            pr = queue.poll();
            Event event = pr.first();
            queue = pr.second();

            servedCustomer = event.updateServed(servedCustomer);
            leftCustomer = event.updateLeft(leftCustomer);
            waitTime = event.updateWaitTime(waitTime, currentServers);

            if (event.toString() != "") {
                resultSb.append(event + "\n");
            }

            Pair<Event, ImList<Server>> result = event.updateStatus(currentServers, numOfServers);
            currentServers = result.second();

            if (!event.equals(result.first())) {
                queue = queue.add(result.first());
            }
        }

        double avgWaitTime;
        if (waitTime == 0.00 && servedCustomer == 0) {
            avgWaitTime = waitTime;
        } else {
            avgWaitTime = waitTime / servedCustomer;
        }

        resultSb.append("[" + String.format("%.3f", avgWaitTime) + " " + servedCustomer + " " +
                leftCustomer + "]");

        return resultSb.toString();
    }

}
