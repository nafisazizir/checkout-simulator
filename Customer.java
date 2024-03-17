import java.util.function.Supplier;

/**
 * Models a Customer class to store all the information
 * of each customer.
 */
public class Customer {
    private final double arrivalTime;
    private final Supplier<Double> serviceTime;
    private final int customerNum;

    /**
     * Build constructor for Customer class.
     * @param arrivalTime the arrival time of customer.
     * @param customerNum the number of the customer.
     * @param serviceTime the service time of the customer.
     */
    public Customer(double arrivalTime, int customerNum, Supplier<Double> serviceTime) {
        this.arrivalTime = arrivalTime;
        this.customerNum = customerNum;
        this.serviceTime = serviceTime;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public double getServiceTime() {
        return this.serviceTime.get();
    }

    public int getCustomerNum() {
        return this.customerNum;
    }

    @Override
    public String toString() {
        return arrivalTime + " customer " + customerNum + " with service time " + serviceTime;
    }
}
