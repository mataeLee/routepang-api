package kr.sm.itaewon.routepang.controller;

import io.jsonwebtoken.JwtBuilder;
import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Role;
import kr.sm.itaewon.routepang.model.Session;
import kr.sm.itaewon.routepang.repo.CustomerRepository;
import kr.sm.itaewon.routepang.repo.RoleRepository;
import kr.sm.itaewon.routepang.service.CustomerService;
import kr.sm.itaewon.routepang.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtService jwtService;

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

    @PostMapping("/signin")
    public ResponseEntity<Customer> signIn(@RequestBody Customer customer){
        System.out.println("sign in");
        Customer loginMember = customerService.signin(customer.getAccount(), customer.getPassword());

        if(loginMember != null) {
            return new ResponseEntity<>(loginMember, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/signup")
    public ResponseEntity<Customer> signUp(@RequestBody Customer customerParam){

        System.out.println("sign up");
        Customer customerModel = customerService.signup(customerParam);

        return new ResponseEntity<>(customerModel, HttpStatus.CREATED);
    }

//    @GetMapping("/logout")
//    public ResponseEntity<Void> logout(){
//
//        if(true)
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}