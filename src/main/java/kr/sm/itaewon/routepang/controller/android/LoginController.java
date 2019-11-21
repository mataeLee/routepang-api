package kr.sm.itaewon.routepang.controller.android;

import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.service.BasketService;
import kr.sm.itaewon.routepang.service.CustomerService;
import kr.sm.itaewon.routepang.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BasketService basketService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Customer customer){
        String account = customer.getAccount();
        String password = customer.getPassword();

        // 계정 찾기
        Customer customerModel = customerService.findByAccount(account);

        if(customerModel == null){
            // 회원가입 팝업
            return new ResponseEntity<>("인증 실패, 아이디와 비밀번호를 확인허세요.",HttpStatus.BAD_REQUEST);
        }

        // 인증 및 토큰 발행
        String loginToken = loginService.signin(customerModel, account, password);

        if(loginToken != null) {
            return new ResponseEntity<>(loginToken, HttpStatus.OK);
        }
        // 인증 실패 팝업
        else
            return new ResponseEntity<>("인증 실패, 아이디와 비밀번호를 확인허세요.",HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Customer customerParam){

        Customer customerModel = loginService.signup(customerParam);

        if(customerModel == null){
            return new ResponseEntity<>("중복된 아이디가 있습니다.", HttpStatus.BAD_REQUEST);
        }
        customerService.save(customerModel);
        basketService.crateBasket(customerModel);

        return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
    }


    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody Customer customer){

        loginService.logout(customer);

        return new ResponseEntity<>("로그아웃 성공",HttpStatus.OK);
    }
}