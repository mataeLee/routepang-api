package kr.sm.itaewon.routepang.service;

import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Role;
import kr.sm.itaewon.routepang.model.Session;
import kr.sm.itaewon.routepang.repo.CustomerRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private SessionService sessionService;

    public Customer signup(Customer customer){
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

        return customerModel;
    }

    public Customer signin(Customer customer, String account, String password){
        System.out.println("signin processing");

        // 로그인 id 검증
        if(!customer.getAccount().equals(account))
            return null;

        // 비밀번호 검증
        if(BCrypt.checkpw(password, customer.getPassword())){

            // 로그인 토큰 발행
            String token = jwtService.create("customer", customer, "user");
            customer.setToken(token);

            // 세션 발행
            Session session = new Session();
            session.setLoginToken(token);
            session.setCustomerId(customer.getCustomerId());
            //session.setPushToken();
            //sessionService.sava(session);
            return customer;
        }
        return null;
    }

    public void logout(Customer customer) {
        //sessionService.delete(customer);
    }

    //    @GetMapping("/create")
//    public ResponseEntity<Void> create(){
//
//        Role role = new Role();
//        role.setRoleName("USER");
//        roleRepository.save(role);
//
//        Customer customer= new Customer();
//        customer.setAccount("tylee94");
//        customer.setRole(role);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String pwd = passwordEncoder.encode("qwe123");
//        customer.setPassword(pwd);
//        customer.setEmail("tylee94@gmail.com");
//        customer.setReference("알로항");
//        customerRepository.save(customer);
//
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
}
