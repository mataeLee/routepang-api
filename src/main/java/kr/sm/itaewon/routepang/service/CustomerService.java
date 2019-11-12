package kr.sm.itaewon.routepang.service;

import kr.sm.itaewon.routepang.model.Session;
import org.mindrot.jbcrypt.BCrypt;
import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Role;
import kr.sm.itaewon.routepang.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer findByCustomerId(long customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId);
        return customer;
    }


    public Customer findByAccount(String account) {
        Customer customer = customerRepository.findByAccount(account);
        return customer;
    }

    public void save(Customer customer){
        customerRepository.save(customer);
    }

}
