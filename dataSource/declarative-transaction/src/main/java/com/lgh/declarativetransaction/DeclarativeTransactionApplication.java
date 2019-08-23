package com.lgh.declarativetransaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(mode = AdviceMode.PROXY)
@Slf4j
public class DeclarativeTransactionApplication implements CommandLineRunner {

	@Autowired
	private FooService fooService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DeclarativeTransactionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		fooService.insertRecord();
		log.info("AAA的条数：{}", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR = 'AAA'", Long.class));

		try {
			fooService.insertThenRollback();
			log.info("BBB的条数：{}", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR = 'BBB'", Long.class));
		} catch (RollBackException e) {
			log.info("BBB的条数：{}", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR = 'BBB'", Long.class));
		}

		try {
			fooService.invokeInsertThenRollback1();
		} catch (RollBackException e) {
			log.info("BBB的条数：{}", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR = 'BBB'", Long.class));
		}

		try {
			//invokeInsertThenRollback2内部调用其他方法抛出的异常被捕获之后不会进catch块
			fooService.invokeInsertThenRollback2();
			log.info("BBB的条数：{}", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR = 'BBB'", Long.class));
			log.info("CCC的条数：{}", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR = 'CCC'", Long.class));
		} catch (RollBackException e) {
			log.info("出错了，BBB的条数：{}", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR = 'BBB'", Long.class));
			log.info("出错了，CCC的条数：{}", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR = 'CCC'", Long.class));
		}
	}
}
