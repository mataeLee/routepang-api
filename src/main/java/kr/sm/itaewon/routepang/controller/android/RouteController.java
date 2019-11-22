package kr.sm.itaewon.routepang.controller.android;

import kr.sm.itaewon.routepang.model.*;
import kr.sm.itaewon.routepang.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/route")
public class RouteController {
    @Autowired
    private ProductService productService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private BasketService basketService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LocationService locationService;

    @GetMapping("/locations/{customerId}/customers")
    public ResponseEntity<List<Location>> getLocationListByProduct(@PathVariable long customerId){

        Customer customer = customerService.findByCustomerId(customerId);

        Basket basket = basketService.findByCustomer(customer);

        List<Location> locationList = productService.findLocationByBasket(basket);
        locationList = locationService.insertCountList(locationList);

        return new ResponseEntity<>(locationList, HttpStatus.OK);
    }


    @GetMapping("/locations/{routeId}/routes")
    public ResponseEntity<List<Product>> getProductListByRouteId(@PathVariable long routeId){

        List<Product> productList =  routeService.findProductsByRouteId(routeId);
        List<Location> locationList = productService.findLocationByProducts(productList);

        locationList = locationService.insertCountList(locationList);

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/{customerId}/customers")
    public ResponseEntity<List<Route>> getRoutes(@PathVariable long customerId){

        Customer customer = customerService.findByCustomerId(customerId);

        List<Route> routelist =  routeService.findByCustomer(customer);

        return new ResponseEntity<>(routelist,HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/list/{customerId}/customers")
    public ResponseEntity<List<Route>> postRouteList(@PathVariable long customerId, @RequestBody List<Route> routes){

        Customer customer = customerService.findByCustomerId(customerId);

        routeService.saveList(routes, customer);

        List<Route> routeList = routeService.findByCustomer(customer);

        return new ResponseEntity<>(routeList, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/{customerId}/customers")
    public ResponseEntity<Route> postRoute(@PathVariable long customerId, @RequestBody Route route){

        Customer customer = customerService.findByCustomerId(customerId);

        route.setCustomer(customer);
        route = routeService.save(route);

        return new ResponseEntity<>(route, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{routeId}")
    public ResponseEntity<Route> putRoute(@PathVariable long routeId, @RequestBody Route routeParam){
        Customer customer = customerService.findByCustomerId(routeParam.getCustomer().getCustomerId());

        routeParam.setCustomer(customer);
        if(routeParam.getRouteId() == routeId)
            routeParam = routeService.save(routeParam);

        return new ResponseEntity<>(routeParam, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{routeId}")
    public ResponseEntity<String> deleteRoute(@PathVariable long routeId){
        Route route = routeService.findByRouteId(routeId);

        routeService.delete(route);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}