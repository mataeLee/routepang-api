package kr.sm.itaewon.travelmaker.controller;

import kr.sm.itaewon.travelmaker.model.Article;
import kr.sm.itaewon.travelmaker.model.Location;
import kr.sm.itaewon.travelmaker.repo.ArticleRepository;
import kr.sm.itaewon.travelmaker.repo.LocationRepository;
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

    private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 26910);

    @RequestMapping("/")
    public ResponseEntity<Void> badRequest(){
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getLocationAll")
    public ResponseEntity<List<Location>> getLocationAll(){

        List<Location> list = new LinkedList<>();
        Iterable<Location> locations = locationRepository.findAll();

        locations.forEach(list::add);

        return new ResponseEntity<List<Location>>(list, HttpStatus.OK);
    }

    @GetMapping("/getLocationById/{location_id}")
    public ResponseEntity<Location> getLocationByLocationId(@PathVariable long location_id){

        Location location = locationRepository.findByLocationId(location_id);

        return new ResponseEntity<Location>(location, HttpStatus.OK);
    }

    @GetMapping("/getLocationByCoordinate/latitude={latitude}&&longitude={longitude}")
    public ResponseEntity<List<Location>> getLocationByCoordinate(@PathVariable double longitude, @PathVariable double latitude){

        try {
            Point point = geometryFactory.createPoint(new Coordinate(latitude, longitude));
            System.out.println(point);
            System.out.println(point.toString());
            System.out.println("point x : " + point.getX());
            System.out.println("point y : " + point.getY());
            List<Location> list = locationRepository.findByCoordinate(point.toString());

            for(Location location:list){
                int count = articleRepository.countArticlesByLocationId(location.getLocationId());
                location.setArticleCount(count);
            }

            return new ResponseEntity<List<Location>>(list, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<Location>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getLocationByLiteral/latitude={latitude}&&longitude={longitude}")
    public ResponseEntity<List<Location>> getLocationByLiteral(@PathVariable double longitude, @PathVariable double latitude){

        try {
            List<Location> list = locationRepository.findByLiteral(longitude, latitude);

            for(Location location:list){
                int count = articleRepository.countArticlesByLocationId(location.getLocationId());
                location.setArticleCount(count);
            }

            return new ResponseEntity<List<Location>>(list, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<Location>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/postLocation")
//    public ResponseEntity<Void> postLocation(@RequestBody Location location){
//
//        try {
//
//
//        }catch (Exception e){
//
//        }
//    }
}
