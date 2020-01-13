package com.example.springredislinkshortner.repository;

import org.springframework.stereotype.Repository;
import com.example.springredislinkshortner.pojo.Link;
import reactor.core.publisher.Mono;

@Repository
public interface LinkRepository {

	Mono<Link> save(Link link);
	
	Mono<Link> findByKey(String key);
}
