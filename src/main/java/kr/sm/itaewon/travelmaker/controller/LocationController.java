package kr.sm.itaewon.travelmaker.controller;

import com.vividsolutions.jts.geom.Point;
import kr.sm.itaewon.travelmaker.model.Article;
import kr.sm.itaewon.travelmaker.model.Location;
import kr.sm.itaewon.travelmaker.repo.ArticleRepository;
import kr.sm.itaewon.travelmaker.repo.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping("/")
    public ResponseEntity<Void> badRequest(){
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getLocationAll")
    public ResponseEntity<List<Location>> getLocationAll(){

        List<Location> list = new ArrayList<>();
        Iterable<Location> locations = locationRepository.findAll();

        locations.forEach(list::add);

        return new ResponseEntity<List<Location>>(list, HttpStatus.OK);
    }

    @GetMapping("/getLocationById/{location_id}")
    public ResponseEntity<List<Location>> getLocationByLocationId(@PathVariable long location_id){

        List<Location> list = locationRepository.findByLocationId(location_id);

        return new ResponseEntity<List<Location>>(list, HttpStatus.OK);
    }

    @GetMapping("/getLocationByCoordinate/latitude={latitude}&&longitude={longitude}")
    public ResponseEntity<List<Location>> getLocationByCoordinate(@PathVariable double longitude, @PathVariable double latitude){

        List<Location> list = locationRepository.findByCoordinate(longitude, latitude);
        for(Location location:list){
            int count = articleRepository.countArticlesByLocationId(location.getLocationId());
            location.setArticleCount(count);
        }

        return new ResponseEntity<List<Location>>(list, HttpStatus.OK);
    }
}
