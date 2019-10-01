package kr.sm.itaewon.travelmaker.util;

import kr.sm.itaewon.travelmaker.category.RouteCategory;
import kr.sm.itaewon.travelmaker.model.Link;
import kr.sm.itaewon.travelmaker.model.Route;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RouteManager {

    /**
     *
     * @param routeList - customer id로 조회한 route들(루트간 수평관계)
     *
     */
    public List<Route> combinationRoute(List<Route> routeList){

        List<Route> subList = routeList;
        List<Route> list = new LinkedList<Route>();

        for(int i=0; i < routeList.size(); i++){

            Route route = routeList.get(i);

            // 폴더 안에 루트 담기
            if(route.getCategory() == RouteCategory.FOLDER) {
                route.setRoutes(new LinkedList<Route>());
                for (Route subRoute : subList) {
                    if (route.getRouteId() == subRoute.getParentId()) {
                        route.getRoutes().add(subRoute);
                    }
                }
            }

            // 최상위 루트/폴더를 리스트에 담기
            if (route.getParentId() == 0) {
                list.add(route);
            }
        }

        return list;
    }

    public List<Route> sortRoute(List<Route> routeList){

        List<Route> list = new LinkedList<Route>();

        for(Route route : routeList){
            if(route.getCategory() == RouteCategory.FOLDER){
                list.addAll(sortRoute(route.getRoutes()));

            }
            list.add(route);
        }
        return list;
    }
}