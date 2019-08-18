package kr.sm.itaewon.travelmaker.controller;

import kr.sm.itaewon.travelmaker.model.Basket;
import kr.sm.itaewon.travelmaker.repo.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private BasketRepository basketRepository;

    @RequestMapping("/")
    public ResponseEntity<Void> badRequest(){
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getBasketListByCustomerId/{customer_id}")
    public ResponseEntity<List<Basket>> getBasketListByCustomerId(@PathVariable long customer_id){

        try{
            List<Basket> list = basketRepository.findByCustomerId(customer_id);

            return new ResponseEntity<List<Basket>>(list, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<Basket>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
