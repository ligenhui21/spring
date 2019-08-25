package com.lgh.mybatisdemo;

import com.lgh.mybatisdemo.mapper.CoffeeMapper;
import com.lgh.mybatisdemo.model.Coffee;
import com.lgh.mybatisdemo.model.CoffeeExample;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@Slf4j
@MapperScan("com.lgh.mybatisdemo.mapper")
public class MybatisDemoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(MybatisDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		//generatorArtifacts();
		playWithArtifacts();
	}

	private void generatorArtifacts() throws Exception {
		List<String> warnings = new ArrayList<>();
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(
				this.getClass().getResourceAsStream("/generatorConfig.xml"));
		DefaultShellCallback callback = new DefaultShellCallback(true);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}

	@Autowired
	private CoffeeMapper coffeeMapper;

	private void playWithArtifacts(){
		Coffee coffee = new Coffee()
				.withName("拿铁")
				.withPrice(Money.of(CurrencyUnit.of("CNY"), 20.0))
				.withCreateTime(new Date())
				.withUpdateTime(new Date());
		int result = coffeeMapper.insert(coffee);
		CoffeeExample coffeeExample = new CoffeeExample();
		coffeeExample.createCriteria().andNameEqualTo("拿铁");
		List<Coffee> coffees = coffeeMapper.selectByExample(coffeeExample);
		coffees.forEach(o -> {
			log.info("咖啡：{}", o.toString());
		});
	}
}
