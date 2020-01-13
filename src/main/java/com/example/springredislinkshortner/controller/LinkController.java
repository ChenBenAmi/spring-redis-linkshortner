package com.example.springredislinkshortner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.springredislinkshortner.pojo.CreateLinkRequest;
import com.example.springredislinkshortner.pojo.CreateLinkResponse;
import com.example.springredislinkshortner.service.LinkService;
import reactor.core.publisher.Mono;

@RestController
public class LinkController {

	@Autowired
	private LinkService linkService;

	@PostMapping("/link")
	Mono<CreateLinkResponse> create(@RequestBody CreateLinkRequest request) {
		return linkService.shortenedLink(request.getLink()).map(CreateLinkResponse::new);
	}

	@GetMapping("/{key}")
	Mono<ResponseEntity<Object>> getOriginalLink(@PathVariable String key) {
		return linkService.getOriginalLinkByKey(key)
				.map(link -> ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
						.header("Location", link.getOriginalLink()).build())
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

}
