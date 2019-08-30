package com.lgh.redisdemo;

import com.lgh.redisdemo.mapper.CoffeeMapper;
import com.lgh.redisdemo.model.Coffee;
import com.lgh.redisdemo.model.CoffeeExample;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;

@SpringBootApplication
@MapperScan("com.lgh.redisdemo.mapper")
@Slf4j
public class RedisDemoApplication implements ApplicationRunner {

	@Autowired
	private CoffeeMapper coffeeMapper;

	@Autowired
	private JedisPool jedisPool;

	@Autowired
	private JedisPoolConfig jedisPoolConfig;

	public static void main(String[] args) {
		SpringApplication.run(RedisDemoApplication.class, args);
	}

	@Bean
	@ConfigurationProperties("redis")
	public JedisPoolConfig jedisPoolConfig(){
		return new JedisPoolConfig();
	}

	@Bean(destroyMethod = "close")
	public JedisPool jedisPool(@Value("${redis.host}") String host){
		return new JedisPool(jedisPoolConfig(), host);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info(jedisPoolConfig.toString());

		try(Jedis jedis = jedisPool.getResource()){
			CoffeeExample coffeeExample = new CoffeeExample();
			List<Coffee> coffees = coffeeMapper.selectByExample(coffeeExample);
			coffees.forEach(coffee -> jedis.hset("springbucks-menu", coffee.getName(), Long.toString(coffee.getPrice().getAmountMajorLong())));

			Map<String, String> menu = jedis.hgetAll("springbucks-menu");
			log.info("Menu：{}", menu);
			String price = jedis.hget("springbucks-menu", "mocha");
			log.info("mocha：{}", Money.ofMinor(CurrencyUnit.of("CNY"), Long.parseLong(price)));
		}



	}

	/**
	 * mybatis-generator自动生成
	 * @author ligh
	 * @date 2019/8/27
	 */
	/*private void generatorArtifacts() throws Exception {
		List<String> warnings = new ArrayList<>();
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(
				this.getClass().getResourceAsStream("/generatorConfig.xml"));
		DefaultShellCallback callback = new DefaultShellCallback(true);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}*/
}
