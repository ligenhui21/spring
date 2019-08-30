package com.lgh.cachedemo.service.impl;

import com.lgh.cachedemo.mapper.CoffeeMapper;
import com.lgh.cachedemo.model.Coffee;
import com.lgh.cachedemo.model.CoffeeExample;
import com.lgh.cachedemo.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@CacheConfig(cacheNames = "coffee")
public class CoffeeServiceImpl implements CoffeeService {

    @Autowired
    private CoffeeMapper coffeeMapper;

    @Cacheable
    @Override
    public List<Coffee> findAllCoffee() {
        CoffeeExample coffeeExample = new CoffeeExample();
        List<Coffee> coffees = coffeeMapper.selectByExample(coffeeExample);
        return coffees;
    }

    @CacheEvict
    @Override
    public void reloadCoffee() {

    }

    @Cacheable
    @Override
    public List<Coffee> findCoffeeByName(String name) {
        CoffeeExample coffeeExample = new CoffeeExample();
        coffeeExample.createCriteria().andNameEqualTo(name);
        List<Coffee> coffees = coffeeMapper.selectByExample(coffeeExample);
        return coffees;
    }
}
