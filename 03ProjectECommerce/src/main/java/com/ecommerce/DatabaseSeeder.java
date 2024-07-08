package com.ecommerce;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ecommerce.dal.OrderDAORepository;
import com.ecommerce.dal.ProductDAORepository;
import com.ecommerce.dal.UserDAORepository;
import com.ecommerce.entities.Order;
import com.ecommerce.entities.Product;
import com.ecommerce.entities.User;

@Component
public class DatabaseSeeder implements CommandLineRunner {

	@Autowired
	private UserDAORepository userDAORepository;
	@Autowired
	private ProductDAORepository productDAORepository;
	@Autowired
	private OrderDAORepository orderDAORepository;
	
	private String DATABASE_SEEDED = "Success, Database seeded!";
	private String DATABASE_NOT_SEEDED = "Fail, there are already data in the database!";
	
	@Override
	public void run(String... args) throws Exception {

		if(userDAORepository.count() == 0 || productDAORepository.count() == 0) {
			List<User> userTempList = Arrays.asList(
					new User("John", "Doe", "password123", new Date(631148400000L), "123 Main St", "john@example.com", null),
					new User("Jane", "Doe", "password456", new Date(703288800000L), "456 Elm St", "jane@example.com", null),
					new User("Alice", "Smith", "password789", new Date(803599200000L), "789 Oak St", "alice@example.com", null),
					new User("Bob", "Brown", "password101", new Date(591577200000L), "101 Pine St", "bob@example.com", null),
					new User("Charlie", "Davis", "password202", new Date(476406000000L), "202 Maple St", "charlie@example.com", null)			
					);
			
			List<Product> productTempList = Arrays.asList(
            new Product("Laptop", "Dell", "Electronics", 10, false, null),
            new Product("Smartphone", "Samsung", "Electronics", 20, false, null),
            new Product("Headphones", "Sony", "Electronics", 15, true, null),
            new Product("Monitor", "LG", "Electronics", 5, false, null),
            new Product("Keyboard", "Logitech", "Electronics", 25, true, null)
            		);
	
			List<Order> orderTempList = Arrays.asList(
	                new Order(userTempList.get(0), productTempList.get(0), new Date(1725141600000L)),
	                new Order(userTempList.get(1), productTempList.get(1), new Date(1725314400000L)),
	                new Order(userTempList.get(2), productTempList.get(2), new Date(1725487200000L)),
	                new Order(userTempList.get(3), productTempList.get(3), new Date(1725919200000L)),
	                new Order(userTempList.get(4), productTempList.get(4), new Date(1726437600000L))
	                );
			
			userDAORepository.saveAll(userTempList);
			productDAORepository.saveAll(productTempList);
			orderDAORepository.saveAll(orderTempList);
			
			System.out.println(DATABASE_SEEDED);
		} else {
			System.out.println(DATABASE_NOT_SEEDED);
		}
	}
	
}
