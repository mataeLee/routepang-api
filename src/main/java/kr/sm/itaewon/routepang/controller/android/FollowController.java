package kr.sm.itaewon.routepang.controller.android;

import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Follow;
import kr.sm.itaewon.routepang.service.CustomerService;
import kr.sm.itaewon.routepang.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    private FollowService followService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/")
    public ResponseEntity<String> follow(@RequestBody Follow follow){

        Follow followModel = followService.follow(follow);

        return new ResponseEntity<>("팔로우 : " + followModel.isFollow(), HttpStatus.OK);
    }

    @GetMapping("/follower/list")
    public ResponseEntity<List<Customer>> getFollowerList(@RequestHeader long customerId){
        Customer customer = customerService.findByCustomerId(customerId);

        List<Customer> followerList = followService.findFollowerByCustomer(customer);

        return new ResponseEntity<>(followerList,HttpStatus.OK);
    }

    @GetMapping("/following/list")
    public ResponseEntity<List<Customer>> getFollowingList(@RequestHeader long customerId){
        Customer customer = customerService.findByCustomerId(customerId);

        List<Customer> followerList = followService.findFollowingByCustomer(customer);

        return new ResponseEntity<>(followerList,HttpStatus.OK);
    }
}
