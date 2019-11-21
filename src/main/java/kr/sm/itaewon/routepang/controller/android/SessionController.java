package kr.sm.itaewon.routepang.controller.android;

import kr.sm.itaewon.routepang.model.redis.Session;
import kr.sm.itaewon.routepang.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping("/save")
    private ResponseEntity<Session> postSession(@RequestBody Session sessionParam){
        Session sessionModel = sessionService.build(sessionParam);

        return new ResponseEntity<>(sessionModel, HttpStatus.CREATED);
    }

//    @GetMapping("/")
//    private ResponseEntity<Session> getSession(@RequestHeader String sessionId){
//        Session session = sessionService.findBySessionId(sessionId);
////                .map(Session::getCustomerId)
////                .orElse(0L);
//
//        return new ResponseEntity<>(session, HttpStatus.OK);
//    }
}

