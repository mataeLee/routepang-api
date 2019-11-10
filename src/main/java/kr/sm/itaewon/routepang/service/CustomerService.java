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

    @Autowired
    private JwtService jwtService;

    public Customer signup(Customer customer){

        System.out.println("signup processing");
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//        //customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        Customer customerModel = new Customer();
        Role role = new Role();
        role.setRoleName("USER");

        customerModel.setRole(role);
        customerModel.setReference(customer.getReference());
        customerModel.setEmail(customer.getEmail());
        customerModel.setPassword(BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt()));
        customerModel.setAccount(customer.getAccount());


        customerRepository.save(customer);
        return customerModel;
    }

    public Customer signin(String account, String password){
        System.out.println("signin processing");
        // 계정 찾기
        Customer customer = customerRepository.findByAccount(account);

        if(customer == null){
            return null;
        }
        // 비밀번호 검증
        if(BCrypt.checkpw(password, customer.getPassword())){
            // 로그인 토큰 발행
            String token = jwtService.create("customer", customer, "user");
            customer.setToken(token);

            // 세션 발행
            Session session = new Session();
            session.setLoginToken(token);
            session.setCustomerId(customer.getCustomerId());
            return customer;
        }
        return null;
    }

}
