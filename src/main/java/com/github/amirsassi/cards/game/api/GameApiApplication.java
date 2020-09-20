package com.github.amirsassi.cards.game.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class to start SpringBoot application
 * @author Amir.Sassi
 *
 */

@SpringBootApplication
public class GameApiApplication {

    public static void main(
        final String[] args) {

        SpringApplication.run(GameApiApplication.class, args);
    }
}
