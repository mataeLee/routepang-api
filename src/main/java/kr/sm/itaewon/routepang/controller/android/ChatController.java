package kr.sm.itaewon.routepang.controller.android;

import com.google.firebase.messaging.FirebaseMessagingException;
import kr.sm.itaewon.routepang.model.redis.ChatMessage;
import kr.sm.itaewon.routepang.service.ChatService;
import kr.sm.itaewon.routepang.service.CustomerService;
import kr.sm.itaewon.routepang.service.fcm.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody ChatMessage chatMessage) throws ExecutionException, InterruptedException, FirebaseMessagingException {
        ChatMessage chatMessageModel = chatService.build(chatMessage);

        //push
        notificationService.pushMessage(chatMessageModel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/read")
    public ResponseEntity<List<ChatMessage>> readMessage(@RequestHeader long receiverId, @RequestHeader long senderId){

        //TODO key mapping
        long ids [] = new long[2];
        ids[0] = senderId;
        ids[1] = receiverId;
        Arrays.sort(ids);
        String key = "dm:" + ids[0] +":"+ ids[1];
        List<ChatMessage> chatMessageList = chatService.findAllByKey(key);
        return new ResponseEntity<>(chatMessageList, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChatMessage>> readMessageAll(){
        List<ChatMessage> chatMessageList = chatService.findAll();

        return new ResponseEntity<>(chatMessageList, HttpStatus.OK);
    }
}
