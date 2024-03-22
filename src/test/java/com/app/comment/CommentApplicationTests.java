package com.app.comment;

import com.app.comment.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommentApplicationTests {

	@Autowired
	private UserRepo userRepo;

	@Test
	void contextLoads() {
	}

	@Test
	public void repoTests() {
		String className = this.userRepo.getClass().getName();
		String packageName = this.userRepo.getClass().getPackageName();
		System.out.println(className);
		System.out.println(packageName);
	}

}
