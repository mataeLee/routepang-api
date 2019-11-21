package kr.sm.itaewon.routepang.controller.android;

import kr.sm.itaewon.routepang.model.Follow;
import kr.sm.itaewon.routepang.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    private FollowService followService;

    @PostMapping("/")
    public ResponseEntity<String> follow(@RequestBody Follow follow){

        Follow followModel = followService.follow(follow);

        return new ResponseEntity<>("팔로우 : " + followModel.isFollow(), HttpStatus.OK);
    }
}
