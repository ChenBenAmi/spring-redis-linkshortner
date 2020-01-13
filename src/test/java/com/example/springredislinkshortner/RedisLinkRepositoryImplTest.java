package com.example.springredislinkshortner;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springredislinkshortner.pojo.Link;
import com.example.springredislinkshortner.repository.RedisLinkRepositoryImpl;

import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisLinkRepositoryImplTest {

	@Autowired
	private RedisLinkRepositoryImpl redisLinkRepositoryImpl;

	@Test
	public void shouldReturnSavedLink() {
		Link link = new Link("https://spring.io", "aaaa22");
		StepVerifier.create(redisLinkRepositoryImpl.save(link)).expectNext(link).verifyComplete();
	}

	@Test
	public void shouldSaveLinkToRedisDatabase() {
		Link link = new Link("https://spring.io", "aaaa22");
		StepVerifier
				.create(redisLinkRepositoryImpl.save(link)
						.flatMap(data -> redisLinkRepositoryImpl.findByKey(link.getKey())))
				.expectNext(link).verifyComplete();
	}

}
