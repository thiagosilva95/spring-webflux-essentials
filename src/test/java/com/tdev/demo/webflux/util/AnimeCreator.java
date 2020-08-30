package com.tdev.demo.webflux.util;

import com.tdev.demo.webflux.domain.Anime;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved() {
        return Anime.builder()
                .name("Tensei Shhitara Slime Datta Ken")
                .build();
    }

    public static Anime createValidAnime() {
        return Anime.builder()
                .id(1)
                .name("Tensei Shhitara Slime Datta Ken")
                .build();
    }

    public static Anime createValidUpdatedAnime() {
        return Anime.builder()
                .id(1)
                .name("Tensei Shhitara Slime Datta Ken 2")
                .build();
    }

}
