package kr.sm.itaewon.routepang.service.fcm;

import kr.sm.itaewon.routepang.model.fcm.NotificationRequest;
import kr.sm.itaewon.routepang.model.redis.ChatMessage;
import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class NotificationService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FCMService fcmService;

    public void pushMessage(ChatMessage chatMessage) throws ExecutionException, InterruptedException {
        Customer sender = customerService.findByCustomerId(chatMessage.getSenderId());
        Customer receiver = customerService.findByCustomerId(chatMessage.getReceiverId());

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .message(chatMessage.getContent())
                .title("chat message")
                .token(receiver.getPushToken())
                .build();
        fcmService.send(notificationRequest);

    }
}
