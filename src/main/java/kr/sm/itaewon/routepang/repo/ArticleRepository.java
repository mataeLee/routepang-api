package kr.sm.itaewon.routepang.repo;

import kr.sm.itaewon.routepang.model.Article;
import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ArticleRepository extends CrudRepository<Article, Long> {

    @Query(value = "SELECT COUNT(*) FROM article where ?1 = location_id;", nativeQuery = true)
    int countArticlesByLocation(Location location);

    Article findByArticleId(long articleId);

    List<Article> findByLocation(Location location);

    List<Article> findByCustomer(Customer customer);

    int countArticlesByCustomer(Customer customer);
}
