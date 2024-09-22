package org.example.service;

import org.example.service.config.TestApplicationConfig;
import org.example.service.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;

class ServiceApplicationTests {

	ApplicationContext ctx = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
	UserService userService = (UserService) ctx.getBean("UserService");

	@Test
	void contextLoads() {
		Assertions.assertNotNull(userService.signUp("a@a.com"));
	}
}
