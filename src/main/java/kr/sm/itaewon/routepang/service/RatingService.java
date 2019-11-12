package kr.sm.itaewon.routepang.service;

import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Location;
import kr.sm.itaewon.routepang.model.Rating;
import kr.sm.itaewon.routepang.repo.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ArticleService articleService;

    public List<Rating> findByLocationId(long locationId) {
        List<Rating> list = ratingRepository.findByLocationId(locationId);
        return list;
    }

    public List<Location> insertRating(List<Location> list) {
        for(Location location : list){
            insertRating(location);
        }
        return list;
    }

    public Location insertRating(Location location){
        List<Rating> ratingList = this.findByLocationId(location.getLocationId());
        int count = articleService.countArticlesByLocationId(location.getLocationId());
        Rating rating = calcRatingAndUsedTime(ratingList);

        location.setRatingCount(ratingList.size());
        location.setRating(rating.getRating());
        location.setUsedTime(rating.getUsedTime());
        location.setArticleCount(count);

        return location;
    }

    private Rating calcRatingAndUsedTime(List<Rating> ratingList){
        Rating calcedRating = new Rating();

        float rate = 0;
        double usedTime = 0;

        // 총합
        for(Rating rating : ratingList){
            rate += rating.getRating();
            usedTime += rating.getUsedTime();
        }

        // 평균
        rate = rate/(ratingList.size());
        usedTime = usedTime/(ratingList.size());

        calcedRating.setRating(rate);
        calcedRating.setUsedTime(usedTime);
        return calcedRating;
    }

    public Rating findByCustomerIdAndLocationId(long customerId, long locationId){
        Rating rating = ratingRepository.findByCustomerIdAndLocationId(customerId, locationId);
        return rating;
    }

    public void postRating(Rating rating) {
        ratingRepository.save(rating);
    }
}
