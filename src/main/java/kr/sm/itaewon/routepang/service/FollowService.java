package kr.sm.itaewon.routepang.service;

import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Follow;
import kr.sm.itaewon.routepang.repo.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public int getFollowingCount(Customer following){
        int count = followRepository.countByTarget(following);

        return count;
    }

    public int getFollowerCount(Customer customer){
        int count = followRepository.countByFollower(customer);

        return count;
    }

}
