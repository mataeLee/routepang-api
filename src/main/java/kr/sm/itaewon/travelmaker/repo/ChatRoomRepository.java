package kr.sm.itaewon.travelmaker.repo;

import kr.sm.itaewon.travelmaker.model.ChatRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, Long> {

    List<ChatRoom> findAllByCustomerId(long customerId);

    ChatRoom findByRoomId(String roomId);
}
