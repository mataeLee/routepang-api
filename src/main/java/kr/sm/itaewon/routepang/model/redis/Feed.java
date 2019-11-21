package kr.sm.itaewon.routepang.model.redis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RedisHash("feed")
public class Feed {

    @Id
    private String id;

}
