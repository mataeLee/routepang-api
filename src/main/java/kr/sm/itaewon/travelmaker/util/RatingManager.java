package kr.sm.itaewon.travelmaker.util;

import kr.sm.itaewon.travelmaker.model.Rating;

import java.util.List;

public class RatingManager {

    public Rating calcRatingAndUsedTime(List<Rating> ratingList){
        Rating calcedRating = new Rating();

        float rate = 0;
        double usedTime = 0;

        //
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
}
