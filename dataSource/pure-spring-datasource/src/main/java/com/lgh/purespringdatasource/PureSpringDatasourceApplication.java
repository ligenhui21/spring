package com.lgh.purespringdatasource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@Slf4j
public class PureSpringDatasourceApplication{

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext*.xml");
		showBeans(applicationContext);
		try {
			dataSourceDemo(applicationContext);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		PureSpringDatasourceApplication applicationBean = applicationContext.getBean("pureSpringDatasourceApplication", PureSpringDatasourceApplication.class);
		log.info("11111");
		log.info("dataSource：----》"+applicationBean.dataSource);
		log.info("jdbcTemplate：-----》"+applicationBean.jdbcTemplate);
		applicationBean.showDatas();
	}

	@Bean(destroyMethod = "close")
	public DataSource dataSource() throws Exception {
		Properties properties = new Properties();
		properties.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver");
		properties.setProperty("url", "jdbc:mysql://localhost:3306/test?serverTimezone=UTC");
		properties.setProperty("username", "root");
		properties.setProperty("password", "ligenhui");
		return BasicDataSourceFactory.createDataSource(properties);
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource){
		return new JdbcTemplate(dataSource);
	}

	private static void showBeans(ApplicationContext applicationContext){
		System.out.println(Arrays.toString(applicationContext.getBeanDefinitionNames()));
	}

	private static void dataSourceDemo(ApplicationContext applicationContext) throws SQLException {
		PureSpringDatasourceApplication application = applicationContext.getBean("pureSpringDatasourceApplication", PureSpringDatasourceApplication.class);
		application.showDataSource();
	}

	public void showDataSource() throws SQLException {
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
	}

	public void showDatas(){
		jdbcTemplate.queryForList("SELECT * FROM author").forEach(row -> log.info(row.toString()));
	}

	public void showDatas2(){
		try {
			dataSource = new PureSpringDatasourceApplication().dataSource();
			jdbcTemplate = new JdbcTemplate(dataSource);
			new PureSpringDatasourceApplication().showDatas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
