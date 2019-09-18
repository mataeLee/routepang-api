package kr.sm.itaewon.travelmaker.repo;

import kr.sm.itaewon.travelmaker.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByCustomerId(long customerId);
}
