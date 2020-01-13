package com.example.springredislinkshortner;

import static org.junit.Assert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.springredislinkshortner.controller.LinkController;
import com.example.springredislinkshortner.pojo.Link;
import com.example.springredislinkshortner.service.LinkService;
import static org.mockito.Mockito.when;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = LinkController.class)
public class LinkControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private LinkService linkService;

	@Test
	public void shouldShortLink() {
		when(linkService.shortenedLink("https://spring.io")).thenReturn(Mono.just("http://localhost:8080//aass2211"));
		webTestClient.post().uri("/link").contentType(MediaType.APPLICATION_JSON)
				.bodyValue("{\"link\":\"https://spring.io\"}").exchange().expectStatus().is2xxSuccessful().expectBody()
				.jsonPath("$.shortenedLink").value(val -> assertThat(val).isEqualTo("http://localhost:8080//aass2211"));
	}

	@Test
	public void shouldReturnSavedLinkByKey() {
		when(linkService.getOriginalLinkByKey("aaaa123"))
				.thenReturn(Mono.just(new Link("https://spring.io", "aaaa123")));
		webTestClient.get().uri("/aaaa123").exchange().expectStatus().isPermanentRedirect().expectHeader()
				.value("Location", location -> assertThat(location).isEqualTo("https://spring.io"));
	}

}
