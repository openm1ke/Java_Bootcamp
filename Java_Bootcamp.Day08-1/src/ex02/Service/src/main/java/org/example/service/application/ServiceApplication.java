package org.example.service.application;

import org.example.service.config.ApplicationConfig;
import org.example.service.models.User;
import org.example.service.repositories.UsersRepository;
import org.example.service.services.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.util.List;

public class ServiceApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);

		initializeDatabase(ctx);

		UsersRepository usersRepository = (UsersRepository) ctx.getBean("UsersRepository");
		List<User> users = usersRepository.findAll();
		users.forEach(System.out::println);
		UserService userService = (UserService) ctx.getBean("UserService");
		String tempPassword = userService.signUp("a@a.com");
		System.out.println(tempPassword);

		users = usersRepository.findAll();
		users.forEach(System.out::println);

	}

	private static void initializeDatabase(ApplicationContext context) {
		DataSource dataSource = (DataSource) context.getBean("SpringDataSource");
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.addScript(new ClassPathResource("schema.sql"));
		databasePopulator.execute(dataSource);
	}

}
