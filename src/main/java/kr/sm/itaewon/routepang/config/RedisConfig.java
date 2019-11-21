package kr.sm.itaewon.routepang.config;

import kr.sm.itaewon.routepang.model.redis.ChatMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHostName;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.timeout}")
    private int redisTimeOut;


    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisHostName, redisPort);
    }
    @Bean
    public RedisTemplate<String, ChatMessage> redisTemplate() {
        RedisTemplate<String, ChatMessage> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }
    @Bean
    public ListOperations<String, ChatMessage> messageListOperations() {
        ListOperations<String, ChatMessage> messageListOperations = redisTemplate().opsForList();
        return messageListOperations;
    }
//    @Bean
//    public RedisTemplate<?, ?> redisTemplate() {
//        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
//        redisTemplate.setEnableDefaultSerializer(false);
//        redisTemplate.setEnableTransactionSupport(true);
//        return redisTemplate;
//    }

}