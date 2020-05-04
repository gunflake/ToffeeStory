package com.toffeestory.backend.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final BeverageCategoryRepository beverageCategoryRepository;
    private final ToppingCategoryRepository toppingCategoryRepository;
    private final BeverageRepository beverageRepository;
    private final ToppingRepository toppingRepository;
    private final BeverageToppingRepository beverageToppingRepository;
    private final QuantityCodeRepository quantityCodeRepository;
    private final SubToppingRepository subToppingRepository;

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

    @GetMapping("/beverageCategories")
    public ResponseEntity<Object> getBeverageCategoryList() {
        return ResponseEntity.ok().body(beverageCategoryRepository.findAllByUseStateCode(ProductStatus.USE));
    }

    @GetMapping("/beverages")
    public ResponseEntity<Object> getBeverageList() {
        return ResponseEntity.ok().body(beverageRepository.findAllByUseStateCode(ProductStatus.USE));
    }

    @GetMapping("/toppingCategories")
    public ResponseEntity<Object> getToppingCategoryList() {
        return ResponseEntity.ok().body(toppingCategoryRepository.findAllByUseStateCode(ProductStatus.USE));
    }

    @GetMapping("/quantityCodes")
    public ResponseEntity<Object> getQuantityCodeList() {
        return ResponseEntity.ok().body(quantityCodeRepository.findAllByUseStateCode(ProductStatus.USE));
    }

    @PostMapping("/toppings")
    public ResponseEntity<Object> getToppingList(@RequestParam("beverageNo") Integer beverageNo) {
        Beverage beverage = new Beverage();
        beverage.setBeverageNo(beverageNo);

        return ResponseEntity.ok().body(beverageToppingRepository.findAllByBeverage(beverage));
    }

    @PostMapping("/subToppings")
    public ResponseEntity<Object> getSubToppingList(@RequestParam("toppingNo") Integer toppingNo) {
        Topping topping = new Topping();
        topping.setToppingNo(toppingNo);

        return ResponseEntity.ok().body(subToppingRepository.findAllByTopping(topping));
    }
}