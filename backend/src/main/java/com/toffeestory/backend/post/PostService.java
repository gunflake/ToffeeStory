package com.toffeestory.backend.post;

import com.toffeestory.backend.toffee.Product;
import com.toffeestory.backend.toffee.ProductRepository;
import com.toffeestory.backend.toffee.Topping;
import com.toffeestory.backend.toffee.ToppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ToppingRepository toppingRepository;

    public void selectProducts(Integer postNo) {
        //상품목록, 토핑목록
        //List<Product> products = productRepository.findByPostNo(postNo);
        //List<Topping> toppings = toppingRepository.findByPostNo(postNo);
    }
}
