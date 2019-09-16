package kr.sm.itaewon.travelmaker.controller;

import kr.sm.itaewon.travelmaker.model.Article;
import kr.sm.itaewon.travelmaker.model.Basket;
import kr.sm.itaewon.travelmaker.model.Link;
import kr.sm.itaewon.travelmaker.repo.ArticleRepository;
import kr.sm.itaewon.travelmaker.repo.BasketRepository;
import kr.sm.itaewon.travelmaker.repo.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private LinkRepository linkRepository;

    @RequestMapping("/")
    public ResponseEntity<Void> badRequest(){
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }


    ////////////Article

    @GetMapping("/getArticleAll")
    public ResponseEntity<List<Article>> getArticleAll(){

        try {
            //TODO List -> Linked 로 고려할 필요 있음 - 오버헤드 줄이기

            List<Article> list = new LinkedList<>();
            Iterable<Article> articles = articleRepository.findAll();

            articles.forEach(list::add);

            if(list == null){
                return new ResponseEntity<List<Article>>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<List<Article>>(list, HttpStatus.OK);

        }catch (Exception e){

            e.printStackTrace();
            return new ResponseEntity<List<Article>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @GetMapping("/getArticleById/{article_id}")
    public ResponseEntity<List<Article>> getArticleById(@PathVariable long articleId){

        try {
            List<Article> list = articleRepository.findByArticleId(articleId);

            if(list == null){
                return new ResponseEntity<List<Article>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Article>>(list, HttpStatus.OK);

        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<Article>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getArticleByCustomerId/{customer_id}")
    public ResponseEntity<List<Article>> getArticleByCustomerId(@PathVariable long customerId){

        try {
            List<Article> list = articleRepository.findByCustomerId(customerId);

            if(list == null){
                return new ResponseEntity<List<Article>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Article>>(list, HttpStatus.OK);

        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<Article>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getArticleByLocationId/{location_id}")
    public ResponseEntity<List<Article>> getArticleByLocationId(@PathVariable long locationId){

        try {
            List<Article> list = articleRepository.findByLocationId(locationId);

            return new ResponseEntity<List<Article>>(list, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<Article>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/postArticle/customer_id={customer_id}&&link_id={link_id}")
    public ResponseEntity<Void> postArticle(@PathVariable long customerId, @PathVariable long linkId, @RequestBody Article article){
        if(article == null){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        try {
            Link link = linkRepository.findByLinkId(linkId);
            article.setLink(link);
            Timestamp timestamp = new Timestamp(new Date().getTime());
            article.setReg_date(timestamp);
            article.setCustomerId(customerId);
            articleRepository.save(article);
            return new ResponseEntity<Void>(HttpStatus.CREATED);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
