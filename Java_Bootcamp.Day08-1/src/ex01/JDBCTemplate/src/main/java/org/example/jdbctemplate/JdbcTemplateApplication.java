package org.example.jdbctemplate;

import com.zaxxer.hikari.HikariConfig;
import org.example.jdbctemplate.models.User;
import org.example.jdbctemplate.repositories.UsersRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.util.List;

public class JdbcTemplateApplication {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

		// Initialize the database and load test data
		initializeDatabase(context);

		// Compare functionality of UsersRepositoryJdbcImpl and UsersRepositoryJdbcTemplateImpl
		UsersRepository usersRepositoryJdbc = context.getBean("usersRepositoryJdbc", UsersRepository.class);
		UsersRepository usersRepositoryJdbcTemplate = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);

		// Find all users
		List<User> usersJdbc = usersRepositoryJdbc.findAll();
		List<User> usersJdbcTemplate = usersRepositoryJdbcTemplate.findAll();

		// Print users to compare results
		System.out.println("Users from UsersRepositoryJdbcImpl:");
		usersJdbc.forEach(System.out::println);

		System.out.println("Users from UsersRepositoryJdbcTemplateImpl:");
		usersJdbcTemplate.forEach(System.out::println);


		// Drop the database
//		dropDatabase(context);
	}

	private static void initializeDatabase(ApplicationContext context) {
		DataSource dataSource = context.getBean("dataSourceHikari", DataSource.class);
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.addScript(new ClassPathResource("schema.sql"));
		databasePopulator.execute(dataSource);
	}

	private static void dropDatabase(ApplicationContext context) {
		DataSource dataSource = context.getBean("dataSourceHikari", DataSource.class);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute("DROP TABLE IF EXISTS users;");
	}
}
