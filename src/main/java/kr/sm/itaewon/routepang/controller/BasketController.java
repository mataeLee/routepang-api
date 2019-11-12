package kr.sm.itaewon.routepang.controller;

import kr.sm.itaewon.routepang.model.Basket;
import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Location;
import kr.sm.itaewon.routepang.model.Route;
import kr.sm.itaewon.routepang.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/basket")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @RequestMapping("/**")
    public ResponseEntity<Void> badRequest(){
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/baskets/{customerId}/add")
    public ResponseEntity<Void> addProducts(@RequestBody Location location, @PathVariable long customerId){

        return new ResponseEntity<>(HttpStatus.OK);
//        try{
//            if(location == null){
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//            Customer customer = customerRepository.findByCustomerId(customerId);
//            if(customer == null){
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//            Location locationModel = locationRepository.findByPlaceId(location.getPlaceId());
//            if( locationModel == null){
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//
//            // 중복 검사
//            Basket basketModel = basketRepository.findBylocationIdAndCustomerId(locationModel.getLocationId(), customerId);
//
//            if(basketModel != null){
//                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//            }
//
//            Basket basket = new Basket();
//            basket.setCustomerId(customerId);
//            basket.setLocationId(locationModel.getLocationId());
//            basket.setRouteId(-1);
//            basketRepository.save(basket);
//            return new ResponseEntity<>(HttpStatus.CREATED);
//
//        }catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    //TODO RouteController
//    @GetMapping("/getLocationListByBasket/customerId={customerId}")
//    public ResponseEntity<List<Location>> getLocationListByBasket(@PathVariable long customerId){
//
//        try{
//            List<Basket> basketList = basketRepository.findByCustomerId(customerId);
//            List<Location> locationList = new ArrayList<>();
//            for(Basket basket: basketList){
//                locationList.add(locationRepository.findByLocationId(basket.getLocationId()));
//            }
//
//            return new ResponseEntity<>(locationList, HttpStatus.OK);
//        }catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @PostMapping("/addBasketInRoute/basketId={basketId}&&routeId={routeId}")
//    public ResponseEntity<Void> addBasketInRoute(@PathVariable long basketId, @PathVariable long routeId){
//
//        try{
//            if(basketId==0 || routeId==0){
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//
//            Basket basket = basketRepository.findByBasketId(basketId);
//            Route route = routeRepository.findByRouteId(routeId);
//            if(basket == null || route == null){
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//
//            if(basket.getRouteId() == 0){
//                basket.setRouteId(routeId);
//                basketRepository.save(basket);
//            }
//
//            else{
//                Basket item = new Basket();
//
//                item.setLocationId(basket.getLocationId());
//                item.setCustomerId(basket.getCustomerId());
//                item.setRouteId(routeId);
//                basketRepository.save(item);
//            }
//
//            return new ResponseEntity<>(HttpStatus.OK);
//
//        }catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

//    @DeleteMapping("/deleteBasketByLocationId/customerId={customerId}")
//    public ResponseEntity<Void> deleteBasketByLocationId(@RequestBody Location location, @PathVariable long customerId){
//
//        try {
//            Location locationModel = locationRepository.findByPlaceId(location.getPlaceId());
//
//            Basket basket = basketRepository.findBylocationIdAndCustomerId(locationModel.getLocationId(),customerId);
//            if(basket == null){
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//            basketRepository.deleteById(basket.getBasketId());
//
//            return new ResponseEntity<>(HttpStatus.OK);
//        }catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }
}
