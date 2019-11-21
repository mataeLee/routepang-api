package kr.sm.itaewon.routepang.service;

import kr.sm.itaewon.routepang.model.redis.Session;
import kr.sm.itaewon.routepang.repo.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.SplittableRandom;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;

//    @Autowired
//    private RedisTemplate redisTemplate;

//    ListOperations listOperations = redisTemplate.opsForList();

    public void save(Session session) {

        sessionRepository.save(session);
    }

    public Session build(Session session) {
        SplittableRandom random = new SplittableRandom();
        String key =  String.valueOf(random.nextInt(1, 1_000_000_000));
        Session sessionModel = Session.builder()
                .id(key)
                .customerId(session.getCustomerId())
                .loginToken(session.getLoginToken())
                .build();
//        listOperations.leftPush(id,session);
        sessionRepository.save(sessionModel);

        return sessionModel;
    }

    public Session findBySessionId(String id){
        Optional<Session> session =sessionRepository.findById(id);

        return session.get();
    }
}
