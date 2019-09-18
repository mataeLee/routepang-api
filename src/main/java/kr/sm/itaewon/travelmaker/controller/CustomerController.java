package kr.sm.itaewon.travelmaker.controller;

import kr.sm.itaewon.travelmaker.model.*;
import kr.sm.itaewon.travelmaker.repo.*;
import org.geolatte.geom.V;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RouteRepository routeRepository;

    @RequestMapping("/")
    public ResponseEntity<Void> badRequest(){
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    ////////// article
    @PostMapping("/postArticle/customerId={customerId}&&linkId={linkId}")
    public ResponseEntity<Void> postArticle(@PathVariable long customerId, @PathVariable long linkId, @RequestBody Article article){
        try {

            Customer customer = customerRepository.findByCustomerId(customerId);

            if(article == null || customer == null){
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }

            Link link = linkRepository.findByLinkId(linkId);
            article.setLink(link);
            Timestamp timestamp = new Timestamp(new Date().getTime());
            article.setRegDate(timestamp);
            article.setCustomerId(customerId);
            articleRepository.save(article);
            return new ResponseEntity<Void>(HttpStatus.CREATED);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ////////// Basket

    @PostMapping("/addBasket/{customerId}")
    public ResponseEntity<Void> addBasket(@RequestBody Location location, @PathVariable long customerId){
        try{
            if(location == null){
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
            Customer customer = customerRepository.findByCustomerId(customerId);
            if(customer == null){
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
            Basket basket = new Basket();
            basket.setCustomerId(customerId);
            basket.setLocationId(location.getLocationId());
            basket.setRouteId(-1);
            basketRepository.save(basket);
            return new ResponseEntity<Void>(HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getBasketListByCustomerId/{customerId}")
    public ResponseEntity<List<Location>> getBasketListByCustomerId(@PathVariable long customerId){

        //TODO locationId가 같은 basket 중복제거 작업 필요
        try{
            List<Basket> basketList = basketRepository.findByCustomerId(customerId);
            List<Location> locationList = new LinkedList<Location>();
            for(Basket basket: basketList){
                locationList.add(locationRepository.findByLocationId(basket.getLocationId()));
            }

            return new ResponseEntity<List<Location>>(locationList, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<Location>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addBasketInRoute/basketId={basketId}&&routeId={routeId}")
    public ResponseEntity<Void> addBasketInRoute(@PathVariable long basketId, @PathVariable long routeId){

        try{
            if(basketId==0 || routeId==0){
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }

            Basket basket = basketRepository.findByBasketId(basketId);
            if(basket == null){
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }

            if(basket.getRouteId() == 0){
                basket.setRouteId(routeId);
                basketRepository.save(basket);
            }

            else{
                Basket item = new Basket();

                item.setLocationId(basket.getLocationId());
                item.setCustomerId(basket.getCustomerId());
                item.setRouteId(routeId);
                basketRepository.save(item);
            }

            return new ResponseEntity<Void>(HttpStatus.OK);

        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /////////// Rating
    @PostMapping("/giveRating")
    public ResponseEntity<Void> giveRating(@PathVariable long customerId, @PathVariable long locationId, @PathVariable float star){
        //TODO 레이팅 구현

        return new ResponseEntity<Void>(HttpStatus.CREATED);

    }

    ////////// Route
    @GetMapping("/getRoutes/customerId={customerId}")
    public ResponseEntity<List<Route>> getRoutes(@PathVariable long customerId){

        try {
            Customer customer = customerRepository.findByCustomerId(customerId);
            if(customer == null || customerId == 0){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<Route> list =  routeRepository.findByCustomerIdAndTop(customerId);

            for(Route route : list){
                long parentId = route.getParentId();
                List<Route> routes = routeRepository.findByParentId(parentId);


            }

            return new ResponseEntity<List<Route>>(list,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/postRoutes/customerId={customerId}")
    public ResponseEntity<Void> postRoute(@PathVariable long customerId, @RequestBody List<Route> routes, @RequestBody List<Folder> folders){
        //TODO



        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}