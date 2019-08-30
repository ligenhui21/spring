package com.lgh.cachedemo;

import com.lgh.cachedemo.model.Coffee;
import com.lgh.cachedemo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.List;

@SpringBootApplication
@Slf4j
@EnableCaching(proxyTargetClass = true)
@MapperScan("com.lgh.cachedemo.mapper")
public class CacheDemoApplication implements ApplicationRunner {

	@Autowired
	private CoffeeService coffeeService;

	public static void main(String[] args) {
		SpringApplication.run(CacheDemoApplication.class, args);
	}


	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("count：{}", coffeeService.findAllCoffee().size());
		for(int i = 0; i < 10; i++){
			List<Coffee> allCoffee = coffeeService.findAllCoffee();
			log.info("查询缓存的结果：{}", allCoffee.size());
		}

		Thread.sleep(5000);
		log.info("after refresh");
		coffeeService.findAllCoffee().forEach(c -> log.info("coffee：{}", c));

		/*coffeeService.reloadCoffee();
		log.info("Reading after refresh.");
		coffeeService.findAllCoffee().forEach(c -> log.info("coffee：{}", c.getName()));

		log.info("首次查询");
		List<Coffee> mocha = coffeeService.findCoffeeByName("mocha");
		mocha.forEach(m -> log.info(m.toString()));

		log.info("第二次查询");
		List<Coffee> latte = coffeeService.findCoffeeByName("latte");
		latte.forEach(l -> log.info(l.toString()));

		log.info("第三次查询");
		List<Coffee> mocha1 = coffeeService.findCoffeeByName("mocha");
		mocha1.forEach(m -> log.info(m.toString()));*/
	}
}
