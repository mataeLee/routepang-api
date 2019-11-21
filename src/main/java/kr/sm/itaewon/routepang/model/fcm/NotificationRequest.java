package kr.sm.itaewon.routepang.model.fcm;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationRequest {

    private String title;
    private String token;
    private String message;

    @Builder
    public NotificationRequest(String title, String token, String message){
        this.title = title;
        this.token = token;
        this.message = message;
    }
}
