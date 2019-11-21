package kr.sm.itaewon.routepang.model.redis;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RedisHash("sesisons")
public class Session implements Serializable {

    @Id
    private String id;

    private long customerId;

    private String loginToken;

    @CreationTimestamp
    private Timestamp regDate;

    @UpdateTimestamp
    private Timestamp updateDate;

    @Builder
    public Session(String id, Long customerId, String loginToken) {
        this.id = id;
        this.customerId = customerId;
        this.loginToken = loginToken;
    }

}