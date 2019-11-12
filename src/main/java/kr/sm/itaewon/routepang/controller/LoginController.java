package kr.sm.itaewon.routepang.controller;

import io.jsonwebtoken.JwtBuilder;
import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Role;
import kr.sm.itaewon.routepang.model.Session;
import kr.sm.itaewon.routepang.repo.CustomerRepository;
import kr.sm.itaewon.routepang.repo.RoleRepository;
import kr.sm.itaewon.routepang.service.CustomerService;
import kr.sm.itaewon.routepang.service.JwtService;
import kr.sm.itaewon.routepang.service.LoginService;
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
    private LoginService loginService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody Customer customer){
        String account = customer.getAccount();
        String password = customer.getPassword();

        // 계정 찾기
        Customer customerModel = customerService.findByAccount(account);

        if(customer == null){
            // 회원가입 팝업
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 인증 및 토큰 발행
        String loginToken = loginService.signin(customerModel, account, password);

        if(loginToken != null) {
            return new ResponseEntity<>(loginToken, HttpStatus.OK);
        }
        // 인증 실패 팝업
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/signup")
    public ResponseEntity<Customer> signup(@RequestBody Customer customerParam){

        Customer customerModel = loginService.signup(customerParam);

        customerService.save(customerModel);

        return new ResponseEntity<>(customerModel, HttpStatus.CREATED);
    }


    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody Customer customer){

        loginService.logout(customer);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}