package org.ntnu.idi.idatt2105.project;

import org.ntnu.idi.idatt2105.project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class MyRepositoryTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testMyMethod() {
		// perform database operations via myRepository
		// assert results
	}
}
