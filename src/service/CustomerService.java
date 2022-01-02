package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private static final CustomerService SINGLETON = new CustomerService();

    private final Map<String, Customer> customers = new HashMap<String, Customer>();

    private CustomerService() {}

    public static CustomerService getSingleton(){
        return SINGLETON;
    }


    /* adds a customer to the customer hashmap
    @param email, firstname, lastname
     */
    public void addCustomer(String email, String firstname, String lastname) {
        Customer customer = new Customer(firstname, lastname, email);
        customers.put(email, customer);
    }

    /* displays the customer information with the specific email
    @param customerEmail the email address (key) from the customer you want to display
    @return Customer-object
     */
    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomer() {
        return (Collection<Customer>) customers;
    }

}
