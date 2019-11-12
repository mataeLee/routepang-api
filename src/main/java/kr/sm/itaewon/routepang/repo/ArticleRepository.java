package kr.sm.itaewon.routepang.repo;

import kr.sm.itaewon.routepang.model.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ArticleRepository extends CrudRepository<Article, Long> {

    @Query(value = "SELECT COUNT(*) FROM article where ?1 = location_id;", nativeQuery = true)
    int countArticlesByLocationId(long locationId);

    Article findByArticleId(long articleId);

    List<Article> findByPlaceId(String placeId);

    List<Article> findByCustomerId(long customerId);
}
