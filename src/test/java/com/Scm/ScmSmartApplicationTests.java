package com.Scm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.Scm.Services.EmailService;

@SpringBootTest
class ScmSmartApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService service;

	@Test
	void sendEmailTest() {
		service.sendEmail(
				"testingishan302@gmail.com",
				"Just managing the emails",
				"this is scm project working on email service");
	}

}
