package kr.sm.itaewon.travelmaker.repo;

import kr.sm.itaewon.travelmaker.model.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ArticleRepository extends CrudRepository<Article, Long> {

    List<Article> findByArticleId(long article_id);

    List<Article> findByLocationId(long loacation_id);

    List<Article> findByCustomerId(long customer_id);
}
