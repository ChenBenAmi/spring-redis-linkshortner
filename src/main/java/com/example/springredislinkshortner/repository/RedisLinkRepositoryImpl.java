package com.example.springredislinkshortner.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;

import com.example.springredislinkshortner.pojo.Link;

import reactor.core.publisher.Mono;

@Repository
public class RedisLinkRepositoryImpl implements LinkRepository {

	@Autowired
	private ReactiveRedisOperations<String, String> reactiveRedisOperations;

	@Override
	public Mono<Link> save(Link link) {
		return reactiveRedisOperations.opsForValue().set(link.getKey(), link.getOriginalLink()).map(data -> link);
	}

	@Override
	public Mono<Link> findByKey(String key) {
		return reactiveRedisOperations.opsForValue().get(key).map(result -> new Link(result, key));
	}

}
