package kr.sm.itaewon.routepang.repo;

import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Follow;
import org.springframework.data.repository.CrudRepository;

public interface FollowRepository extends CrudRepository<Follow, Long> {

    Follow findByFollowId(Follow follow);

    int countByFollower(Customer follower);

    Follow findByFollowerAndTarget(Customer follower, Customer target);

    int countByTarget(Customer target);
}
