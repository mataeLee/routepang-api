package kr.sm.itaewon.routepang.controller;

import kr.sm.itaewon.routepang.model.Location;
import kr.sm.itaewon.routepang.model.Rating;
import kr.sm.itaewon.routepang.repo.ArticleRepository;
import kr.sm.itaewon.routepang.repo.LocationRepository;
import kr.sm.itaewon.routepang.repo.RatingRepository;
import kr.sm.itaewon.routepang.service.LocationService;
import kr.sm.itaewon.routepang.service.RatingService;
import kr.sm.itaewon.routepang.util.DegreeCalcurator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private RatingService ratingService;

    @RequestMapping("/**")
    public ResponseEntity<Void> badRequest(){
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getLocationAll(){

        List<Location> list = locationService.findAll();

        list = ratingService.insertRating(list);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/location/{locationId}")
    public ResponseEntity<Location> getLocationByLocationId(@PathVariable long locationId){

        Location location = locationService.findByLocationId(locationId);

        location = ratingService.insertRating(location);

        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @GetMapping("/locations/{latitude}&&{longitude}&&{radius}/coordinates")
    public ResponseEntity<List<Location>> getLocationByCoordinates(@PathVariable double longitude, @PathVariable double latitude, @PathVariable int radius){

        List<Location> list = locationService.findByCoordinates(longitude, latitude, radius);

        list = ratingService.insertRating(list);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

//    @PostMapping("/postLocation")
//    public ResponseEntity<Void> postLocation(@RequestBody Location location){
//
//        try {
//            locationRepository.save(location);
//            return new ResponseEntity<>(HttpStatus.CREATED);
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
