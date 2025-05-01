package br.wesley.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisSpringLabApplication {
	public static void main(String[] args) {
		SpringApplication.run(RedisSpringLabApplication.class, args);
	}
}
