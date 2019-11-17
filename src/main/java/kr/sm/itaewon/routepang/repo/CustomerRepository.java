package kr.sm.itaewon.routepang.repo;

import kr.sm.itaewon.routepang.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByCustomerId(long customerId);

    //@Query(value = "SELECT * FROM customer where customer.account = ?", nativeQuery = true)
    Customer findByAccount(String account);
}
