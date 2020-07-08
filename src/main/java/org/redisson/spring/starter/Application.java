package org.redisson.spring.starter;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
// @ComponentScan(basePackages = {"org.redisson.spring.starter"})
public class Application {


  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }


}
