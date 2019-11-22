package kr.sm.itaewon.routepang.repo;

import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Follow;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FollowRepository extends CrudRepository<Follow, Long> {

    Follow findByFollowId(Follow follow);

    int countByFollower(Customer follower);

    Follow findByFollowerAndTarget(Customer follower, Customer target);

    int countByTarget(Customer target);

    List<Follow> findByFollowerAndFollow(Customer customer, boolean follow);

    List<Follow> findByTargetAndFollow(Customer customer, boolean follow);

    int countByTargetAndFollow(Customer customer, boolean follow);

    int countByFollowerAndFollow(Customer customer, boolean follow);
}
