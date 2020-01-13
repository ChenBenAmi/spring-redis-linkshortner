package com.example.springredislinkshortner.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.springredislinkshortner.pojo.Link;
import com.example.springredislinkshortner.repository.LinkRepository;

import reactor.core.publisher.Mono;

@Service
public class LinkService {

	private final String baseUrl;

	private LinkRepository linkRepository;

	public LinkService(@Value("${app.baseUrl}") String baseUrl,LinkRepository linkRepository) {
		this.baseUrl = baseUrl;
		this.linkRepository = linkRepository;
	}

	public Mono<String> shortenedLink(String link) {
		String randomKey = RandomStringUtils.randomAlphabetic(6);

		// save to db
		return linkRepository.save(new Link(link, randomKey)).map(result -> baseUrl + result.getKey());

	}

	public Mono<Link> getOriginalLinkByKey(String key) {
		return linkRepository.findByKey(key);
	}

}
