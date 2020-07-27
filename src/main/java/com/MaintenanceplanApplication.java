package com;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement 
//@EnableSwagger2
@EnableCaching// 开启缓存
public class MaintenanceplanApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(MaintenanceplanApplication.class, args);		
	}
}
