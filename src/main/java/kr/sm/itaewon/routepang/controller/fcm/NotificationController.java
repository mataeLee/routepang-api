package kr.sm.itaewon.routepang.controller.fcm;

import com.google.api.Http;
import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/token")
    private ResponseEntity<String> postToken(@RequestHeader String pushToken, @RequestHeader long customerId){

        Customer customer = customerService.findByCustomerId(customerId);
        if(customer == null){
            return new ResponseEntity<>("계정정보 유효하지 않음", HttpStatus.NOT_FOUND);
        }
        if(customer.getPushToken().equals(pushToken)){

            return new ResponseEntity<>("토큰 값 이미 존재", HttpStatus.OK);
        }
        else{

            customer.setPushToken(pushToken);
            customerService.save(customer);

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}