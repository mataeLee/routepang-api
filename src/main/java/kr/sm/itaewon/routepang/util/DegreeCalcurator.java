package kr.sm.itaewon.routepang.util;

import lombok.Getter;

@Getter
public class DegreeCalcurator {

    /**
     *  위도 1도당 실측 거리
     */
    private final float unit = 111000;

    /**
     * @param radius - 단위가 meter
     * @return latitude degree
     */
    public float getDegreeByMeter(int radius){

        /**
         *  반경 거리에 따른 위도 계산
         *  ex) 반경 500m의 위도차는 500 * 1/111000도
         */
        float degree = radius * (1/unit);

        return degree;
    }

}
