package kr.sm.itaewon.routepang.controller.android;

import kr.sm.itaewon.routepang.model.Article;
import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Link;
import kr.sm.itaewon.routepang.model.Location;
import kr.sm.itaewon.routepang.repo.ArticleRepository;
import kr.sm.itaewon.routepang.repo.BasketRepository;
import kr.sm.itaewon.routepang.repo.CustomerRepository;
import kr.sm.itaewon.routepang.repo.LinkRepository;
import kr.sm.itaewon.routepang.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FeedService feedService;

    @RequestMapping("/**")
    public ResponseEntity<Void> badRequest(){
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Article>> getArticleAll(){

        List<Article> list = articleService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<Article> getArticlesById(@PathVariable long articleId){

        Article article = articleService.findById(articleId);

        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    /**
     * @param customerId : Long
     */
    @GetMapping("/{customerId}/customers")
    public ResponseEntity<List<Article>> getArticleByCustomerId(@PathVariable long customerId){

        Customer customer = customerService.findByCustomerId(customerId);

        List<Article> list = articleService.findByCustomer(customer);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * @param placeId : String
     */
    @GetMapping("/{placeId}/places")
    public ResponseEntity<List<Article>> getArticlesByPlaceId(@PathVariable String placeId){

        System.out.println("place id : " + placeId);
        Location location = locationService.findByPlaceIdLike(placeId);

        List<Article> list = articleService.findByLocation(location);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Void> postArticle(@RequestBody Article article){
        // place 유무 검사
        Location locationParam = article.getLocation();

        Location locationModel = locationService.findByPlaceIdLike(locationParam.getPlaceId());

        if(locationModel == null){
            locationModel = locationService.create(locationParam);
        }
        article.setLocation(locationModel);

        // link 유무 검사
        Link link = article.getLink();

        Link linkModel = linkService.findByLinkUrl(link.getLinkUrl());

        if(linkModel == null){
            linkService.save(link);
            article.setLink(link);
        }
        else
            article.setLink(linkModel);

        articleService.save(article);

        feedService.saveArticle(article);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}