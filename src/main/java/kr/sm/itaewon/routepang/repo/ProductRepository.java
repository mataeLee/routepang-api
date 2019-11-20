package kr.sm.itaewon.routepang.repo;

import kr.sm.itaewon.routepang.model.Basket;
import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Product;
import kr.sm.itaewon.routepang.model.Route;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByBasket(Basket basket);

    int countByBasket(Basket basket);

    //List<Product> findAllByRoute(Route route);

    List<Product> findAllByRouteId(long routeId);
}
