package kr.sm.itaewon.travelmaker.controller;

import kr.sm.itaewon.travelmaker.model.Article;
import kr.sm.itaewon.travelmaker.model.Basket;
import kr.sm.itaewon.travelmaker.model.Customer;
import kr.sm.itaewon.travelmaker.model.Link;
import kr.sm.itaewon.travelmaker.repo.ArticleRepository;
import kr.sm.itaewon.travelmaker.repo.BasketRepository;
import kr.sm.itaewon.travelmaker.repo.CustomerRepository;
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

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping("/")
    public ResponseEntity<Void> badRequest(){
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }


    ////////////Article

    @GetMapping("/getArticleAll")
    public ResponseEntity<List<Article>> getArticleAll(){

        try {

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


    @GetMapping("/getArticleById/articleId={articleId}")
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

    @GetMapping("/getArticleByCustomerId/customerId={customerId}")
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

    @GetMapping("/getArticleByLocationId/locationId={locationId}")
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

}
