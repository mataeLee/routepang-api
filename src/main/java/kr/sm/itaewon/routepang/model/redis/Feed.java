package kr.sm.itaewon.routepang.model.redis;

import kr.sm.itaewon.routepang.category.FeedCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Feed {

    private long customerId;

    private long eventId;

    private FeedCategory eventType;

    private String jsonData;
}