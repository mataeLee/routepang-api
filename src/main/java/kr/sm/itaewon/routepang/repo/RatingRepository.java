package kr.sm.itaewon.routepang.repo;

import kr.sm.itaewon.routepang.model.Rating;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RatingRepository extends CrudRepository<Rating, Long> {

    List<Rating> findByLocationId(long locationId);

    Rating findByCustomerIdAndLocationId(long customerId, long locatinoId);
}
