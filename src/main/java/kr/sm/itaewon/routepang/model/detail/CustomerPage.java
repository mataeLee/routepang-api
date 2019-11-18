package kr.sm.itaewon.routepang.model.detail;

import kr.sm.itaewon.routepang.model.Route;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CustomerPage {
    private long customerId;

    private String reference;

    private String email;

    private int followerCount;

    private int followingCount;

    private int articleCount;

    private int routeCount;

    private int productCount;
}
