package com.toffeestory.backend.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final BeverageRepository beverageRepository;
    private final ToppingRepository toppingRepository;

    @GetMapping("/beverages/names")
    public ResponseEntity<Object> getProductName(){

        List<Beverage> findAllBeverages = beverageRepository.findAll();

        String [] beverages = new String[findAllBeverages.size()];

        for (int i = 0; i < findAllBeverages.size(); i++) {
            beverages[i] = findAllBeverages.get(i).getBeverageName();
        }
        return ResponseEntity.ok().body(beverages);
    }

    @GetMapping("/toppings/names")
    public ResponseEntity<Object> getToppingName(){
        List<Topping> findAllToppings = toppingRepository.findAll();

        String [] toppings = new String[findAllToppings.size()];

        for (int i = 0; i < findAllToppings.size(); i++) {
            toppings[i] = findAllToppings.get(i).getToppingName();
        }

        return ResponseEntity.ok().body(toppings);
    }
}
