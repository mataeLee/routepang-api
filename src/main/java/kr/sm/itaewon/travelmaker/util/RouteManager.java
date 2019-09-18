package kr.sm.itaewon.travelmaker.util;

import kr.sm.itaewon.travelmaker.model.Route;

import java.util.LinkedList;
import java.util.List;

public class RouteManager {

    /**
     *
     * @param routeList -
     *
     */
    public List<Route> makeRoute(List<Route> routeList){
        //TODO 클라이언트에게 응답하기 위해 조합

        List<Route> list = new LinkedList<>();

        for(Route route : routeList){
            long parentId = route.getParentId();
            if(parentId==0){
                list.add(route);
            }
            else {
                for(Route subRoute : list){
                    if(subRoute.getRouteId()==route.getParentId()){

                    }
                }
            }
        }

        return list;
    }

}
