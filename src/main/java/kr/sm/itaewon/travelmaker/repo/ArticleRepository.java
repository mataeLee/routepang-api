package kr.sm.itaewon.travelmaker.repo;

import kr.sm.itaewon.travelmaker.model.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import static org.hibernate.loader.Loader.SELECT;


public interface ArticleRepository extends CrudRepository<Article, Long> {

    @Query(value = "SELECT COUNT(*) FROM article where ?1 = location_id;", nativeQuery = true)
    int countArticlesByLocationId(long locationId);

    List<Article> findByArticleId(long articleId);

    List<Article> findByLocationId(long loacationId);

    List<Article> findByCustomerId(long customerId);
}
