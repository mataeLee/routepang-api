package kr.sm.itaewon.routepang.service;

import kr.sm.itaewon.routepang.model.redis.ChatMessage;
import kr.sm.itaewon.routepang.repo.ChatMessageRepository;
import kr.sm.itaewon.routepang.util.KeyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ListOperations<String, ChatMessage> messageListOperations;

    public KeyManager keyManager = new KeyManager();

    public void save(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    public ChatMessage build(ChatMessage chatMessage) {

        long senderId = chatMessage.getSenderId();
        long receiverId = chatMessage.getReceiverId();

        long ids [] = new long[2];
        ids[0] = senderId;
        ids[1] = receiverId;
        Arrays.sort(ids);
        String key = "dm:" + ids[0] +":"+ ids[1];
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
