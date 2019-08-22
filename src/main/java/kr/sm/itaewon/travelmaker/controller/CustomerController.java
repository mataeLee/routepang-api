package kr.sm.itaewon.travelmaker.controller;

import kr.sm.itaewon.travelmaker.model.Article;
import kr.sm.itaewon.travelmaker.model.Basket;
import kr.sm.itaewon.travelmaker.model.Location;
import kr.sm.itaewon.travelmaker.repo.BasketRepository;
import kr.sm.itaewon.travelmaker.repo.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private LocationRepository locationRepository;

    @RequestMapping("/")
    public ResponseEntity<Void> badRequest(){
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getBasketListByCustomerId/{customer_id}")
    public ResponseEntity<List<Location>> getBasketListByCustomerId(@PathVariable long customer_id){

        try{
            List<Basket> basketList = basketRepository.findByCustomerId(customer_id);
            List<Location> locationList = new ArrayList<Location>();
            for(Basket basket: basketList){
                locationList.add(locationRepository.findByLocationId(basket.getLocationId()));
            }

            return new ResponseEntity<List<Location>>(locationList, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<Location>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //////////Basket

    @PostMapping("/addBasket/{customer_id}")
    public ResponseEntity<Void> addBasket(@RequestBody Location location, @PathVariable long customer_id){

        if(location == null){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        try{
            Basket basket = new Basket();
            basket.setCustomerId(customer_id);
            basket.setLocationId(location.getLocationId());
            //basket.setRouteId();
            basketRepository.save(basket);
            return new ResponseEntity<Void>(HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
