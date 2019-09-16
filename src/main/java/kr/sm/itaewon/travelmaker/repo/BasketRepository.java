package kr.sm.itaewon.travelmaker.repo;

import kr.sm.itaewon.travelmaker.model.Basket;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BasketRepository extends CrudRepository<Basket, Long> {

    List<Basket> findByCustomerId(long customerId);

}
