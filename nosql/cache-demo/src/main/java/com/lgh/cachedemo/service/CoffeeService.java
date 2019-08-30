package com.lgh.cachedemo.service;

import com.lgh.cachedemo.model.Coffee;

import java.util.List;

public interface CoffeeService {

    List<Coffee> findAllCoffee();

    void reloadCoffee();

    List<Coffee> findCoffeeByName(String name);
}
