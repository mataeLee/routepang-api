package kr.sm.itaewon.routepang.repo;

import kr.sm.itaewon.routepang.model.redis.ChatMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, String> {

    @Query(value = "SELEC * FROM chatmassage WHERE receiver_id = ?",nativeQuery = true)
    List<ChatMessage> findAllByReceiverId(long receiverId);

    List<ChatMessage> findAllById(String id);
}
