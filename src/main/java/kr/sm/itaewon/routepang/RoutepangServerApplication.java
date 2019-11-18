package kr.sm.itaewon.routepang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.ListOperations;

import javax.annotation.Resource;


@SpringBootApplication
public class RoutepangServerApplication {

    @Resource(name="redisTemplate")
    private ListOperations<String, String> listOperations;

    public static void main(String[] args) {

        SpringApplication.run(RoutepangServerApplication.class, args);
    }

}