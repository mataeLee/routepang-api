package kr.sm.itaewon.travelmaker.controller;

import kr.sm.itaewon.travelmaker.model.*;
import kr.sm.itaewon.travelmaker.repo.*;
import kr.sm.itaewon.travelmaker.util.DegreeCalcurator;
import kr.sm.itaewon.travelmaker.util.RouteManager;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

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

    private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    private RouteManager routeManager = new RouteManager();


    @RequestMapping("/")
    public ResponseEntity<Void> badRequest(){
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    ////////// customer
    @GetMapping("/getCustomerById/customerId={customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable long customerId){

        try{
            Customer customer = customerRepository.findByCustomerId(customerId);
            if(customer == null){
                System.out.println("no customer");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<Customer>(customer, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Customer>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/getCustomerByName")
    public ResponseEntity<Customer> getCustomerByName(@RequestBody String name){

        try{
            Customer customer = customerRepository.findByName(name);
            if(customer == null){
                System.out.println("no customer");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<Customer>(customer, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Customer>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/getCustomerIdByAccount")
    public ResponseEntity<Long> getCustomerIdByName(@RequestBody String account){

        try{
            Customer customer = customerRepository.findByAccount(account);
            if(customer == null){
                System.out.println("no customer");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<Long>(customer.getCustomerId(), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ////////// article
    @PostMapping("/postArticle/customerId={customerId}")
    public ResponseEntity<Void> postArticle(@PathVariable long customerId, @RequestBody Link link, @RequestBody Article article){
        try {

            Customer customer = customerRepository.findByCustomerId(customerId);

            if(article == null || customer == null){
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
            //TODO place id 검사 후 없으면 새로운 location 삽입
            Location location = locationRepository.findByPlaceId(article.getPlaceId());
            if(location == null){
                location = new Location();
//                location.setCategory();
//                location.setAddress();
//                Point point = geometryFactory.createPoint(new Coordinate(latitude, longitude));
//                location.setCoordinates(point);
//                location.setPlaceId();
//                location.setName();
//                location.setUsedTime();
                locationRepository.save(location);
            }

            Link linkTmp = linkRepository.findByLinkId(link.getLinkId());
            if(linkTmp == null){
                article.setLink(link);
            }

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
    @PostMapping("/addBasket/customerId={customerId}")
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

    @GetMapping("/getBasketListByCustomerId/customerId={customerId}")
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
    public ResponseEntity<Void> giveRating(@PathVariable long customerId, @PathVariable long placeId, @PathVariable float star){
        //TODO 레이팅 구현

        return new ResponseEntity<Void>(HttpStatus.CREATED);

    }

    ////////// Route
    @GetMapping("/getRoutes/customerId={customerId}")
    public ResponseEntity<List<Route>> getRoutes(@PathVariable long customerId){

        try {
            Customer customer = customerRepository.findByCustomerId(customerId);
            if(customer == null || customerId == 0){
                System.out.println("no customer");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<Route> routelist =  routeRepository.findByCustomerId(customerId);
            System.out.println("route size : " + routelist.size());
            List<Route> list = routeManager.combinationRoute(routelist);
            return new ResponseEntity<List<Route>>(list,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @PostMapping("/postRoutes/customerId={customerId}")
    public ResponseEntity<Void> postRoute(@PathVariable long customerId, @RequestBody List<Route> routes){

        try{
            Customer customer = customerRepository.findByCustomerId(customerId);

            if(customer == null || customerId == 0){
                System.out.println("no customer");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<Route> list = routeManager.sortRoute(routes);

            // 중복 방지를 위해 기존 레코드 삭제 후 새로 삽입 - IO latency 증가 시 기존 레코드들 수정하는 방향으로 수정
            Integer result = routeRepository.deleteAllByCustomerId(customerId);
            System.out.println("delete routes result : " + result);

            for(Route route: list){
                //System.out.println("route : " + route + ", parentId : " + route.getParentId() + ", title : " + route.getTitle());
                routeRepository.save(route);
            }
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}