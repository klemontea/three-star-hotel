package com.miniproject.threestarhotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.CacheControl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.Principal;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RestController
public class ThreeStarHotelApplication implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/").setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
	}

	/* Google SSO */
	@RequestMapping(value = "/user")
	public Principal user(Principal principal) {
		return principal;
	}

	public static void main(String[] args) {
		SpringApplication.run(ThreeStarHotelApplication.class, args);
	}
}
