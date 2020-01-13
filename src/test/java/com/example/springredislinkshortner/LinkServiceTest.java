package com.example.springredislinkshortner;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Any;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.example.springredislinkshortner.pojo.*;
import com.example.springredislinkshortner.repository.LinkRepository;
import com.example.springredislinkshortner.service.LinkService;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class LinkServiceTest {

	private LinkRepository linkRepository = mock(LinkRepository.class);
	private LinkService LinkService = new LinkService("http://some-domain.com/", linkRepository);

	@BeforeEach
	public void init() {
		System.out.println(linkRepository);
		when(linkRepository.save(any())).thenAnswer(new Answer<Mono<Link>>() {

			@Override
			public Mono<Link> answer(InvocationOnMock invocation) throws Throwable {
				return Mono.just((Link) invocation.getArguments()[0]);
			}
		});
	}

	@Test
	void shortendLinkFromService() {
		StepVerifier.create(LinkService.shortenedLink("https://spring.io"))
				.expectNextMatches(
						result -> result != null && result.length() > 0 && result.startsWith("http://some-domain.com/"))
				.expectComplete().verify();
	}

}
