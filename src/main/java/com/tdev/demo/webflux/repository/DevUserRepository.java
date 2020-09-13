package com.tdev.demo.webflux.repository;

import com.tdev.demo.webflux.domain.DevUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface DevUserRepository extends ReactiveCrudRepository<DevUser, Integer> {

    Mono<DevUser> findByUsername(String username);
}
