package kr.sm.itaewon.travelmaker.repo;

import kr.sm.itaewon.travelmaker.model.Basket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BasketRepository extends CrudRepository<Basket, Long> {

    List<Basket> findByCustomerId(long customerId);

    Basket findByBasketId(long basketId);

    @Query(value = "SELECT * FROM  basket WHERE location_id = ?1 AND customer_id = ?2", nativeQuery = true)
    Basket findBylocationIdAndCustomerId(long locationId, long customerId);

    @Query(value = "SELECT * FROM  basket WHERE route_id = ?1 AND customer_id = ?2", nativeQuery = true)
    List<Basket> findByRouteIdAndCustomerId(long routeId, long customerId);
}
