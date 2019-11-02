package kr.sm.itaewon.travelmaker.controller;

import kr.sm.itaewon.travelmaker.model.*;
import kr.sm.itaewon.travelmaker.repo.*;
import kr.sm.itaewon.travelmaker.util.DegreeCalcurator;
import kr.sm.itaewon.travelmaker.util.LinkManager;
import kr.sm.itaewon.travelmaker.util.RouteManager;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private RatingRepository ratingRepository;

    private LinkManager linkManager = new LinkManager();

    /*private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);*/

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
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(customer, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/getCustomerByName")
    public ResponseEntity<Customer> getCustomerByName(@RequestBody String name){

        try{
            Customer customer = customerRepository.findByName(name);
            if(customer == null){
                System.out.println("no customer");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(customer, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCustomerIdByAccount")
    public ResponseEntity<Long> getCustomerIdByName(@RequestBody String account){

        try{
            Customer customer = customerRepository.findByAccount(account);
            if(customer == null){
                System.out.println("no customer");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(customer.getCustomerId(), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    ////////// article

    @PostMapping("/postArticle/customerId={customerId}")
    public ResponseEntity<Void> postArticle(@PathVariable long customerId, @RequestBody Link link, @RequestBody Article article, @RequestBody Location location){
        try {

            Customer customer = customerRepository.findByCustomerId(customerId);

            if(article == null || customer == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            //TODO place id 검사 후 없으면 새로운 location 삽입
            Location locationModel = locationRepository.findByPlaceId(article.getPlaceId());
            if(locationModel == null){
                locationRepository.save(location);
            }

            Link linkModel = linkRepository.findByLinkId(link.getLinkId());
            if(linkModel == null){
                article.setLink(link);
            }

            Timestamp timestamp = new Timestamp(new Date().getTime());
            article.setRegDate(timestamp);
            article.setCustomerId(customerId);
            articleRepository.save(article);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ////////// Basket
    @PostMapping("/addBasket/customerId={customerId}")
    public ResponseEntity<Void> addBasket(@RequestBody Location location, @PathVariable long customerId){
        try{
            if(location == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Customer customer = customerRepository.findByCustomerId(customerId);
            if(customer == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Location locationModel = locationRepository.findByPlaceId(location.getPlaceId());
            if( locationModel == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // 중복 검사
            Basket basketModel = basketRepository.findBylocationIdAndCustomerId(locationModel.getLocationId(), customerId);

            if(basketModel != null){
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

            Basket basket = new Basket();
            basket.setCustomerId(customerId);
            basket.setLocationId(locationModel.getLocationId());
            basket.setRouteId(-1);
            basketRepository.save(basket);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getLocationListByBasket/customerId={customerId}")
    public ResponseEntity<List<Location>> getLocationListByBasket(@PathVariable long customerId){

        try{
            List<Basket> basketList = basketRepository.findByCustomerId(customerId);
            List<Location> locationList = new ArrayList<>();
            for(Basket basket: basketList){
                locationList.add(locationRepository.findByLocationId(basket.getLocationId()));
            }

            return new ResponseEntity<>(locationList, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addBasketInRoute/basketId={basketId}&&routeId={routeId}")
    public ResponseEntity<Void> addBasketInRoute(@PathVariable long basketId, @PathVariable long routeId){

        try{
            if(basketId==0 || routeId==0){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Basket basket = basketRepository.findByBasketId(basketId);
            Route route = routeRepository.findByRouteId(routeId);
            if(basket == null || route == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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

            return new ResponseEntity<>(HttpStatus.OK);

        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/deleteBasketByLocationId/customerId={customerId}")
    public ResponseEntity<Void> deleteBasketByLocationId(@RequestBody Location location, @PathVariable long customerId){

        try {
            Location locationModel = locationRepository.findByPlaceId(location.getPlaceId());

            Basket basket = basketRepository.findBylocationIdAndCustomerId(locationModel.getLocationId(),customerId);
            if(basket == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            basketRepository.deleteById(basket.getBasketId());

            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /////////// Rating
    @PostMapping("/giveRating")
    public ResponseEntity<Void> giveRating(@RequestBody Rating rating){

        try {
            Location location = locationRepository.findByLocationId(rating.getLocationId());
            Customer customer = customerRepository.findByCustomerId(rating.getCustomerId());
            Rating rate = ratingRepository.findByCustomerIdAndLocationId(rating.getCustomerId(), rating.getLocationId());

            if(location == null || customer == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if(rate != null){
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            if(rating.getRating() < 0 || rating.getRating() > 5){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if(rating.getUsedTime() < 0){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            ratingRepository.save(rating);

            return new ResponseEntity<>(HttpStatus.CREATED);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ////////// Route
    @GetMapping("/getRoutes/customerId={customerId}")
    public ResponseEntity<List<Route>> getRoutes(@PathVariable long customerId){

        try {
            Customer customer = customerRepository.findByCustomerId(customerId);
            if(customer == null || customerId == 0){
                System.out.println("no customer");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<Route> routelist =  routeRepository.findByCustomerId(customerId);
            System.out.println("route size : " + routelist.size());
            List<Route> list = routeManager.combinationRoute(routelist);
            return new ResponseEntity<>(list,HttpStatus.OK);
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
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<Route> list = routeManager.sortRoute(routes);

            // 중복 방지를 위해 기존 레코드 삭제 후 새로 삽입 - IO latency 증가 시 기존 레코드들 수정하는 방향으로 수정
            Integer result = routeRepository.deleteAllByCustomerId(customerId);
            System.out.println("delete routes result : " + result);

            for(Route route: list){
                routeRepository.save(route);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getLocationListByRouteId/customerId={customerId}&&routeId={routeId}")
    public ResponseEntity<List<Location>> getLocationListByRouteId(@PathVariable long customerId, @PathVariable long routeId){
        try {

            Customer customer = customerRepository.findByCustomerId(customerId);
            if(customer == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            List<Basket> basketList = basketRepository.findByRouteIdAndCustomerId(customerId, routeId);
            List<Location> locationList = new ArrayList<>();

            for(Basket basket : basketList){
                Location location = locationRepository.findByLocationId(basket.getLocationId());
                if(location != null)
                    locationList.add(location);
            }

            return new ResponseEntity<>(locationList, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ////////// Link

    @PostMapping("/postLink")
    public ResponseEntity<Link> postLink(@RequestBody String linkUrl) {
        Link model = linkManager.LinkApi(linkUrl);

        if (model == null || model.getLinkUrl() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            linkRepository.save(model);

            return new ResponseEntity<>(model, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}