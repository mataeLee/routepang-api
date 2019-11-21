package kr.sm.itaewon.routepang.service;

import kr.sm.itaewon.routepang.model.Article;
import kr.sm.itaewon.routepang.model.ArticleComment;
import kr.sm.itaewon.routepang.repo.ArticleCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleCommentService {

    @Autowired
    private ArticleCommentRepository articleCommentRepository;

    public List<ArticleComment> findByArticle(Article article){

        List<ArticleComment> commentList = articleCommentRepository.findByArticle(article);

        return commentList;
    }

    public void save(ArticleComment comment) {
        articleCommentRepository.save(comment);
    }
}
