package kr.sm.itaewon.routepang.service;

import kr.sm.itaewon.routepang.model.Location;
import kr.sm.itaewon.routepang.repo.LocationRepository;
import kr.sm.itaewon.routepang.util.DegreeCalcurator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    private DegreeCalcurator degreeCalcurator = new DegreeCalcurator();

    public List<Location> findAll() {
        List<Location> list = new ArrayList<>();
        Iterable<Location> locations = locationRepository.findAll();

        locations.forEach(list::add);

        return list;
    }

    public Location findByLocationId(long locationId) {
        Location location = locationRepository.findByLocationId(locationId);;

        return location;
    }

    public List<Location> findByCoordinates(double longitude, double latitude, int radius) {
        float degree = degreeCalcurator.getDegreeByMeter(radius);

        Point point = geometryFactory.createPoint(new Coordinate(latitude, longitude));

        List<Location> list = locationRepository.findByCoordinate(point.toString(), degree);

        return list;
    }

    public Location findByPlaceIdLike(String placeId) {
        Location location = locationRepository.findByPlaceIdLike(placeId);

        return location;
    }

    public boolean save(Location location) {
        try {
            locationRepository.save(location);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Location create(Location location) {
        Location locationModel = locationRepository.save(location);
        return locationModel;
    }
//    public boolean saveWithPlaceId(String placeId){
//
//        Location location = new Location();
//        location.setPlaceId(placeId);
//
//    }

}
