package com.lgh.readfiledemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
@Slf4j
public class ReadfileDemoApplication implements ApplicationRunner{

	public static void main(String[] args) {
		SpringApplication.run(ReadfileDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("classPathResource:");
		classpathResource();
		log.info("fileSystemResource:");
		fileSystemResource();
		log.info("resourceLoader:");
		resourceLoader();
		log.info("readResource:");
		resourceResource();
		log.info("amazingResourceLoader:");
		amazingResourceLoader();
	}

	/**
	 * org.springframework.core.io.ClassPathResource 用来加载classpath下的资源
	 */
	private void classpathResource() throws IOException {
		ClassPathResource resource = new ClassPathResource("application.properties");
		InputStream inputStream = resource.getInputStream();
		Properties properties = new Properties();
		properties.load(inputStream);
		properties.forEach((key, value) -> {
			log.info("{} = {}", key, value);
		});
	}

	/**
	 * org.springframework.core.io.FileSystemResource 用来加载系统文件
	 * 通常通过文件的绝对或相对路径来读取
	 */
	private void fileSystemResource() throws IOException {
		String path = "D:\\study\\workspaces\\spring\\io\\readfile-demo\\src\\main\\resources\\application.properties";
		log.info(this.getClass().getResource("/").getPath());
		FileSystemResource resource = new FileSystemResource(path);
		InputStream inputStream = resource.getInputStream();
		Properties properties = new Properties();
		properties.load(inputStream);
		properties.forEach((key, value) -> {
			log.info("{} = {}", key, value);
		});
	}

	/**
	 * ResourceLoader
	 */
	private void resourceLoader() throws IOException {
		DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
		String location = "application.properties";
		Resource resource = resourceLoader.getResource(location);
		InputStream inputStream = resource.getInputStream();
		Properties properties = new Properties();
		properties.load(inputStream);
		properties.forEach((key, value) -> {
			log.info("{} = {}", key, value);
		});
	}

	@Autowired
	private ApplicationContext webApplicationContext;

	/**
	 * 通过注入bean的方式来读取
	 */
	private void resourceResource() throws IOException {
		String location = "application.properties";
		Resource resource = webApplicationContext.getResource(location);
		InputStream inputStream = resource.getInputStream();
		Properties properties = new Properties();
		properties.load(inputStream);
		properties.forEach((key, value) -> {
			log.info("{} = {}", key, value);
		});
	}

	@Value("classpath:application.properties")
	private Resource resource;

	/**
	 * 使用value注解直接注入
	 */
	private void amazingResourceLoader() throws IOException {
		InputStream inputStream = resource.getInputStream();
		Properties properties = new Properties();
		properties.load(inputStream);
		properties.forEach((key, value) -> {
			log.info("{} = {}", key, value);
		});
	}
}
