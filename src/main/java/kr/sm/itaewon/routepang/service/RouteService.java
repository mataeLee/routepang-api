package kr.sm.itaewon.routepang.service;

import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Location;
import kr.sm.itaewon.routepang.model.Product;
import kr.sm.itaewon.routepang.model.Route;
import kr.sm.itaewon.routepang.repo.RouteRepository;
import kr.sm.itaewon.routepang.util.RouteManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ProductService productService;

    private RouteManager routeManager = new RouteManager();

    public List<Route> findByCustomer(Customer customer) {
        List<Route> list = routeRepository.findByCustomer(customer);

        list = routeManager.combinationRoute(list);

        return list;
    }

    public void save(List<Route> routes, Customer customer) {
        List<Route> list = routeManager.sortRoute(routes);

        // 중복 방지를 위해 기존 레코드 삭제 후 새로 삽입 - IO latency 증가 시 기존 레코드들 수정하는 방향으로 수정
        Integer result = routeRepository.deleteAllByCustomer(customer);

        for(Route route: list){
            route.setCustomer(customer);
            routeRepository.save(route);
        }
    }

    public List<Product> findProductsByRouteId(long routeId) {
        Route route = routeRepository.findByRouteId(routeId);

        List<Product> productList = productService.findAllByRouteId(route);

        return productList;
    }

    public int countByCustomer(Customer customer) {
        int count = routeRepository.countByCustomer(customer);
        return count;
    }

    public Route findByRouteId(long routeId) {
        Route route = routeRepository.findByRouteId(routeId);
        return route;
    }
}
