package kr.sm.itaewon.routepang.controller;

import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Location;
import kr.sm.itaewon.routepang.model.Rating;
import kr.sm.itaewon.routepang.service.CustomerService;
import kr.sm.itaewon.routepang.service.LocationService;
import kr.sm.itaewon.routepang.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/rate/location")
    public ResponseEntity<Void> postRatingLocation(@RequestBody Rating rating){
        long locationId = rating.getLocation().getLocationId();
        long customerId = rating.getCustomer().getCustomerId();
        Location location = locationService.findByLocationId(locationId);
        Customer customer = customerService.findByCustomerId(customerId);
        Rating rate = ratingService.findByCustomerAndLocation(customer, location);

        if(location == null || customer == null || rate != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        ratingService.postRating(rating);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}