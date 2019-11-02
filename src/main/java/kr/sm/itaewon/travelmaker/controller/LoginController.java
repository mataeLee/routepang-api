package kr.sm.itaewon.travelmaker.controller;

import kr.sm.itaewon.travelmaker.model.Customer;
import kr.sm.itaewon.travelmaker.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/signIn")
    public ResponseEntity<Void> signIn(@RequestBody Customer customer){

        if(true)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> signUp(@RequestBody Customer customer){
        //        MemberRole role = new MemberRole();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
//        role.setRoleName("BASIC");
//        member.setRoles(Arrays.asList(role));
        customerRepository.save(customer);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(){

        if(true)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/findAccountByEmail")
    public ResponseEntity<Void> findAccountByEmail(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/findPasswordByEmail")
    public ResponseEntity<Void> findPasswordByEmail(){
        return new ResponseEntity<>(HttpStatus.OK);
    }


}