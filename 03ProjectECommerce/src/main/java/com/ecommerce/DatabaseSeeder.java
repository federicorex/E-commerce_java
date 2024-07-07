package com.ecommerce;

import org.springframework.boot.CommandLineRunner;

import com.ecommerce.dal.OrderDAORepository;
import com.ecommerce.dal.ProductDAORepository;
import com.ecommerce.dal.UserDAORepository;
import com.ecommerce.entities.Order;
import com.ecommerce.entities.Product;
import com.ecommerce.entities.User;

public class DatabaseSeeder implements CommandLineRunner {

	private UserDAORepository userDAORepository;
	private ProductDAORepository productDAORepository;
	private OrderDAORepository orderDAORepository;
	
	private String DATABASE_SEEDED = "Success, Database seeded!";
	private String DATABASE_NOT_SEEDED = "Fail, there are already data in the database!";
	
	@Override
	public void run(String... args) throws Exception {

		if(userDAORepository.count() == 0 || productDAORepository.count() == 0) {
			User user1 = new User(null, null, null, null, null, null, null);
			User user2 = new User(null, null, null, null, null, null, null);
			User user3 = new User(null, null, null, null, null, null, null);
			User user4 = new User(null, null, null, null, null, null, null);
			User user5 = new User(null, null, null, null, null, null, null);
			Product product1 = new Product(null, null, null, 0, false, null);
			Product product2 = new Product(null, null, null, 0, false, null);
			Product product3 = new Product(null, null, null, 0, false, null);
			Product product4 = new Product(null, null, null, 0, false, null);
			Product product5 = new Product(null, null, null, 0, false, null);
			Order order1 = new Order(user1, product1);
			Order order2 = new Order(user2, product2);
			Order order3 = new Order(user3, product3);
			Order order4 = new Order(user4, product4);
			Order order5 = new Order(user5, product5);
			
			userDAORepository.save(user1);
			userDAORepository.save(user2);
			userDAORepository.save(user3);
			userDAORepository.save(user4);
			userDAORepository.save(user5);
			productDAORepository.save(product1);
			productDAORepository.save(product2);
			productDAORepository.save(product3);
			productDAORepository.save(product4);
			productDAORepository.save(product5);
			orderDAORepository.save(order1);
			orderDAORepository.save(order2);
			orderDAORepository.save(order3);
			orderDAORepository.save(order4);
			orderDAORepository.save(order5);
			
			System.out.println(DATABASE_SEEDED);
		} else {
			System.out.println(DATABASE_NOT_SEEDED);
		}
		
	}
	
}
