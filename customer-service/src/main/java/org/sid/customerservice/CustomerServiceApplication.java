package org.sid.customerservice;

import org.sid.customerservice.entities.Customer;
import org.sid.customerservice.repositories.CustomerRepository;
import org.sid.customerservice.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(CustomerRepository customerRepository, CustomerService customerService) {
        return args -> {
            List<String> names = List.of("Mohamed","Imane","yassine");
            names.forEach(name->{
                customerService.saveCustomer(Customer.builder()
                        .name(name).email(name + "@gmail.com")
                        .build());
            });

        };
    }
}
