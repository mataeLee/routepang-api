package kr.sm.itaewon.routepang.service;

import kr.sm.itaewon.routepang.model.Article;
import kr.sm.itaewon.routepang.model.Link;
import kr.sm.itaewon.routepang.model.Location;
import kr.sm.itaewon.routepang.repo.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> findAll() {

        List<Article> list = new ArrayList<>();
        Iterable<Article> articles = articleRepository.findAll();
        articles.forEach(list::add);

        return list;
    }

    public Article findById(long articleId) {
        Article article = articleRepository.findByArticleId(articleId);

        return article;
    }

    public List<Article> findByCustomerId(long customerId) {
        List<Article> list = articleRepository.findByCustomerId(customerId);
        return list;
    }

    public List<Article> findByPlaceId(String placeId) {
        List<Article> list = articleRepository.findByPlaceId(placeId);
        return list;
    }

    public int countArticlesByLocationId(long locationId) {
        int count = articleRepository.countArticlesByLocationId(locationId);

        return count;
    }

    public boolean save(Article article) {
        try {
            articleRepository.save(article);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

//    public void save(Article article, String linkUrl, Location location) {
//
//        articleRepository.save(article);
//    }
}
