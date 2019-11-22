package kr.sm.itaewon.routepang.service;

import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Follow;
import kr.sm.itaewon.routepang.repo.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowService {
    @Autowired
    private FollowRepository followRepository;

    public Follow follow(Follow followParam) {
        Follow followModel = followRepository.findByFollowerAndTarget(followParam.getFollower(), followParam.getTarget());

        if(followModel != null){

            if(followModel.isFollow())
                followModel.setFollow(false);
            else
                followModel.setFollow(true);

            followRepository.save(followModel);

            return followModel;
        }
        else{
            followParam.setFollow(true);

            followRepository.save(followParam);

            return followParam;
        }
    }

    public int getFollowingCount(Customer customer){
        int count = followRepository.countByFollowerAndFollow(customer, true);

        return count;
    }

    public int getFollowerCount(Customer customer){
        int count = followRepository.countByTargetAndFollow(customer,true);

        return count;
    }

    public List<Customer> findFollowerByCustomer(Customer customer) {
        List<Follow> followerList = followRepository.findByTargetAndFollow(customer, true);
        List<Customer> customerList = new ArrayList<>();

        for(Follow follow: followerList){
            customerList.add(follow.getFollower());
        }
        return customerList;
    }

    public List<Customer> findFollowingByCustomer(Customer customer) {
        List<Follow> followingList = followRepository.findByFollowerAndFollow(customer, true);
        List<Customer> customerList = new ArrayList<>();

        for(Follow follow: followingList){
            customerList.add(follow.getTarget());
        }
        return customerList;
    }
}
