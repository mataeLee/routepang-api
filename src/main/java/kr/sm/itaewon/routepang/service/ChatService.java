package kr.sm.itaewon.routepang.service;

import kr.sm.itaewon.routepang.model.redis.ChatMessage;
import kr.sm.itaewon.routepang.repo.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ListOperations<String, ChatMessage> messageListOperations;

    public void save(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    public ChatMessage build(ChatMessage chatMessage) {
//        SplittableRandom random = new SplittableRandom();
//        String key =  String.valueOf(random.nextInt(1, 1_000_000_000));

        String key = "" + chatMessage.getReceiverId() + chatMessage.getSenderId();
        ChatMessage chatMessageModel = ChatMessage.builder()
                .id(key)
                .senderId(chatMessage.getSenderId())
                .receiverId(chatMessage.getReceiverId())
                .content(chatMessage.getContent().trim())
                //.regDate(chatMessage.getRegDate())
                .build();
        messageListOperations.leftPush(key,chatMessageModel);
//        chatMessageRepository.save(chatMessageModel);
        return chatMessageModel;
    }

    public List<ChatMessage> findAllByKey(String key) {

        List<ChatMessage> chatMessageList = messageListOperations.range(key,0,messageListOperations.size(key));
//        List<ChatMessage> chatMessageList = chatMessageRepository.findAllByReceiverId(customerId);
        return chatMessageList;
    }

    public List<ChatMessage> findAll() {
      //  listOperations.range("0",listOperations.size("0"));

            Iterable<ChatMessage> iterable = chatMessageRepository.findAll();
            List<ChatMessage> chatMessageList = new ArrayList<>();
            iterable.forEach(chatMessageList::add);
            return chatMessageList;
    }
}
