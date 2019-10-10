package kr.sm.itaewon.travelmaker.controller;

import kr.sm.itaewon.travelmaker.model.Article;
import kr.sm.itaewon.travelmaker.model.Location;
import kr.sm.itaewon.travelmaker.model.Rating;
import kr.sm.itaewon.travelmaker.repo.ArticleRepository;
import kr.sm.itaewon.travelmaker.repo.LocationRepository;
import kr.sm.itaewon.travelmaker.repo.RatingRepository;
import kr.sm.itaewon.travelmaker.util.DegreeCalcurator;
import kr.sm.itaewon.travelmaker.util.RatingManager;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RatingRepository ratingRepository;

    private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    private DegreeCalcurator degreeCalcurator = new DegreeCalcurator();

    private RatingManager ratingManager = new RatingManager();

    @RequestMapping("/")
    public ResponseEntity<Void> badRequest(){
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getLocationAll")
    public ResponseEntity<List<Location>> getLocationAll(){

        List<Location> list = new LinkedList<>();
        Iterable<Location> locations = locationRepository.findAll();

        locations.forEach(list::add);

        for(Location location : list){
            List<Rating> ratingList = ratingRepository.findByLocationId(location.getLocationId());
            int count = articleRepository.countArticlesByLocationId(location.getLocationId());
            Rating rating = ratingManager.calcRatingAndUsedTime(ratingList);

            location.setRatingCount(ratingList.size());
            location.setRating(rating.getRating());
            location.setUsedTime(rating.getUsedTime());
            location.setArticleCount(count);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/getLocationById/locationId={location_id}")
    public ResponseEntity<Location> getLocationByLocationId(@PathVariable long location_id){

        Location location = locationRepository.findByLocationId(location_id);
        List<Rating> ratingList = ratingRepository.findByLocationId(location.getLocationId());

        int count = articleRepository.countArticlesByLocationId(location.getLocationId());
        Rating rating = ratingManager.calcRatingAndUsedTime(ratingList);

        location.setRatingCount(ratingList.size());
        location.setRating(rating.getRating());
        location.setUsedTime(rating.getUsedTime());
        location.setArticleCount(count);

        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @GetMapping("/getLocationByCoordinate/latitude={latitude}&&longitude={longitude}&&radius={radius}")
    public ResponseEntity<List<Location>> getLocationByCoordinate(@PathVariable double longitude, @PathVariable double latitude, @PathVariable int radius){

        try {

            float degree = degreeCalcurator.getDegreeByMeter(radius);
            Point point = geometryFactory.createPoint(new Coordinate(latitude, longitude));
            List<Location> list = locationRepository.findByCoordinate(point.toString(), degree);

            for(Location location:list){
                int count = articleRepository.countArticlesByLocationId(location.getLocationId());
                List<Rating> ratingList = ratingRepository.findByLocationId(location.getLocationId());

                Rating rating = ratingManager.calcRatingAndUsedTime(ratingList);

                location.setRatingCount(ratingList.size());
                location.setRating(rating.getRating());
                location.setUsedTime(rating.getUsedTime());
                location.setArticleCount(count);
            }

            return new ResponseEntity<>(list, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/getLocationByLiteral/latitude={latitude}&&longitude={longitude}")
//    public ResponseEntity<List<Location>> getLocationByLiteral(@PathVariable double longitude, @PathVariable double latitude){
//
//        try {
//            List<Location> list = locationRepository.findByLiteral(longitude, latitude);
//
//            for(Location location:list){
//                int count = articleRepository.countArticlesByLocationId(location.getLocationId());
//                location.setArticleCount(count);
//            }
//
//            return new ResponseEntity<List<Location>>(list, HttpStatus.OK);
//        }catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<List<Location>>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/postLocation")
    public ResponseEntity<Void> postLocation(@RequestBody Location location){

        try {
            locationRepository.save(location);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
