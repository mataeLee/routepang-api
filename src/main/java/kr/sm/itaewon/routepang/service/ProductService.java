package kr.sm.itaewon.routepang.service;

import kr.sm.itaewon.routepang.model.Basket;
import kr.sm.itaewon.routepang.model.Location;
import kr.sm.itaewon.routepang.model.Product;
import kr.sm.itaewon.routepang.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void save(Product product, Basket basket) {
        product.setBasket(basket);
        productRepository.save(product);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }

    public List<Product> findByBasket(Basket basket) {
        List<Product> list = productRepository.findByBasket(basket);
        return list;
    }

    public List<Location> findLocationByBasket(Basket basket) {

        List<Product> products = findByBasket(basket);

        List<Location> locations = new ArrayList<>();

        for(Product product: products){
            locations.add(product.getLocation());
        }
        return locations;
    }

    public List<Location> findLocationByProducts(List<Product> productList) {
        List<Location> locationList = new ArrayList<>();
        for(Product product:productList){
            locationList.add(product.getLocation());
        }
        return locationList;
    }
}
