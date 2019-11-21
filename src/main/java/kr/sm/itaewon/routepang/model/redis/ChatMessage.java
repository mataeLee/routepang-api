package kr.sm.itaewon.routepang.model.redis;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.redis.core.RedisHash;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RedisHash("chatmessage")
public class ChatMessage implements Serializable {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private long senderId;
    private long receiverId;

    private String content;

    @CreationTimestamp
    private Timestamp regDate;

    @Builder
    public ChatMessage(String id, long senderId, long receiverId, String content, Timestamp regDate){
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.regDate = regDate;
    }
}
