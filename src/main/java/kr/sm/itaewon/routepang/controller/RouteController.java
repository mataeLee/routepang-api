package kr.sm.itaewon.routepang.controller;

import kr.sm.itaewon.routepang.model.Basket;
import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Location;
import kr.sm.itaewon.routepang.model.Route;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/route")
public class RouteController {
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

    //    ////////// Route
//    @GetMapping("/getRoutes/customerId={customerId}")
//    public ResponseEntity<List<Route>> getRoutes(@PathVariable long customerId){
//
//        try {
//            Customer customer = customerRepository.findByCustomerId(customerId);
//            if(customer == null || customerId == 0){
//                System.out.println("no customer");
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//            List<Route> routelist =  routeRepository.findByCustomerId(customerId);
//            System.out.println("route size : " + routelist.size());
//            List<Route> list = routeManager.combinationRoute(routelist);
//            return new ResponseEntity<>(list,HttpStatus.OK);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @Transactional
//    @PostMapping("/postRoutes/customerId={customerId}")
//    public ResponseEntity<Void> postRoute(@PathVariable long customerId, @RequestBody List<Route> routes){
//
//        try{
//            Customer customer = customerRepository.findByCustomerId(customerId);
//
//            if(customer == null || customerId == 0){
//                System.out.println("no customer");
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//
//            List<Route> list = routeManager.sortRoute(routes);
//
//            // 중복 방지를 위해 기존 레코드 삭제 후 새로 삽입 - IO latency 증가 시 기존 레코드들 수정하는 방향으로 수정
//            Integer result = routeRepository.deleteAllByCustomerId(customerId);
//            System.out.println("delete routes result : " + result);
//
//            for(Route route: list){
//                routeRepository.save(route);
//            }
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/getLocationListByRouteId/customerId={customerId}&&routeId={routeId}")
//    public ResponseEntity<List<Location>> getLocationListByRouteId(@PathVariable long customerId, @PathVariable long routeId){
//        try {
//
//            Customer customer = customerRepository.findByCustomerId(customerId);
//            if(customer == null){
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//
//            List<Basket> basketList = basketRepository.findByRouteIdAndCustomerId(customerId, routeId);
//            List<Location> locationList = new ArrayList<>();
//
//            for(Basket basket : basketList){
//                Location location = locationRepository.findByLocationId(basket.getLocationId());
//                if(location != null)
//                    locationList.add(location);
//            }
//
//            return new ResponseEntity<>(locationList, HttpStatus.OK);
//        }catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
