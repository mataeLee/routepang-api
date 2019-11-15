package kr.sm.itaewon.routepang.controller;

import kr.sm.itaewon.routepang.model.*;
import kr.sm.itaewon.routepang.repo.*;
import kr.sm.itaewon.routepang.service.CustomerService;
import kr.sm.itaewon.routepang.util.LinkManager;
import kr.sm.itaewon.routepang.util.RouteManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/**")
    public ResponseEntity<Void> badRequest(){
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable long customerId){

        Customer customer = customerService.findByCustomerId(customerId);

        return new ResponseEntity<>(customer, HttpStatus.OK);

    }

    @GetMapping("/{account}/account")
    public ResponseEntity<Customer> getCustomerByAccount(@RequestBody String account){

        Customer customer = customerService.findByAccount(account);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }


}