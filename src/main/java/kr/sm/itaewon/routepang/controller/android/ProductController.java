package kr.sm.itaewon.routepang.controller.android;

import kr.sm.itaewon.routepang.model.*;
import kr.sm.itaewon.routepang.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private BasketService basketService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ArticleService articleService;
    @GetMapping("/{routeId}/routes")
    public ResponseEntity<List<Location>> getLocationByRouteId(@PathVariable long routeId){

        Route route = routeService.findByRouteId(routeId);

        List<Product> productList = productService.findAllByRouteId(route);

        List<Location> locationList = productService.findLocationByProducts(productList);
        locationList = locationService.insertCountList(locationList);

        return new ResponseEntity<>(locationList,HttpStatus.OK);

    }

    @PostMapping("/{customerId}/customers")
    public ResponseEntity<Void> addProduct(@RequestBody Product product, @PathVariable long customerId){

        Customer customer = customerService.findByCustomerId(customerId);

        Basket basket = basketService.findByCustomer(customer);

        if(basket == null){
            basket = basketService.crateBasket(customer);
        }

        String placeId = product.getLocation().getPlaceId();
        Location location = locationService.findByPlaceIdLike(placeId);
        if(location == null){
            location = locationService.create(product.getLocation());
            locationService.insertCount(location);
            product.setLocation(location);
        }

        product.setLocation(location);
        productService.save(product, basket);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{routeId}/routes")
    public ResponseEntity<String> putProductInRoute(@PathVariable long routeId, @RequestBody Product product){

        Route route = routeService.findByRouteId(routeId);

        Customer customer = route.getCustomer();

        Basket basket = basketService.findByCustomer(customer);
        //product.getRoute().add(route);
        product.setRouteId(route.getRouteId());

        String placeId = product.getLocation().getPlaceId();
        Location location = locationService.findByPlaceIdLike(placeId);
        if(location == null){
            location = locationService.create(product.getLocation());
            product.setLocation(location);
        }
        product.setLocation(location);
        product.setBasket(basket);
        productService.save(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/lists")
    public ResponseEntity<String> putProductListInRoute(@RequestBody List<Route> routes, @RequestBody List<Product> products){

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}/customers")
    public ResponseEntity<Void> deleteProduct(@RequestBody Product product, @PathVariable long customerId){

        productService.delete(product);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
