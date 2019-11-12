package kr.sm.itaewon.routepang.service;

import kr.sm.itaewon.routepang.repo.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {
    @Autowired
    private BasketRepository basketRepository;
}
