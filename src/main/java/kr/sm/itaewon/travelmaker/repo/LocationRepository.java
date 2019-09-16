package kr.sm.itaewon.travelmaker.repo;


import kr.sm.itaewon.travelmaker.model.Location;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {

    @Query(value = "SELECT location_id, place_id FROM location", nativeQuery = true)
    List<Location> findAllLocation();

    Location findByLocationId(long loacation_id);

    @Query(value = "SELECT DISTINCT * FROM location WHERE ST_DWithin(coordinates, ?, 500)",
    nativeQuery = true)
    List<Location> findByCoordinate(String point);
    //"SELECT * FROM location WHERE ST_DWithin(coordinates, ST_Transform(ST_GeomFromText(?, 4326), 2097), 500)"
    //"SELECT * FROM location WHERE ST_Distance(coordinates, ST_Transform(ST_SetSRID(ST_MakePoint(?1,?2), '4326'), '5179') <= 100 ORDER BY ST_Distance(coordinates, ST_SetSRID(ST_MakePoint(?1,?2), 5179))"

    @Query(value = "SELECT DISTINCT * FROM location WHERE longitude between ?1 - 0.0056 AND ?1 + 0.0056 AND latitude between ?2 - 0.0049 AND ?2 + 0.0049", nativeQuery = true)
    List<Location> findByLiteral(double longitude, double latitude);

}