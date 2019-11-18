package kr.sm.itaewon.routepang.repo;


import kr.sm.itaewon.routepang.model.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {

//    @Query(value = "SELECT location_id, place_id FROM location", nativeQuery = true)
//    List<Location> findAllLocation();

    Location findByLocationId(long loacationId);


    //@Query(value = "SELECT DISTINCT * FROM location WHERE ST_DWithin(coordinates, ST_Transform(ST_GeomFromText(?1, 4326),2097), ?2)", nativeQuery = true)
    //@Query(value = "SELECT * FROM location WHERE ST_DWithin(coordinates, ST_GeomFromText(?1, 2097), ?2)", nativeQuery = true)
    @Query(value = "SELECT * FROM location WHERE ST_DWithin(coordinates, ST_GeomFromText(?1, 4326), ?2)", nativeQuery = true)
    List<Location> findByCoordinate(String point, float degree);
    //"SELECT * FROM location WHERE ST_DWithin(coordinates, ST_Transform(ST_GeomFromText(?, 4326), 2097), 500)"
    //"SELECT * FROM location WHERE ST_Distance(coordinates, ST_Transform(ST_SetSRID(ST_MakePoint(?1,?2), '4326'), '5179') <= 100 ORDER BY ST_Distance(coordinates, ST_SetSRID(ST_MakePoint(?1,?2), 5179))"

//    @Query(value = "SELECT DISTINCT * FROM location WHERE longitude between ?1 - 0.0056 AND ?1 + 0.0056 AND latitude between ?2 - 0.0049 AND ?2 + 0.0049", nativeQuery = true)
//    List<Location> findByLiteral(double longitude, double latitude);

   // @Query(value = "SELECT * FROM location WHERE place_id LIKE ? ", nativeQuery = true)
    List<Location> findByPlaceIdLike(String placeId);
}