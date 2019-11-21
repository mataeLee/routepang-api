package kr.sm.itaewon.routepang.repo;

import kr.sm.itaewon.routepang.model.Article;
import kr.sm.itaewon.routepang.model.ArticleComment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleCommentRepository extends CrudRepository<ArticleComment, Long> {


    List<ArticleComment> findByArticle(Article article);
}
