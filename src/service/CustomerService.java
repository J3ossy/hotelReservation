package service;
import model.Customer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private static final CustomerService SINGLETON = new CustomerService();

    private final Map<String, Customer> customers = new HashMap<>();

    private CustomerService() {}

    public static CustomerService getSingleton(){
        return SINGLETON;
    }

    /**
     * This method  adds a new customer to the HasMap of customers
     * @param email The email from the Customer
     * @param firstname Customers firstname
     * @param lastname Customers lastname
     */
    public void addCustomer(String email, String firstname, String lastname) {
        Customer customer = new Customer(firstname, lastname, email);
        customers.put(email, customer);
    }

    /**
     * This method gets the customer by the customers email
     * @param customerEmail String the email from the customer you want to return
     * @return Returns the Customer
     */
    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    /**
     * This method gets all customer from the hotel
     * @return Returns the values of the customer HashMap
     */
    public Collection<Customer> getAllCustomer() {
        return customers.values();
    }

}
