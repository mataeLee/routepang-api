package kr.sm.itaewon.routepang.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kr.sm.itaewon.routepang.category.FeedCategory;
import kr.sm.itaewon.routepang.model.Article;
import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Follow;
import kr.sm.itaewon.routepang.model.Route;
import kr.sm.itaewon.routepang.model.redis.Feed;
import kr.sm.itaewon.routepang.repo.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {

    @Autowired
    private FollowService followService;

    @Autowired
    private ListOperations<String, Feed> listOperations;

//    @Resource(name = "redisTemplate")
//    public ListOperations<String, Feed> listOperations;

    // Article DB에 저장할 때 같이
    public void saveArticle(Article article){
        article.setLocation(null);
        GsonBuilder gb = new GsonBuilder();
        gb.serializeSpecialFloatingPointValues();
        Gson gson = gb.create();
        String jsonData = gson.toJson(article);
        //  System.out.println(jsonData);

        long userId = article.getCustomer().getCustomerId();
        Feed newsfeed = new Feed();
        newsfeed.setCustomerId(userId);
        newsfeed.setEventId(article.getArticleId());
        newsfeed.setEventType(FeedCategory.ARTICLE);
        newsfeed.setJsonData(jsonData);

        List<Customer> followers = followService.findFollowerByCustomer(article.getCustomer());
        // System.out.println(followers.toString());

        for(Customer follower : followers)
            listOperations.rightPush("feed:"+follower.getCustomerId(), newsfeed);

        System.out.println("Success : save article to Redis");
    }

    // TODO Route + Route안의 Product객체들
    public void saveRoute(Route route){

        GsonBuilder gb = new GsonBuilder();
        gb.serializeSpecialFloatingPointValues();
        Gson gson = gb.create();
        String jsonData = gson.toJson(route);

        long userId = route.getCustomer().getCustomerId();
        Feed newsfeed = new Feed();
        newsfeed.setCustomerId(userId);
        newsfeed.setEventId(route.getRouteId());
        newsfeed.setEventType(FeedCategory.ROUTE);
        newsfeed.setJsonData(jsonData);

        List<Customer> followers = followService.findFollowerByCustomer(route.getCustomer());
        System.out.println(followers.toString());
        for(Customer follower : followers)
            listOperations.rightPush("feed:"+follower.getCustomerId(), newsfeed);
    }

    // 성공?
    public List<Feed> getFeed(long userId){
        System.out.println(listOperations.index("feed:"+userId, 0));

        return listOperations.range("feed:"+userId, 0, listOperations.size("feed:"+userId));
    }

//    public void delete(Route route) {
//        String key = "feed:"+route.getCustomer().getCustomerId();
//
////        listOperations.remove(key,1, );
//    }
}