package kr.sm.itaewon.routepang.service;

import kr.sm.itaewon.routepang.model.Basket;
import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.repo.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {
    @Autowired
    private BasketRepository basketRepository;

    public Basket findByCustomer(Customer customer) {
        Basket basket = basketRepository.findByCustomer(customer);

        return basket;
    }

    public Basket crateBasket(Customer customer) {
        Basket basket = new Basket();
        basket.setCustomer(customer);
        basketRepository.save(basket);

        return basket;
    }
}
