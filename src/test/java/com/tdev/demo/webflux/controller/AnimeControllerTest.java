package com.tdev.demo.webflux.controller;

import com.tdev.demo.webflux.domain.Anime;
import com.tdev.demo.webflux.service.AnimeService;
import com.tdev.demo.webflux.util.AnimeCreator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;


@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks
    private AnimeController animeController;

    @Mock
    private AnimeService animeServiceMock;

    private final Anime anime = AnimeCreator.createValidAnime();

    @BeforeAll
    public static void blockHoundSetub() {
        BlockHound.install();
    }

    @BeforeEach
    public void setup() {
        BDDMockito.when(animeServiceMock.findAll())
                .thenReturn(Flux.just(anime));

//        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyInt()))
//                .thenReturn(Mono.just(anime));
//
//        BDDMockito.when(animeRepositoryMock.save(AnimeCreator.createAnimeToBeSaved()))
//                .thenReturn(Mono.just(anime));
//
//        BDDMockito.when(animeRepositoryMock.delete(ArgumentMatchers.any(Anime.class)))
//                .thenReturn(Mono.empty());
//
//        BDDMockito.when(animeRepositoryMock.save(AnimeCreator.createValidAnime()))
//                .thenReturn(Mono.just(anime));
    }

    @Test
    public void blockHoundWorks() {
        try {
            FutureTask<?> task = new FutureTask<>(() -> {
                Thread.sleep(0);
                return "";
            });
            Schedulers.parallel().schedule(task);

            task.get(10, TimeUnit.SECONDS);
            Assertions.fail("should fail");
        } catch (Exception e) {
            Assertions.assertTrue(e.getCause() instanceof BlockingOperationError);
        }
    }

    @Test
    @DisplayName("findAll returns a flux of anime")
    public void findAll_ReturnsFluxOfAnime_WhenSuccessful() {
        StepVerifier
                .create(animeServiceMock.findAll())
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();
    }

}