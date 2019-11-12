package kr.sm.itaewon.routepang.controller;

import kr.sm.itaewon.routepang.model.Article;
import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Link;
import kr.sm.itaewon.routepang.model.Location;
import kr.sm.itaewon.routepang.repo.ArticleRepository;
import kr.sm.itaewon.routepang.repo.BasketRepository;
import kr.sm.itaewon.routepang.repo.CustomerRepository;
import kr.sm.itaewon.routepang.repo.LinkRepository;
import kr.sm.itaewon.routepang.service.ArticleService;
import kr.sm.itaewon.routepang.service.CustomerService;
import kr.sm.itaewon.routepang.service.LinkService;
import kr.sm.itaewon.routepang.service.LocationService;
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

    @RequestMapping("/**")
    public ResponseEntity<Void> badRequest(){
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> getArticleAll(){

        List<Article> list = articleService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<Article> getArticlesById(@PathVariable long articleId){

        Article article = articleService.findById(articleId);

        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    /**
     * @param customerId : Long
     */
    @GetMapping("/articles/{customerId}/customers")
    public ResponseEntity<List<Article>> getArticleByCustomerId(@PathVariable long customerId){

        List<Article> list = articleService.findByCustomerId(customerId);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * @param placeId : String
     */
    @GetMapping("/articles/{placeId}/places")
    public ResponseEntity<List<Article>> getArticleByPlaceId(@PathVariable String placeId){

        List<Article> list = articleService.findByPlaceId(placeId);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/article/{linkUrl}/link")
    public ResponseEntity<Void> postArticle(@RequestBody Article article, @PathVariable String linkUrl, @PathVariable Location location){
        // place 유무 검사
        Location locationModel = locationService.findByPlaceId(location.getPlaceId());

        if(locationModel == null){
            locationService.save(location);
            //TODO article, location 연관관계 체크
            article.setLocationId(location.getLocationId());
        }

        // link 유무 검사
        Link link = linkService.findByLinkUrl(linkUrl);
        if(link == null){
            link = linkService.save(linkUrl);
            article.setLink(link);
        }

        articleService.save(article);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
