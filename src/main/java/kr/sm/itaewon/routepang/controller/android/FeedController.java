package kr.sm.itaewon.routepang.controller.android;

import kr.sm.itaewon.routepang.model.redis.Feed;
import kr.sm.itaewon.routepang.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private FeedService feedService;

    @GetMapping("/{customerId}")
    public ResponseEntity<List<Feed>> getFeedList(@PathVariable long customerId){
        List<Feed> feedList = feedService.getFeed(customerId);

        //TODO Feed 바꾸기

        return new ResponseEntity<>(feedList, HttpStatus.OK);
    }
}