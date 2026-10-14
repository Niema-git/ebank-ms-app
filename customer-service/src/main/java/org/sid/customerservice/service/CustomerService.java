package org.sid.customerservice.service;

import org.sid.customerservice.entities.Customer;
import org.sid.customerservice.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
    public Customer findCustomerById(Long id){
        return customerRepository.findById(id)
                .orElseThrow(()->new RuntimeException(("Customer not found")));
    }
    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }
}
