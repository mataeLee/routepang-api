package kr.sm.itaewon.routepang.service.fcm;

import com.google.firebase.messaging.FirebaseMessagingException;
import kr.sm.itaewon.routepang.model.fcm.NotificationRequest;
import kr.sm.itaewon.routepang.model.redis.ChatMessage;
import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class NotificationService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FCMService fcmService;

    public void pushMessage(ChatMessage chatMessage) throws ExecutionException, InterruptedException, FirebaseMessagingException {
//        Customer sender = customerService.findByCustomerId(chatMessage.getSenderId());
        Customer receiver = customerService.findByCustomerId(chatMessage.getReceiverId());

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .message(chatMessage.getContent())
                .title(receiver.getAccount()+"님의 메시지")
                .token(receiver.getPushToken())
                .build();
        fcmService.send(notificationRequest);
    }

    private static final String firebase_server_key="firebase server key!!";
    private static final String firebase_api_url="https://fcm.googleapis.com/fcm/send";

    @Async
    public CompletableFuture<String> send(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

//        interceptors.add(new HeaderRequestInterceptor("Authorization",  "key=" + firebase_server_key));
//        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json; UTF-8 "));
        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(firebase_api_url, entity, String.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }
}
