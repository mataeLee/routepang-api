package kr.sm.itaewon.travelmaker.util;

import kr.sm.itaewon.travelmaker.category.RouteCategory;
import kr.sm.itaewon.travelmaker.model.Route;

import java.util.LinkedList;
import java.util.List;

public class RouteManager {

    /**
     *
     * @param routeList - customer id로 조회한 route들(루트간 수평관계)
     *
     */
    public List<Route> combinationRoute(List<Route> routeList){
        //TODO 클라이언트에게 응답하기 위해 조합

        List<Route> subList = routeList;

        for(Route route : routeList){
            for(Route subRoute: subList){
                if(route.getRouteId()==subRoute.getParentId()){
                    route.getRoutes().add(subRoute);
                }
            }
        }

        return routeList;
    }



}
