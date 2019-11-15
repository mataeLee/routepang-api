package kr.sm.itaewon.routepang.controller;

import kr.sm.itaewon.routepang.model.Basket;
import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Location;
import kr.sm.itaewon.routepang.model.Product;
import kr.sm.itaewon.routepang.service.BasketService;
import kr.sm.itaewon.routepang.service.CustomerService;
import kr.sm.itaewon.routepang.service.LocationService;
import kr.sm.itaewon.routepang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private BasketService basketService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @PostMapping("/{customerId}/customers")
    public ResponseEntity<Void> addProduct(@RequestBody Product product, @PathVariable long customerId){

        Customer customer = customerService.findByCustomerId(customerId);

        Basket basket = basketService.findByCustomer(customer);

        if(basket == null){
            basket = basketService.crateBasket(customer);
        }

        productService.save(product, basket);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{customerId}/customers")
    public ResponseEntity<Void> deleteProduct(@RequestBody Product product, @PathVariable long customerId){

        productService.delete(product);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
