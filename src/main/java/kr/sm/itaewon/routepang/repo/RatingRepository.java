package kr.sm.itaewon.routepang.repo;

import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Location;
import kr.sm.itaewon.routepang.model.Rating;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RatingRepository extends CrudRepository<Rating, Long> {

    List<Rating> findByLocation(Location location);

    Rating findByCustomerAndLocation(Customer customer, Location location);
}
